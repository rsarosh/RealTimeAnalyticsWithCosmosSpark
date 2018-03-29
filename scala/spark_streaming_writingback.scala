import com.microsoft.azure.cosmosdb.spark._
import com.microsoft.azure.cosmosdb.spark.schema._
import com.microsoft.azure.cosmosdb.spark.config.Config
import org.codehaus.jackson.map.ObjectMapper
import com.microsoft.azure.cosmosdb.spark.streaming._
import java.time._


val sourceConfigMap = Map(
"Endpoint" -> "https://rafat-metric-demo.documents.azure.com:443/",
"Masterkey" -> "Zgwy9oQYyglYQ1iQ0ZA==",
"Database" -> "IoT",
"Collection" -> "IoT",
"ConnectionMode" -> "Gateway",
"ChangeFeedCheckpointLocation" -> "checkpointlocation2",
"changefeedqueryname" -> "Streaming Query from Cosmos DB Change Feed Internal Count"
)


val sinkConfigMap = Map (
    "Endpoint" -> "https://rafat-metric-demo.documents.azure.com:443/",
    "Masterkey" -> "Zgwy9oQglYQ1iQ0ZA==",
    "Database" -> "IoT",
    "preferredRegions" -> "Central US;East US2",
    "Collection" -> "IoT6", 
    "Upsert" -> "true",
    "checkpointLocation" -> "streamingcheckpointlocation"
    )

// Start reading change feed as a stream
var streamData = spark.readStream.format(classOf[CosmosDBSourceProvider].getName).options(sourceConfigMap).load()

val streamingQueryWriter = streamData.withColumn("state", streamData.col("id").substr(0, 0)).
    groupBy("state").count().
    writeStream.format(classOf[CosmosDBSinkProvider].getName).
    outputMode("complete").options(sinkConfigMap).
    option("checkpointLocation", "streamingcheckpointlocation")
                  
var streamingQuery = streamingQueryWriter.start()


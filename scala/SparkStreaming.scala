import com.microsoft.azure.cosmosdb.spark._
import com.microsoft.azure.cosmosdb.spark.schema._
import com.microsoft.azure.cosmosdb.spark.config.Config
import org.codehaus.jackson.map.ObjectMapper
import com.microsoft.azure.cosmosdb.spark.streaming._
import java.time._
import spark.implicits._
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

val sourceConfigMap = Map(
"Endpoint" -> "https://rafat-metric-demo.documents.azure.com:443/",
"Masterkey" -> "Zgwy9YQ1iQ0ZA==",
"Database" -> "IoT",
"Collection" -> "IoT",
"ConnectionMode" -> "Gateway",
"ChangeFeedCheckpointLocation" -> "checkpointlocation2",
"changefeedqueryname" -> "Streaming Query from Cosmos DB Change Feed Internal Count")

//Define the schema of the JSON files as having the "time" of type TimeStamp and the "temp" field of type String
//val jsonSchema = new StructType().add("_ts", TimestampType).add("state", StringType)

// Start reading change feed as a stream
var streamData = spark.readStream.format(classOf[CosmosDBSourceProvider].getName).options(sourceConfigMap).load()

streamData.
    groupBy("state").count().
    writeStream.outputMode("complete").
    format("console").start()


 /*
streamData.
    withColumn("state", streamData.col("id").substr(0, 0)).
    groupBy(window($"_ts", "10 second", "5 second"),
    $"state").count().
    writeStream.outputMode("complete").
    format("console").start()
 */


/*
streamData.createOrReplaceTempView("IoT")
val count_df = spark.sql("select  state,  count(*) as Count from IoT group by  state")
count_df.writeStream.outputMode("complete").
format("console").start()
 */


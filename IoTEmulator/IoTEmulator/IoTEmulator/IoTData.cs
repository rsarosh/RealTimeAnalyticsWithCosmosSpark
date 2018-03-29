using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace IoTEmulator
{
    public class IoTData
    {
      
        [JsonProperty("iotid")]
        public string iotid;

        //seattle NY SF Chicago London Paris 
        [JsonProperty("state")]
        public string state;

        //Lexus Avalon Camery Rav4 Prius   
        [JsonProperty("model")]
        public string model;

        //low, medium, high -> 1,2,3
        [JsonProperty("engineoil")]
        public string engineoil;

        //red black red grey blue white green
        [JsonProperty("color")]
        public string color;

        
        [JsonProperty("lat")]
        public double lat;

        [JsonProperty("longitude")]
        public double longitude;

        [JsonProperty("carid")]
        public string carid;

        [JsonProperty("temp")]
        public double temp;

        [JsonProperty("timestamp")]
        public long timestamp;

        public static explicit operator IoTData(Microsoft.Azure.Documents.Document doc)
        {
            IoTData _iotData = new IoTData();
            _iotData.iotid = doc.GetPropertyValue<string>("iotid");
            _iotData.temp = doc.GetPropertyValue<double>("temp");
            _iotData.timestamp = doc.GetPropertyValue<long>("timestamp");
            return _iotData;
        }
    }
}

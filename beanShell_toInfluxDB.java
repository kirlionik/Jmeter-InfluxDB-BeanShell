import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

//prepare string for request
result = new StringBuilder();

status = "Failure";
if(sampleResult.isSuccessful())
status = "Success";

result.append("samples,")
.append("tag=")
.append("${myTag}")
.append(",label=")
.append(sampleResult.getSampleLabel())
.append(",status=")
.append(status)
.append(" ")
.append("count=")
.append(sampleResult.getSampleCount())
.append(",threads=")
.append(sampleResult.getAllThreads())
.append(",duration=")
.append(sampleResult.getTime())
.append(",responsecode=")
.append(sampleResult.getResponseCode())
.append( " ")
.append(sampleResult.getTimeStamp())
.append("000000");

//Escape the string values before posting the data
String escapeValue(String val){
val = val.replaceAll(",", "\\\\,")
.replaceAll(" ", "\\\\ ")
.replaceAll("=", "\\\\=")
.trim();
return val;
}

//Post the result to influxDB 
void PostMeasurement(String metric){
httpclient = new DefaultHttpClient(new BasicHttpParams());
httpPost = new HttpPost(); 
httpPost.setURI(new URI("http://localhost:8086/write?db=vtm_jmeter"));
httpPost.setEntity(new StringEntity(metric));
HttpResponse response = httpclient.execute(httpPost);
EntityUtils.consumeQuietly(response.getEntity());
}

//To call the function which posts the data
PostMeasurement(result.toString());

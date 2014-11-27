import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.HttpResponse;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("deprecation")
public class ISOString {
 
    private String token;
    private String apiURL;
    private static int interval = 0;
    private static String date = null;
    private static int year = 0;
    private static int month = 0;
    private static int days = 0;
    private static int hours = 0;
    private static int minutes = 0;
    private static int seconds = 0;
    private static String finalISO = null;
    
    public ISOString(String token, String apiURL) {        
        this.apiURL = apiURL;
        this.token = token;
    }
    
    public void sendHTTPPOST()
    {
    	 try {
             @SuppressWarnings("deprecation")
 			HttpClient send = new DefaultHttpClient();        
            HttpPost post = new HttpPost(this.apiURL);        
  
             post.setEntity(new StringEntity("{\"token\":\"" + this.token + "\" , \"datestamp\":\"" + finalISO + "\"}", ContentType.create("application/json")));
  
             HttpResponse response = send.execute(post);
             System.out.println("Status: "+ response.getStatusLine());
         }
         catch(IOException e) {
             System.out.println(e);
         }   
    	
    }
 
    public void makeHTTPPOSTRequest() {
        try {
            @SuppressWarnings("deprecation")
			HttpClient send = new DefaultHttpClient();        
            HttpPost post = new HttpPost(this.apiURL);        
 
            post.setEntity(new StringEntity("{\"token\":\"" + this.token + "\"}", ContentType.create("application/json")));
 
            HttpResponse response = send.execute(post);
          
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            while ((line = rd.readLine()) != null) {
               //Parse our JSON response               
               JSONParser dateParsing = new JSONParser();
               JSONObject result = (JSONObject)dateParsing.parse(line);
             
              System.out.println(result);
              parseJson(result);
            }
        }
        catch(ParseException e) {
            System.out.println(e);
        }
        catch(IOException e) {
            System.out.println(e);
        }     
        parseISO(date);
    }    
    
    public static void parseJson(JSONObject jsonObject) throws ParseException {

        Set<Object> set = jsonObject.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (jsonObject.get(obj) instanceof JSONObject)
            	parseJson((JSONObject) jsonObject.get(obj));
            else
            {
            	System.out.println(obj.toString() + "\t" + jsonObject.get(obj));
            	if(obj.toString().equals("interval"))
            	{
            		interval = Integer.parseInt(jsonObject.get(obj).toString());
            	}
            	else if(obj.toString().equals("datestamp"))
            	{
            		date = jsonObject.get(obj).toString();
            	}
            }
          }
        }
 
	public static void updateDate()
    {
		SimpleDateFormat createDate = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
		Calendar calendar = new GregorianCalendar(year,month - 1,days,hours,minutes,seconds);
				
		System.out.println("Date : " + createDate.format(calendar.getTime()));
		calendar.add(Calendar.SECOND, interval);
				
		setFinalISO(createDate.format(calendar.getTime()));
		System.out.println("Date : " + createDate.format(calendar.getTime()));
		System.out.println(getFinalISO());
				
		SimpleDateFormat finalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = finalDate.format(calendar.getTime()) + ".000Z" ;
		System.out.println(date); 
		date = date.substring(0, 10) + "T" + date.substring(11,24);
		System.out.println(date); 
    }
	
	public static void parseISO(String dateStamp)
	{
		year = Integer.parseInt(dateStamp.substring(0,4));
		month = Integer.parseInt(dateStamp.substring(5, 7));
		days = Integer.parseInt(dateStamp.substring(8, 10));
		hours = Integer.parseInt(dateStamp.substring(11, 13));
		minutes = Integer.parseInt(dateStamp.substring(14,16));
		seconds = Integer.parseInt(dateStamp.substring(17, 19));
		
		updateDate();
	}

	public static int getInterval() {
		return interval;
	}

	public static void setInterval(int interval) {
		ISOString.interval = interval;
	}

	public static String getDate() {
		return date;
	}

	public static void setDate(String date) {
		ISOString.date = date;
	}

	public static String getFinalISO() {
		return finalISO;
	}

	public static void setFinalISO(String finalISO) {
		ISOString.finalISO = finalISO;
	}
}



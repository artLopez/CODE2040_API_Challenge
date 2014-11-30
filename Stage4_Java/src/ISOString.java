/**
 * Title: ISOString.java
 * Abstract: The ISOString class 
 * acquires the interval and datestamp.
 * Afterwards, the datestamp is parsed
 * and adds the interval in seconds using
 * the Class Calendar.
 * 
 * 
 * Author: Arturo Lopez
 */

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
	//Variable Declaration
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
    
    //Constructor for the ISOString class
    public ISOString(String token, String apiURL) {        
        this.apiURL = apiURL;
        this.token = token;
    }
    //sends a post request with the updated datestamp
    public void sendHTTPPOST()
    {
    	 try {
             @SuppressWarnings("deprecation")
 			HttpClient send = new DefaultHttpClient();        
            HttpPost post = new HttpPost("http://challenge.code2040.org/api/validatetime");        
            //post sent with the dictionary {"token" : myToken, "datestamp": finalISO(datestamp}
             post.setEntity(new StringEntity("{\"token\":\"" + token + "\" , \"datestamp\":\"" + finalISO + "\"}", ContentType.create("application/json")));
             
             //response for the post request
             HttpResponse response = send.execute(post);
             //prints out the status code from the post request
             System.out.println("Status: "+ response.getStatusLine().getStatusCode());
         }
         catch(IOException e) {
             System.out.println(e);
         }   
    	
    }
    //sends a post request to acquire the datestamp and interval
    public void makeHTTPPOSTRequest() {
        try {
            @SuppressWarnings("deprecation")
			HttpClient send = new DefaultHttpClient();        
            HttpPost post = new HttpPost(this.apiURL);        
            //post request sending the token to acquire the datestamp and interval
            post.setEntity(new StringEntity("{\"token\":\"" + token + "\"}", ContentType.create("application/json")));
 
            HttpResponse response = send.execute(post);
            
            //bufferedReader to parse the JSON dictionary
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line = "";
            //reading the JSON dictionary
            while ((line = rd.readLine()) != null) {               
               JSONParser dateParsing = new JSONParser();
               JSONObject result = (JSONObject)dateParsing.parse(line);
              //print the JSON dictionary
              System.out.println(result);
              //parse the dictionary
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
    	//iterating through the dictionary to acquire the datestamo and interval
        Set<Object> set = jsonObject.keySet();
        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (jsonObject.get(obj) instanceof JSONObject)
            	parseJson((JSONObject) jsonObject.get(obj));
            else
            {
            	//print out the keys that are getting parsed 
            	System.out.println(obj.toString() + "\t" + jsonObject.get(obj));
            	//checks the keys by comparing string to store the data in the right variable
            	if(obj.toString().equals("interval"))
            		interval = Integer.parseInt(jsonObject.get(obj).toString());
            	else if(obj.toString().equals("datestamp"))
            		date = jsonObject.get(obj).toString();
            }
          }
        }
 
	public static void updateDate()
    {
		//create a Calendar instance given the year, month,days,hours,minutes and seconds
		SimpleDateFormat createDate = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
		Calendar calendar = new GregorianCalendar(year,month - 1,days,hours,minutes,seconds);
				
		//print out the current date
		System.out.println("Date : " + createDate.format(calendar.getTime()));
		
		//upgrade the current date with the interval to acquire the final datestamp
		calendar.add(Calendar.SECOND, interval);
		
		//input the the new date into the finalISO String
		setFinalISO(createDate.format(calendar.getTime()));
		//print out updated date
		System.out.println("Date : " + createDate.format(calendar.getTime()));
		//print out finalISO String
		System.out.println(getFinalISO());
				
		//Updating the final datestamp into ISO 8601 format
		SimpleDateFormat finalDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = finalDate.format(calendar.getTime()) + ".000Z" ;
		date = date.substring(0, 10) + "T" + date.substring(11,24);
		//print date
		System.out.println(date); 
    }
	
	public static void parseISO(String dateStamp)
	{
		//parsing the datestamp to seperate years,month,days,hours,minutes,seconds
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



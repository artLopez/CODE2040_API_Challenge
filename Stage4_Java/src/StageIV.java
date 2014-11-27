/**
 * Title: StageIV.java
 * Abstract: Create an instance 
 * of the ISOString class with
 * parameters containing the token
 * and URL.Then calls post request
 * to acquire the datestamp and the
 * interval to add to the datestamp.
 * 
 * 
 * Author: Arturo Lopez
 * ID:2412
 * Date: October 1, 2014
 */

public class StageIV {
	public static void main(String[] args) {
		ISOString test = new ISOString("dLcGAO9jnJ","http://challenge.code2040.org/api/time");
		test.makeHTTPPOSTRequest();
		//Sends back the updated datestamp
		test.sendHTTPPOST();
	}

}

public class StageIV {
	public static void main(String[] args) {
		ISOString test = new ISOString("dLcGAO9jnJ","http://challenge.code2040.org/api/time");
		test.makeHTTPPOSTRequest();
		test.sendHTTPPOST();
	}

}

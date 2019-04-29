package Crypto;

public class Util {
	public String str2hex(String str) {
		String hex = "";
		
		for(int i = 0; i<str.length(); i++){
			int ch=(int)str.charAt( i );
			hex += String.format("%02x",ch);
		}
		return hex;
	}
	public String hex2str(String hex) {
		String str = "";

		int intVal = Integer.parseInt(hex, 16);
		char charVal = (char) intVal;
		str += charVal;
		return str;
	}
}

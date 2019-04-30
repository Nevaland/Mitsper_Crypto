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
//		System.out.println("TEST: "+hex);
		for(int i = 0; i<hex.length(); i+=2){
			int intVal = Integer.parseInt(hex.substring(i, i+2), 16);
			char charVal = (char) intVal;
			str += charVal;
		}
//		System.out.println("TEST: "+str);
		return str;
	}
}

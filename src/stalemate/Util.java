package stalemate;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

public class Util {	
	public String str2hex(String str) {
//		return str;
//    byte[] buffer;
//	buffer = str.getBytes();
//	String res = "";
//    String token = "";
//
//    for( int i=0; i < buffer.length; i++) {
//        token = Integer.toHexString( buffer[i] );
//
//        if(token.length() > 2) {
//            token = token.substring( token.length() - 2 );
//        }
//        else {
//            for(int j = 0 ; j < 2 - token.length(); j++) {
//                token = "0" + token;
//            }
//        }
//        res += token;
//    }
//	System.out.println("STR2HEX-"+res); // Debug
//
//    return res;
		
		String hex = "";
		for(int i = 0; i<str.length(); i++){
			int ch=(int)str.charAt( i );
			hex += String.format("%02x",ch);
		}
		return hex;
	}
	
	public String hex2str(String hex) {
//		return hex;
//		System.out.println("HEX2STR 1-"+hex); // Debug
//		ByteBuffer buff = ByteBuffer.allocate(hex.length()/2);
//		for (int i = 0; i < hex.length(); i+=2) {
//		    buff.put((byte)Integer.parseInt(hex.substring(i, i+2), 16));
//		}
//		buff.rewind();
//		Charset cs = Charset.forName("UTF-8");
//		CharBuffer cb = cs.decode(buff);
//		System.out.println("HEX2STR 2-"+cb.toString()); // Debug
//		return cb.toString();

		String str = "";
		for(int i = 0; i<hex.length(); i+=2){
			int intVal = Integer.parseInt(hex.substring(i, i+2), 16);
			char charVal = (char) intVal;
			str += charVal;
		}
		return str;
	}
}

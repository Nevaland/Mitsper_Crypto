package checkmate;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

import stalemate.Mitsper;

public class Main {
	public static void main(String[] args) throws UnsupportedEncodingException {
//		RSAManager manager = new RSAManager();
//		RSABlocking block = manager.generateBlock();
		Scanner sc = new Scanner(System.in);

		String key = "Test";
		String Encrypted = "";
		String Decrypted = "";
		
//		Encrypted = manager.encrypt(data, block);
//		System.out.println("Encrypted:" + Encrypted);
//		
//		Decrypted = manager.decrypt(block);
//		System.out.println("Decrypted:" + Decrypted);
//
////		String input = "데이터";
////		StringBuilder sb = new StringBuilder(input.length());
////		for (int i = 0; i < input.length(); i++) {
////		    char ch = input.charAt(i);
////		    if (ch <= 0xFF) {
////		        sb.append(ch);
////		    }
////		}
////
////		byte[] ascii = sb.toString().getBytes("ISO-8859-1"); // aka LATIN-1
////		String output = new String(ascii, StandardCharsets.UTF_8);
////			
////		
////		System.out.println("["+input+"]");
////		System.out.println("["+output+"]");
		
//		String name = new String("�� S̮��є�4��;��T�;�����Fo�w");
//        byte[] buffer;
//		buffer = name.getBytes();
//		String res = "";
//        String token = "";
// 
//        for( int i=0; i < buffer.length; i++)
//        {
//            token = Integer.toHexString( buffer[i] );
// 
//            if(token.length() > 2)
//            {
//                token = token.substring( token.length() - 2 );
//            }
//            else
//            {
//                for(int j = 0 ; j < 2 - token.length(); j++)
//                {
//                    token = "0" + token;
//                }
//            }
//            res += token;
//        }
		
//        System.out.println("TEST1: "+res);
		
//		String hex = "eece2053ccae8fbed1949b34f801f93be4f254ba173ba7a39aa9d2466f801e77"; // "6174656ec3a7c3a36f";                                  // AAA
//		ByteBuffer buff = ByteBuffer.allocate(hex.length()/2);
//		for (int i = 0; i < hex.length(); i+=2) {
//		    buff.put((byte)Integer.parseInt(hex.substring(i, i+2), 16));
//		}
//		buff.rewind();
//		Charset cs = Charset.forName("UTF-8");                              // BBB
//		CharBuffer cb = cs.decode(buff);                                    // BBB
//		System.out.println("TEST2: "+cb.toString());                                  // CCC
		
		
		
		
		
		
		String content = "데이터";
		System.out.println(content);
		/////////
        byte[] buffer;
		buffer = content.getBytes();
		String res = "";
        String token = "";
 
        for( int i=0; i < buffer.length; i++)
        {
            token = Integer.toHexString( buffer[i] );
 
            if(token.length() > 2)
            {
                token = token.substring( token.length() - 2 );
            }
            else
            {
                for(int j = 0 ; j < 2 - token.length(); j++)
                {
                    token = "0" + token;
                }
            }
            res += token;
        }
        content = res;
		///////
        
        Mitsper mc = new Mitsper(key,content);

    	mc.setContents(content);
    	Encrypted = mc.encrypt();
		System.out.println("Encrypted:" + Encrypted);

		mc.setContents(Encrypted);
		Decrypted = mc.decrypt();
		///
		String hex = Decrypted; // "6174656ec3a7c3a36f";                                  // AAA
		ByteBuffer buff = ByteBuffer.allocate(hex.length()/2);
		for (int i = 0; i < hex.length(); i+=2) {
		    buff.put((byte)Integer.parseInt(hex.substring(i, i+2), 16));
		}
		buff.rewind();
		Charset cs = Charset.forName("UTF-8");                              // BBB
		CharBuffer cb = cs.decode(buff);                                    // BBB
		Decrypted = cb.toString();
		///
		System.out.println("Decrypted:" + Decrypted);
		
	}
}

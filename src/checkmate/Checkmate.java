package checkmate;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import safebox.RSABlocking;
import safebox.RSAManager;
import stalemate.Mitsper;

public class Checkmate {
	private String contents, key;
	private RSAManager manager = new RSAManager();
	private RSABlocking block;
	
	public Checkmate() {
	}

	public void genSafebox() {
		 this.block = manager.generateBlock();
	}
	public String getPubkey() {
		return block.toString();
	}
	public String pubDecrypt(String pub_key, String cipher) {
	
		return "";
	}
	public String symDecrypt(String sym_key, String cipher) {
		String Decrypted = "";
        Mitsper mc = new Mitsper(sym_key, cipher);

		Decrypted = mc.decrypt();

		String hex = Decrypted;
		ByteBuffer buff = ByteBuffer.allocate(hex.length()/2);
		for (int i = 0; i < hex.length(); i+=2) {
		    buff.put((byte)Integer.parseInt(hex.substring(i, i+2), 16));
		}
		buff.rewind();
		Charset cs = Charset.forName("UTF-8");
		CharBuffer cb = cs.decode(buff);
		Decrypted = cb.toString();

		return Decrypted;
	}
	public String pubEncrypt(String pub_key, String data) {
		
		return "";
	}
	public String symEncrypt(String sym_key, String data) {
		String Encrypted = "";
		String content = data;

        byte[] buffer;
		buffer = content.getBytes();
		String res = "";
        String token = "";
 
        for( int i=0; i < buffer.length; i++) {
            token = Integer.toHexString( buffer[i] );
 
            if(token.length() > 2){
                token = token.substring( token.length() - 2 );
            }
            else{
                for(int j = 0 ; j < 2 - token.length(); j++){
                    token = "0" + token;
                }
            }
            res += token;
        }
        content = res;
        
        Mitsper mc = new Mitsper(sym_key, content);

    	mc.setContents(content);
    	Encrypted = mc.encrypt();
		return Encrypted;	
	}
	
}

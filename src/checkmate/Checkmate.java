package checkmate;

import safebox.RSABlocking;
import safebox.RSAManager;

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
//	public String pubDecrypt(String cipher) {
//	
//		return "";
//	}
//	public String symDecrypt(String cipher) {
//		
//		return "";
//	}
//	public String pubEncrypt(String pub_key, String data) {
//		
//		return "";
//	}
//	public String symEncrypt(String sym_key, String data) {
//		
//		return "";	
//	}
	
}

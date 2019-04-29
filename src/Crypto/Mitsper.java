package Crypto;

public class Mitsper {
	final int BLOCK_SIZE = 64;
	private String contents, key;
	private int len;
	private ChessObject c_object;
	private Util util = new Util();
	
	public Mitsper(String key, String contents) {
		this.contents = util.str2hex(contents);
		this.len = getLen(contents);
		padding();
		this.key = key;
		
	}
	private int getLen(String contents) {
		return contents.length();
	}
	private void padding() {
		// Padding
		if(len%BLOCK_SIZE != 0) {
			for(int i=len; i<(len/BLOCK_SIZE)*BLOCK_SIZE+BLOCK_SIZE; i++) this.contents += "00";
			this.len = getLen(contents);
		}
	}
	private String subContent(String contents, int index) {
		// Substring
		return contents.substring(index*BLOCK_SIZE, index*BLOCK_SIZE+BLOCK_SIZE); // modify
	}

	public void setKey(String key) {
		this.key = key;
	}
	public void setContents(String contents) {
		this.len = getLen(contents);
		this.contents = util.str2hex(contents);
		padding();
	}
	
	public String encrypt() {
		String content, encrypted="";
		for(int i=0; i<len/BLOCK_SIZE; i++) {
			content = subContent(this.contents,i);
			this.c_object = new ChessObject(key, content);

//			// test
			this.c_object.test();
//			System.out.print("[test]"+len); String test = content;
//			for(int j=0; j<test.length();j+=2) {if(j%8==0) System.out.println(); System.out.print(test.substring(j, j+2)+" ");}
			
			// Encryption
			c_object.checkmate();	// sample

			encrypted+="";
		}
		return encrypted;
	}
	public String decrypt() {
		String decrypted="";
		for(int i=0; i<5; i++) {
			c_object.checkmate();	// sample
			decrypted+="";
		}
		return "TEST";	// sample
	}
}

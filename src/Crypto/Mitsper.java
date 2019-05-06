package Crypto;

public class Mitsper {
	private final int BLOCK_SIZE = 64;
	private String contents, key;
	private int len;
	private ChessObject c_object;
	private Util util = new Util();

	private final int FORWARD = 0;
	private final int BACKWARD = 1;
	
	public Mitsper(String key, String contents) {
		this.contents = util.str2hex(contents);
		this.len = getLen(this.contents);
		padding();
		this.key = key;
	}

	private int getLen(String contents) {
//		System.out.println("[GET LEN]" + contents + " -> " + contents.length());
		return contents.length();
	}
	private void padding() {
		// Padding
		if(len%BLOCK_SIZE != 0) {
//			System.out.println("[PADDING]" + this.len);
			for(int i=len; i<(len/BLOCK_SIZE)*BLOCK_SIZE+BLOCK_SIZE; i+=2) this.contents += "00";
			this.len = getLen(this.contents);
		}
//		System.out.println("[padding]" + this.contents);
	}
	private String subContent(String contents, int index) {
		// Substring
		return contents.substring(index*BLOCK_SIZE, index*BLOCK_SIZE+BLOCK_SIZE); // modify
	}

	public void setKey(String key) {
		this.key = key;
	}
	public void setContents(String contents) {
		this.contents = util.str2hex(contents);
		this.len = getLen(this.contents);
		padding();
	}
	
	public String encrypt() {
		String content, encrypted="";
		for(int i=0; i<len/BLOCK_SIZE; i++) {
//			System.out.println("[encrypt]"+len); // String test = content;
			content = subContent(this.contents,i);
			this.c_object = new ChessObject(key, content);

//			// test
//			this.c_object.test();
//			System.out.println("[len]"+len); // String test = content;
//			for(int j=0; j<test.length();j+=2) {if(j%8==0) System.out.println(); System.out.print(test.substring(j, j+2)+" ");}
			
//			// Encryption
			c_object.mapPiece(0);
			c_object.checkmate(FORWARD);
			c_object.movePiece();
			c_object.checkmate(FORWARD);
			c_object.switchPiece();
			c_object.checkmate(FORWARD);
			for(int j=1;j<10;j++) {
				c_object.mapPiece(j);
				c_object.movePiece();
				c_object.checkmate(FORWARD);
				c_object.switchPiece();
				c_object.checkmate(FORWARD);
			}
			encrypted += c_object.getBlocks();
		}
		return util.hex2str(encrypted);
	}
	public String decrypt() {
		String content, decrypted="";
		for(int i=0; i<len/BLOCK_SIZE; i++) {
			content = subContent(this.contents,i);
			this.c_object = new ChessObject(key, content);

			// Decryption
			for(int j=9;j>0;j--) {
				c_object.checkmate(BACKWARD);
				c_object.switchPiece();
				c_object.checkmate(BACKWARD);
				c_object.inverseMovePiece();
				c_object.inverseMapPiece(j);
			}
			c_object.checkmate(BACKWARD);
			c_object.switchPiece();
			c_object.checkmate(BACKWARD);
			c_object.inverseMovePiece();
			c_object.checkmate(BACKWARD);
			c_object.inverseMapPiece(0);
			decrypted += c_object.getBlocks();
		}
		return util.hex2str(decrypted);
	}
}

package stalemate;

public class Mitsper {
	private final int BLOCK_SIZE = 64;
	private final int FORWARD = 0;
	private final int BACKWARD = 1;
	
	private String contents, key;
	private int len;
	private ChessObject c_object;
	private Util util = new Util();

	public Mitsper(String key, String contents) {
		this.contents = contents; // util.str2hex(contents);
		this.len = getLen(this.contents);
		padding();
		this.key = key;
	}

	private int getLen(String contents) {
		return contents.length();
	}
	private void padding() {
		// Padding
		if(len%BLOCK_SIZE != 0) {
			for(int i=len; i<(len/BLOCK_SIZE)*BLOCK_SIZE+BLOCK_SIZE; i+=2) this.contents += "00";
			this.len = getLen(this.contents);
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
//		System.out.println("<<<SetContents 1>>> "+contents);
		this.contents = contents; // util.str2hex(contents);
		this.len = getLen(this.contents);
		padding();
//		System.out.println("<<<SetContents 2>>> "+this.contents);
	}
	
	public String encrypt() {
		String content, encrypted="";
		String output_bf = "", output_aft = "";
		for(int i=0; i<len/BLOCK_SIZE; i++) {
			content = subContent(this.contents,i);
			this.c_object = new ChessObject(key, content);

			output_bf += c_object.printBlock("Before"+i);
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

			output_aft += c_object.printBlock("After"+i);
			encrypted += c_object.getBlocks();
		}
		System.out.print(output_bf + output_aft);
		return encrypted; // util.hex2str(encrypted);
	}
	public String decrypt() {
		String content, decrypted="";
		String output_bf = "", output_aft = "";
		for(int i=0; i<len/BLOCK_SIZE; i++) {
			content = subContent(this.contents,i);
			this.c_object = new ChessObject(key, content);

			output_bf += c_object.printBlock("Before"+i);
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

			output_aft += c_object.printBlock("After"+i);
			decrypted += c_object.getBlocks();
		}
		System.out.print(output_bf + output_aft);
		return decrypted; // util.hex2str(decrypted);
	}
}

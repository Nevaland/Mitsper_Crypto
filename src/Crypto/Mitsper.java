package Crypto;

public class Mitsper {
	private ChessObject c_object;
	
	public Mitsper(String key, String content) {
		this.c_object = new ChessObject(key, content);
	}

	public void setKey(String key) {
		this.c_object.setKey(key);
	}
	public void setContent(String content) {
		this.c_object.setBlock(content);
	}
	
	public String encrypt() {
		c_object.checkmate();	// sample
		return "TEST";	// sample
	}
	public String decrypt() {
		c_object.checkmate();	// sample
		return "TEST";	// sample
	}
}

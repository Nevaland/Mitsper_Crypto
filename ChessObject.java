package Crypto;

public class ChessObject {
	private Key key;
	private String[] block = new String[2];
	private Piece[] board = new Piece[256];
	
	public ChessObject(String key, String content) {
		this.key = new Key(key);
		this.setBlock(content);
	}
	public void setBlock(String content) {
		this.block[0] = content;	// sample
		this.block[1] = content;	// sample
	}
	public void setKey(String key) {
		this.key = new Key(key);
	}
	
	private void placing() {
		this.board[0] = new Piece(0,0);	// sample
	}
	
	public void loc2val() {
		
	}
	public void val2loc() {
		
	}
	public void checkmate() {
		
	}
	public void movePice() {
		
	}
	public void switchPiece() {
		
	}
}
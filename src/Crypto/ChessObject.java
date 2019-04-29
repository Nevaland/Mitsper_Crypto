package Crypto;

public class ChessObject {
	private Key key;
	private String[] block = new String[2];
	private Piece[] board = new Piece[256];
	
	public ChessObject(String key, String content) {
		this.key = new Key(key);
		this.setBlock(content);
	}

//	public void setKey(String key) {
//		this.key = new Key(key);
//	}
	private void setBlock(String content) {
		this.block[0] = content.substring(0, 32);
		this.block[1] = content.substring(32, 64);
	}	
	private void placing() {
		this.board[0] = new Piece(0,0);	// sample
	}

	private String addKey(int round, String block) {
		String round_key = key.getKey(round);
		String added_block = "";
		for(int i=0;i<32;i+=2) added_block += String.format("%02x",0xFF & 
				(Integer.parseInt(block.substring(i, i+2),16) + Integer.parseInt(round_key.substring(i, i+2),16))
				%256);
		return added_block;
	}
	private String subKey(int round, String block) {
		String round_key = key.getKey(round);
		String subed_block = "";
		for(int i=0;i<32;i+=2) subed_block += String.format("%02x",0xFF & 
				(Integer.parseInt(block.substring(i, i+2),16) - Integer.parseInt(round_key.substring(i, i+2),16))
				%256);
		return subed_block;
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
	
	public void test() {
		System.out.println("block[0]: "+this.block[0]);
		System.out.println("added:    "+addKey(1,this.block[0]));
		System.out.println("subed:    "+subKey(1,this.block[0]));
	}
}
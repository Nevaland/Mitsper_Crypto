package Crypto;

public class Piece {
	public int index;
	public int chesstype;
	public Piece next_piece;

	public Piece(int index, int chesstype) {
		this.index = index;
		this.chesstype = chesstype;
		this.next_piece = null;
	}
	public Piece(int index, int chesstype, Piece next_piece) {
		this.index = index;
		this.chesstype = chesstype;
		this.next_piece = next_piece;
	}
}

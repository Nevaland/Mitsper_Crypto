package Crypto;

public class ChessObject {
	private final int PWN = 0;
	private final int KNIGHT = 1;
	private final int ROOK = 2;
	private final int BISHOP = 3;
	private final int QUEEN = 4;
	private final int KING = 5;	
	
	private Key key;
	private String[] block = new String[2];
	private Piece[] board = new Piece[256];
	
	public ChessObject(String key, String content) {
		this.key = new Key(key);
		setBlock(content);
		placing();
	}

//	public void setKey(String key) {
//		this.key = new Key(key);
//	}
	private void setBlock(String content) {
		this.block[0] = content.substring(0, 32);
		this.block[1] = content.substring(32, 64);
	}	
	private void placing() {
		for(int i=0; i<32; i+=2) {
			int posit = Integer.parseInt(this.block[1].substring(i, i+2),16);
			if(this.board[posit] != null)
				this.board[posit] = new Piece(i, Integer.parseInt(this.block[0].substring(i, i+2),16)%6,this.board[posit]);
			else
				this.board[posit] = new Piece(i, Integer.parseInt(this.block[0].substring(i, i+2),16)%6);
		}
	}

	public String getBlocks() {
		return this.block[0]+this.block[1];	
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
	private String loc2val(String block) {
		String cbox = this.key.getCBox();
		String maped_block = "";
		int x, y;
		
		for(int i=0; i<32; i+=2) {
			x = Integer.parseInt(block.substring(i, i+1), 16); // need to switch 2 to 1
			y = Integer.parseInt(block.substring(i+1, i+2), 16);
//			System.out.print(x+"-"+y+" ");
			maped_block += cbox.substring(2*16*x+2*y,2*16*x+2*y+2);
		}
//		System.out.println("\n"+cbox.substring(45,47));
		return maped_block;
	}
	private String val2loc(String block) {
		String cbox = this.key.getCBox();
		String maped_block = "";
		int x, y;
		
		for(int i=0; i<32; i+=2) {
			int j;
			for(j=0; j<256; j+=2) 
				if(cbox.substring(j,j+2).equals(block.substring(i,i+2)))
					break;
			y = j % (16*2);
			j -= y;
			y /= 2;
			x = j / (16*2);
			
//			System.out.print(x+"-"+y+" ");
			maped_block += String.format("%01x",x) + String.format("%01x",y);
		}
//		System.out.println("");
		return maped_block;
	}
	
	public void mapPiece(int round) {
		String[] maped_block = new String[2];
		maped_block[0] = addKey(round,this.block[0]);
		maped_block[1] = addKey(round,this.block[1]);

		maped_block[0] = loc2val(maped_block[0]);
		maped_block[1] = val2loc(maped_block[1]);
		
		maped_block[0] = subKey(round,maped_block[0]);
		maped_block[1] = subKey(round,maped_block[1]);
		this.block = maped_block;
	}
	public void checkmate() {
		
		
//		Piece p;
//		for(int i=0;i<256;i++)
//			if(this.board[i]!=null) {
//				p=this.board[i];
//				while(p!=null) { // switch to do while
//								
//					p=p.next_piece;
//				}
//			}	
	}
	public void movePiece() {
		for(int i=0; i<32; i+=2) {
			int chesstype = Integer.parseInt(this.block[0].substring(i, i+2),16) % 6;
			switch(chesstype) {
			case PWN:
//				this.block[1]
				break;
			case KNIGHT:
				
				break;
			case ROOK:
				
				break;
			case BISHOP:
				
				break;
			case QUEEN:
				
				break;
			case KING:
				
				break;
			}
		}
		
//		Piece p;
//		for(int i=0;i<256;i++)
//			if(this.board[i]!=null) {
//				p=this.board[i];
//				while(p!=null) { // switch to do while
//					
//
//					
//					p=p.next_piece;
//				}
//			}	
		
	}
	public void switchPiece() {
		String switched_block="";
		for(int i=0; i<32; i+=2)
			switched_block += String.format("%02x",Integer.parseInt(this.block[0].substring(i, i+2), 16)^Integer.parseInt(this.block[1].substring(i, i+2), 16));
		this.block[0] = switched_block;	
	}
//this.block[0] = this.block[0].substring(0,p.index) + this.block[0].substring(p.index,p.index+2) + this.block[0].substring(p.index+2);

	public void test() {
		System.out.println("block[0]: "+this.block[0]);
		System.out.println("block[1]: "+this.block[1]);
//		Piece p;
//		for(int i=0;i<256;i++)
//			if(this.board[i]!=null) {
//				p=this.board[i];
//				while(p!=null) {
//					System.out.print(this.block[0].substring(p.index,p.index+2)+" ");
//					p=p.next_piece;
//				}
//				System.out.println(String.format("%02x",i));
//			}
//		System.out.println("tests:    ");
		switchPiece();
	}
}
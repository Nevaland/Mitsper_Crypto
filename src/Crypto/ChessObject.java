package Crypto;

public class ChessObject {
	private final int PWN = 0;
	private final int KNIGHT = 1;
	private final int ROOK = 2;
	private final int BISHOP = 3;
	private final int QUEEN = 4;
	private final int KING = 5;	
	
	private final int[] PWN_mov = {0,-1}; // x,y
	private final int[] KNIGHT_mov = {-1,-2};
	private final int[] ROOK_mov = {0,-2};
	private final int[] BISHOP_mov = {-1,-1};
	private final int[] QUEEN_mov = {-2,-2};
//	private final int[] KING_mov = {0,0};	
	
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
	private void placing() { // need to remove or change
		for(int i=0; i<32; i+=2) {
			int posit = Integer.parseInt(this.block[1].substring(i, i+2),16);
			if(this.board[posit] != null)
				this.board[posit] = new Piece(i, Integer.parseInt(this.block[0].substring(i, i+2),16)%6,this.board[posit]);
			else
				this.board[posit] = new Piece(i, Integer.parseInt(this.block[0].substring(i, i+2),16)%6);
		}
	}

	public String getBlocks() { return this.block[0]+this.block[1];	}
	
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
			maped_block += cbox.substring(2*16*x+2*y,2*16*x+2*y+2);
		}
		return maped_block;
	}
	private String val2loc(String block) {
		String cbox = this.key.getCBox();
		String maped_block = "";
		int x, y;

		for(int i=0; i<32; i+=2) {
			int j;
			for(j=0; j<512; j+=2) {
				if(cbox.substring(j,j+2).equals(block.substring(i,i+2)))
					break;
			}
			y = j % (16*2);
			j -= y;
			y /= 2;
			x = j / (16*2);
			
			maped_block += String.format("%01x",x) + String.format("%01x",y);
		}
		return maped_block;
	}
	
	private int[] getPan(int y, int x) {
		int[] pan = {0,0};
		if(y < 8)
			if(x<8) { pan[0]=0; pan[1]=0; }
			else{ pan[0]=0; pan[1]=1; }
		else
			if(x<8) { pan[0]=1; pan[1]=0; }
			else{ pan[0]=1; pan[1]=1; }
		
		return pan;
	}
	private void rotatePan(int p, int n, int dir) {
		int x, y;
		int[] pan, z_posit = {0,0};
		int[] center = {0x3,0x3, 0x3,0xb, 0xb,0x3, 0xb,0xb};
		n %= 4;
		
		for(int m=0; m<n; m++) {
//			System.out.println("Rotate - "+p+":"+n);// Test
			String rotated = "";
			for(int i=0; i<32; i+=2) {
				y = Integer.parseInt(this.block[1].substring(i, i+1),16);
				x = Integer.parseInt(this.block[1].substring(i+1, i+2),16);
				
				// Check Pan
				pan = getPan(y,x);
				if(pan[0]*2+pan[1] != p) {
					rotated += this.block[1].substring(i, i+2);
					continue;
				}
				z_posit[0] = center[p*2];	// y
				z_posit[1] = center[p*2+1];	// x
				if(dir == 0) { // Rotate
					if(y%8 < 4) {
						if(x%8 < 4) {	// 0
//							System.out.println("[0]->[1] - "+y+":"+x);// Test
							int[] n_posit = {z_posit[0], z_posit[1]+1};	// y, x
							int tmp_y;
							tmp_y = n_posit[0] - (z_posit[1]-x);
							x = n_posit[1] + (z_posit[0]-y);
							y = tmp_y;
						}
						else {	// 1
//							System.out.println("[1]->[3] - "+y+":"+x);// Test
							z_posit[1] += 1;	// x
							int[] n_posit = {z_posit[0]+1, z_posit[1]};	// y, x
//							System.out.print("="+z_posit[0]+":"+z_posit[1]);// Test
//							System.out.print("="+n_posit[0]+":"+n_posit[1]);// Test
//							System.out.print("="+(x-z_posit[1])+":"+(z_posit[0]-y));// Test
							int tmp_y;
							tmp_y = n_posit[0] + (x-z_posit[1]);
							x = n_posit[1] + (z_posit[0]-y);
							y = tmp_y;
//							System.out.println("="+y+":"+x);// Test
						}
					}
					else {
						if(x%8 < 4) {	// 2
//							System.out.println("[2]->[0] - "+y+":"+x);// Test
							z_posit[0] += 1;	// y
							int[] n_posit = {z_posit[0]-1, z_posit[1]};	// y, x
							int tmp_y;
							tmp_y = n_posit[0] - (z_posit[1]-x);
							x = n_posit[1] - (y-z_posit[0]);
							y = tmp_y;
						}
						else {	// 3
//							System.out.println("[3]->[2] - "+y+":"+x);// Test
							z_posit[0] += 1;	// y
							z_posit[1] += 1;	// x
							int[] n_posit = {z_posit[0], z_posit[1]-1};	// y, x
							int tmp_y;
							tmp_y = n_posit[0] + (x-z_posit[1]);
							x = n_posit[1] - (y-z_posit[0]);
							y = tmp_y;
						}
					}
					rotated += String.format("%x", y & 0xf);
					rotated += String.format("%x", x & 0xf);
				}
				else {	// Inverse Rotate
					if(y%8 < 4) {
						if(x%8 < 4) {	// 0 -> 2
							int[] n_posit = {z_posit[0]+1, z_posit[1]};	// y, x
							int tmp_y;
							tmp_y = n_posit[0] + (z_posit[1]-x);
							x = n_posit[1] - (z_posit[0]-y);
							y = tmp_y;
						}
						else {	// 1 -> 0
							z_posit[1] += 1;	// x
							int[] n_posit = {z_posit[0], z_posit[1]-1};	// y, x
							int tmp_y;
							tmp_y = n_posit[0] - (x-z_posit[1]);
							x = n_posit[1] - (z_posit[0]-y);
							y = tmp_y;
						}
					}
					else {
						if(x%8 < 4) {	// 2 -> 3
							z_posit[0] += 1;	// y
							int[] n_posit = {z_posit[0], z_posit[1]+1};	// y, x
							int tmp_y;
							tmp_y = n_posit[0] + (z_posit[1]-x);
							x = n_posit[1] + (y-z_posit[0]);
							y = tmp_y;
						}
						else {	// 3 -> 1
//							System.out.println("[3]->[1] - "+y+":"+x);// Test
							z_posit[0] += 1;	// y
							z_posit[1] += 1;	// x
							int[] n_posit = {z_posit[0]-1, z_posit[1]};	// y, x
//							System.out.print("="+z_posit[0]+":"+z_posit[1]);// Test
//							System.out.print("="+n_posit[0]+":"+n_posit[1]);// Test
//							System.out.print("="+(x-z_posit[1])+":"+(z_posit[0]-y));// Test
							int tmp_y;
							tmp_y = n_posit[0] - (x-z_posit[1]);
							x = n_posit[1] + (y-z_posit[0]);
							y = tmp_y;
//							System.out.println("="+y+":"+x);// Test
						}
					}
					rotated += String.format("%x", y & 0xf);
					rotated += String.format("%x", x & 0xf);
				}
			}
			this.block[1] = rotated;
//			System.out.println("AFTER ROTATE: "+this.block[1]);	// TEST
		}
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
	public void checkmate(int dir) {
//		System.out.println("BEFORE CHECK: "+this.block[1]);	// TEST
		String[] king = {"","","",""};
		int[] rotate = {0,0,0,0};
		for(int i=0;i<32;i+=2) {	// King Collecting
			int x, y;
			int pan[];
			int chesstype = Integer.parseInt(this.block[0].substring(i, i+2),16) % 6;
			if(chesstype == KING) {
				y = Integer.parseInt(this.block[1].substring(i, i+1),16);
				x = Integer.parseInt(this.block[1].substring(i+1, i+2),16);
				pan = getPan(y,x);
				king[pan[0]*2+pan[1]] += String.format("%02d",i);
			}
		}
		for(int i=0; i<32; i+=2) {	// Pick Piece without King
			int chesstype = Integer.parseInt(this.block[0].substring(i, i+2),16) % 6;
			if(chesstype == KING) continue;
			
			int x, y, p;
			int pan[];
			y = Integer.parseInt(this.block[1].substring(i, i+1),16);
			x = Integer.parseInt(this.block[1].substring(i+1, i+2),16);
			pan = getPan(y,x);
			p = pan[0]*2+pan[1];
			
			for(int k=0; k<king[p].length(); k+=2) {	// Pick King in The Pan
				int j = Integer.parseInt(king[p].substring(k, k+2),10);	// king's Position in Block
				int king_y = Integer.parseInt(this.block[1].substring(j, j+1),16);
				int king_x = Integer.parseInt(this.block[1].substring(j+1, j+2),16);

//				System.out.println("chess: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
				switch(chesstype) {
				case PWN:
					if(((x-1) & 0xf) == king_x) {
						if(((y-1) & 0xf) == king_y) {
							rotate[p]++;
//							System.out.println("Cheskmate: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
						}
						else if(((y+1) & 0xf) == king_y) {
							rotate[p]++;
//							System.out.println("Cheskmate: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
						}
					}
					else if(((x+1) & 0xf) == king_x) {
						if(((y-1) & 0xf) == king_y) {
							rotate[p]++;
//							System.out.println("Cheskmate: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
						}
						else if(((y+1) & 0xf) == king_y) {
							rotate[p]++;
//							System.out.println("Cheskmate: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
						}
					}
					break;
				case KNIGHT:
					if(((y-2) & 0xf) == king_y) {
						if(((x-1) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x+1) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					else if(((y-1) & 0xf) == king_y) {
						if(((x-2) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x+2) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					else if(((y+1) & 0xf) == king_y) {
						if(((x+2) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x-2) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					else if(((y+2) & 0xf) == king_y) {
						if(((x+1) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x-1) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					break;
				case ROOK:
					if(((x-2) & 0xf) == king_x) {
						rotate[p]++;
					}
					else if(((x-1) & 0xf) == king_x) {
						rotate[p]++;
					}
					else if(((x+1) & 0xf) == king_x) {
						rotate[p]++;
					}
					else if(((x+2) & 0xf) == king_x) {
						rotate[p]++;
					}
					if(((y-2) & 0xf) == king_y) {
						rotate[p]++;
					}
					else if(((y-1) & 0xf) == king_y) {
						rotate[p]++;
					}
					else if(((y+1) & 0xf) == king_y) {
						rotate[p]++;
					}
					else if(((y+2) & 0xf) == king_y) {
						rotate[p]++;
					}
					break;
				case BISHOP:
					if(((y-1) & 0xf) == king_y) {
						if(((x-1) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x+1) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					else if(((y+1) & 0xf) == king_y) {
						if(((x-1) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x+1) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					break;
				case QUEEN:
					if(((y-2) & 0xf) == king_y) {
						if(((x-2) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x+2) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					else if(((y-1) & 0xf) == king_y) {
						if(((x-1) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x+1) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					else if(((y+1) & 0xf) == king_y) {
						if(((x+1) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x-1) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					else if(((y+2) & 0xf) == king_y) {
						if(((x+2) & 0xf) == king_x) {
							rotate[p]++;
						}
						else if(((x-2) & 0xf) == king_x) {
							rotate[p]++;
						}
					}
					break;
				}
			}
		}
		for(int p=0;p<4;p++) rotatePan(p, rotate[p], dir);
//		System.out.println("AFTER CHECK: "+this.block[1]);	// TEST
	}
	public void movePiece() {
		String moved = "";
		int x, y;
		int[] pan, moved_pan;

//		System.out.println("Block: "+this.block[1]);
		for(int i=0; i<32; i+=2) {
			int chesstype = Integer.parseInt(this.block[0].substring(i, i+2),16) % 6;
//			System.out.print(chesstype+" ");

			y = Integer.parseInt(this.block[1].substring(i, i+1),16);
			x = Integer.parseInt(this.block[1].substring(i+1, i+2),16);
			pan = getPan(y,x); // {y,x} ...

			// Move
			switch(chesstype) {
			case PWN:
				y += PWN_mov[1];
				x += PWN_mov[0];
				break;
			case KNIGHT:
				y += KNIGHT_mov[1];
				x += KNIGHT_mov[0];
				break;
			case ROOK:
				y += ROOK_mov[1];
				x += ROOK_mov[0];
				break;
			case BISHOP:
				y += BISHOP_mov[1];
				x += BISHOP_mov[0];
				break;
			case QUEEN:
				y += QUEEN_mov[1];
				x += QUEEN_mov[0];
				break;
			case KING:
				break;
			}
			y = y & 0xf;
			x = x & 0xf;

			// Repositioning Pan
			moved_pan = getPan(y, x); // {y,x} ...

			y += (pan[0] - moved_pan[0]) * 8;
			x += (pan[1] - moved_pan[1]) * 8;

			moved += String.format("%x",y & 0xf);
			moved += String.format("%x",x & 0xf);
		}
//		System.out.println("Moved: "+moved);

		this.block[1] = moved;
	}
	public void switchPiece() {
		String switched_block="";
//		System.out.println("block[0]: "+this.block[0]);	// test
		for(int i=0; i<32; i+=2)
			switched_block += String.format("%02x",Integer.parseInt(this.block[0].substring(i, i+2), 16) ^ Integer.parseInt(this.block[1].substring(i, i+2), 16));
		this.block[0] = switched_block;	
//		System.out.println("switched_block: "+this.block[0]);	// test
	}

	public void inverseMapPiece(int round) {
		String[] maped_block = new String[2];
		maped_block[0] = addKey(round,this.block[0]);
		maped_block[1] = addKey(round,this.block[1]);

		maped_block[0] = val2loc(maped_block[0]);
		maped_block[1] = loc2val(maped_block[1]);
		
		maped_block[0] = subKey(round,maped_block[0]);
		maped_block[1] = subKey(round,maped_block[1]);
		this.block = maped_block;
	}
//	public void inverseCheckmate() {
////		System.out.println("BEFORE InvCHECK: "+this.block[1]);	// TEST
//		String[] king = {"","","",""};
//		int[] rotate = {0,0,0,0};
//		for(int i=0;i<32;i+=2) {	// King Collecting
//			int x, y;
//			int pan[];
//			int chesstype = Integer.parseInt(this.block[0].substring(i, i+2),16) % 6;
//			if(chesstype == KING) {
//				y = Integer.parseInt(this.block[1].substring(i, i+1),16);
//				x = Integer.parseInt(this.block[1].substring(i+1, i+2),16);
//				pan = getPan(y,x);
//				king[pan[0]*2+pan[1]] += String.format("%02d",i);
//				
//			}
//		}
//		for(int i=0; i<32; i+=2) {	// Pick Piece without King
//			int chesstype = Integer.parseInt(this.block[0].substring(i, i+2),16) % 6;
//			if(chesstype == KING) continue;
//			
//			int x, y, p;
//			int pan[];
//			y = Integer.parseInt(this.block[1].substring(i, i+1),16);
//			x = Integer.parseInt(this.block[1].substring(i+1, i+2),16);
//			pan = getPan(y,x);
//			p = pan[0]*2+pan[1];
//			
//			for(int k=0; k<king[p].length(); k+=2) {	// Pick King in The Pan
//				int j = Integer.parseInt(king[p].substring(k, k+2),10);	// king's Position in Block
//				int king_y = Integer.parseInt(this.block[1].substring(j, j+1),16);
//				int king_x = Integer.parseInt(this.block[1].substring(j+1, j+2),16);
//
////				System.out.println("chess: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
//				switch(chesstype) {
//				case PWN:
//					if(((x-1) & 0xf) == king_x) {
//						if(((y-1) & 0xf) == king_y) {
//							rotate[p]++;
////							System.out.println("Cheskmate: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
//						}
//						else if(((y+1) & 0xf) == king_y) {
//							rotate[p]++;
////							System.out.println("Cheskmate: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
//						}
//					}
//					else if(((x+1) & 0xf) == king_x) {
//						if(((y-1) & 0xf) == king_y) {
//							rotate[p]++;
////							System.out.println("Cheskmate: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
//						}
//						else if(((y+1) & 0xf) == king_y) {
//							rotate[p]++;
////							System.out.println("Cheskmate: "+x+":"+y+"-"+king_x+":"+king_y);	// TEST
//						}
//					}
//					break;
//				case KNIGHT:
//					break;
//				case ROOK:
//					if(((x-2) & 0xf) == king_x) {
//						rotate[p]++;
//					}
//					else if(((x-1) & 0xf) == king_x) {
//						rotate[p]++;
//					}
//					else if(((x+1) & 0xf) == king_x) {
//						rotate[p]++;
//					}
//					else if(((x+2) & 0xf) == king_x) {
//						rotate[p]++;
//					}
//					if(((y-2) & 0xf) == king_y) {
//						rotate[p]++;
//					}
//					else if(((y-1) & 0xf) == king_y) {
//						rotate[p]++;
//					}
//					else if(((y+1) & 0xf) == king_y) {
//						rotate[p]++;
//					}
//					else if(((y+2) & 0xf) == king_y) {
//						rotate[p]++;
//					}
//					break;
//				case BISHOP:
//					if(((y-2) & 0xf) == king_y) {
//						if(((x-2) & 0xf) == king_x) {
//							rotate[p]++;
//						}
//						else if(((x+2) & 0xf) == king_x) {
//							rotate[p]++;
//						}
//					}
//					else if(((y-1) & 0xf) == king_y) {
//						if(((x-1) & 0xf) == king_x) {
//							rotate[p]++;
//						}
//						else if(((x+1) & 0xf) == king_x) {
//							rotate[p]++;
//						}
//					}
//					else if(((y+1) & 0xf) == king_y) {
//						if(((x+1) & 0xf) == king_x) {
//							rotate[p]++;
//						}
//						else if(((x+1) & 0xf) == king_x) {
//							rotate[p]++;
//						}
//					}
//					else if(((y+2) & 0xf) == king_y) {
//						if(((x-2) & 0xf) == king_x) {
//							rotate[p]++;
//						}
//						else if(((x-2) & 0xf) == king_x) {
//							rotate[p]++;
//						}
//					}
//					break;
//				case QUEEN:
//					break;
//				case KING:
//					break;
//				}
//			}
//		}
//		for(int p=0;p<4;p++) rotatePan(p, rotate[p], 1);
////		System.out.println("AFTER InvCHECK: "+this.block[1]);	// TEST
//	}
	public void inverseMovePiece() {

		String moved = "";
		int x, y;
		int[] pan, moved_pan;

//		System.out.println("Block: "+this.block[1]);
		for(int i=0; i<32; i+=2) {
			int chesstype = Integer.parseInt(this.block[0].substring(i, i+2),16) % 6;
//			System.out.print(chesstype+" ");
			y = Integer.parseInt(this.block[1].substring(i, i+1),16);
			x = Integer.parseInt(this.block[1].substring(i+1, i+2),16);
			pan = getPan(y,x); // {y,x} ...

			// Move
			switch(chesstype) {
			case PWN:
				y -= PWN_mov[1];
				x -= PWN_mov[0];
				break;
			case KNIGHT:
				y -= KNIGHT_mov[1];
				x -= KNIGHT_mov[0];
				break;
			case ROOK:
				y -= ROOK_mov[1];
				x -= ROOK_mov[0];
				break;
			case BISHOP:
				y -= BISHOP_mov[1];
				x -= BISHOP_mov[0];
				break;
			case QUEEN:
				y -= QUEEN_mov[1];
				x -= QUEEN_mov[0];
				break;
			case KING:
				break;
			}
			y = y & 0xf;
			x = x & 0xf;

			// Repositioning Pan
			moved_pan = getPan(y,x); // {y,x} ...

			y += (pan[0] - moved_pan[0]) * 8;
			x += (pan[1] - moved_pan[1]) * 8;

			moved += String.format("%x",y & 0xf);
			moved += String.format("%x",x & 0xf);
		}
//		System.out.println("Moved: "+moved);
		
		this.block[1] = moved;
	}
	
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
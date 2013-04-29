
import java.util.Random;

import java.io.*;

import javax.bluetooth.*;
import lejos.nxt.*;
import lejos.nxt.comm.*;


public class Board {


	public static final int BOARD_SIZE = 5;
	

	private char[][] board;
	private int[] bestCoord;
	
	private int positionX=(int)(5*Math.random());
	private int positionY=(int)(5*Math.random());
	private int positionX2=(int)(5*Math.random());
	private int positionY2=(int)(5*Math.random());
	
	public boolean defensiveO = false;
	public boolean defensiveX = false;
	public char empty = ' ';
	
	Random random = new Random();
	
	public boolean randomX = true;
	public int drawcount = BOARD_SIZE*BOARD_SIZE;
	public boolean isFinished = false;
	int xu;
	int xy;
	
	DataInputStream dis = Main.connection.openDataInputStream();
    DataOutputStream dos = Main.connection.openDataOutputStream();

	public char turn='X';
	
	public Board() {
		board = new char[BOARD_SIZE][BOARD_SIZE];
		for (int y = 0; y < BOARD_SIZE; y++) {
			for (int x = 0; x < BOARD_SIZE; x++) {
				board[x][y] = empty;
			}
		}
		
	    try {
	    	xu = dis.readInt();
			xy = dis.readInt();
			System.out.println("xu: " + xu + "xy: " + xy );
			Button.waitForAnyPress();
			LCD.clear();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("OCU3");
		}
	    doMove(xu, xy);
	}
	
	public void doMove(int positionX, int positionY){
		
		if(defensiveX){
			changeTurn();
			defensiveX = !defensiveX;
		}
		
		if(defensiveO){
			changeTurn();
			defensiveO = !defensiveO;
		}
		
		board[positionY][positionX]=turn;
		LCD.clear();
		
		
		for(int i=0; i<BOARD_SIZE; i++){
			for(int j = 0; j<BOARD_SIZE; j++){
				if(board[i][j] == empty){
					System.out.print("-");			
				}else{
				System.out.print(board[i][j]);
				}
			}
			System.out.print("\n");
		}
		Button.waitForAnyPress();
		
		LCD.clear();
		validateGame();
		
		if(!isFinished){
			if(turn == 'X'){
				changeTurn();
				findBestMove();
			}
			else
			{
				try {
					Draw2Board.makeMove(positionX, positionY);
					dos.writeInt(positionX);
					dos.writeInt(positionY);
			        dos.flush();
			        
			        changeTurn();
			        
			    	xu = dis.readInt();
					xy = dis.readInt();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("OCU5");
				}
			    
				doMove(xu, xy);
				
			}
		}
		else
			System.exit(0);
	}

	public void changeTurn(){
		if(turn == 'X')
			turn = 'O';
		else
			turn = 'X';
	}
	
	public void validateGame(){

		// 4lüler için kontrol
		if(board[0][0] == turn) {
            if(board[0][1] == turn && board[0][2] == turn && board[0][3] == turn)
                finishGame(turn);
            if(board[1][1] == turn && board[2][2] == turn && board[3][3] == turn)
                finishGame(turn);
            if(board[1][0] == turn && board[2][0] == turn && board[3][0] == turn)
                finishGame(turn);
        }
	
		if(board[1][1] == turn) { 
            if(board[0][0] == turn && board[2][2] == turn && board[3][3] == turn)
                finishGame(turn);
            if(board[4][4] == turn && board[2][2] == turn && board[3][3] == turn)
                finishGame(turn);
            if(board[1][0] == turn && board[1][2] == turn && board[1][3] == turn )
                finishGame(turn);
            if(board[1][4] == turn && board[1][2] == turn && board[1][3] == turn )
                finishGame(turn);
            if(board[0][1] == turn && board[2][1] == turn && board[3][1] == turn )
                finishGame(turn);
            if(board[2][1] == turn && board[3][1] == turn && board[4][1] == turn )
                finishGame(turn);
            }
		
            
            
        if(board[2][2] == turn) {
    		if(board[2][1] == turn && board[2][0] == turn && board[2][3] == turn)
                finishGame(turn);
        	if(board[2][1] == turn && board[2][3] == turn && board[2][4] == turn)
                finishGame(turn);
        	if(board[0][2] == turn && board[1][2] == turn && board[3][2] == turn)
                finishGame(turn);
        	if(board[1][2] == turn && board[3][2] == turn && board[4][2] == turn)
                finishGame(turn);
        	if(board[4][0] == turn && board[3][1] == turn && board[1][3] == turn)
                finishGame(turn);
        	if(board[0][4] == turn && board[1][3] == turn && board[3][1] == turn)
                finishGame(turn);
        }   
            
        if(board[3][3] == turn) {
        	if(board[1][3] == turn && board[2][3] == turn && board[4][3] == turn)
                finishGame(turn);
        	if(board[0][3] == turn && board[1][3] == turn && board[2][3] == turn)
                finishGame(turn);
        	if(board[3][0] == turn && board[3][1] == turn && board[3][2] == turn)
                finishGame(turn);
        	if(board[3][1] == turn && board[3][2] == turn && board[4][3] == turn)
                finishGame(turn);
        }   
            
        if(board[4][4] == turn) {
        	if(board[4][3] == turn && board[4][2] == turn && board[4][1] == turn)
                finishGame(turn);
        	if(board[1][4] == turn && board[2][4] == turn && board[3][4] == turn)
                finishGame(turn);
        }   
        
        if(board[4][0] == turn) {
        	if(board[1][0] == turn && board[2][0] == turn && board[3][0] == turn)
                finishGame(turn);
        	if(board[4][1] == turn && board[4][2] == turn && board[4][3] == turn)
                finishGame(turn);
        }   
        
        if(board[0][4] == turn) {
        	if(board[1][4] == turn && board[2][4] == turn && board[3][4] == turn)
                finishGame(turn);
        	if(board[0][1] == turn && board[0][2] == turn && board[0][3] == turn)
                finishGame(turn);
        }
        
        if(board[0][1] == turn && board[1][2] == turn && board[2][3] == turn && board[3][4] == turn)
            finishGame(turn);
        if(board[1][0] == turn && board[2][1] == turn && board[3][2] == turn && board[4][3] == turn)
            finishGame(turn);
        if(board[4][1] == turn && board[3][2] == turn && board[2][3] == turn && board[1][4] == turn)
            finishGame(turn);
        if(board[3][0] == turn && board[2][1] == turn && board[1][2] == turn && board[0][3] == turn)
            finishGame(turn);
        
        drawcount--;
        if(drawcount == 0)
        	finishGame('D');
        
        
	}
		
	public void finishGame(char winner){
		
		isFinished = true;
		
		try {
			dis.close();
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		
		if(winner == 'X')	
			LCD.drawString("Winner is X!", 0, 0);
		if(winner == 'O')
			LCD.drawString("Winner is O!", 0, 0);
		if(winner == 'D')
			LCD.drawString("Draw", 0, 0);
		
		
	}
	
	public void defensiveX(boolean value){
		if(turn == 'O')
			defensiveX = value;
		else
			defensiveX = true;

		if(defensiveX){
			changeTurn();
		}
	}
	
	public void defensiveO(boolean value){
		if(turn == 'X')
			defensiveO = value;
		else
			defensiveO = true;

		if(defensiveO){
			changeTurn();
		}
	}
	
	public int[] findPossibleTwoMoves(){
		
		for(int i=0;i<BOARD_SIZE;i++)
			for(int j=0;j<BOARD_SIZE;j++)
				if(board[j][i] == turn)
					for(int k=j-1;k<=j+1;k++)
						for(int l=i-1;l<=i+1;l++)
							if(k >= 0 && k <= 4 && l >= 0 && l <= 4 && board[k][l] == empty){
								int[] arr = new int[]{k,l};
								return arr;
							}
							

		return null;
	}
	
	public void findPossibleThreeMoves(){

		
		defensiveX(true);
		/**2lileri 3 yap */
		
		//point[0][0]
		if(board[0][0] == turn && board[0][1] == turn){
        	positionX=0;positionY=2;
        	positionX2=0;positionY=3;
        	if(board[positionX][positionY] == empty)
        		if(board[0][3] == empty){
        			if(random.nextBoolean())
        				doMove(positionX, positionY);
        			else
        				doMove(positionX2, positionY2);
        			}
        }		
		if(board[0][0] == turn && board[1][1] == turn){
        	positionX=2;positionY=2;
        	positionX2=3;positionY=3;
        	if(board[positionX][positionY] == empty)
        		if(board[3][3] == empty){
        			if(random.nextBoolean())
        				doMove(positionX, positionY);
        			else
        				doMove(positionX2, positionY2);
        			}
        }
		if(board[0][0] == turn && board[1][0] == turn){
        	positionX=2;positionY=0;
        	positionX2=3;positionY=0;
        	if(board[positionX][positionY] == empty)
        		if(board[3][0] == empty){
            		if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		
		//point[0][4]
		if(board[0][4] == turn && board[0][3] == turn){
        	positionX=0;positionY=2;
        	positionX2=0;positionY=1;
        	if(board[positionX][positionY] == empty)
        		if(board[0][1] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		
		if(board[0][4] == turn && board[1][4] == turn){
        	positionX=2;positionY=4;
        	positionX2=3;positionY=4;
        	if(board[positionX][positionY] == empty)
        		if(board[3][4] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		if(board[0][4] == turn && board[1][3] == turn){
        	positionX=2;positionY=2;
        	positionX2=3;positionY=1;
        	if(board[positionX][positionY] == empty)
        		if(board[3][1] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		
		//point[4][4]
		if(board[4][4] == turn && board[4][3] == turn){
        	positionX=4;positionY=2;
        	positionX2=4;positionY=1;
        	if(board[positionX][positionY] == empty)
        		if(board[4][1] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }		
		if(board[4][4] == turn && board[3][4] == turn){
        	positionX=2;positionY=4;
        	positionX2=1;positionY=4;
        	if(board[positionX][positionY] == empty)
        		if(board[1][4] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		if(board[4][4] == turn && board[3][3] == turn){
        	positionX=2;positionY=2;
        	positionX2=1;positionY=1;
        	if(board[positionX][positionY] == empty)
        		if(board[1][1] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		
		//point[4][0]
		if(board[4][0] == turn && board[4][1] == turn){
        	positionX=4;positionY=2;
        	positionX2=4;positionY=3;
        	if(board[positionX][positionY] == empty)
        		if(board[4][3] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }		
		if(board[4][0] == turn && board[3][0] == turn){
        	positionX=2;positionY=0;
        	positionX2=1;positionY=0;
        	if(board[positionX][positionY] == empty)
        		if(board[1][0] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		if(board[4][0] == turn && board[3][1] == turn){
        	positionX=2;positionY=2;
        	positionX2=1;positionY=3;
        	if(board[positionX][positionY] == empty)
        		if(board[1][3] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		
		//point[2][0]
		if(board[2][0] == turn && board[2][1] == turn){
        	positionX=2;positionY=2;
        	positionX2=2;positionY=3;
        	if(board[positionX][positionY] == empty)
        		if(board[2][3] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		if(board[2][0] == turn && board[1][0] == turn){
			positionX=0;positionY=0;
        	positionX2=3;positionY2=0;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}
           	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);
        	if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }		
		if(board[2][0] == turn && board[3][0] == turn){
			positionX=1;positionY=0;
        	positionX2=4;positionY2=0;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);
        	if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }
		
		//point[0][2]
		if(board[0][2] == turn && board[1][2] == turn){
        	positionX=2;positionY=2;
        	positionX2=3;positionY=2;
        	if(board[positionX][positionY] == empty)
        		if(board[3][2] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		if(board[0][2] == turn && board[0][1] == turn){
			positionX=0;positionY=0;
        	positionX2=0;positionY2=3;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);
        	if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }		
		if(board[0][2] == turn && board[0][3] == turn){
			positionX=0;positionY=1;
        	positionX2=0;positionY2=4;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }	
		
		//point[2][4]
		if(board[2][4] == turn && board[2][3] == turn){
        	positionX=2;positionY=2;
        	positionX2=2;positionY=4;
        	if(board[positionX][positionY] == empty)
        		if(board[2][4] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		if(board[2][4] == turn && board[1][4] == turn){
			positionX=0;positionY=4;
        	positionX2=3;positionY2=4;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);
        	if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }		
		if(board[2][4] == turn && board[3][4] == turn){
			positionX=4;positionY=4;
        	positionX2=1;positionY2=4;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }	
		
		//point[2][4]
		if(board[4][2] == turn && board[3][2] == turn){
        	positionX=2;positionY=2;
        	positionX2=1;positionY=2;
        	if(board[positionX][positionY] == empty)
        		if(board[1][2] == empty){
        			if(random.nextBoolean())
            			doMove(positionX, positionY);
            		else
            			doMove(positionX2, positionY2);
            		}
        }
		if(board[4][2] == turn && board[4][1] == turn){
			positionX=4;positionY=0;
        	positionX2=4;positionY2=3;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);
        	if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }		
		if(board[4][2] == turn && board[4][3] == turn){
			positionX=4;positionY=1;
        	positionX2=4;positionY2=4;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }
		
		//point[2][2]
		if(board[2][2] == turn && board[1][2] == turn){
			positionX=0;positionY=2;
        	positionX2=3;positionY2=2;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }
		if(board[2][2] == turn && board[1][3] == turn){
			positionX=0;positionY=4;
        	positionX2=3;positionY2=1;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }
		if(board[2][2] == turn && board[2][3] == turn){
			positionX=2;positionY=4;
        	positionX2=2;positionY2=1;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }
		if(board[2][2] == turn && board[3][3] == turn){
			positionX=4;positionY=4;
        	positionX2=1;positionY2=1;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }
		if(board[2][2] == turn && board[3][2] == turn){
			positionX=4;positionY=2;
        	positionX2=1;positionY2=2;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }
		if(board[2][2] == turn && board[3][1] == turn){
			positionX=4;positionY=0;
        	positionX2=1;positionY2=3;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }
		if(board[2][2] == turn && board[2][1] == turn){
			positionX=2;positionY=0;
        	positionX2=2;positionY2=3;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }
		if(board[2][2] == turn && board[1][1] == turn){
			positionX=0;positionY=0;
        	positionX2=3;positionY2=3;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
        }

		/** -- 2lileri 3 yap -- */	
	}
	
	public void findPossibleFourMoves(){
		
		/** 3lüleri 4 yap */
		
		// point[0,2]
		if(board[0][2] == turn && board[0][3] == turn && board[0][4] == turn){
        	positionX=0;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }  
        if(board[0][2] == turn && board[0][1] == turn && board[0][0] == turn){
        	positionX=0;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }            
        if(board[0][2] == turn && board[1][2] == turn && board[2][2] == turn){
        	positionX=3;positionY=2;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[0][2] == turn && board[0][1] == turn && board[0][3] == turn){
        	positionX=0;positionY=0;
        	positionX2=0;positionY2=4;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}
            
        //point[1,2]
        if(board[1][2] == turn && board[1][3] == turn && board[1][4] == turn){
        	positionX=1;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }  
        if(board[1][2] == turn && board[1][1] == turn && board[1][0] == turn){
        	positionX=1;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }           
        if(board[1][2] == turn && board[2][3] == turn && board[3][4] == turn){
        	positionX=0;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }       
        if(board[1][2] == turn && board[2][3] == turn && board[0][1] == turn){
        	positionX=3;positionY=4;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }       
        if(board[1][2] == turn && board[0][3] == turn && board[2][1] == turn){
        	positionX=3;positionY=0;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }       
        if(board[1][2] == turn && board[2][1] == turn && board[3][0] == turn){
        	positionX=0;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }       
        if(board[1][2] == turn && board[2][2] == turn && board[3][2] == turn){
        	positionX=0;positionY=2;
        	positionX2=3;positionY2=2;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}
        
        //point[2,2]
        if(board[2][2] == turn && board[3][2] == turn && board[4][2] == turn){
        	positionX=1;positionY=2;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][2] == turn && board[2][3] == turn && board[2][4] == turn){
        	positionX=2;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][2] == turn && board[2][1] == turn && board[2][0] == turn){
        	positionX=2;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][2] == turn && board[3][3] == turn && board[4][4] == turn){
        	positionX=1;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][2] == turn && board[1][1] == turn && board[0][0] == turn){
        	positionX=3;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][2] == turn && board[1][3] == turn && board[0][4] == turn){
        	positionX=3;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][2] == turn && board[3][1] == turn && board[4][0] == turn){
        	positionX=1;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][2] == turn && board[1][1] == turn && board[3][3] == turn){
        	positionX=0;positionY=0;
        	positionX2=4;positionY2=4;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}        
        if(board[2][2] == turn && board[1][3] == turn && board[3][1] == turn){
        	positionX=0;positionY=4;
        	positionX2=4;positionY2=0;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}
        
        //point[3][2]
        if(board[3][2] == turn && board[3][3] == turn && board[3][4] == turn){
        	positionX=3;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[3][2] == turn && board[3][1] == turn && board[3][0] == turn){
        	positionX=3;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[3][2] == turn && board[2][1] == turn && board[4][3] == turn){
        	positionX=1;positionY=0;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[3][2] == turn && board[2][1] == turn && board[1][0] == turn){
        	positionX=4;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[3][2] == turn && board[2][3] == turn && board[1][4] == turn){
        	positionX=4;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[3][2] == turn && board[4][1] == turn && board[2][3] == turn){
        	positionX=1;positionY=4;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[3][2] == turn && board[3][1] == turn && board[3][3] == turn){
        	positionX=3;positionY=0;
        	positionX2=3;positionY2=4;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}

        //point[4][2]
        if(board[4][2] == turn && board[4][3] == turn && board[4][4] == turn){
        	positionX=4;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[4][2] == turn && board[4][1] == turn && board[4][0] == turn){
        	positionX=4;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[4][2] == turn && board[4][3] == turn && board[4][1] == turn){
        	positionX=4;positionY=0;
        	positionX2=4;positionY2=4;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}
        
        //point[2][0]
        if(board[2][0] == turn && board[1][0] == turn && board[0][0] == turn){
        	positionX=3;positionY=0;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][0] == turn && board[3][0] == turn && board[4][0] == turn){
        	positionX=1;positionY=0;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][0] == turn && board[1][0] == turn && board[3][0] == turn){
        	positionX=0;positionY=0;
        	positionX2=4;positionY2=0;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}
        
      //point[2][1]
        if(board[2][1] == turn && board[1][1] == turn && board[0][0] == turn){
        	positionX=3;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][1] == turn && board[3][1] == turn && board[4][1] == turn){
        	positionX=1;positionY=1;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][1] == turn && board[1][1] == turn && board[3][1] == turn){
        	positionX=0;positionY=1;
        	positionX2=4;positionY2=1;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}
        
      //point[2][3]
        if(board[2][3] == turn && board[1][3] == turn && board[0][3] == turn){
        	positionX=3;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][3] == turn && board[3][3] == turn && board[4][3] == turn){
        	positionX=1;positionY=3;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][3] == turn && board[1][3] == turn && board[3][3] == turn){
        	positionX=0;positionY=3;
        	positionX2=4;positionY2=3;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}
        
      //point[2][4]
        if(board[2][4] == turn && board[1][4] == turn && board[0][4] == turn){
        	positionX=3;positionY=4;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][4] == turn && board[3][4] == turn && board[4][4] == turn){
        	positionX=1;positionY=4;
        	if(board[positionX][positionY] == empty)
        		doMove(positionX, positionY);
        }        
        if(board[2][4] == turn && board[1][4] == turn && board[3][4] == turn){
        	positionX=0;positionY=4;
        	positionX2=4;positionY2=4;
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] == empty){
        		if(Math.random() >= 0.5)
        			doMove(positionX, positionY);
        		else
        			doMove(positionX2, positionY2);
        	}        
        	if(board[positionX][positionY] == empty && board[positionX2][positionY2] != empty)
        		doMove(positionX, positionY);        	
    		if(board[positionX][positionY] != empty && board[positionX2][positionY2] == empty)
        		doMove(positionX2, positionY2);		
    	}
        
        /** -- 3lüleri 4 yap -- */
		
	}
	

	public void findBestMove(){
		
		findPossibleFourMoves();
		
		findPossibleThreeMoves();
		
		/**1lileri 2 yap */
		
		bestCoord = findPossibleTwoMoves();
		if(bestCoord != null) 
			doMove(bestCoord[0],bestCoord[1]);
		else
		{
			int emptyX=0;
			int emptyY=0;
			for (int x = 0; x < BOARD_SIZE; x++) {
				for (int y = 0; y < BOARD_SIZE; y++) {
					if(board[x][y] == empty){
						emptyX = x;
						emptyY = y;
					}
				}
			}
			doMove(emptyX, emptyY);
		}
		
		
	}
}
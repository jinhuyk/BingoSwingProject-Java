/******************************************
- written by Jinhuyk. Mun 11/20/2022
- JAVA programming Personal Project
*******************************************/
package bingo;

import java.util.Random;


public class Game{

	Board userBoard;
	Board comBoard;
	String logStr = "";
	GameDialog gameFrame;
	public Game(Board u, Board c,GameDialog gameFrame) {
		this.userBoard = u;
		this.comBoard = c;
		this.gameFrame = gameFrame;
	}
	int[] comNearBingoAlgorithm(Board board){
		int[] countBingoCheck = new int[2*board.getN()+2];
		
		for(int i = 0;i<2*board.getN()+2;i++) {
			countBingoCheck[i] = 0;
		}
		
		//row check 
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j <board.getN();j++) {
				if(board.board[i][j].isChecked==true) {
					countBingoCheck[i]+=1;
					
				}
			}
		}
		//column check
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j < board.getN();j++) {
				if(board.board[j][i].isChecked==true) {
					countBingoCheck[i+board.getN()]+=1;
					
				}
			}
		}
		
		//cross check
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j < board.getN();j++) {
				if(i==j) {
					if(board.board[i][j].isChecked==true) {
						countBingoCheck[2*board.getN()]+=1;
						
					}
				}

			}
		}
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j < board.getN();j++) {
				if(i+j == board.getN()) {
					if(board.board[i][j].isChecked==true) {
						countBingoCheck[2*board.getN()+1]+=1;
						
					}
				}

			}
		}
		
		int max = 0;
		int maxIdx = 0;
		int[] rstIdx = new int[2];
		boolean check = true;
		for(int i : countBingoCheck) {
			if(i>0) {
				check = false;
				break;
			}
		}
		if(check ==true) {
			rstIdx[0] = new Random().nextInt(this.comBoard.getN()-1);
			rstIdx[1] = new Random().nextInt(this.comBoard.getN()-1);
			return rstIdx;
		}

		for (int i = 0;i<board.getN()*2 + 2 ;i++ ) {
			if( countBingoCheck[i] < board.getN() && max < countBingoCheck[i]) {
				max = countBingoCheck[i];
				maxIdx = i;
			}
		}
		
		
		if(maxIdx < board.getN()) {
			rstIdx[0] = maxIdx;
			for(int j =0;j<board.getN();j++) {
				if(board.board[rstIdx[0]][j].isChecked==false) {
					rstIdx[1] = j;
					break;
				}
			}
		}
		else if(maxIdx>=board.getN() && maxIdx < board.getN()*2) {
			rstIdx[1] = maxIdx - board.getN();
			for(int i =0;i<board.getN();i++) {
				if(board.board[i][rstIdx[1]].isChecked==false) {
					rstIdx[0] = i;
					break;
				}
			}
		}
		else if(maxIdx == board.getN()*2){
			for(int i = 0; i < board.getN();i++) {
				for(int j = 0; j < board.getN();j++) {
					if(i==j) {
						if(board.board[i][j].isChecked==false) {
							rstIdx[0] = i;
							rstIdx[1] = j;
							break;
						}
					}

				}
			}
		}
		else if(maxIdx == board.getN()*2+1) {
			for(int i = 0; i < board.getN();i++) {
				for(int j = 0; j < board.getN();j++) {
					if(i+j == board.getN()) {
						if(board.board[i][j].isChecked==false) {
							rstIdx[0]=i;
							rstIdx[1] = j;
							break;
						}
					}

				}
			}
		}
		return rstIdx;
	}
	
	int isBingo(Board board) {
		
		boolean[] isBingoCheck = new boolean[2*board.getN()+2];
		
		for(int i = 0;i<2*board.getN()+2;i++) {
			isBingoCheck[i] = true;
		}
		
		//row check 
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j <board.getN();j++) {
				if(board.board[i][j].isChecked==false) {
					
					isBingoCheck[i]=false;
					break;
				}
			}
		}
		//column check
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j < board.getN();j++) {
				if(board.board[j][i].isChecked==false) {
					
					isBingoCheck[i+board.getN()]=false;
					break;
				}
			}
		}
		
		//cross check
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j < board.getN();j++) {
				if(i==j) {
					if(board.board[i][j].isChecked==false) {
						
						isBingoCheck[2*board.getN()]=false;
						break;
					}
				}

			}
		}
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j < board.getN();j++) {
				if(i+j == board.getN()) {
					if(board.board[i][j].isChecked==false) {
						
						isBingoCheck[2*board.getN()+1]=false;
						break;
					}
				}

			}
		}
		
		// full array check
		int countBingo = 0;
		
		for(int i = 0;i<2*board.getN()+2;i++) {
			if(isBingoCheck[i] == true) {
				countBingo++;
			}
		}
		return countBingo;
	}
	
	boolean isAllOpenBingo(Board board) {
		
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j < board.getN();j++) {
				if(board.board[i][j].isChecked==false) return false;
			}
		}
		return true;
	}
	
	void selectWord(boolean comAI,String data) {
		
		
		//now Turn is user Turn
		boolean check = false;
		String selectEngWord = data;
		gameFrame.addLog("your word : "+selectEngWord+"\n");
		gameFrame.addLog("Player Turn\n");
		
		
		for(int i = 0; i < this.userBoard.getN();i++) {
			for(int j = 0; j < this.userBoard.getN();j++) {
				
				if(selectEngWord.equals(this.userBoard.board[i][j].eng)
						&& this.userBoard.board[i][j].isChecked==false) {
					gameFrame.addLog("Player Selected Word : "+this.userBoard.board[i][j]+"\n");
					this.userBoard.board[i][j].isChecked = true;
					check =true;
					break;
				}
			}
		}
		if(check== false) {
			gameFrame.addLog("NO WORD IN BOARD\n");
		}
		for(int i = 0; i < this.comBoard.getN();i++) {
			for(int j = 0; j < this.comBoard.getN();j++) {
				
				if(selectEngWord.equals(this.comBoard.board[i][j].eng)
						&& this.comBoard.board[i][j].isChecked==false) {
					this.comBoard.board[i][j].isChecked = true;
					break;
				}
			}
		}

	//nowTurn is computer Turn
		gameFrame.addLog("Computer Turn\n");
		int idxI = new Random().nextInt(this.comBoard.getN()-1);
		int idxJ = new Random().nextInt(this.comBoard.getN()-1);
		
		if(comAI == true) {
			int[] idx = this.comNearBingoAlgorithm(comBoard);
			idxI = idx[0];
			idxJ = idx[1];
		}
		
		selectEngWord = this.comBoard.board[idxI][idxJ].eng;
		gameFrame.addLog("Computer Selected Word : "+this.comBoard.board[idxI][idxJ]+"\n");
		if(this.comBoard.board[idxI][idxJ].isChecked==false)
		this.comBoard.board[idxI][idxJ].isChecked = true;
		
		for(int i = 0; i < this.userBoard.getN();i++) {
			for(int j = 0; j < this.userBoard.getN();j++) {
				
				if(selectEngWord.equals(this.userBoard.board[i][j].eng)
						&& this.userBoard.board[i][j].isChecked==false) {
					gameFrame.addLog("Player Selected Word : "+this.userBoard.board[i][j]+"\n");
					this.userBoard.board[i][j].isChecked = true;
					break;
				}
			}
		}
	}
	

}

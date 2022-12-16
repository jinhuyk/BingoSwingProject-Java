/******************************************
- written by Jinhuyk. Mun 12/06/2022
- JAVA programming Personal Project
*******************************************/
package bingo;

import java.util.Random;


public class Game{

	Board userBoard;
	Board comBoard;
	String logStr = "";
	GameDialog parent;
	public Game(Board u, Board c,GameDialog parent) {
		this.userBoard = u;
		this.comBoard = c;
		this.parent = parent;
	}
	
	//컴퓨터 승리 알고리즘
	int[] comNearBingoAlgorithm(Board board){
		
		// 빙고가 몇개인지를 확인하는 배열
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
				if(i+j == board.getN()-1) {
					if(board.board[i][j].isChecked==true) {
						countBingoCheck[2*board.getN()+1]+=1;
						
					}
				}

			}
		}
		
		
		// 빙고의 개수 확인 후, 최대한 짧은 턴에 빙고를 할 수 있는 위치를 선정
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
			rstIdx[0] = new Random().nextInt(this.comBoard.getN());
			rstIdx[1] = new Random().nextInt(this.comBoard.getN());
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
					if(i+j == board.getN()-1) {
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
	
	
	//빙고의 개수 확인 알고리즘
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
				if(i+j == board.getN()-1) {
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
	
	//모든 빙고칸이 채워진 경우 확인 
	boolean isAllOpenBingo(Board board) {
		
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j < board.getN();j++) {
				if(board.board[i][j].isChecked==false) return false;
			}
		}
		return true;
	}
	
	//단어 선택 알고리즘
	void selectWord(boolean comAI,String data) {
		
		
		//now Turn is user Turn
		boolean check = false;
		String selectEngWord = data;
		parent.addLog("your word : "+selectEngWord+"\n");
		parent.addLog("Player Turn\n");
		
		// for문을 이용하여 해당 단어가 보드에 있는지 확인 후 checking
		for(int i = 0; i < this.userBoard.getN();i++) {
			for(int j = 0; j < this.userBoard.getN();j++) {
				
				if(selectEngWord.equals(this.userBoard.board[i][j].eng)
						&& this.userBoard.board[i][j].isChecked==false) {
					parent.addLog("Player Selected Word : "+this.userBoard.board[i][j]+"\n");
					this.userBoard.board[i][j].isChecked = true;
					check =true;
					break;
				}
			}
		}
		if(check== false) {
			parent.addLog("NO WORD IN BOARD\n");
		}
		//컴퓨터의 단어 확인 checking
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
		parent.addLog("Computer Turn\n");
		int idxI = new Random().nextInt(this.comBoard.getN());
		int idxJ = new Random().nextInt(this.comBoard.getN());
		
		// 승리 알고리즘을 통한 위치 수정
		if(comAI == true) {
			int[] idx = this.comNearBingoAlgorithm(comBoard);
			idxI = idx[0];
			idxJ = idx[1];
		}
		
		selectEngWord = this.comBoard.board[idxI][idxJ].eng;
		parent.addLog("Computer Selected Word : "+this.comBoard.board[idxI][idxJ]+"\n");
		if(this.comBoard.board[idxI][idxJ].isChecked==false)
		this.comBoard.board[idxI][idxJ].isChecked = true;
		
		//플레이어의 보드에 단어가 있는지 확인
		for(int i = 0; i < this.userBoard.getN();i++) {
			for(int j = 0; j < this.userBoard.getN();j++) {
				
				if(selectEngWord.equals(this.userBoard.board[i][j].eng)
						&& this.userBoard.board[i][j].isChecked==false) {
					parent.addLog("Player Selected Word : "+this.userBoard.board[i][j]+"\n");
					this.userBoard.board[i][j].isChecked = true;
					break;
				}
			}
		}
	}
	

}

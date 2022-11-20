/******************************************
- written by Jinhuyk. Mun 11/20/2022
- JAVA programming Personal Project
*******************************************/
package bingo;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Game {
	
	Scanner scan = new Scanner(System.in);
	Board userBoard;
	Board comBoard;
	
	private int[] comNearBingoAlgorithm(Board board){
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
	
	private int isBingo(Board board) {
		
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
	
	private boolean isAllOpenBingo(Board board) {
		
		for(int i = 0; i < board.getN();i++) {
			for(int j = 0; j < board.getN();j++) {
				if(board.board[i][j].isChecked==false) return false;
			}
		}
		return true;
	}
	
	private void selectWord(boolean nowTurn, boolean comAI) {
		
		
		//now Turn is user Turn
		if(nowTurn == true) {
			boolean check = false;
			System.out.println("USER TURN>>>");
			System.out.print("Input English Word -> ");
			String selectEngWord = "";
			while(selectEngWord.equals("")){
				selectEngWord = scan.nextLine();
			}
			System.out.println();
			System.out.println("User choose this >> "+selectEngWord);
			for(int i = 0; i < this.userBoard.getN();i++) {
				for(int j = 0; j < this.userBoard.getN();j++) {
					
					if(selectEngWord.equals(this.userBoard.board[i][j].eng)
							&& this.userBoard.board[i][j].isChecked==false) {
						
						System.out.println(this.userBoard.board[i][j]+" selected");
						this.userBoard.board[i][j].isChecked = true;
						check =true;
						break;
					}
				}
			}
			if(check== false) {
				System.out.println("It is not on Your Board >> "+selectEngWord);
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
			
		}
		//nowTurn is computer Turn
		else if (nowTurn == false) {
			System.out.println("COMPUTER TURN>>>");
			
			int idxI = new Random().nextInt(this.comBoard.getN()-1);
			int idxJ = new Random().nextInt(this.comBoard.getN()-1);
			
			if(comAI == true) {
				int[] idx = this.comNearBingoAlgorithm(comBoard);
				idxI = idx[0];
				idxJ = idx[1];
			}
			
			String selectEngWord = this.comBoard.board[idxI][idxJ].eng;
			System.out.println("Computer choose this >> "+selectEngWord);
			
			if(this.comBoard.board[idxI][idxJ].isChecked==false)
			this.comBoard.board[idxI][idxJ].isChecked = true;
			
			for(int i = 0; i < this.userBoard.getN();i++) {
				for(int j = 0; j < this.userBoard.getN();j++) {
					
					if(selectEngWord.equals(this.userBoard.board[i][j].eng)
							&& this.userBoard.board[i][j].isChecked==false) {
						this.userBoard.board[i][j].isChecked = true;
						break;
					}
				}
			}
		}
		System.out.println();
	}

	private void newGame(boolean comAI) {
		int ranNum = new Random().nextInt(1);
		boolean nowTurn = false;
		boolean isFinished = false;
		System.out.print("input board Size ->");
		int n = scan.nextInt();
		
		if(ranNum == 0) {
			nowTurn = true;
		}
		this.comBoard = new Board("com",n);
		this.userBoard = new Board("user",n);
		
		this.comBoard.generateBoard();
		this.userBoard.generateBoard();
		
		System.out.println("GAME START");
		if(comAI == true) System.out.println("computer start caculate win point");
		System.out.println("-----------------------------\n");
		while(isFinished == false) {

			
			this.userBoard.printBoard();
			this.comBoard.printBoard();
			selectWord(nowTurn,comAI);
			selectWord(!nowTurn,comAI);
			System.out.println();
			int countUserBoardBingo = isBingo(this.userBoard);
			int countComBoardBingo = isBingo(this.comBoard);
			
			if(countUserBoardBingo>0 || countComBoardBingo>0) {
				
				if(countUserBoardBingo > countComBoardBingo) {
					System.out.println("User B!I!N!G!O! Win");
					System.out.println(countUserBoardBingo + " <-- your Bingo");
					System.out.println(countComBoardBingo + " <-- com Bingo");
					this.userBoard.printBoard();
					this.comBoard.printBoard();
					break;
				}
				else if(countUserBoardBingo < countComBoardBingo) {
					System.out.println("Com B!I!N!G!O! Lose");
					System.out.println(countUserBoardBingo + " <-- your Bingo");
					System.out.println(countComBoardBingo + " <-- com Bingo");
					this.userBoard.printBoard();
					this.comBoard.printBoard();
					break;
				}
				else if(countUserBoardBingo == countComBoardBingo) {
					if(isAllOpenBingo(this.comBoard) && isAllOpenBingo(this.userBoard)) {
						System.out.println("DRAW");
						System.out.println(countUserBoardBingo + " <-- your Bingo");
						System.out.println(countComBoardBingo + " <-- com Bingo");
						this.userBoard.printBoard();
						this.comBoard.printBoard();
						break;
					}
				}
			}
			
			
		}
		
		
	}
	
	public void menu() {
		int choice = 0;
		
		while(choice != 3) {
			System.out.println("####### B I N G O #######");
			System.out.println("1) Start (com will be random) 2) Start (com will Caculate WIN) 3) Exit");
			System.out.print("select Number -> ");
			
			try {
				choice = scan.nextInt();
			}catch (InputMismatchException e) {
				choice = 0;
			}
			scan.nextLine();
			System.out.println();

			switch (choice) {
			case 1:
				newGame(false);
				break;
			case 2:
				newGame(true);
				break;

			case 3:
				System.out.println("exit");
				break;
			default:
				break;
			}
		}
	}
}

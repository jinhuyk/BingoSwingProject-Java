package bingo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class GameDialog extends JDialog{
	JPanel gamePanel;
	JPanel boardPanel;
	JPanel inputPanel;
	
	JButton[][] userBoardBtn;
	JButton comBoardBtn;
	
	JTextField inputWordTxt;
	JScrollPane logScrollPane;
	
	JTextArea logTextArea;
	
	String result="";
	ComBoardDialog dlg = null;
	
	Board userBoard;
	Board comBoard;
	int boardSize;
	
	boolean nearBingoCom;
	boolean isFinished=false;
	
	double[] countArray = new double[3];
	
	Game gameSystem;
	
	Container dialog = this.getContentPane();
	
	public GameDialog(MainFrame parent, String title, boolean modal,boolean nearBingoCom) {
		// TODO Auto-generated constructor stub
		
		super(parent,title,modal);
		String score="";
		
		// 승률 계산을 위한 "score.txt"에 저장된 정보 불러오기
		try(Scanner file = new Scanner(new File("score.txt"));) {
			while(file.hasNextLine()) {
				score = file.nextLine();
			}
			file.close();
        } catch (IOException e1) {
			e1.printStackTrace();
		} 
		
		//split을 이용하여 배열에 정보를 저장 후, double 타입으로 변환
		String[] tempArray =score.split(" ");
		for(int i =0;i<3;i++) {
			if(tempArray[i]=="") {
				tempArray[i] = "0";
			}
			countArray[i] = Double.parseDouble(tempArray[i]);
		}
		
		this.setSize(1000,500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(parent);
		this.boardSize = parent.boardSize;
		this.nearBingoCom = nearBingoCom;
		gameInit();
		this.setVisible(true);
	}
	private void gameInit() {
		
		gamePanel = new JPanel(new BorderLayout());
		boardPanel = new JPanel();
		inputPanel = new JPanel(new BorderLayout());
		comBoardBtn = new JButton("computer Board");
		inputWordTxt = new JTextField(30);
		logTextArea = new JTextArea("GAMESTART\nPlease Write The Eng Word\n");
		
		double pRate = Math.round((countArray[1]/countArray[0])*100)/100.0;
		double cRate = Math.round((countArray[2]/countArray[0])*100)/100.0;
		
		String rateStr = "Player Win Rate : "+pRate+"\n";
		rateStr += "Computer Win Rate : "+cRate+"\n";
		addLog(rateStr);
		
		// Random userBoard 생성, comBoard 생성
		makeBoard();
		
		gameSystem = new Game(userBoard,comBoard,this);
		
		boardPanel.setLayout(new GridLayout(boardSize,boardSize));
        userBoardBtn = new JButton[boardSize][boardSize];
        
        //gridLayout을 이용하여 버튼에 해당 보드의 영문을 표기
        for(int i = 0;i<boardSize;i++) {
        	for(int j = 0;j<boardSize;j++) {
            	userBoardBtn[i][j] = new JButton();
            	userBoardBtn[i][j].setText(userBoard.board[i][j].eng);
            	
            	boardPanel.add(userBoardBtn[i][j]);
            }
        }
        
        addLog("==================================================\n");
        
        logScrollPane = new JScrollPane(logTextArea);
        inputPanel.add(logScrollPane,BorderLayout.CENTER);
        inputPanel.add(inputWordTxt,BorderLayout.SOUTH);
        inputPanel.add(comBoardBtn,BorderLayout.NORTH);
        dialog.add(gamePanel,BorderLayout.CENTER);
        gamePanel.add(boardPanel,BorderLayout.CENTER);
        gamePanel.add(inputPanel,BorderLayout.EAST);
        
        // comBoard를 볼수 있는 버튼 이벤트 구현
        comBoardBtn.addActionListener(e->{
			new ComBoardDialog(GameDialog.this, "ComBoard",true);
        });
        // 텍스트창에 단어 입력 시 gameSystem 실행 ( 보드에 단어 여부 확인 알고리즘) 
        inputWordTxt.addActionListener(e->{
        	gameSysFunc();
        });
	}	
	
	// 체크한 단어를 표시하기 위해 보드판을 새로고침 해주는 함수
	private void refreshBoard() {
		for(int i = 0;i<boardSize;i++) {
        	for(int j = 0;j<boardSize;j++) {
            	userBoardBtn[i][j].setText(userBoard.board[i][j].eng);
            	if(userBoard.board[i][j].isChecked==true) {
            		userBoardBtn[i][j].setBackground(Color.YELLOW);
            	}
            	boardPanel.add(userBoardBtn[i][j]);
            }
        }
	}
	
	
	void addLog(String data) {
		String temp = logTextArea.getText();
		logTextArea.setText(temp+data);
	}
	// 보드 생성 함수 
	private void makeBoard() {
		this.comBoard = new Board("com",boardSize);
		this.userBoard = new Board("user",boardSize);
		
		this.comBoard.generateBoard();
		this.userBoard.generateBoard();
	}
	
	// 게임 시스템 
	private void gameSysFunc() {
		String stt ="";
		
		
		String engData = inputWordTxt.getText();
		//단어 입력 후 보드에 단어가 존재하는지 확인 및, com의 빙고 단어 고르기 실행
		gameSystem.selectWord(nearBingoCom, engData.trim());
		
		addLog("==================================================\n");
		inputWordTxt.setText("");
		
		//보드 새로고침
		refreshBoard();
		
		// 현재의 빙고 여부 확인
		int countUserBoardBingo = gameSystem.isBingo(userBoard);
		int countComBoardBingo = gameSystem.isBingo(comBoard);
		
		
		//승패 확인
		if(countUserBoardBingo>0 || countComBoardBingo>0) {
			addLog("Player Bingo : "+ countUserBoardBingo+"\n");
			addLog("Computer Bingo : "+ countComBoardBingo+"\n");
			
			//유저가 이기는 경우
			if(countUserBoardBingo > countComBoardBingo) {
				countArray[1]++;
				countArray[0]++;
				double pRate = Math.round((countArray[1]/countArray[0])*100)/100.0;
				double cRate = Math.round((countArray[2]/countArray[0])*100)/100.0;
				result = (countArray[0])+" "+(countArray[1])+" "+(countArray[2]);
				stt += "Player Win Rate : "+pRate+"\n";
				stt += "Computer Win Rate : "+cRate+"\n";
				stt += "Player Bingo : "+ countUserBoardBingo+"\n";
				stt += "Computer Bingo : "+ countComBoardBingo+"\n";
				new ComBoardDialog(GameDialog.this, "ComBoard",false);
				JOptionPane.showMessageDialog(null,stt + "★ Player가 이겼습니다.");
				isFinished = true;
				dispose();
			}
			
			//컴퓨터가 이기는 경우
			else if(countUserBoardBingo < countComBoardBingo) {
				countArray[0]++;
				countArray[2]++;
				double pRate = Math.round((countArray[1]/countArray[0])*100)/100.0;
				double cRate = Math.round((countArray[2]/countArray[0])*100)/100.0;
				result = (countArray[0])+" "+(countArray[1])+" "+(countArray[2]);
				stt += "Player Win Rate : "+pRate+"\n";
				stt += "Computer Win Rate : "+cRate+"\n";
				stt += "Player Bingo : "+ countUserBoardBingo+"\n";
				stt += "Computer Bingo : "+ countComBoardBingo+"\n";
				new ComBoardDialog(GameDialog.this, "ComBoard",false);
				JOptionPane.showMessageDialog(null, stt + "★ Computer가 이겼습니다.");
				isFinished = true;
				dispose();
			}
			
			// 빙고 개수가 같은 경우
			else if(countUserBoardBingo == countComBoardBingo) {
				// 무승부 
				if(gameSystem.isAllOpenBingo(this.comBoard) && gameSystem.isAllOpenBingo(this.userBoard)) {
					countArray[0]++; countArray[2]++; countArray[1]++;
					double pRate = Math.round((countArray[1]/countArray[0])*100)/100.0;
					double cRate = Math.round((countArray[2]/countArray[0])*100)/100.0;
					result = (countArray[0])+" "+(countArray[1])+" "+(countArray[2]);

					stt += "Player Win Rate : "+pRate+"\n";
					stt += "Computer Win Rate : "+cRate+"\n";
					stt += "Player Bingo : "+ countUserBoardBingo+"\n";
					stt += "Computer Bingo : "+ countComBoardBingo+"\n";
					new ComBoardDialog(GameDialog.this, "ComBoard",false);
					JOptionPane.showMessageDialog(null,stt+ "★ 비겼습니다");
					isFinished = true;
					dispose();
				}
			}
		}
	}
}

/******************************************
- written by Jinhuyk. Mun 12/06/2022
- JAVA programming Personal Project
*******************************************/
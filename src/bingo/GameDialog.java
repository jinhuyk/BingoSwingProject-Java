package bingo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class GameDialog extends JDialog implements ActionListener{
	JPanel gamePanel;
	JPanel boardPanel;
	JPanel inputPanel;
	
	JButton[][] userBoardBtn;
	JButton comBoardBtn;
	
	JTextField inputWordTxt;
	JScrollPane logSPane;
	
	JTextArea logTarea;
	String logStr = "";
	
	String result="";
	ComBoardDialog dlg = null;
	
	Board userBoard;
	Board comBoard;
	int boardSize;
	
	boolean comAi;
	boolean isFinished=false;
	
	
	int countUserBoardBingo;
	int countComBoardBingo;
	
	double[] countArray = new double[3];

	
	Game gameLogic;
	
	Container dialog = this.getContentPane();
	public GameDialog(MainFrame parent, String title, boolean modal,boolean comAi) {
		// TODO Auto-generated constructor stub
		
		super(parent,title,modal);
		String score="";
		try(Scanner file = new Scanner(new File("score.txt"));) {
			while(file.hasNextLine()) {
				score = file.nextLine();
			}
			file.close();
        } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
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
		this.comAi = comAi;
		gameInit();
		this.setVisible(true);
	}
	private void gameInit() {
		
		gamePanel = new JPanel(new BorderLayout());
		boardPanel = new JPanel();
		inputPanel = new JPanel(new BorderLayout());
		comBoardBtn = new JButton("computer Board");
		inputWordTxt = new JTextField(30);
		logTarea = new JTextArea("GAMESTART\nPlease Write The Eng Word\n");
		double pRate = Math.round((countArray[1]/countArray[0])*100)/100.0;
		double cRate = Math.round((countArray[2]/countArray[0])*100)/100.0;
		String stt = "Player Win Rate : "+pRate+"\n";
		stt += "Computer Win Rate : "+cRate+"\n";
		addLog(stt);
		makeBoard();
		gameLogic = new Game(userBoard,comBoard,this);
		inputWordTxt.addActionListener(this);
		boardPanel.setLayout(new GridLayout(boardSize,boardSize));
        userBoardBtn = new JButton[boardSize][boardSize];
        for(int i = 0;i<boardSize;i++) {
        	for(int j = 0;j<boardSize;j++) {
            	userBoardBtn[i][j] = new JButton();
            	userBoardBtn[i][j].setText(userBoard.board[i][j].eng);
            	
            	boardPanel.add(userBoardBtn[i][j]);
            }
        }
        addLog("==================================================\n");
        logSPane = new JScrollPane(logTarea);
        inputPanel.add(logSPane,BorderLayout.CENTER);
        inputPanel.add(inputWordTxt,BorderLayout.SOUTH);
        inputPanel.add(comBoardBtn,BorderLayout.NORTH);
        dialog.add(gamePanel,BorderLayout.CENTER);
        gamePanel.add(boardPanel,BorderLayout.CENTER);
        gamePanel.add(inputPanel,BorderLayout.EAST);
        
        
        comBoardBtn.addActionListener(e->{
			new ComBoardDialog(GameDialog.this, "ComBoard",true);

        });
	}	

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
		String temp = logTarea.getText();
		logTarea.setText(temp+data);
	}
//game source
	private void makeBoard() {
		this.comBoard = new Board("com",boardSize);
		this.userBoard = new Board("user",boardSize);
		
		this.comBoard.generateBoard();
		this.userBoard.generateBoard();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==inputWordTxt) {
			
			
			String stt ="";
			
			
			String engData = inputWordTxt.getText();
			gameLogic.selectWord(comAi, engData);
			
			addLog("==================================================\n");
			inputWordTxt.setText("");
			
			refreshBoard();
			countUserBoardBingo = gameLogic.isBingo(userBoard);
			countComBoardBingo = gameLogic.isBingo(comBoard);
			
			if(countUserBoardBingo>0 || countComBoardBingo>0) {
				addLog("Player Bingo : "+ countUserBoardBingo+"\n");
				addLog("Computer Bingo : "+ countComBoardBingo+"\n");
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
				else if(countUserBoardBingo == countComBoardBingo) {
					
					if(gameLogic.isAllOpenBingo(this.comBoard) && gameLogic.isAllOpenBingo(this.userBoard)) {
						countArray[0]++;
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
	
	
}

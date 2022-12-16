package bingo;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ItemListener, ActionListener{
	Container frame = this.getContentPane();
	JPanel menuPanel;
	
	JCheckBox isNearBingoComCbx; //승리 알고리즘 적용할지 안할지 확인 하는 체크박스
	JButton startBtn; // 게임 시작 버튼
	JTextField inputNumberTxt; // 보드판 크기 입력 Text필드
	JLabel lbl;
	
	
	int boardSize = 3; // 기본 보드판 크기
	boolean isNearBingoCom = false; // 승리 알고리즘 여부 확인

	public MainFrame(String title) {
		//Swing 창 구현
		super(title);
		this.setSize(400,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		init();
		this.setVisible(true);
	}
	
	private void init() {
		// TODO 메인패널 구현 
		mainPanel();
	}

	private void mainPanel() {
		// 게임 세팅 
		menuPanel = new JPanel();
		isNearBingoComCbx = new JCheckBox("Hard?");
		startBtn = new JButton("START BINGO");
		lbl = new JLabel("빙고판 크기(3~7) <enter 누르기>:");
		inputNumberTxt = new JTextField("3",2);
		
		addListener();
		menuPanel.add(isNearBingoComCbx);
		menuPanel.add(lbl);
		menuPanel.add(inputNumberTxt);
		menuPanel.add(startBtn);
		frame.add(menuPanel, BorderLayout.CENTER);
		

	}
	// 각 컴포넌트의 리스너 등록
	private void addListener() {
		isNearBingoComCbx.addItemListener(this);
		startBtn.addActionListener(this);
		inputNumberTxt.addActionListener(this);
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		// random 고르기가 아닌 승리알고리즘이 적용된 컴퓨터를 사용할지 여부 확인
		if(e.getSource() == isNearBingoComCbx) {
			isNearBingoCom = true;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == startBtn) {
			
			// 시작 버튼 누르면 GameDialog 창을 실행 (Modal) 
			GameDialog dlg = new GameDialog(MainFrame.this,"202210748 문진혁",true,isNearBingoCom);
			
			// 게임이 종료 후(Modal 창이 닫히면) 만약 게임이 성공적으로 끝난경우 (승/패/무 가 결정이 되었을때) "score.txt"에 해당 점수를 저장
			if(dlg.isFinished==true) {
				try(FileWriter fos = new FileWriter("score.txt", false);) {
					System.out.println(dlg.result);
					fos.write(dlg.result);
					fos.close();
		        } catch (IOException e1) {
					e1.printStackTrace();
				} 
			}

		}
		if(e.getSource() == inputNumberTxt) {
			
			// 보드판 크기 입력 (만약 다른 문자가 올경우 예외처리)
			try {
				int n = Integer.parseInt(inputNumberTxt.getText());
				if(n>= 3 && n <=7 && new Board("ex",n).wordN >= n*n) {
					boardSize = n;
				}
				else if(n>= 3 && n <=7 && new Board("ex",n).wordN < n*n) {
					JOptionPane.showMessageDialog(null, "단어장의 단어가 부족합니다.");
				}
				else {
					JOptionPane.showMessageDialog(null, "3부터 7사이 정수를 입력해주세요 (기본값 3");
				}
			}
			catch(NumberFormatException err) {
				JOptionPane.showMessageDialog(null, "3부터 7사이 정수를 입력해주세요 (기본값 3");
				inputNumberTxt.setText("3");
			}
		}
	}
}

/******************************************
- written by Jinhuyk. Mun 12/06/2022
- JAVA programming Personal Project
*******************************************/
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
	JPanel gamePanel;
	JPanel boardPanel;
	JCheckBox isComAiCbx;
	JButton startBtn;
	JTextField inputNumberTxt;
	JLabel lbl;
	int boardSize = 3;
	
	int nowState = 0;
	boolean isComAi = false;
	// nowState -> 0 : game menu
	public MainFrame(String title) {
		super(title);
		this.setSize(500,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		nowState = 0;
		init();
		this.setVisible(true);
	}

	private void init() {
		// TODO Auto-generated method stub
		mainPanel();
	}

	private void mainPanel() {
		// TODO Auto-generated method stub
		menuPanel = new JPanel();
		isComAiCbx = new JCheckBox("COM AI?");
		startBtn = new JButton("START BINGO");
		lbl = new JLabel("빙고판 크기(3~7) <enter 누르기>:");
		inputNumberTxt = new JTextField("3",2);
		
		
		
		
		
		startBtn.setVisible(true);
		isComAiCbx.setVisible(true);
		inputNumberTxt.setVisible(true);
		addListener();
		menuPanel.add(isComAiCbx);
		menuPanel.add(lbl);
		menuPanel.add(inputNumberTxt);
		menuPanel.add(startBtn);
		frame.add(menuPanel, BorderLayout.NORTH);
		

	}
	private void addListener() {
		isComAiCbx.addItemListener(this);
		startBtn.addActionListener(this);
		inputNumberTxt.addActionListener(this);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == isComAiCbx) {
			isComAi = true;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == startBtn) {
			
			nowState = 1;
			GameDialog dlg = new GameDialog(MainFrame.this,"Game",true,isComAi);
			if(dlg.isFinished==true) {
				try(FileWriter fos = new FileWriter("score.txt", false);) {
					System.out.println(dlg.result);
					fos.write(dlg.result);
					fos.close();
		        } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
			}

		}
		if(e.getSource() == inputNumberTxt) {
			try {
				int n = Integer.parseInt(inputNumberTxt.getText());
				if(n>= 3 && n <=7) {
					boardSize = n;
				}else {
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

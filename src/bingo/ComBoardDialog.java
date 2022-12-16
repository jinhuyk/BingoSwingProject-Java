package bingo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ComBoardDialog extends JDialog {
	Container dialog = this.getContentPane();
	GameDialog parent = null;
	JPanel boardPanel;
	JButton[][] userBoardBtn;
	Board userBoard;
	int boardSize;
	public ComBoardDialog(GameDialog parent,String title,boolean modal) {
		super(parent,title,modal);
		
		this.parent = parent;
		this.setSize(500,500);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.boardSize = parent.boardSize;
		
		init();
		
		this.setVisible(true);
	}
	
	
	private void init() {
		dialog.setLayout(new BorderLayout());
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(boardSize,boardSize));
        userBoardBtn = new JButton[boardSize][boardSize];
		// TODO Auto-generated method stub
		for(int i = 0;i<boardSize;i++) {
        	for(int j = 0;j<boardSize;j++) {
            	userBoardBtn[i][j] = new JButton();
            	userBoardBtn[i][j].setBackground(Color.GREEN);
            	userBoardBtn[i][j].setText(parent.comBoard.board[i][j].eng);
            	if(parent.comBoard.board[i][j].isChecked==true) {
            		userBoardBtn[i][j].setBackground(Color.YELLOW);
            	}
            	boardPanel.add(userBoardBtn[i][j]);
            }
        }
		dialog.add(boardPanel);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				super.windowClosing(e);
				dispose();
			}
			
		});
	}
}

/******************************************
- written by Jinhuyk. Mun 12/06/2022
- JAVA programming Personal Project
*******************************************/
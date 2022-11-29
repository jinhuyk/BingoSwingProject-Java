package bingo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BingoMain {

	public static void main(String[] args) {
		
		File file = new  File("score.txt");
		if(file.exists()==false) {
			try(FileWriter fos = new FileWriter("score.txt", false);) {
				fos.write("0 0 0");
				fos.close();
	        } catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
		
		// TODO Auto-generated method stub
		new MainFrame("202210748 문진혁");
		
	}

}

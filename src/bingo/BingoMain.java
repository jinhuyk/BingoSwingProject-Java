package bingo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BingoMain {

	public static void main(String[] args) {
		
		// 승률을 저장하는 "score.txt"파일이 존재하지 않는 경우 생성을 해준다.
		File file = new  File("score.txt");
		if(file.exists()==false) {
			try(FileWriter fw = new FileWriter("score.txt", false);) {
				fw.write("0 0 0");
				fw.close();
	        } catch (IOException e1) {
				e1.printStackTrace();
			} 
		}

		new MainFrame("202210748 문진혁");
		
	}

}

/******************************************
- written by Jinhuyk. Mun 12/06/2022
- JAVA programming Personal Project
*******************************************/
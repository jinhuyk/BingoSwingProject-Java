/******************************************
- written by Jinhuyk. Mun 11/20/2022
- JAVA programming Personal Project
*******************************************/

package bingo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Board {
	
	private final int N;
	Word[][] board;

	String username;
	private List<Word> wordList;
	
	public Board(String username, int n) {
		this.N = n;
		this.username = username;
		board = new Word[N][N];

	}
	
	private void makeWordList(String fileName) {
		Set<Word> voc = new HashSet<>();
		try(Scanner scan = new Scanner(new File(fileName))){
			while(scan.hasNextLine()) {
				String str = scan.nextLine();
				String[] temp = str.split("\t");
				voc.add(new Word(temp[0].trim(),temp[1].trim()));
			}
			wordList = new ArrayList<>(voc);
		}catch(FileNotFoundException e) {
			System.out.println("can't find <word.txt> ");
		}
	}
	
	public void generateBoard() {
		makeWordList("words.txt");
		Collections.shuffle(wordList);
		for(int i =0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				board[i][j] = wordList.get(i*N+j);
			}
		}
	}
	
	public void printBoard() {
		String str= "{" + username+" board} \n";
		for(int i =0;i<N;i++) {
			for(int j = 0;j<N;j++) {
				if(board[i][j].isChecked == true) {
					str += "{★}  ";
				}
				else {
					str +="["+ board[i][j].eng + "]  ";
				}

			}
			str += "\n";
		}
		str += "--------------------------\n";
		System.out.println(str);
	}

	public int getN() {
		return N;
	}
	
}

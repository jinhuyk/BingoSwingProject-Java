/******************************************
- written by Jinhuyk. Mun 11/20/2022
- JAVA programming Personal Project
*******************************************/

package bingo;

public class Word {
	String eng;
	String kor;
	boolean isChecked;
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return this.eng.hashCode()+this.kor.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		Word tmp = (Word) obj;
		boolean rst = this.eng.equals(tmp.eng) && this.kor.equals(tmp.kor);
		return rst;
		
	}

	public Word(String eng, String kor) {
		super();
		this.eng = eng;
		this.kor = kor;
		this.isChecked = false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub		
		return eng+"("+kor+")";
	}
	
}

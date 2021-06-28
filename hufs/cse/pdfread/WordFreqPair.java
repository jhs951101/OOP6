/**
 * Created on 2014. 12. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */

package hufs.cse.pdfread;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * @author cskim
 *
 */
public class WordFreqPair implements Comparable<WordFreqPair> {

	private String word = null;
	private Integer freq = null;
	private ArrayList<Integer> pages = new ArrayList<Integer>();  // pages: 단어가 들어간 페이지들을 저장
	
	public WordFreqPair(String word, FnPs fnps){
		this.word = word;
		this.freq = fnps.getFreq();
		
		for(int i=0; i<fnps.getPages().size(); i++){  // (수정)
			this.pages.add( fnps.getPages().get(i) );
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(WordFreqPair wfp) {
		// TODO Auto-generated method stub
		return wfp.freq - freq;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public Integer getFreq() {
		return freq;
	}
	public void setFreq(Integer freq) {
		this.freq = freq;
	}

	public void printPages(){  // 페이지를 모두 출력하는 함수
		
		if(pages.size() == 0)
			return;
		else {
			for(int i=0; i<pages.size()-1; i++){
				System.out.print(pages.get(i) + ",");
			}
			
			System.out.print(pages.get(pages.size()-1));
		}
	}
}

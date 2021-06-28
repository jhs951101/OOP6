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
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author cskim
 *
 */
public class WordExtractTest {

	static final String INPUT_TEXT	= "src/result/TextBook.txt";  // (수정)
	static final String FILTER_TEXT = "src/data/filter100.txt";  // (수정)
	static final String JH_FILTER_TEXT = "src/data/201401793_jhshim_filter.txt";  // My단어리스트 파일 경로
	static HashMap<String,FnPs> wordFreqMap = new HashMap<String,FnPs>();  // (수정)
	static ArrayList<WordFreqPair> wfList = new ArrayList<WordFreqPair>();
	static Set<String> filterSet = new HashSet<String>();
	/**
	 * @param args
	 */
	public static void main(String[] args) {


		Scanner input = null;
		Scanner input2 = null;  // input2: My단어리스트 파일을 담는 Scanner
		// Read all Text and Build keyword-frequency hash map
		try {
			input = new Scanner(new File(INPUT_TEXT));
			Integer page = null;  // page: 쪽번호를 저장하는 변수
			
			while (input.hasNext()){
				String textLine = input.nextLine();

				if(textLine.length() >= 4){  // 페이지를 설정하는 부분
					if( (textLine.substring(0,4)).equals("--- ") ){
						String[] arr = textLine.split(" ");
						page = Integer.valueOf(arr[1]);
					}
				}

				String[] words = textLine.split("(\\s|\\p{Punct})+");

				for (int i=0; i<words.length; ++i){
					//System.out.println(words[i]);
					if (words[i].matches("\\p{Alpha}+")){
						String lword = words[i].toLowerCase();
						FnPs fnps = wordFreqMap.get(lword);  // (수정)
						Integer freq = null;  // (수정)
						
						if(fnps == null){  // (수정)
							wordFreqMap.put(lword, new FnPs(1,page));
						}
						else {  // (수정)
							freq = fnps.getFreq();
							freq++;
							fnps.setFreq(freq);
							fnps.addPage(page);
							wordFreqMap.put(lword, fnps);
						}
					}
				}

			}
			input.close();


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Build Filter Set
		try {
			input = new Scanner(new File(FILTER_TEXT));
			while (input.hasNext()){
				String textLine = input.nextLine().trim();
				filterSet.add(textLine);
				
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("filter size="+filterSet.size());
		input.close();

		try {
			input = new Scanner(new File(JH_FILTER_TEXT));
			while (input.hasNext()){
				String textLine = input.nextLine().trim();
				filterSet.add(textLine);
				
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("filter size="+filterSet.size());
		//input2.close();
		
		// sort keyword by frequency
		Set<String> keys = wordFreqMap.keySet();
		for (String k:keys){
			//System.out.println(k+"--"+wordFreqMap.get(k));
			if (!filterSet.contains(k)){
				wfList.add(new WordFreqPair(k, wordFreqMap.get(k)));
			}
		}
		
		Object[] wfArray = wfList.toArray();
		Arrays.sort(wfArray);
		
		/*for (Object obj:wfArray){
			WordFreqPair wf = (WordFreqPair)obj;
			System.out.print(wf.getFreq() + "--" + wf.getWord() + " -- ");  // (수정)
			
			wf.printPages();  // 단어가 들어간 페이지를 모두 출력하는 함수
			
			System.out.println("");  // (수정)
			
			//System.out.println(wf.getWord());
		}*/
		
		for (int i=0; i<30; i++){
			Object obj = wfArray[i];
			WordFreqPair wf = (WordFreqPair)obj;
			System.out.print(wf.getFreq() + "--" + wf.getWord() + " -- ");  // (수정)
		
			wf.printPages();  // 단어가 들어간 페이지를 모두 출력하는 함수
		
			System.out.println("");  // (수정)
		
			//System.out.println(wf.getWord());
		}

	}

}

class FnPs  // FnPs: 단어의 갯수(freq)와 존재 페이지(pages)를 담는 클래스
{
	private Integer freq = null;
	private ArrayList<Integer> pages = new ArrayList<Integer>();
	
	public FnPs(){}
	
	public FnPs(Integer freq, Integer page){
		this.freq = freq;
		
		if(!isPageExist(page)) pages.add(page);
	}
	
	public boolean isPageExist(Integer page){  // 해당 페이지가 이미 저장되어 있는지 확인하는 함수
		for(int i=0; i<pages.size(); i++)
			if(pages.get(i) == page)
				return true;
		
		return false;
	}

	public ArrayList<Integer> getPages(){
		return this.pages;
	}
	
	public void setFreq(Integer freq){
		this.freq = freq;
	}
	
	public Integer getFreq(){
		return this.freq;
	}
	
	public void addPage(Integer page){  // 해당 페이지를 add하는 함수
		if(!isPageExist(page)) pages.add(page);
	}
}

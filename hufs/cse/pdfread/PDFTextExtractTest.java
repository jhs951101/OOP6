/**
 * Created on 2014. 12. 4.
 * @author cskim -- hufs.ac.kr, Dept of CSE
 * Copy Right -- Free for Educational Purpose
 */

package hufs.cse.pdfread;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

/**
 * @author cskim
 *
 */
public class PDFTextExtractTest {

	static final String OUTPUT_TEXT	= "src/result/TextBook.txt";
	static final String INPUT_PDF = "src/data/TextBookNode.pdf";
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		PrintWriter writer = null;   
		PdfReader reader = null;
		try {
			writer = new PrintWriter(new FileOutputStream(OUTPUT_TEXT));
			reader = new PdfReader(INPUT_PDF);
			
			int n = reader.getNumberOfPages();
			for (int ipage = 1; ipage <= n; ipage++) {
				String page = null;
				try {
				page = PdfTextExtractor.getTextFromPage(reader, ipage);
				} catch (java.lang.IllegalArgumentException e) {
					page = null;
				}
				if (page != null && page !="") { 	            	
					Scanner inpage = new Scanner(page);

					writer.println("--- "+ipage+" ---");
					while (inpage.hasNext()){
						writer.println(inpage.nextLine());
					}
					inpage.close();
				}
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

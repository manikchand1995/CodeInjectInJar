package home.utils.codeinject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class LineNumberFinder {


    @SuppressWarnings("finally")
	public static List<String> searchWord(String key,String filePath) {

        LineNumberReader lnr;
		try {
			lnr = new LineNumberReader(new FileReader(new File(filePath)));
			return recursiveSearch(lnr.readLine(), key, lnr,filePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			return null;
		}
    }

    public static List<String> recursiveSearch(String currentLineText, String key, LineNumberReader lnr,String filePath)
                     {

        List<String> results = new ArrayList<String>(25);
        List<String> lineNumbers = new ArrayList<String>(25);
        if (currentLineText != null) {
            String lCase = currentLineText.toLowerCase();
            if (lCase.contains(key.toLowerCase())) {

                results.add("Line " + lnr.getLineNumber() + ": " + currentLineText);
                lineNumbers.add(String.valueOf(lnr.getLineNumber()));
                System.out.println("Line : "+key+"\n \t is found at "+lnr.getLineNumber()+" in "+filePath);
            }
            try {
				lineNumbers.addAll(recursiveSearch(lnr.readLine(), key, lnr,filePath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        return lineNumbers;
    }

}
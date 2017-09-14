package home.utils.codeinject;
import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class LineNumberFinder {


	//	public static List<String> searchWord(String key,String filePath) throws IOException {
	//
	//        LineNumberReader lnr;
	//	
	//			lnr = new LineNumberReader(new FileReader(new File(filePath)));
	//			return recursiveSearch(lnr.readLine(), key, lnr,filePath);
	//
	//
	//    }

	public static List<String> recursiveSearch(String currentLineText, String key, LineNumberReader lnr,String filePath) throws IOException
	{

		List<String> results = new ArrayList<String>(25);
		List<String> lineNumbers = new ArrayList<String>(25);
		if (currentLineText != null) {
			String lCase = currentLineText.toLowerCase().replace(" ", "");
			if (lCase.contains(key.toLowerCase().replace(" ", ""))) {

				results.add("Line " + lnr.getLineNumber() + ": " + currentLineText);
				lineNumbers.add(String.valueOf(lnr.getLineNumber()));
				System.out.println("Line <-- "+key+" --> is found at "+lnr.getLineNumber()+" in "+filePath);
			}

			lineNumbers.addAll(recursiveSearch(lnr.readLine(), key, lnr,filePath));

		}
		lnr.close();
		return lineNumbers;

	}
	@SuppressWarnings("finally")
	public static List<String>searchWord(String key, String filePath)
	{
		File file = new File(filePath);
		List<String> lineNumbers = new ArrayList<String>(25);
		try 
		{
			List<String> lines = FileUtils.readLines(file, "UTF-8");
			for(String line : lines)
			{
				if (line != null) {
					String lCase = line.replace(" ", "");
					if (lCase.contains(key.replace(" ", ""))) {
						lineNumbers.add(String.valueOf(lines.indexOf(line)+1));
						System.out.println("Line <-- "+key+" --> is found at "+lines.indexOf(line)+1+" in "+filePath);
					}
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			return lineNumbers;
		}
	}

}
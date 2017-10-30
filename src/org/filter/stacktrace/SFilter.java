package org.filter.stacktrace;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * To avoid huge dumping of unwanted printing of stack trace, 
 * Users can use this SFilter to filter the stacktrace based on his requirement.
 * <br>
 * {@link SData} provides some default filter conditions that can be used.
 * Users can also extend {@link SData} interface to create their own filter condition. 
 * <br>
 * Example:<br>
 * <pre>
 * try
 * {
 * 	.....
 * }	
 * 
 * catch(Exception e)
 * {	
 * 	......
 * 	e.printStackTrace();	//prints the entire stacktrace without any filteration
 * 	SFilter.printStackTrace(e);	//prints after applying default filter. Refer {@linkplain #printStackTrace(Throwable)}
 * 	SFilter.printStackTrace(e,SData.ONLY_JAVA);	//prints after applying the given filter. Refer {@linkplain #printStackTrace(Throwable, List)}
 * 	SFilter.setDefaultFilter(SData.NO_JAVA);	//refer {@linkplain #setDefaultFilter(List)}
 * 	SFilter.resetDefaultFilter();	//refer {@linkplain #resetDefaultFilter()}
 * }
 * </pre>
 * 
 * By default, {@linkplain SData#ONLY_ZOHO} condition is applied for SFilter. 
 * This can be changed by using {@linkplain SFilter#setDefaultFilter(List)}
 * 
 * @author mani-5328
 *
 */
public class SFilter
{

	private static List<String[]> defaultFilter = SData.ONLY_ZOHO;
	static PrintWriter writer;
	static PrintStream stream;

	/**
	 * Prints Stacktrace for the given exception 
	 * after the excluding and including some traces based on given arrays.
	 * @param e the throwable instance
	 * @param exclude the traces containing these strings are excluded
	 * @param include the traces containing these strings are included
	 */
	 public static void printStackTrace(Throwable e,String[] exclude,String[] include)
	 {
		 StackTraceElement[] trace = e.getStackTrace();
		 if(exclude != null && exclude.length!=0){trace=exclude(trace,exclude);}
		 if(include != null && include.length!=0){trace=include(trace,include);}
		 e.setStackTrace(trace);
		 if(writer==null && stream==null)
		 {e.printStackTrace();}
		 else
		 {
			 if(writer!=null)
			 {
				 e.printStackTrace(writer);
			 }
			 else
			 {
				 e.printStackTrace(stream); 
			 }

		 }
	 }

	 /**
	  * Sets the custom {@linkplain PrintWriter} for displaying the exception
	  * @param printWriter the printwriter instance
	  */
	 public static void setPrintWriter(PrintWriter printWriter)
	 {
		 writer = printWriter;
	 }

	 /**
	  * Sets the custom {@linkplain PrintStream} for displaying the exception
	  * @param printStream the printstream instance
	  */
	 public static void setPrintStream(PrintStream printStream)
	 {
		 stream = printStream;
	 }
	 
	 /**
	  * Handles the exclude part of SFilter
	  * @param trace the entire stacktrace array
	  * @param exclude the traces containing these strings are excluded
	  * @return the stacktrace array after exclusion
	  */
	 private static StackTraceElement[] exclude(StackTraceElement[] trace, String[] exclude)
	 {
		 List<StackTraceElement> list = new ArrayList<StackTraceElement>();
		 for(int i=0;i<trace.length;i++)
		 {
			 int count=0;
			 for(int j=0; j<exclude.length;j++)
			 {	
				 if(!trace[i].getClassName().startsWith(exclude[j]))
				 {
					 count++;
				 } 
			 }
			 if(count==exclude.length || i==0)
			 {
				 list.add(trace[i]);
			 }
		 }
		 return listToStackTrace(list);
	 }
	 
	 /**
	  * Handles the include part of SFilter
	  * @param trace the entire stacktrace array
	  * @param include the traces containing these strings are included
	  * @return the stacktrace array after inclusion
	  */
	 private static StackTraceElement[] include(StackTraceElement[] trace, String[] include)
	 {
		 List<StackTraceElement> list = new ArrayList<StackTraceElement>();
		 for(int i=0;i<trace.length;i++)
		 {
			 int count=0;
			 for(int j=0; j<include.length;j++)
			 {	
				 if(trace[i].getClassName().startsWith(include[j]))
				 {
					 count++;
				 }
			 }
			 if(count>0 || i==0)
			 {
				 list.add(trace[i]);
			 }
		 }
		 return listToStackTrace(list);
	 }

	 /**
	  * Converts a list of StackTraceElement(s) into StackTraceElement array.
	  * @param list the list of StackTraceElement(s)
	  * @return the StackTraceElement array
	  */
	 private static StackTraceElement[] listToStackTrace(List<StackTraceElement> list)
	 {
		 StackTraceElement[] output = new StackTraceElement[list.size()];
		 for(int i=0; i<list.size();i++)
		 {
			 output[i]=(StackTraceElement)list.get(i);
		 }
		 return output;
	 }

	 /**
	  * Prints Stacktrace for the given exception 
	  * after applying the given filter.<p>
	  * Default filter conditions are provided in {@link SData} and 
	  * users can also create new conditions by extending the {@linkplain SData} interface
	  * </p>
	  * @param e the throwable instance
	  * @param filterCondition the filter condition for stackfilter
	  * @see SData
	  */
	 public static void printStackTrace(Throwable e,List<String[]> filterCondition)
	 {
		 printStackTrace(e, filterCondition.get(0), filterCondition.get(1));
	 }
	 
	 /**
	  * Prints Stacktrace for the given exception 
	  * after applying the defaultfilter mentioned in {@link #defaultFilter} 
	  * @param e the throwable instance
	  * @see #printStackTrace(Throwable, List)
	  */
	 public static void printStackTrace(Throwable e)
	 {
		 printStackTrace(e, defaultFilter);
	 }
	 
	 /**
	  * Sets the default filter to be applied throughout SFilter  
	  * @param defaultFilter the default filter condition
	  * @see SData
	  */
	 public static void setDefaultFilter(List<String[]> defaultFilter)
	 {
		 SFilter.defaultFilter = defaultFilter;
	 }
	 
	 /**
	  * Resets the default filter condition of SFilter to {@link SData#ONLY_ZOHO}
	  * @see SData
	  */
	 public static void resetDefaultFilter()
	 {
		 SFilter.defaultFilter = SData.ONLY_ZOHO;
	 }
}

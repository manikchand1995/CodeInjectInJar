package org.filter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * To avoid huge dumping of unwanted printing of stack trace, 
 * Users can use this Stack to filter the stacktrace based on his requirement.
 * <br>
 * {@link StackFilterData} provides some default filter conditions that can be used.
 * Users can also extend {@link StackFilterData} interface to create their own filter condition. 
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
 * 	Stack.printStackTrace(e);	//prints after applying default filter. Refer {@linkplain #printStackTrace(Throwable)}
 * 	Stack.printStackTrace(e,SData.ONLY_JAVA);	//prints after applying the given filter. Refer {@linkplain #printStackTrace(Throwable, List)}
 * 	Stack.setDefaultFilter(SData.NO_JAVA);	//refer {@linkplain #setDefaultFilter(String)}
 * 	Stack.resetDefaultFilter();	//refer {@linkplain #resetDefaultFilter()}
 * }
 * </pre>
 * 
 * By default,  condition is applied for Stack. 
 * This can be changed by using {@linkplain StackTraceFilterCopy#setDefaultFilter(String)}
 * 
 * @author mani-5328
 *
 */
public class StackTraceFilterCopy
{

	private static List<String[]> defaultFilter;
	static PrintWriter writer;
	static PrintStream stream;
	private static boolean isFilterInitiated = false;
	private static StackFilterData sData;
    private static Logger logger  =  Logger.getLogger(StackTraceFilterCopy.class.getName());

	/**
	 * Prints Stacktrace for the given exception 
	 * after the excluding and including some traces based on given arrays.
	 * @param e the throwable instance
	 * @param exclude the traces containing these strings are excluded
	 * @param include the traces containing these strings are included
	 */
	public static void printStackTrace(Throwable e,String[] exclude,String[] include)
	{
		e.setStackTrace(getStackTrace(e, exclude, include));
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

	public static StackTraceElement[] getStackTrace(Throwable e,String[] exclude,String[] include)
	{
		StackTraceElement[] trace = e.getStackTrace();
		if(exclude != null && exclude.length!=0){trace=exclude(trace,exclude);}
		if(include != null && include.length!=0){trace=include(trace,include);}
		return trace;
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
	 * Handles the exclude part of Stack
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
	 * Handles the include part of Stack
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
	 * Default filter conditions are provided in {@link StackFilterData} and 
	 * users can also create new conditions by extending the {@linkplain StackFilterData} interface
	 * </p>
	 * @param e the throwable instance
	 * @param filterCondition the filter condition for stackfilter
	 * @see StackFilterData
	 */
	public static void printStackTrace(Throwable e,List<String[]> filterCondition)
	{
		e.setStackTrace(getStackTrace(e, filterCondition.get(0), filterCondition.get(1)));
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

	public static StackTraceElement[] getStackTrace(Throwable e,List<String[]> filterCondition)
	{
		return getStackTrace(e, filterCondition.get(0), filterCondition.get(1));
	}

	/**
	 * Prints Stacktrace for the given exception 
	 * after applying the defaultfilter mentioned in {@link #defaultFilter} 
	 * @param e the throwable instance
	 * @see #printStackTrace(Throwable, List)
	 */
	public static void printStackTrace(Throwable e)
	{
		e.printStackTrace();
		e.setStackTrace(getStackTrace(e));
        logger.log(Level.SEVERE,"Filtered exception :",e);
		if(writer==null && stream==null)
		{
			e.printStackTrace();	}
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
	 */
	public static StackTraceElement[] getStackTrace(Throwable e)
	{
		checkAndInitialize();
		return getStackTrace(e, defaultFilter);
	}

	/**
	 * Sets the default filter to be applied throughout Stack  
	 * @param filterKey the default filter condition
	 * @see StackFilterData
	 */
	public static void setDefaultFilter(String filterKey)
	{
		checkAndInitialize();
		sData.setAsDefault(filterKey);
		StackTraceFilterCopy.defaultFilter = sData.getDefault();
		isFilterInitiated=true;
	}

	/**
	 * Resets the default filter condition of Stack to 
	 * @see StackFilterData
	 */
	public static void resetDefaultFilter()
	{
		checkAndInitialize();
		sData.resetDefaultFilter();
		defaultFilter = sData.getDefault();
	}

	private static void checkAndInitialize()
	{
		if(!isFilterInitiated)
		{sData = new StackFilterData();
		defaultFilter = sData.getDefault();
		isFilterInitiated=true;}
	}

	public static String toString(Throwable e)
	{
		return traceToString(e,getStackTrace(e));
	}
	public static String toString(Throwable e,List<String[]> filterCondition)
	{
		return traceToString(e,getStackTrace(e,filterCondition));
	}
	public static String toString(Throwable e,String[] exclude,String[] include)
	{
		return traceToString(e,getStackTrace(e,exclude,include));
	}
	private static String traceToString(Throwable e, StackTraceElement[] traceArray)
	{
		String exceptionString = "";
		exceptionString = exceptionString+(e);
		for(StackTraceElement trace : traceArray)
		{
			exceptionString = exceptionString+("\n\t at "+trace.getClassName()+"."+trace.getMethodName()+"("+trace.getFileName()+":"+trace.getLineNumber()+")");
		}
		return exceptionString;
	}
}

package org.filter.stacktrace;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StackFilter
{
	static String[] excludePackages;
	static String[] includePackages;

	StackTraceElement[] traces;
	static PrintWriter writer;
	static PrintStream stream;
	/**
	 * returns <code>StackTraceElement[]</code> for the given exception without any filteration
	 * **/
	public static StackTraceElement[] getStackTrace(Throwable e)
	{
		return getStackTrace(e,"",null);
	}
	/**
	 * returns StackTrace as <code>String</code> for the given exception without any filteration
	 * **/
	public static String getStackTraceAsString(Throwable e)
	{
		return traceToString(e,getStackTrace(e,"",null));
	}
	/**
	 * returns <code>StackTraceElement[]</code> for the given exception excluding the traces containing 
	 * the second parameter (String exclude) 
	 * **/
	public static StackTraceElement[] getStackTrace(Throwable e,String exclude)
	{
		return getStackTrace(e,exclude,null);
	}
	/**
	 * returns StackTrace as <code>String</code> for the given exception excluding the traces containing 
	 * the second parameter (String exclude) 
	 * **/
	public static String getStackTraceAsString(Throwable e,String exclude)
	{
		return traceToString(e,getStackTrace(e,exclude,null));
	}
	/**
	 * returns <code>StackTraceElement[]</code> for the given exception excluding the traces containing 
	 * the second parameter (String exclude) and then the obtained
	 * **/
	public static StackTraceElement[] getStackTrace(Throwable e,String[] exclude)
	{
		return getStackTrace(e,exclude,null);
	}
	/**
	 * returns StackTrace as <code>String</code> for the given exception excluding the traces containing 
	 * any other String in given array of Strings (String[] exclude) 
	 * **/
	public static String getStackTraceAsString(Throwable e,String[] exclude)
	{
		return traceToString(e,getStackTrace(e,exclude,null));
	}
	/**
	 * returns <code>StackTraceElement[]</code> for the given exception excluding the traces containing 
	 * the second parameter (String exclude) and then the obtained
	 * stacktrace is even filtered by displaying only the traces having 
	 * the third parameter (String include).	 
	 * * **/
	public static StackTraceElement[] getStackTrace(Throwable e,String exclude,String include)
	{
		String[] excludeArray;
		String[] includeArray;
		if(exclude!=null && exclude!=""){ excludeArray = new String[]{exclude};}
		else{excludeArray = null;}
		if(include!=null && include!=""){ includeArray = new String[]{include};}
		else{includeArray = null;}
		return getStackTrace(e,excludeArray,includeArray);
	}
	/**
	 * returns StackTrace as <code>String</code> for the given exception excluding the traces containing 
	 * the second parameter (String exclude) and then the obtained
	 * stacktrace is even filtered by displaying only the traces having 
	 * the third parameter (String include).
	 ** **/
	public static String getStackTraceAsString(Throwable e,String exclude,String include)
	{
		String[] excludeArray;
		String[] includeArray;
		if(exclude!=null && exclude!=""){ excludeArray = new String[]{exclude};}
		else{excludeArray = null;}
		if(include!=null && include!=""){ includeArray = new String[]{include};}
		else{includeArray = null;}
		return traceToString(e,getStackTrace(e,excludeArray,includeArray));
	}
	/**
	 * returns <code>StackTraceElement[]</code>  for the given exception excluding the traces containing 
	 * any other String in given array of Strings (String[] exclude) and then the obtained
	 * stacktrace is even filtered by displaying only the traces having all the strings given
	 * in third parameter (String[] include).
	 * 	 * **/
	public static StackTraceElement[] getStackTrace(Throwable e,String[] exclude,String[] include)
	{
		StackTraceElement[] trace = e.getStackTrace();
		if(exclude != null && exclude.length!=0){trace=exclude(trace,exclude);}
		if(include != null && include.length!=0){trace=include(trace,include);}
		return trace;
	}
	/**
	 * returns StackTrace as <code>String</code> for the given exception excluding the traces containing 
	 * any other String in given array of Strings (String[] exclude) and then the obtained
	 * stacktrace is even filtered by displaying only the traces having all the strings given
	 * in third parameter (String[] include).
	 * **/	public static String getStackTraceAsString(Throwable e,String[] exclude,String[] include)
	 {
		 StackTraceElement[] trace = e.getStackTrace();
		 if(exclude != null && exclude.length!=0){trace=exclude(trace,exclude);}
		 if(include != null && include.length!=0){trace=include(trace,include);}
		 return traceToString(e,trace);
	 }
	 /**
	  * prints Stacktrace for the given exception
	  * **/
	 public static void printStackTrace(Throwable e)
	 {
		 printStackTrace(e,"",null);
	 }
	 /**
	  * prints Stacktrace for the given exception excluding the traces containing 
	  * the second parameter (String exclude)
	  ****/
	 public static void printStackTrace(Throwable e,String exclude)
	 {
		 printStackTrace(e,exclude,null);
	 }
	 /**
	  * prints Stacktrace for the given exception excluding the traces containing 
	  * any other String in given array of Strings (String[] exclude) 
	  * **/
	 public static void printStackTrace(Throwable e,String[] exclude)
	 {
		 printStackTrace(e,exclude,null);
	 }
	 /**
	  * prints Stacktrace for the given exception excluding the traces containing 
	  * the second parameter (String exclude) and then the obtained
	  * stacktrace is even filtered by displaying only the traces having 
	  * the third parameter (String include).
	  * **/
	 public static void printStackTrace(Throwable e,String exclude,String include)
	 {
		 String[] excludeArray;
		 String[] includeArray;
		 if(exclude!=null && exclude!=""){ excludeArray = new String[]{exclude};}
		 else{excludeArray = null;}
		 if(include!=null && include!=""){ includeArray = new String[]{include};}
		 else{includeArray = null;}
		 printStackTrace(e,excludeArray,includeArray);
	 }
	 //	/**
	 //	 * prints Stacktrace for the given exception excluding the traces containing 
	 //	 * any other String in given array of Strings (String[] exclude) and then the obtained
	 //	 * stacktrace is even filtered by displaying only the traces having 
	 //	 * the third parameter (String include).
	 //	 * **/
	 //	public static void printStackTrace(Throwable e,String[] exclude,String include)
	 //	{
	 //		String[] includeArray;
	 //		if(include!=null && include!=""){ includeArray = new String[]{include};}
	 //		else{includeArray = null;}
	 //		printStackTrace(e,exclude,includeArray);
	 //	}
	 //	/**
	 //	 * prints Stacktrace for the given exception excluding the traces containing 
	 //	 * the second parameter (String exclude) and then the obtained
	 //	 * stacktrace is even filtered by displaying only the traces having all the strings given
	 //	 * in third parameter (String[] include).
	 //	 * **/
	 //	public static void printStackTrace(Throwable e,String exclude,String[] include)
	 //	{
	 //		String[] excludeArray;
	 //		if(exclude!=null && exclude!=""){ excludeArray = new String[]{exclude};}
	 //		else{excludeArray = null;}
	 //		printStackTrace(e,excludeArray,include);
	 //	}
	 /**
	  * prints Stacktrace for the given exception excluding the traces containing 
	  * any other String in given array of Strings (String[] exclude) and then the obtained
	  * stacktrace is even filtered by displaying only the traces having all the strings given
	  * in third parameter (String[] include).
	  * **/
	 public static void printStackTrace(Throwable e,String[] exclude,String[] include)
	 {
		 StackTraceElement[] trace = e.getStackTrace();
		 if(exclude != null && exclude.length!=0){trace=exclude(trace,exclude);}
		 if(include != null && include.length!=0){trace=include(trace,include);}
		 e.setStackTrace(trace);
		 if(StackFilter.writer==null && StackFilter.stream==null)
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
	  * sets the custom <code>PrintWriter</code> for displaying the exception
	  * **/
	 public static void setPrintWriter(PrintWriter printWriter)
	 {
		 writer = printWriter;
	 }
	 /**
	  * sets the custom <code>PrintStream</code> for displaying the exception
	  * **/
	 public static void setPrintStream(PrintStream printStream)
	 {
		 stream = printStream;
	 }
	 /**
	  * handles the include part of StackFilter
	  * **/
	 private static StackTraceElement[] exclude(StackTraceElement[] trace, String[] exclude)
	 {
		 List<StackTraceElement> list = new ArrayList<StackTraceElement>();
		 for(int i=0	;i<trace.length;i++)
		 {
			 int count=0;
			 for(int j=0; j<exclude.length;j++)
			 {	
				 if(!trace[i].getClassName().contains(exclude[j]))
				 {
					 count++;
				 } 
			 }
			 if(count==exclude.length)
			 {
				 list.add(trace[i]);
			 }
		 }
		 return listToStackTrace(list);
	 }
	 /**
	  * handles the include part of StackFilter
	  * **/
	 private static StackTraceElement[] include(StackTraceElement[] trace, String[] include)
	 {
		 List<StackTraceElement> list = new ArrayList<StackTraceElement>();
		 for(int i=0	;i<trace.length;i++)
		 {
			 int count=0;
			 for(int j=0; j<include.length;j++)
			 {	
				 if(trace[i].getClassName().contains(include[j]))
				 {
					 count++;
				 } 
			 }
			 if(count>0)
			 {
				 list.add(trace[i]);
			 }
		 }
		 return listToStackTrace(list);
	 }
	 /**
	  * converts a <code>list<StackTraceElement></code> into a <code>StackTraceElement[]</code>
	  * **/
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
	  * converts a <code>StackTraceElement[]</code> into a compatible String
	  * **/
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
	 
	 public static void printStackTrace(Throwable e,List<String[]> filterConf)
	 {
		 printStackTrace(e, filterConf.get(0), filterConf.get(1));
	 }
}

package org.filter.stacktrace;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StackFilter
{
	StackTraceElement[] traces;
	static PrintWriter writer;
	static PrintStream stream;
	public static StackTraceElement[] getStackTrace(Throwable e)
	{
		return getStackTrace(e,"",null);
	}
	public static StackTraceElement[] getStackTrace(Throwable e,String exclude)
	{
		return getStackTrace(e,exclude,null);
	}
	public static StackTraceElement[] getStackTrace(Throwable e,String[] exclude)
	{
		return getStackTrace(e,exclude,null);
	}
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
	public static StackTraceElement[] getStackTrace(Throwable e,String[] exclude,String[] include)
	{
		StackTraceElement[] trace = e.getStackTrace();
		if(exclude != null && exclude.length!=0){trace=exclude(trace,exclude);}
		if(include != null && include.length!=0){trace=include(trace,include);}
		return trace;
	}
	public static void printStackTrace(Throwable e)
	{
		printStackTrace(e,"",null);
	}
	public static void printStackTrace(Throwable e,String exclude)
	{
		printStackTrace(e,exclude,null);
	}
	public static void printStackTrace(Throwable e,String[] exclude)
	{
		printStackTrace(e,exclude,null);
	}
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
	public static void setPrintWriter(PrintWriter writer1)
	{
		writer = writer1;
	}
	public static void setPrintStream(PrintStream stream1)
	{
		stream = stream1;
	}

	public static StackTraceElement[] exclude(StackTraceElement[] trace, String[] exclude)
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

	public static StackTraceElement[] include(StackTraceElement[] trace, String[] include)
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
	public static StackTraceElement[] listToStackTrace(List list)
	{
		StackTraceElement[] output = new StackTraceElement[list.size()];
		for(int i=0; i<list.size();i++)
		{
			output[i]=(StackTraceElement)list.get(i);
		}
		return output;
	}
    public static String getStackAsString(Throwable e)
    {
   	 String exceptionString = "";
   	 StackTraceElement[] traceArray = e.getStackTrace();
   	 exceptionString = exceptionString+(e+" : "+e.getMessage());
   	 for(StackTraceElement trace : traceArray)
   	 {
   		 exceptionString = exceptionString+("\n\t at "+trace.getClassName()+"."+trace.getMethodName()+"("+trace.getFileName()+":"+trace.getLineNumber()+")");
   	 }
   	 return exceptionString;
    }

}

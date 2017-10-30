package home.test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.filter.StackTraceFilter;

public class checkWorks {

	public static void main(String[] args) throws Exception {
		Boolean bool = true;
		Integer integer;
		String str ="a";
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceFilter.setDefaultFilter("8");
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
			//assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			StackTraceFilter.printStackTrace(e);

			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(i==1)
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace doesn't contain the included element", bool);
		}
	
	}
}
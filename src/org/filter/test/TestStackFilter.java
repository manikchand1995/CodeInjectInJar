// $Id$
package org.filter.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.filter.StackTraceFilter;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestStackFilter
{
	String str = "str";
	int integer ;

	@BeforeClass
	public static void init() 
	{
	System.setProperty("server.home", "/home/likewise-open/ZOHOCORP/mani-5328/git/myworks/src/org/filter");
	}

	
	@Test
	public void exclude() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
			assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			
			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(traceArray[i].getClassName().startsWith("org.junit"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace contains the excluded element", bool);
		}
	}
	@Test
	public void include() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceFilter.setDefaultFilter("2");
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
			assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			
			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(!traceArray[i].getClassName().startsWith("org.junit"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace doesn't contain the included element", bool);
		}
	}
	@Test
	public void excludeArray() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceFilter.setDefaultFilter("3");
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
			assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			
			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(traceArray[i].getClassName().startsWith("org.junit")||traceArray[i].getClassName().startsWith("sun.reflect"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace contains the excluded Array element", bool);

		}
	}
	@Test
	public void includeArray() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceFilter.setDefaultFilter("4");
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
			assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(!traceArray[i].getClassName().startsWith("org.junit")&&!traceArray[i].getClassName().startsWith("sun.reflect"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace doesn't contain the included Array element", bool);
		}
		
		
	}
	@Test
	public void excludeInclude() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceFilter.setDefaultFilter("5");
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
			assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(!traceArray[i].getClassName().startsWith("sun.reflect"))
				{
					bool = false;
				}
				if(traceArray[i].getClassName().startsWith("java.lang"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace contains the excluded element or doesn't contain the included element", bool);
		}
	}
	
	@Test
	public void excludeIncludeArray() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceFilter.setDefaultFilter("6");
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
			assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(!traceArray[i].getClassName().startsWith("org.eclipse.jdt") && !traceArray[i].getClassName().startsWith("org.junit.runners.ParentRunner"))
				{
					bool = false;
				}
				if(traceArray[i].getClassName().startsWith("org.junit.internal.runners") || traceArray[i].getClassName().startsWith("sun.reflect"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace contains the excluded Array element or doesn't contain the included Array element", bool);
		}
	}
	@Test
	public void testResetToDefault() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceFilter.resetDefaultFilter();
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
			assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			
			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(traceArray[i].getClassName().startsWith("org.junit"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace contains the excluded element", bool);
		}
	}
	@Test
	public void testListParams() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e, new ArrayList<String[]>
			(Arrays.asList(new String[]{"org.eclipse"},new String[]{"com.zoho","com.adventnet"})));
			assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(!traceArray[i].getClassName().startsWith("org.eclipse"))
				{
					bool = false;
				}
				if(traceArray[i].getClassName().startsWith("com.zoho") || traceArray[i].getClassName().startsWith("com.adventnet"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace contains the excluded element", bool);
		}
	}
	@Test
	public void testSeperateParams() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e,new String[]{"org.eclipse"},new String[]{"com.zoho","com.adventnet"});
			assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
			for(int i = 0; i< traceArray.length;i++) 
			{
				if(i==0){continue;}
				if(!traceArray[i].getClassName().startsWith("org.eclipse"))
				{
					bool = false;
				}
				if(traceArray[i].getClassName().startsWith("com.zoho") || traceArray[i].getClassName().startsWith("com.adventnet"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace contains the excluded element", bool);
		}
	}
	@Test
	public void excludeAll() 
	{
	Boolean bool = true;
	try{integer = Integer.parseInt(str);}
	catch(Exception e)
	{
		StackTraceFilter.setDefaultFilter("7");
		StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
		assertFalse(Arrays.equals(e.getStackTrace(), traceArray));
		
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
	@Test
	public void includeAll() 
	{
	try{integer = Integer.parseInt(str);}
	catch(Exception e)
	{
		StackTraceFilter.setDefaultFilter("8");
		StackTraceElement[] traceArray = StackTraceFilter.getStackTrace(e);
		assertTrue(Arrays.equals(e.getStackTrace(), traceArray));
	}
	}
	@Test
	public void testToString()
	{
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			StackTraceFilter.setDefaultFilter("7");
			assertEquals("java.lang.NumberFormatException: For input string: \"str\"\n\t at java.lang.NumberFormatException.forInputString(NumberFormatException.java:65)", StackTraceFilter.toString(e));
		}
	}
	@AfterClass
	public static void tearDown() 
	{
	}
}

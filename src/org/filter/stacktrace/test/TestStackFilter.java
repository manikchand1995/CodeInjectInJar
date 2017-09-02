// $Id$
package org.filter.stacktrace.test;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.filter.stacktrace.StackFilter;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class TestStackFilter
{
	String str = "str";
	int integer ;
	Boolean bool;
	@BeforeClass
	public static void init() 
	{
	
	}
	
	@Test
	public void full() 
	{
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
//		e.printStackTrace();
//		StackFilter.printStackTrace(e,"junit");
//			System.out.println(Arrays.equals(e.getStackTrace(),(StackFilter.getStackTrace(e))));
			assertTrue("Sorry, some problem occured while printing entire stacktrace",Arrays.equals(e.getStackTrace(), StackFilter.getStackTrace(e)));
		}
	}
	@Test
	public void exclude() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			assertFalse(Arrays.equals(e.getStackTrace(), StackFilter.getStackTrace(e,"junit")));
			for(StackTraceElement ste : StackFilter.getStackTrace(e, "junit") )
			{
				if(ste.getClassName().contains("junit"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace contains the excluded element", bool);
			//StackFilter.getStackTrace(e,"junit");
		}
	}
	@Test
	public void include() 
	{
		Boolean bool = true;
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{
			assertFalse(Arrays.equals(e.getStackTrace(), StackFilter.getStackTrace(e,null,"junit")));
			for(StackTraceElement ste : StackFilter.getStackTrace(e,null, "junit") )
			{
				if(!ste.getClassName().contains("junit"))
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
			assertFalse(Arrays.equals(e.getStackTrace(), StackFilter.getStackTrace(e,new String[]{"junit","reflect"})));
			for(StackTraceElement ste : StackFilter.getStackTrace(e, new String[]{"junit","reflect"}) )
			{
				if(ste.getClassName().contains("junit")||ste.getClassName().contains("reflect"))
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
			assertFalse(Arrays.equals(e.getStackTrace(), StackFilter.getStackTrace(e,null,new String[]{"junit","reflect"})));
//			StackFilter.printStackTrace(e,null,new String[]{"junit","reflect"});
			for(StackTraceElement ste : StackFilter.getStackTrace(e,null, new String[]{"junit","reflect"}) )
			{
				if(!ste.getClassName().contains("junit") && !ste.getClassName().contains("reflect"))
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
			assertFalse(Arrays.equals(e.getStackTrace(), StackFilter.getStackTrace(e,"reflect","lang")));
			for(StackTraceElement ste : StackFilter.getStackTrace(e, "reflect","lang") )
			{
				if(ste.getClassName().contains("reflect"))
				{
					bool = false;
				}
				if(!ste.getClassName().contains("lang"))
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
			assertFalse(Arrays.equals(e.getStackTrace(), StackFilter.getStackTrace(e,new String[]{"model","Integer"},new String[]{"lang","runners"})));
			for(StackTraceElement ste : StackFilter.getStackTrace(e,new String[]{"model","Integer"},new String[]{"lang","runners"}) )
			{
				if(ste.getClassName().contains("model") || ste.getClassName().contains("Integer"))
				{
					bool = false;
				}
				if(!ste.getClassName().contains("lang") && !ste.getClassName().contains("runners"))
				{
					bool = false;
				}
			}
			assertTrue("Sorry, the stackTrace contains the excluded Array element or doesn't contain the included Array element", bool);
		}
	}
	@Test
	public void testtest()
	{
		try{integer = Integer.parseInt(str);}
		catch(Exception e)
		{System.out.println(StackFilter.getStackAsString(e));}
	}
	@AfterClass
	public static void tearDown() 
	{
	}
}

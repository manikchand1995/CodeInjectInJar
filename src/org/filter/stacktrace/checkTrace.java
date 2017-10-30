//$Id$
package org.filter.stacktrace;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

public class checkTrace {
public static void main(String[] args) {
	try{int a = Integer.parseInt("mani");}
	catch(Exception e)
	{
	e.printStackTrace();
	//	DefaultSData.setDefault(DefaultSData.ONLY_JAVA);
	//SFilter.printStackTrace(e);
	//DefaultSData.setDefault(DefaultSData.NO_JAVA);
SFilter.printStackTrace(e, null, new String[]{"checkTrace"});
//		System.out.println(Arrays.equals(e.getStackTrace(),(StackFilter.getStackTrace(e))));
	}
}
}

package home.test;
import home.utils.codeinject.CodeInjectInJar;
import home.utils.logger.MyLogger;

import java.util.logging.Logger;

public class checkWorks {

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


		try{MyLogger.setup(); 	
		CodeInjectInJar.setSource( "/home/likewise-open/ZOHOCORP/mani-5328/eclipse/ZIDE/mickeylite/source/persistence/src/");
		CodeInjectInJar.setClassMethod("com/adventnet/ds/query/Criteria", "validateInput");
		CodeInjectInJar.setJarAndClassPath("/home/likewise-open/ZOHOCORP/manik-5328/builds/mine/AdventNet/MickeyLite/lib/AdvPersistence.jar", new String[]{"/home/likewise-open/ZOHOCORP/mani-5328/builds/mine/AdventNet/MickeyLite/lib/*"});
		CodeInjectInJar.injectEndNewJar("System.out.println();");
		}
		catch(Exception e){
			System.out.println(getExceptionAsString(e));
			LOGGER.severe(getExceptionAsString(e));
			e.printStackTrace();}

		long endTime = System.currentTimeMillis();
		double totalTime = (double)(endTime - startTime)/1000;
		System.out.println(totalTime);

	}

	public static String getExceptionAsString(Throwable e)
	{
		String exceptionString = "";
		StackTraceElement[] traceArray = e.getStackTrace();
		exceptionString = exceptionString+(e+" : "+e.getMessage());
		for(StackTraceElement trace : traceArray)
		{
			exceptionString = exceptionString+("<br>&emsp;&emsp;&emsp; at "+trace.getClassName()+"."+trace.getMethodName()+"("+trace.getFileName()+":"+trace.getLineNumber()+")");
		}
		return exceptionString;
	}

}
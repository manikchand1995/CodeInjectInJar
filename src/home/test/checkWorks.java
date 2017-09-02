package home.test;
import home.utils.codeinject.CodeInjectInJar;
import home.utils.logger.MyLogger;

import java.io.File;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

public class checkWorks {

	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


//		try{MyLogger.setup(); 	
//		CodeInjectInJar.setSource( "/home/likewise-open/ZOHOCORP/mani-5328/eclipse/ZIDE/mickeylite/source/persistence/src/");
//		CodeInjectInJar.setClassMethod("com/adventnet/ds/query/Criteria", "validateInput");
//		CodeInjectInJar.setJarAndClassPath("/home/likewise-open/ZOHOCORP/manik-5328/builds/mine/AdventNet/MickeyLite/lib/AdvPersistence.jar", new String[]{"/home/likewise-open/ZOHOCORP/mani-5328/builds/mine/AdventNet/MickeyLite/lib/*"});
//		CodeInjectInJar.injectEndNewJar("System.out.println();");
//		}
//		catch(Exception e){
//			System.out.println(getExceptionAsString(e));
//			LOGGER.severe(getExceptionAsString(e));
//			e.printStackTrace();}

		File tempJarFile= new File("/home/likewise-open/ZOHOCORP/mani-5328/Desktop/Cricket07.lnk");
		String outputJarName = "/home/likewise-open/ZOHOCORP/mani-5328/Desktop/asa.jar";

		if(new File(outputJarName.substring(0,outputJarName.lastIndexOf("/"))).isDirectory())
		{
			FileUtils.copyFile((tempJarFile), new File(outputJarName));
//			tempJarFile.renameTo(new File(outputJarName));
		System.out.println(outputJarName + " created.");}
		else{throw new Exception("thappu");}
		
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
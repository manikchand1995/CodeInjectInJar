//$Id$
package home.utils.codeinject;

import java.util.ArrayList;
import java.util.List;

public class CodeInjectInJar {
	private static String sourceClassName, methodName, sourcePath, jarPath, codeToInject,searchString, destinationJarPath;
	private static String[] methodParameters, classPath;

	public CodeInjectInJar()
	{
		sourceClassName = "";
		methodName = "";
		methodParameters = new String[]{};
		sourcePath = "";
		jarPath = "";
		codeToInject = "";
		searchString = "";
		classPath = new String[]{};
		destinationJarPath = "";
	}
	protected static String getFile(String sourceClassName, String sourcePath)
	{
		if(sourcePath.charAt(sourcePath.length()-1) != ('/'))
		{
			sourcePath = sourcePath +"/";
		}
		sourceClassName = sourceClassName.replace(".", "/");
		return (sourcePath+sourceClassName+".java");
	}
	public static void setJarAndClassPath(String jarFilePath, String[] jarClassPath)
	{
		jarPath = jarFilePath;
		classPath = jarClassPath;

	}
	public static void setClassMethod(String className, String method)
	{
		sourceClassName = className;
		methodName = method;
		methodParameters = null;
	}
	public static void setClassMethod(String className, String method, String[] parameters)
	{
		sourceClassName = className;
		methodName = method;
		methodParameters = parameters;
	}
	public static void setSource(String sourceDirectory)
	{
		sourcePath = sourceDirectory;
	}

	public static void injectAfterNewJar(String codeToInjectIn, String injectAfter) throws Exception
	{

		injectAfterNewJar(codeToInjectIn, injectAfter, jarPath.substring(0, jarPath.length() - 4)+"_injected.jar");
	}
	public static void injectAfterNewJar(String codeToInjectIn, String injectAfter,String destinationJarName) throws Exception
	{
		codeToInject = codeToInjectIn;
		searchString = injectAfter;
		destinationJarPath = destinationJarName;
		List<String> lineNumbers = new ArrayList<String>(25);
		lineNumbers = LineNumberFinder.searchWord(searchString, getFile(sourceClassName,sourcePath));
		for(int count=0;count<lineNumbers.size();count++)
		{
			int lineNumberInt = Integer.parseInt(lineNumbers.get(count));
		//	lineNumberInt = lineNumberInt + count;
			JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumberInt+1);
		}

	}
	public static void injectAfter(String codeToInjectIn, String injectAfter) throws Exception
	{
		codeToInject = codeToInjectIn;
		searchString = injectAfter;
		destinationJarPath = jarPath;
		List<String> lineNumbers = new ArrayList<String>(25);
		lineNumbers = LineNumberFinder.searchWord(searchString, getFile(sourceClassName,sourcePath));
		for(int count=0;count<lineNumbers.size();count++)
		{
			int lineNumberInt = Integer.parseInt(lineNumbers.get(count));
	//		lineNumberInt = lineNumberInt + count;
			JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumberInt+1);
		}
	}
	public static void injectBeforeNewJar(String codeToInjectIn, String injectAfter) throws Exception
	{

		injectBeforeNewJar(codeToInjectIn, injectAfter, jarPath.substring(0, jarPath.length() - 4)+"_injected.jar");
	}
	public static void injectBeforeNewJar(String codeToInjectIn, String injectAfter,String destinationJarName) throws Exception
	{
		codeToInject = codeToInjectIn;
		searchString = injectAfter;
		destinationJarPath = destinationJarName;
		List<String> lineNumbers = new ArrayList<String>(25);
		lineNumbers = LineNumberFinder.searchWord(searchString, getFile(sourceClassName,sourcePath));
		for(int count=0;count<lineNumbers.size();count++)
		{
			int lineNumberInt = Integer.parseInt(lineNumbers.get(count));
	//		lineNumberInt = lineNumberInt + count;
			JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumberInt-1);
		}
	}
	public static void injectBefore(String codeToInjectIn, String injectAfter) throws Exception
	{
		codeToInject = codeToInjectIn;
		searchString = injectAfter;
		destinationJarPath = jarPath;
		List<String> lineNumbers = new ArrayList<String>(25);
		lineNumbers = LineNumberFinder.searchWord(searchString, getFile(sourceClassName,sourcePath));
		for(int count=0;count<lineNumbers.size();count++)
		{
			int lineNumberInt = Integer.parseInt(lineNumbers.get(count));
	//		lineNumberInt = lineNumberInt + count;
			JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumberInt-1);
		}
	}
	public static void injectAt(String codeToInjectIn, int lineNumber) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = jarPath;
		JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumber);

	}
	public static void injectAt(String codeToInjectIn, int[] lineNumber) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = jarPath;

		for(int count=0;count<lineNumber.length;count++)
		{
			int lineNumberInt = lineNumber[count] ;
	//		lineNumberInt = lineNumberInt + count;
			JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumberInt);
		}
	}
	public static void injectAtNewJar(String codeToInjectIn, int lineNumber) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = jarPath.substring(0, jarPath.length() - 4)+"_injected.jar";
		JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumber);

	}
	public static void injectAtNewJar(String codeToInjectIn, int lineNumber,String destinationJarName) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = destinationJarName;
		JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumber);

	}
	public static void injectAtNewJar(String codeToInjectIn, int[] lineNumber) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = jarPath.substring(0, jarPath.length() - 4)+"_injected.jar";
		for(int count=0;count<lineNumber.length;count++)
		{
			int lineNumberInt = lineNumber[count] ;
//			lineNumberInt = lineNumberInt + count;
			JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumberInt);
		}
	}

	public static void injectAtNewJar(String codeToInjectIn, int[] lineNumber,String destinationJarName) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = destinationJarName;
		for(int count=0;count<lineNumber.length;count++)
		{
			int lineNumberInt = lineNumber[count] ;
//			lineNumberInt = lineNumberInt + count;
			JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, lineNumberInt);
		}
	}
	public static void injectEndNewJar(String codeToInjectIn) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = jarPath.substring(0, jarPath.length() - 4)+"_injected.jar";
		JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, 0, "after");

	}
	public static void injectEndNewJar(String codeToInjectIn, String destinationJarName) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = destinationJarName;
		JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, 0, "after");

	}
	public static void injectBeginNewJar(String codeToInjectIn) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = jarPath.substring(0, jarPath.length() - 4)+"_injected.jar";
		JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, 0, "before");

	}
	public static void injectBeginNewJar(String codeToInjectIn, String destinationJarName) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = destinationJarName;
		JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, 0, "before");

	}

	public static void injectEnd(String codeToInjectIn) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = jarPath;
		JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, 0, "after");

	}

	public static void injectBegin(String codeToInjectIn) throws Exception
	{
		codeToInject = codeToInjectIn;
		destinationJarPath = jarPath;
		JarChangeTest.handleJarOperation(classPath, sourceClassName, methodName,methodParameters, codeToInject, jarPath, destinationJarPath, 0, "before");

	}
}

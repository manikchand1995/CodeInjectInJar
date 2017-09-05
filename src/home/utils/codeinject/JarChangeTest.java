//$Id$
package home.utils.codeinject;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class JarChangeTest {

	public static void handleJarOperation(String[] classPath, String sourceClassName, String methodName, String[] parameters, String codeToInject, String JarPath, String destinationJarPath, int lineNumber) throws Exception
	{
		ClassPool pool = new ClassPool(true);
		ClassPath[] cp = new ClassPath[classPath.length];
		if(isClassExistsinJar(sourceClassName, JarPath))
		{	for(int i = 0; i<classPath.length; i++)
		{
			 cp[i] = pool.insertClassPath(classPath[i]);
		} 
		if(sourceClassName.contains(".")){sourceClassName=sourceClassName.replace(".", "/");}
		CtClass cc = pool.get(sourceClassName.replace("/", "."));
		CtClass[] methodParameters = new CtClass[parameters[0]!=""?parameters.length:0];
		if(parameters[0]!="")
		{	  
			for (int i =0 ; i< parameters.length;i++)
			{
				if(parameters[i].contains(".")){parameters[i] = (parameters[i].replace(".", "/")); }
				methodParameters[i] = pool.get(parameters[i]);
			}
		}
		CtMethod cm = cc.getDeclaredMethod(methodName, methodParameters);
		try{
			cm.insertAt(lineNumber, codeToInject);
		}
		catch(CannotCompileException e)
		{
			throw new CannotCompileException(e.getMessage()+" ( error in "+codeToInject+" )");
		}
		
		System.out.println("Injected "+codeToInject+"\n \t\tat "+lineNumber+"\n \t\tin "+sourceClassName+" \n\t\t inside jar: "+JarPath);
		byte[] b = cc.toBytecode(); 

for(int i = 0; i<cp.length; i++)
{
	pool.removeClassPath(cp[i]);
}
//		pool.removeClassPath(pool.insertClassPath(JarPath));
		JarHandler jarHandler = new JarHandler();
		jarHandler.createJarFile(JarPath,destinationJarPath, b, sourceClassName+".class");
		}
		else
		{
			throw new NotFoundException((sourceClassName +" doesn't exist in "+JarPath));
		}

	}
	public static void handleJarOperation(String[] classPath, String sourceClassName, String methodName,String[] parameters, String codeToInject, String JarPath, String destinationJarPath, int lineNumber, String afterBefore) throws Exception
	{
		ClassPool pool = new ClassPool(true);
		ClassPath[] cp = new ClassPath[classPath.length];
		if(isClassExistsinJar(sourceClassName, JarPath))
		{	for(int i = 0; i<classPath.length; i++)
		{
			 cp[i] = pool.insertClassPath(classPath[i]);
		} 
		if(sourceClassName.contains("/")){sourceClassName=sourceClassName.replace("/", ".");}
		CtClass cc = pool.get(sourceClassName);
		CtClass[] methodParameters = new CtClass[parameters.length>0?parameters.length:0];
		if(parameters.length>0)
		{	  
			for (int i =0 ; i< parameters.length;i++)
			{
				if(parameters[i].contains(".")){parameters[i] = (parameters[i].replace(".", "/")); }
				methodParameters[i] = pool.get(parameters[i]);
			}
		}

		CtMethod cm = cc.getDeclaredMethod(methodName, methodParameters);
		if(afterBefore.equalsIgnoreCase("after"))
		{
			try{
				cm.insertAfter(codeToInject);			}
			catch(CannotCompileException e)
			{
				throw new CannotCompileException(e.getMessage()+" ( error in "+codeToInject+" )");
			}
			System.out.println("Injected "+codeToInject+"\n \t\tat "+lineNumber+"\n \t\tin "+sourceClassName+" \n\t\t inside jar: "+JarPath);

		}
		else
		{
			try{
				cm.insertBefore(codeToInject);			}
			catch(CannotCompileException e)
			{
				throw new CannotCompileException(e.getMessage()+" ( error in "+codeToInject+" )");
			}			System.out.println("Injected "+codeToInject+"\n \t\tat "+lineNumber+"\n \t\tin "+sourceClassName+" \n\t\t inside jar: "+JarPath);

		}

		byte[] b = cc.toBytecode(); 
		for(int i = 0; i<cp.length; i++)
		{
			pool.removeClassPath(cp[i]);
		}

//		pool.removeClassPath(new ClassClassPath(cc.getClass()));
//		pool.removeClassPath(pool.insertClassPath(JarPath));
		JarHandler jarHandler = new JarHandler();
		jarHandler.createJarFile(JarPath,destinationJarPath, b, sourceClassName+".class");
		}
		else
		{
			throw new NotFoundException((sourceClassName +" doesn't exist in "+JarPath));
		}
	}

	public static boolean isClassExistsinJar(String className, String jarPath) 
	{
		Boolean classExists = false;
		try 
		{
			className = className.replace(".", "/");
			FileInputStream jarFileStream = new FileInputStream(jarPath);
			ZipInputStream zip = new ZipInputStream(new FileInputStream(jarPath)) ;
			for (ZipEntry entry = zip.getNextEntry(); entry != null; entry = zip.getNextEntry()) 
			{
			    if (entry.getName().contains(className)) 
			    {
			    	classExists = true;
			    }
			}
			zip.close();
			jarFileStream.close();
		}
			 catch (Exception e) {
			System.out.println("ClassPath : "+jarPath+"doesn't exist");
			e.printStackTrace();
		}

		return classExists;


		
		
	}

}

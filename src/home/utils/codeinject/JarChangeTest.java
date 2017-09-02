//$Id$
package home.utils.codeinject;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

public class JarChangeTest {

	public static void handleJarOperation(String[] classPath, String sourceClassName, String methodName, String[] parameters, String codeToInject, String JarPath, String destinationJarPath, int lineNumber) throws Exception
	{
		ClassPool pool = new ClassPool(true);
		if(isClassExistsinJar(sourceClassName, JarPath))
		{	for(String path : classPath)
		{
			pool.insertClassPath(path);
		} 
		if(sourceClassName.contains(".")){sourceClassName=sourceClassName.replace(".", "/");}
		CtClass cc = pool.get(sourceClassName.replace("/", "."));
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
		cm.insertAt(lineNumber, codeToInject);
		System.out.println("Injected "+codeToInject+"\n \t\tat "+lineNumber+"\n \t\tin "+sourceClassName+" \n\t\t inside jar: "+JarPath);
		byte[] b = cc.toBytecode(); 


		pool.removeClassPath(new ClassClassPath(cc.getClass()));
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
		if(isClassExistsinJar(sourceClassName, JarPath))
		{ for(String path : classPath)
		{
			pool.appendClassPath(path);
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
			cm.insertAfter(codeToInject);
			System.out.println("Injected "+codeToInject+"\n \t\tat "+lineNumber+"\n \t\tin "+sourceClassName+" \n\t\t inside jar: "+JarPath);

		}
		else
		{
			cm.insertBefore(codeToInject);
			System.out.println("Injected "+codeToInject+"\n \t\tat "+lineNumber+"\n \t\tin "+sourceClassName+" \n\t\t inside jar: "+JarPath);

		}

		byte[] b = cc.toBytecode(); 


		pool.removeClassPath(new ClassClassPath(cc.getClass()));
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
		ClassPool pool = new ClassPool(true);
		try {
			pool.insertClassPath(jarPath);

			{  try {
				@SuppressWarnings("unused")
				CtClass cc = pool.get(className.replace("/", "."));
				classExists = true;
			} catch (NotFoundException e) {
				classExists=false;
				System.out.println(className +" doesn't exist in "+jarPath);
				e.printStackTrace();
			}
			}
		} catch (NotFoundException e1) {
			System.out.println("ClassPath : "+jarPath+"doesn't exist");
			e1.printStackTrace();
		}
		return classExists;

	}
}

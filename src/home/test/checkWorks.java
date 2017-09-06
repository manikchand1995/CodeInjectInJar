package home.test;
import home.utils.codeinject.CodeInjectInJar;
import home.utils.logger.MyLogger;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class checkWorks {

	public static void main(String[] args) throws Exception {
		//		long startTime = System.currentTimeMillis();
		//		final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
		//		File f = new File("/home/likewise-open/ZOHOCORP/mani-5328/Desktop/manik.xml");
		//	FileUtils.forceDelete(f);
		////		Boolean bool = com.zoho.framework.utils.FileUtils.deleteFile(f);
		//	System.out.println(bool);
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

		//		File tempJarFile= new File("/home/likewise-open/ZOHOCORP/mani-5328/Desktop/Cricket07.lnk");
		//		String outputJarName = "/home/likewise-open/ZOHOCORP/mani-5328/Desktop/asa.jar";
		//
		//		if(new File(outputJarName.substring(0,outputJarName.lastIndexOf("/"))).isDirectory())
		//		{
		//			FileUtils.copyFile((tempJarFile), new File(outputJarName));
		////			tempJarFile.renameTo(new File(outputJarName));
		//		System.out.println(outputJarName + " created.");}
		//		else{throw new Exception("thappu");}
		//		
		//		long endTime = System.currentTimeMillis();
		//		double totalTime = (double)(endTime - startTime)/1000;
		//		System.out.println(totalTime);
		Boolean classExists = false;
		String className = "com.adventnet.client.components.table.web.TableRetrieverAction";
		String jarPath = "/home/likewise-open/ZOHOCORP/mani-5328/builds/AdventNet_test/MickeyLite/lib/AdventNetClientComponents.jar";
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
			    	System.out.print("1");
			    }
			}
		    System.out.println(classExists);

			zip.close();
			jarFileStream.close();
		}
			 catch (Exception e) {
			System.out.println("ClassPath : "+jarPath+"doesn't exist");
			e.printStackTrace();
		}

	}

	private static BufferedImage getDifferenceImage(BufferedImage bufferedImageRecorded, BufferedImage bufferedImageFromDB)
			throws IOException
	{
		int w = bufferedImageRecorded.getWidth();int h = bufferedImageRecorded.getHeight();
		int highlight = Color.YELLOW.getRGB();
		System.out.println("expected dimensions --- " + w + "x" + h + " &&& obtained dimensions --- " + bufferedImageFromDB.getWidth() + "x" + bufferedImageFromDB.getHeight());
		if(w < 1000 && h <1000)
		{int[] p1 = bufferedImageRecorded.getRGB(0, 0, w, h, null, 0, w);
		int[] p2 = bufferedImageFromDB.getRGB(0, 0, w, h, null, 0, w);
		if (Arrays.equals(p1, p2))
		{
			System.out.println("--- Screenshots are same ---");
			return null;
		}
		else
		{
			System.out.println("--- Screenshots are different / check the diff ---");
			for (int i = 0; i < p1.length; i++) {
				if (p1[i] != p2[i]) {
					p1[i] = highlight;
				}
			}
			BufferedImage out = new BufferedImage(w, h, 
					1);
			out.setRGB(0, 0, w, h, p1, 0, w);
			return out;
		}}
		else
		{
			return handleBigImages(bufferedImageRecorded, bufferedImageFromDB);
		}
	}
	private static BufferedImage handleBigImages(BufferedImage bufferedImageRecorded, BufferedImage bufferedImageFromDB)
	{
		int w = bufferedImageRecorded.getWidth();int h = bufferedImageRecorded.getHeight();
		int highlight = Color.YELLOW.getRGB();

		int[] p11 = bufferedImageRecorded.getRGB(0, 0, w/2, h/2, null, 0, w/2);
		int[] p12 = bufferedImageRecorded.getRGB(0, h/2, w/2, h/2, null, 0, w/2);
		int[] p13 = bufferedImageRecorded.getRGB(w/2, 0, w/2, h/2, null, 0, w/2);
		int[] p14 = bufferedImageRecorded.getRGB(w/2, h/2, w/2, h/2, null, 0, w/2);

		int[] p21 = bufferedImageFromDB.getRGB(0, 0, w/2, h/2, null, 0, w/2);
		int[] p22 = bufferedImageFromDB.getRGB(0, h/2, w/2, h/2, null, 0, w/2);
		int[] p23 = bufferedImageFromDB.getRGB(w/2, 0, w/2, h/2, null, 0, w/2);
		int[] p24 = bufferedImageFromDB.getRGB(w/2, h/2, w/2, h/2, null, 0, w/2);

		if (Arrays.equals(p11, p21) && Arrays.equals(p12, p22) && Arrays.equals(p13, p23) && Arrays.equals(p14, p24))
		{
			System.out.println("--- Screenshots are same ---");
			return null;
		}
		else{
			System.out.println("--- Screenshots are different / check the diff ---");
			for (int i = 0; i < p11.length; i++) {
				if (p11[i] != p21[i]) {
					p11[i] = highlight;
				}
				if (p12[i] != p22[i]) {
					p12[i] = highlight;
				}
				if (p13[i] != p23[i]) {
					p13[i] = highlight;
				}
				if (p14[i] != p24[i]) {
					p14[i] = highlight;
				}
			}
			BufferedImage out = new BufferedImage(w, h, 1);
			out.setRGB(0, 0, w/2, h/2, p11, 0, w/2);
			out.setRGB(0, h/2, w/2, h/2, p12, 0, w/2);
			out.setRGB(w/2, 0, w/2, h/2, p13, 0, w/2);
			out.setRGB(w/2, h/2, w/2, h/2, p14, 0, w/2);
			return out;
		} 
	}
}
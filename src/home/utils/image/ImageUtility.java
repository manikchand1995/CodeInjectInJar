//$Id$
package home.utils.image;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageUtility {
	private static String destinationPath;
	private static Color highlightColor;
	private static String imageType;
	public ImageUtility()
	{}

	public static boolean isImage(String filePath) throws IOException
	{
		File file = new File(filePath);
		if(isImage(file))
		{
			return true;
		}
		else
		{
			return false;
		}
	}	

	public static boolean isImage(File file) throws IOException
	{

		String mimetype= Files.probeContentType(file.toPath());
		String type = mimetype.split("/")[0];
		if(type.equals("image"))
			return true;
		else 
			System.out.println(file.toPath()+" - is not an image file, but "+mimetype);
		return false;
	}

	/**
	 * returns the buffered image of your image file given in File format<br>
	 * 
	 * **/
	public static byte[] imageFileToByteArray(String filePath) throws IOException
	{
		if(isFileExists(filePath) && isImage(filePath))
		{
			return imageFileToByteArray(new File(filePath));
		}
		else
		{
			return null;
		}
	}
	public static byte[] imageFileToByteArray(File imageFile) throws IOException
	{
		BufferedImage img = getBufferedImage(imageFile);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(img, "png", baos);
		byte[] bytes = baos.toByteArray();
		return bytes;
	}
	public static BufferedImage getBufferedImage(byte[] imageInBytes) throws IOException
	{
		return ImageIO.read(new ByteArrayInputStream(imageInBytes));
	}
	public static BufferedImage getBufferedImage(File imageFile) throws IOException
	{
		BufferedImage img = ImageIO.read(imageFile);
		return img;
	}
	public static void setImageType(String imageType)
	{
		ImageUtility.imageType = imageType;
	}
	public static String getImageType()
	{
		return imageType;
	}
	public static String getImageType(String filePath) throws IOException
	{
		File file = new File(filePath);
		return getImageType(file);
		
	}
	public static String getImageType(File imageFile) throws IOException
	{
		String mimetype= Files.probeContentType(imageFile.toPath());
		String fileType = mimetype.split("/")[1];
		return fileType;
	}
	public static void compareImages(byte[] image1, byte[] image2, String ImageType, String destinationPath)
	{
	
try{
		BufferedImage bufferedImage1 = getBufferedImage(image1);
		BufferedImage bufferedImage2 = getBufferedImage(image2);

		BufferedImage diffImage = getDifferenceImage(bufferedImage1, bufferedImage2);
		writeBuffImageToFile(diffImage, imageType, destinationPath);
		System.out.println("Pictures successfully compared and stored as "+destinationPath);
	}
catch(Exception e){
	System.out.println("Problem occured while comparing !");
	e.printStackTrace();
}

		
	}
	public static void compareImages(String imagePath1, String imagePath2) throws IOException
	{
		compareImages(imagePath1,imagePath2, imageType, destinationPath);
	}
	public static void compareImages(String imagePath1, String imagePath2, String imageType) throws IOException
	{
		compareImages(imagePath1,imagePath2, imageType, destinationPath);

	}	
	public static boolean checkBeforeCompare(String imagePath1, String imagePath2, String imageType, String destinationPath) throws IOException
	{
		if(isFileExists(imagePath1) && isFileExists(imagePath2) && isValidImageType(imageType) && isDestinationFolderExists(destinationPath))
		{
			if(isImage(imagePath1) && isImage(imagePath2))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		else
			return false;
	}
	public static boolean isValidImageType(String imageType)
	{
		List<String> imageTypes = Arrays.asList("png", "jpeg", "gif", "bmp", "wbmp");
		if (imageTypes.contains(imageType))
		{
			return true;
		}
		else 
		{
			System.out.println(imageType+" - Image type is not valid !");
			return false;
		}
	}

	public static boolean isFileExists(String path)
	{
		if(new File(path).exists())
		{
			return true;
		}
		else
		{
			System.out.println(path + " - file doesn't exists !");
			return false;
		}
	}
	public static boolean isDestinationFolderExists(String destinationPath)
	{
		String folderPath = "";
		String destFileName = "";
		String destFileType ="";
		if (null != destinationPath && destinationPath.length() > 0 )
		{
			int endIndex = destinationPath.lastIndexOf("/");
			if (endIndex != -1)  
			{
				folderPath = destinationPath.substring(0, endIndex);
				destFileName=destinationPath.substring(endIndex+1,destinationPath.length());
				destFileType = destFileName.substring(destFileName.lastIndexOf(".")+1,destFileName.length());
			}
		} 
		if((new File(folderPath)).isDirectory())
		{
			if(isValidImageType(destFileType))
				return true;
			else
				return false;
		}
		else
		{
			System.out.println(folderPath+" - path doesn't exists !");
			return false;
		}
	}
	public static void compareImages(String imagePath1, String imagePath2, String imageType, String destinationPath) throws IOException {

		if(checkBeforeCompare(imagePath1,imagePath2, imageType, destinationPath))
				{			
				File file1 = new File(imagePath1);
				File file2 = new File(imagePath2);

				BufferedImage bufferedImage1 = getBufferedImage(file1);
				BufferedImage bufferedImage2 = getBufferedImage(file2);

				BufferedImage diffImage = getDifferenceImage(bufferedImage1, bufferedImage2);
				writeBuffImageToFile(diffImage, imageType, destinationPath);
				System.out.println("Pictures successfully compared and stored as "+destinationPath);
			}
		else{
			System.out.println("Problem occured while comparing !");
		}

				}
	public static void writeBuffImageToFile(BufferedImage buffImage, String imageType, String destinationPath) throws IOException
	{
		if (buffImage != null || buffImage == null) 
		{
			ImageIO.write(buffImage, imageType, new File(destinationPath));
		}
	}
		public static void setDestinationPath(String destinationPath)
		{
			ImageUtility.destinationPath = destinationPath;
		}

		public static String getDestinationPath()
		{
			return destinationPath;
		}

		public static void setHighlightColor(String color) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, ClassNotFoundException
		{
			Field field;
			try
			{
				field = Class.forName("java.awt.Color").getField(color.toLowerCase());
			}
			catch(NoSuchFieldException e)
			{
				System.out.println("Invalid Highlight Color mentioned. Taking the default yellow colour");
				field = Class.forName("java.awt.Color").getField("yellow");
			}
			highlightColor = (Color)field.get(null);
		}

		public static Color getHighlightColor()
		{
			if(highlightColor != null)
			{
				return highlightColor;
			}
			else
			{
				return Color.YELLOW;
			}

		}
		public static int getWidth(String imagePath) throws IOException
		{
			File imageFile = new File(imagePath);
			return getWidth(imageFile);
		}
		public static int getWidth(File imageFile) throws IOException
		{
			BufferedImage buffImageFile = getBufferedImage(imageFile);
			return getWidth(buffImageFile);
		}
		public static int getWidth(BufferedImage buffImageFile)
		{
			return buffImageFile.getWidth();
		}
		public static int getHeight(String imagePath) throws IOException
		{
			File imageFile = new File(imagePath);
			return getHeight(imageFile);
		}
		public static int getHeight(File imageFile) throws IOException
		{
			BufferedImage buffImageFile = getBufferedImage(imageFile);
			return getHeight(buffImageFile);
		}
		public static int getHeight(BufferedImage buffImageFile)
		{
			return buffImageFile.getHeight();
		} 
		public static int getTransparency(BufferedImage buffImageFile)
		{
			return buffImageFile.getTransparency();
		}
		public static int getTransparency(String imagePath) throws IOException
		{
			File imageFile = new File(imagePath);
			return getTransparency(imageFile);
		}
		public static int getTransparency(File imageFile) throws IOException
		{
			BufferedImage buffImageFile = getBufferedImage(imageFile);
			return getTransparency(buffImageFile);
		}

		public static BufferedImage getDifferenceImage(BufferedImage bufferedImage1, BufferedImage bufferedImage2) throws IOException {
			final int w = getWidth(bufferedImage1), h = getHeight(bufferedImage1), highlight = getHighlightColor().getRGB();

			final int[] pixelArray1 = bufferedImage1.getRGB(0, 0, w, h, null, 0, w);
			final int[] pixelArray2 = bufferedImage2.getRGB(0, 0, w, h, null, 0, w);

			if (Arrays.equals(pixelArray1, pixelArray2)) 
			{
				return bufferedImage1;
			} 
			else 
			{

				for (int i = 0; i < pixelArray1.length; i++) {
					if (pixelArray1[i] != pixelArray2[i]) {
						pixelArray1[i] = highlight;
					}
				}
				final BufferedImage out = new BufferedImage(w, h,
						BufferedImage.TYPE_INT_RGB);
				out.setRGB(0, 0, w, h, pixelArray1, 0, w);
				return out;
			}
		}

	}

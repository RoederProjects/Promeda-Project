/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.singleton;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.SanselanConstants;
import org.apache.sanselan.common.IBufferedImageFactory;
import org.imgscalr.Scalr;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.MultiStepRescaleOp;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

import psd.model.Psd;

/**
 *
 * @author Win10 Pro x64
 */
public class ImageHandler {

	PropertiesModel prop;

	public ImageHandler() {
		this.prop = new PropertiesModel();
		prop.loadAppProperties();
	}

	/**
	 *
	 * @param psdFiles
	 * @param imageSizes
	 * @throws IOException
	 */
	/*
	 * public void createImgFromPsd(List<File> psdFiles, List<ImageSize> imageSizes)
	 * throws IOException { Psd psd; BufferedImage img; File imgFile; String
	 * imgFileName; for (Iterator psdFilesIterator = psdFiles.iterator();
	 * psdFilesIterator.hasNext();) { File psdFile = (File) psdFilesIterator.next();
	 * psd = new Psd(psdFile); img = psd.getImage(); for (Iterator imageSizeIterator
	 * = imageSizes.iterator(); imageSizeIterator.hasNext();) { ImageSize imageSize
	 * = (ImageSize) imageSizeIterator.next(); BufferedImage scaledImage =
	 * resizeImage(imageSize.getWidth(), imageSize.getHeight(), img); imgFileName =
	 * FilenameUtils.getBaseName(psdFile.getName()) + imageSize.getName() + ".png";
	 * imgFile = new File(prop.get("filePathTemporary") + imgFileName);
	 * ImageIO.write(scaledImage, "png", imgFile);
	 * 
	 * } } }
	 */

	public BufferedImage getImageFromPsd(File psdFile) throws IOException {
		Psd psd;
		BufferedImage img;
		psd = new Psd(psdFile);
		img = psd.getImage();
		return img;
	}

	public BufferedImage getImageFromPsd2(File psdFile) throws IOException {
		BufferedImage img = null;
		ImageFormat imageFormat;
		try {
			imageFormat = Sanselan.guessFormat(psdFile);
			if (imageFormat.equals(ImageFormat.IMAGE_FORMAT_PSD)) {
				img = Sanselan.getBufferedImage(psdFile);
			}
		} catch (ImageReadException e) {
			e.printStackTrace();
			return null;
		}
		return img;
	}

	public BufferedImage getImageFromPsd3(File psdFile) throws IOException {
		PSDParser r = new PSDParser();
		InputStream input = new FileInputStream(psdFile);
		r.read(input);
		int n = r.getFrameCount();
		System.out.println(n);
		BufferedImage image = r.getImage();
		Graphics2D graphics = image.createGraphics();
		for (int i = 1; i < n; i++) {
			BufferedImage layer = r.getLayer(i);
			graphics.drawImage(layer, 0, 0, null);
		}
		return image;
	}

	/**
	 *
	 * @param width
	 * @param height
	 * @param bImage
	 * @return
	 */
	public BufferedImage resizeImage(int width, int height, BufferedImage bImage) {
		ResampleOp resampleOp = new ResampleOp(width, height);
		// ImprovedMultistepRescaleOp rescaleOp = new ImprovedMultistepRescaleOp(width,
		// height);
		System.out.println(resampleOp.getFilter().getName());
		resampleOp.setFilter(ResampleFilters.getLanczos3Filter());
		System.out.println(resampleOp.getFilter().getName());
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);
		System.out.println(resampleOp.getUnsharpenMask().name());
		// System.out.println(resampleOp.getRenderingHints().values().toString());
		// rescaleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.VerySharp);
		// rescaleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);

		BufferedImage rescaledBImage = resampleOp.filter(bImage, new BufferedImage(width, height, bImage.getType()));
		// BufferedImage rescaledBImage = rescaleOp.filter(bImage,
		// new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB));
		return rescaledBImage;
	}

	/**
	 *
	 * @param width
	 * @param height
	 * @param bImage
	 * @return
	 */
	public BufferedImage resizeImage2(BufferedImage srcImage, int width) {
	    srcImage = Scalr.resize(srcImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_TO_WIDTH, width, Scalr.OP_ANTIALIAS);
	    return srcImage;
	}

	/**
	 *
	 * @param imageARGB
	 * @return returns an bufferedImage without transparency
	 */
	public BufferedImage removeAlphaChannel(BufferedImage imageARGB) {
		BufferedImage imageRGB = new BufferedImage(imageARGB.getWidth(), imageARGB.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = imageRGB.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, imageRGB.getWidth(), imageRGB.getHeight());
		g2d.drawImage(imageARGB, 0, 0, null);
		g2d.dispose();
		return imageRGB;
	}

	public BufferedImage readBufferedImage(File srcFile) {
		String fileExt = FilenameUtils.getExtension(srcFile.getName());
		try {
			BufferedImage srcImage;
			if (fileExt.equalsIgnoreCase("psd") || fileExt.equalsIgnoreCase("psb")) {
				Psd psd = new Psd(srcFile);
				srcImage = psd.getImage();
				return srcImage;
			} else if (fileExt.equalsIgnoreCase("jpg") || fileExt.equalsIgnoreCase("jpeg")) {
				srcImage = ImageIO.read(srcFile);
				return srcImage;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public BufferedImage createThumbnail(File srcFile, int size) throws IOException, ImageReadException {
	    BufferedImage thumbnail = imageReadSanselan(srcFile);
	    int width = thumbnail.getWidth();
	    int height = thumbnail.getHeight();
	    if (width > size || height > size) {
	        if (width > height) {
	            thumbnail = Scalr.resize(thumbnail, Scalr.Mode.FIT_TO_HEIGHT, size);
	            thumbnail = Scalr.crop(thumbnail, thumbnail.getWidth() / 2 - size / 2, 0, size, size);
	        } else {
	            thumbnail = Scalr.resize(thumbnail, Scalr.Mode.FIT_TO_WIDTH, size);
	            thumbnail = Scalr.crop(thumbnail, 0, thumbnail.getWidth() / 2 - size / 2, size, size);
	        }
	    }
	    return thumbnail;
	}
	
	public BufferedImage createThumbnail(BufferedImage thumbnail, int size) throws IOException, ImageReadException {
	    int width = thumbnail.getWidth();
	    int height = thumbnail.getHeight();
	    if (width > size || height > size) {
	        if (width > height) {
	            thumbnail = Scalr.resize(thumbnail, Scalr.Mode.FIT_TO_HEIGHT, size);
	            thumbnail = Scalr.crop(thumbnail, thumbnail.getWidth() / 2 - size / 2, 0, size, size);
	        } else {
	            thumbnail = Scalr.resize(thumbnail, Scalr.Mode.FIT_TO_WIDTH, size);
	            thumbnail = Scalr.crop(thumbnail, 0, thumbnail.getWidth() / 2 - size / 2, size, size);
	        }
	    }
	    return thumbnail;
	}
	
	public BufferedImage imageReadSanselan(File file)
			throws ImageReadException, IOException
	{
		Map<String, ManagedImageBufferedImageFactory> params = new HashMap<String, ManagedImageBufferedImageFactory>();

		// set optional parameters if you like
		params.put(SanselanConstants.BUFFERED_IMAGE_FACTORY,
				new ManagedImageBufferedImageFactory());

		//		params.put(SanselanConstants.PARAM_KEY_VERBOSE, Boolean.TRUE);

		// read image
		BufferedImage image = Sanselan.getBufferedImage(file, params);

		return image;
	}

	public static class ManagedImageBufferedImageFactory
			implements
				IBufferedImageFactory
	{

		public BufferedImage getColorBufferedImage(int width, int height,
				boolean hasAlpha)
		{
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();
			GraphicsDevice gd = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gd.getDefaultConfiguration();
			return gc.createCompatibleImage(width, height,
					Transparency.TRANSLUCENT);
		}

		public BufferedImage getGrayscaleBufferedImage(int width, int height,
				boolean hasAlpha)
		{
			return getColorBufferedImage(width, height, hasAlpha);
		}
	}
}

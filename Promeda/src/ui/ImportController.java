package ui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import org.apache.sanselan.ColorTools;

import model.singleton.ImageHandler;


public class ImportController {
	public ImageHandler imageHandler = new ImageHandler();
	public File srcFile;
	public BufferedImage srcImage;
	public ColorTools colorTools = new ColorTools();

	public Dimension lockAspectRatioWidth(Dimension dim, int newHeight) {
		float factor = (float) newHeight / (float) dim.getHeight();
		int newWidth = Math.round((float) dim.getWidth() * factor);
		dim.setSize(newWidth, newHeight);
		return dim;
	}
	
	public Dimension lockAspectRatioHeight(Dimension dim, int newWidth) {
		float factor = (float) newWidth / (float) dim.getWidth();
		int newHeight = Math.round((float) dim.getWidth() * factor);
		dim.setSize(newWidth, newHeight);
		return dim;
	}
	
	

}

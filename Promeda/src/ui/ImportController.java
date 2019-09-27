package ui;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.sanselan.ColorTools;

import model.prototype.StoreDataModel;
import model.singleton.ImageHandler;
import model.singleton.PropertiesModel;


public class ImportController {
	public ImageHandler imageHandler = new ImageHandler();
	public File srcFile;
	public BufferedImage srcImage;
	public ColorTools colorTools = new ColorTools();
	public Configuration configApp;
	public Vector<StoreDataModel> stores;
	public Vector<StoreDataModel> selectedStores;

	public void initAppConfig() {
		System.out.println("run initAppConfig()");
		try {
			configApp = new PropertiesConfiguration("config" + File.separator + "app.properties");
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initStores(String configPrefix) {
		System.out.println("run initStores(String configPrefix)");
		
		stores = new Vector<StoreDataModel>();
		selectedStores = new Vector<StoreDataModel>();
		
		File f = new File(configApp.getString("dir.config.stores"));
		System.out.println(f.getPath());
		File[] files = f.listFiles();

		try {
			for (File file : files) {
				if (!file.isDirectory() && FilenameUtils.isExtension(file.getName(), "properties")) {
					Configuration configStore = new PropertiesConfiguration(file);
					
					stores.add(new StoreDataModel(configStore.getString("url"), configStore.getString("ftp.host"),
							Integer.parseInt(configStore.getString("ftp.port")), configStore.getString("ftp.protocol"),
							configStore.getString("ftp.user"), configStore.getString("ftp.pswd"),
							configStore.getString("ftp.dir.default"),
							configStore.getBoolean(configPrefix + ".image.compression.enabled"),
							configStore.getString(configPrefix + ".image.compression.command"),
							configStore.getList(configPrefix + ".image.size")));
				}
			}
		} catch (ConfigurationException cex) {
			// Something went wrong
		}
	}
	
	public Dimension lockAspectRatioWidth(Dimension dim, int newHeight) {
		if (newHeight == dim.getHeight()) {
			return dim;
		} else {
		float factor = (float) newHeight / (float) dim.getHeight();
		int newWidth = Math.round((float) dim.getWidth() * factor);
		dim.setSize(newWidth, newHeight);
		return dim;
		}
	}
	
	public Dimension lockAspectRatioHeight(Dimension dim, int newWidth) {
		if (newWidth == dim.getWidth()) {
			return dim;
		} else {
		float factor = (float) newWidth / (float) dim.getWidth();
		int newHeight = Math.round((float) dim.getHeight() * factor);
		dim.setSize(newWidth, newHeight);
		return dim;
		}
	}

}

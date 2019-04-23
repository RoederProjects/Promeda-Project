package ui;

import static org.apache.commons.io.FileUtils.copyFile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Vector;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.imgscalr.Scalr;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

// import com.enterprisedt.net.ftp.FTPException;

import model.prototype.ImageSize;
import model.prototype.StoreDataModel;
import model.singleton.Executor;
import model.singleton.PropertiesModel;
import model.singleton.SFTPClientModel;

public class ProdImgImpWzrdController extends ImportController
		implements ActionListener, ComponentListener, ListSelectionListener {

	private ProdImgImpWzrdView view;
	private FTPClient ftp = null;
	private SFTPClientModel sftp = null;
	private PropertiesModel propApp;
	private Vector<StoreDataModel> stores;
	private Vector<StoreDataModel> selectedStores;
	private File[] psdFiles;
	private Vector<File> psdFileList = new Vector<File>();
	Vector<ImageSize> imageSizeList = new Vector<ImageSize>();
	private BufferedImage srcImage;
	private String configPrefix;

	public ProdImgImpWzrdController() {
		initProperties();
		initView();
		// initStores();
		this.configPrefix = "product";
	}

	public ProdImgImpWzrdController(Vector<File> psdFileList) {
		initProperties();
		initView();
		// initStores();
		this.psdFileList = psdFileList;
		view.fileListSourceFiles.setListData(psdFileList);
	}

	public ProdImgImpWzrdController(String configPrefix) {
		initProperties();
		initView();
		// initStores();
		this.configPrefix = configPrefix;
	}
	private void initProperties() {
		propApp = new PropertiesModel();
		propApp.loadAppProperties();
	}

	private void initView() {
		view = new ProdImgImpWzrdView(this);
		view.setVisible(true);
	}

	public void initStores() {
		stores = new Vector<StoreDataModel>();
		selectedStores = new Vector<StoreDataModel>();
		File f = new File(propApp.get("locNetworkRes") + "stores");
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

	public void process() {

		File imgFile;
		DateTimeFormatter fmt = DateTimeFormat.forPattern("_yyyyMMdd");
		String currentDate = LocalDate.now().toString(fmt);

		double progressBarMax = 100;
		double progressSteps = psdFileList.size() * selectedStores.size();
		double progressStepSizef = progressBarMax / progressSteps;
		int progressStepSize = (int) Math.round(progressStepSizef);

		for (StoreDataModel store : selectedStores) {
			try {
				if (store.getStoreFtpProtocol().equals("ftp")) {
					ftp = new FTPClient();
					ftp.connect(store.getStoreFtpServer());
					ftp.login(store.getStoreFtpUser(), store.getStoreFtpPass());
					ftp.cwd(store.getDirDefault());
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
				} else if (store.getStoreFtpProtocol().equals("sftp")) {
					sftp = new SFTPClientModel(store.getStoreFtpServer(), store.getStoreFtpPort(),
							store.getStoreFtpUser(), store.getStoreFtpPass(), store.getDirDefault());
					sftp.connect();
				}

				for (File psdFile : psdFileList) {

					// COPY PSD FILE TO ORIGINALS FOLDER
					copyFile(psdFile,
							new File(propApp.get("locMediaBackup") + propApp.get("mediaBackupDirOriginals")
									+ FilenameUtils.getBaseName(psdFile.getName()) + File.separator
									+ FilenameUtils.getBaseName(psdFile.getName()) + currentDate + "."
									+ FilenameUtils.getExtension(psdFile.getName())));

					// GET BUFFEREDIMAGE FROM PSD FILE
					srcImage = initSrcFile(psdFile);
					progressThumbUpdate(imageHandler.createThumbnail(srcImage, 150));
					for (ImageSize imgSize : store.getStoreImageSizeListNew()) {

						// *** IMAGE SCALING ***
						progressLabelUpdate("Resize " + FilenameUtils.getBaseName(psdFile.getName()) + " to "
								+ imgSize.getWidth() + "px");
						
						
//						BufferedImage scaledImage = imageHandler.improvedMultiStepRescale(imgSize.getWidth(),
//								imgSize.getHeight(), srcImage);
						
//						BufferedImage scaledImage = imageHandler.resizeImage(imgSize.getWidth(),
//						imgSize.getHeight(), srcImage);
						
						//BufferedImage scaledImage = imageHandler.resizeImage(
						//		lockAspectRatioHeight(new Dimension(srcImage.getWidth(), srcImage.getHeight()),
						//				imgSize.getWidth()),
						//		srcImage);
						// BufferedImage scaledImage = imageHandler.resizeImage3(srcImage,
						// imgSize.getWidth());

						BufferedImage scaledImage = null;
						BufferedImage croppedImage = null;
						try {
							scaledImage = imageHandler.resizeImage(lockAspectRatioHeight(Sanselan.getImageSize(srcFile), imgSize.getWidth()), srcImage);
							System.out.println(lockAspectRatioHeight(Sanselan.getImageSize(srcFile), imgSize.getWidth()));
						} catch (ImageReadException e) {
							// TODO Auto-generated catch block		
							e.printStackTrace();
						}

						// *** IMAGE TRANSFORMATION
						croppedImage = imgSize.isSquerePadding() ? padImageToSquere(scaledImage) : scaledImage;
						
						// *** IMAGE CONVERSION ***
						// TODO method call convertToRGB
						progressLabelUpdate("Remove Alpha Channel from " + FilenameUtils.getBaseName(psdFile.getName())
								+ " (" + imgSize.getWidth() + "px)");
						BufferedImage rgbImage = imageHandler.removeAlphaChannel(croppedImage);

						// *** IMAGE WRITING ***
						File directory = new File(
								propApp.get("locMediaBackup") + propApp.get("mediaBackupDirLive") + imgSize.getName());
						if (!directory.exists()) {
							directory.mkdirs();
						}

						imgFile = new File(
								directory.getPath() + "/" + FilenameUtils.getBaseName(psdFile.getName()) + ".jpg");
						ImageIO.write(rgbImage, "jpg", imgFile);
						// imageHandler.imageWriteSanselan(rgbImage, imgFile);
						//writeFile(rgbImage, imgFile);

						// *** IMAGE FILE COMPRESSION ***
						if (store.isCompressionEnabled()) {

							// File workingDir=new File(System.getProperty("user.dir"));
							// Executor.compressImage(imgFile, workingDir, store.getCompressionCommand());
							Executor.exec(imgFile, store.getCompressionCommand());
						}

						// *** IMAGE FILE UPLOAD ***
						progressLabelUpdate("Upload " + FilenameUtils.getBaseName(psdFile.getName()) + " ("
								+ imgSize.getName() + ") to " + store.getStoreName());

						File remoteFile = new File(imgFile.getName());

						// connection using FTP
						if (store.getStoreFtpProtocol().equals("ftp")) {
							if (!ftp.isConnected()) {
								ftp.connect(store.getStoreFtpServer());
							}
							InputStream input = new FileInputStream(imgFile);
//							ftp.mkd(imgSize.getName());
//							ftp.cwd(imgSize.getName());
							ftp.storeFile(remoteFile.getName(), input);
//							ftp.changeToParentDirectory();

							// connection using SFTP
						} else if (store.getStoreFtpProtocol().equals("sftp")) {
							if (!sftp.session.isConnected()) {
								sftp.connect();
							}
							sftp.makeDir(imgSize.getName());
							sftp.changeDir(imgSize.getName());
							sftp.upload(imgFile, remoteFile);
							sftp.changeDir("..");
						}
					}
					progressBarUpdate(progressStepSize);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		progressBarUpdate(100);
		progressLabelUpdate("complete");
		view.btnCardNext.setEnabled(true);
		view.btnCardNext.setText("Done");
	}

	public BufferedImage initSrcFile(File srcFile) {
		this.srcFile = srcFile;
		String fileExt = FilenameUtils.getExtension(srcFile.getName());
		srcImage = null;
		try {
			if (fileExt.equalsIgnoreCase("psd") || fileExt.equalsIgnoreCase("psb")) {
//				Psd psd = new Psd(srcFile);
//				srcImage = psd.getImage();
				srcImage = imageHandler.imageReadSanselan(srcFile);
				srcImage = colorTools.correctImage(srcImage, srcFile);
			} else if (fileExt.equalsIgnoreCase("jpg") || fileExt.equalsIgnoreCase("jpeg")) {
				srcImage = ImageIO.read(srcFile);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ImageReadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return srcImage;
	}

	public void writeFile(BufferedImage image, File destFile) {

		String format = "JPEG";
		// Get the writer
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);

//	    if (!writers.hasNext()) {
//	        throw new IllegalArgumentException("No writer for: " + format);
//	    }

		ImageWriter writer = writers.next();
		System.out.println("reader: " + writer);
//	    while (writers.hasNext()) {
//	    	writer = writers.next();
//		    System.out.println("reader: " + writer);
//		    
//	    }

		try {
			// Create output stream
			ImageOutputStream output = ImageIO.createImageOutputStream(destFile);

			try {
				writer.setOutput(output);

				// Optionally, listen to progress, warnings, etc.

				ImageWriteParam param = writer.getDefaultWriteParam();
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				System.out.println(param.getProgressiveMode());
				System.out.println(param.getCompressionMode());
				System.out.println(param.getCompressionQuality());
				System.out.println(param.getBitRate(1f));

				// Optionally, control format specific settings of param (requires casting), or
				// control generic write settings like sub sampling, source region, output type
				// etc.

				// Optionally, provide thumbnails and image/stream metadata
				writer.write(writer.getDefaultStreamMetadata(param), new IIOImage(image, null, null), param);
			} finally {
				// Close stream in finally block to avoid resource leaks
				output.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// Dispose writer in finally block to avoid memory leaks
			writer.dispose();
		}
	}

	public void compress(BufferedImage srcImage, File destFile) throws IOException {
		OutputStream os = new FileOutputStream(destFile);
		Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = (ImageWriter) writers.next();

		ImageOutputStream ios = ImageIO.createImageOutputStream(os);
		writer.setOutput(ios);

		ImageWriteParam param = writer.getDefaultWriteParam();

//		if (param.canWriteProgressive()) {
//			param.setProgressiveMode(ImageWriteParam.MODE_DEFAULT);
//		}
//
//		if (param.canWriteCompressed()) {
//			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//			// param.setCompressionType("JPEG-LS");
//			param.setCompressionQuality(0.85f); // Change the quality value you prefer
//		}

		writer.write(writer.getDefaultStreamMetadata(param), new IIOImage(srcImage, null, null), param);

		os.close();
		ios.close();
		writer.dispose();
	}
	
	public BufferedImage padImageToSquere(BufferedImage scaledImage) {
		
		BufferedImageOp[] imageOp = null;
		BufferedImage paddedImage = null;
		BufferedImage croppedImage = null;
		int padding = 0;
		System.out.println(scaledImage.getWidth() + " " + scaledImage.getHeight());
		padding = Math.abs((scaledImage.getWidth() - scaledImage.getHeight()) * 1/2);
		System.out.println(padding);
		paddedImage = Scalr.pad(scaledImage, padding, Color.WHITE, imageOp);
		progressThumbUpdate(imageHandler.createThumbnail(paddedImage, 150));
		croppedImage = Scalr.crop(paddedImage, 0, padding, paddedImage.getWidth(), scaledImage.getHeight(), imageOp);
		progressThumbUpdate(imageHandler.createThumbnail(croppedImage, 150));
		return croppedImage;
	}

	public File[] openFiles() {
		File[] files = null;

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setDialogTitle("Select files (multiple selection possible)");
		fileChooser.setMultiSelectionEnabled(true);
		fileChooser.setLocation(100, 100);

		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			files = fileChooser.getSelectedFiles();
		}
		return files;
	}

	public void addFiles() {
		psdFiles = openFiles();
		if (psdFiles != null || psdFiles.length >= 0) {
			for (File file : psdFiles) {
				psdFileList.add(file);
			}
		}
		updatePanelCardSourceFiles();
	}

	public void removeFiles() {
		psdFileList.remove(view.fileListSourceFiles.getSelectedIndex());
		updatePanelCardSourceFiles();
	}

	public void clearList() {
		psdFileList.clear();
		updatePanelCardSourceFiles();
	}

	private void updatePanelCardSourceFiles() {
		view.fileListSourceFiles.setListData(psdFileList);
		System.out.println(psdFileList.isEmpty() + " -+- " + psdFileList.size());
		if (!psdFileList.isEmpty() || psdFileList.size() > 0) {
			view.btnCardNext.setEnabled(true);
		} else {
			view.btnCardNext.setEnabled(false);
		}
	}

	public void initSelectedStoreList() {
		selectedStores.clear();
		for (StoreDataModel store : stores) {
			if (store.getSelectStatus())
				selectedStores.add(store);
		}
		if (!selectedStores.isEmpty() || selectedStores.size() > 0) {
			view.btnCardNext.setEnabled(true);
		} else {
			view.btnCardNext.setEnabled(false);
		}
	}

	public void progressBarUpdate(int progressStepSize) {
		view.progressBar.setValue(view.progressBar.getValue() + progressStepSize);
	}

	public void progressLabelUpdate(String LabelText) {
		view.labelProgressStep.setText(LabelText);
	}

	public void progressThumbUpdate(BufferedImage previewThumb) {
		view.labelProgressThumb.setIcon(new ImageIcon(previewThumb));
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == view.btnCardBack) {
			view.cardLayoutContentContainer.previous(view.panelContentContainer);
			view.btnCardNext.setText("Next");
		} else if (ae.getSource() == view.btnCardNext) {
			if (view.btnCardNext.getText() == "Done") {
				view.dispose();
			} else {
				view.btnCardBack.setVisible(true);
				view.cardLayoutContentContainer.next(view.panelContentContainer);
			}
		} else if (ae.getSource() == view.btnAddFiles) {
			addFiles();
		} else if (ae.getSource() == view.btnRemoveFiles) {
			removeFiles();
		} else if (ae.getSource() == view.btnClearFileList) {
			clearList();
		} else if (ae.getSource() == view.btnSelectAll) {
			view.checkBoxListStores.selectAll();
			initSelectedStoreList();
		} else if (ae.getSource() == view.btnDeselectAll) {
			view.checkBoxListStores.deselectAll();
			initSelectedStoreList();
		}
	}

	@Override
	public void componentShown(ComponentEvent ce) {
		if (ce.getSource() == view.panelCardSourceFiles) {
			view.btnCardBack.setVisible(false);
		} else if (ce.getSource() == view.panelCardTargetStores) {
			initStores();
			initSelectedStoreList();
			view.checkBoxListStores.setListData(stores);
		} else if (ce.getSource() == view.panelCardSummary) {
			view.btnCardNext.setText("Import");
			initSelectedStoreList();
			view.fileListSourceFilesSummary.setListData(psdFileList);
			view.fileListSourceFilesSummary.setEnabled(false);
			view.storeListTargetStoresSummary.setListData(selectedStores);
			view.storeListTargetStoresSummary.setEnabled(false);
		} else if (ce.getSource() == view.panelCardProcessing) {
			view.btnCardBack.setVisible(false);
			view.btnCardNext.setEnabled(false);
			Thread t = new Thread() {
				@Override
				public void run() {
					process();
				}
			};
			t.start();
		}
	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChanged(ListSelectionEvent lse) {
		if (lse.getSource() == view.checkBoxListStores) {
			initSelectedStoreList();
		}

	}

}

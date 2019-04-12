package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;

import org.apache.sanselan.ImageDump;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

public class ImageProcessingSandBoxController extends ImportController implements ActionListener {

	private ImageProcessingSandBoxView view;

	public ImageProcessingSandBoxController() {
		initView();
	}

	private void initView() {
		view = new ImageProcessingSandBoxView(this);
		view.setVisible(true);
	}

	public File openFile() {
		File file = null;

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setDialogTitle("Select files (multiple selection possible)");
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setLocation(100, 100);

		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			file = fileChooser.getSelectedFile();
		}
		return file;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getActionCommand() == "BrowseSrcFile") {
			srcFile = openFile();
			view.textField.setText(srcFile.getPath());
			try {
				ImageDump dump = new ImageDump();
				dump.dump(Sanselan.getBufferedImage(srcFile));
				view.lblViewportSrcFile.setIcon(new ImageIcon(Sanselan.getBufferedImage(srcFile)));
			} catch (ImageReadException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}

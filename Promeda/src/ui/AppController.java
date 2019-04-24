package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppController implements ActionListener {

	private AppView view;

	public AppController() {
		initView();
	}

	private void initView() {
		view = new AppView(this);
		view.setVisible(true);
	}

	public void initProdImgImpWzrd() {
		new ProdImgImpWzrdController();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == view.btnExit) {
			System.exit(0);
		} else if (ae.getSource() == view.btnMenuImageImport) {
			view.popupMenuImageImport.show(view.getContentPane(), view.btnMenuImageImport.getX(), view.btnMenuImageImport.getY() + view.btnMenuImageImport.getHeight());
		} else if (ae.getSource() == view.mntmProduct) {
			new ProdImgImpWzrdController("product");
		} else if (ae.getSource() == view.mntmKombiProduct) {
			new ProdImgImpWzrdController("kombiproduct");
		} else if (ae.getSource() == view.mntmBanner) {
			new BannerImgImpWzrdController();
		} else if (ae.getSource() == view.mntmThemenwelt) {
			new CustomImgImpWzrdController();
		} else if (ae.getSource() == view.mntmFromList) {
			new MassImgImpWzrdController();
		} else if (ae.getSource() == view.btnBrands) {
			new BrandsController();
		} else if (ae.getSource() == view.btnSettings) {
			new SettingsController();
		} else if (ae.getSource() == view.btnSysInfo) {
			new SysInfoController();
		} else if (ae.getSource() == view.btnSearch || ae.getSource() == view.textFieldProdNr) {
			new ArticleController(view.textFieldProdNr.getText());
		} else if (ae.getSource() == view.btnImageProcessingSandBox) {
			new ImageProcessingSandBoxController();
		}
	}
}

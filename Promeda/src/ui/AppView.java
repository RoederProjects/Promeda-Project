package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AppView extends JFrame {

	public AppController controller;
	public JPanelWithBackgroundImage contentPane;
	public JButton btnSearch;
	public JButton btnSettings;
	public JButton btnExit;
	public JTextField textFieldProdNr;
	public JPopupMenu popupMenuImageImport;
	public JMenuItem mntmProduct;
	public JMenuItem mntmBanner;
	public JMenuItem mntmThemenwelt;
	public JButton btnMenuImageImport;
	public JButton btnImageProcessingSandBox;
	public JButton btnSysInfo;
	public JMenuItem mntmFromList;
	public JMenuItem mntmKombiProduct;
	public JButton btnBrands;

	/**
	 * Create the frame.
	 */
	public AppView(AppController controller) {
		setResizable(false);

		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 250, 477);
		contentPane = new JPanelWithBackgroundImage(getClass().getResource("/img/promeda-app-bg3.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("PROMEDA");
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
		lblNewLabel.setBounds(72, 11, 163, 40);
		lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(10, 96, 225, 40);
		contentPane.add(panel);

		textFieldProdNr = new JTextField();
		textFieldProdNr.setBounds(0, 0, 152, 40);
		textFieldProdNr.addActionListener(controller);
		panel.setLayout(null);
		textFieldProdNr.setFont(new Font("Open Sans", Font.BOLD, 14));
		panel.add(textFieldProdNr);
		textFieldProdNr.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.setBounds(149, 0, 76, 40);
		btnSearch.addActionListener(controller);
		panel.add(btnSearch);

		btnSettings = new JButton("Settings");
		btnSettings.addActionListener(controller);
		btnSettings.setSize(new Dimension(225, 40));
		btnSettings.setPreferredSize(new Dimension(300, 23));
		btnSettings.setAlignmentX(0.5f);
		btnSettings.setBounds(10, 312, 111, 40);
		contentPane.add(btnSettings);

		btnExit = new JButton("");
		btnExit.setContentAreaFilled(false);
		btnExit.setBorderPainted(false);
		btnExit.setIcon(new ImageIcon(AppView.class.getResource("/img/Power.png")));
		btnExit.addActionListener(controller);
		btnExit.setSize(new Dimension(225, 40));
		btnExit.setPreferredSize(new Dimension(300, 23));
		btnExit.setAlignmentX(0.5f);
		btnExit.setBounds(91, 364, 64, 64);
		contentPane.add(btnExit);

		JLabel lblNewLabel_1 = new JLabel("Promondo Media Administration");
		lblNewLabel_1.setFont(new Font("Gill Sans MT", Font.PLAIN, 12));
		lblNewLabel_1.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel_1.setBounds(72, 49, 163, 36);
		contentPane.add(lblNewLabel_1);

		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setIcon(new ImageIcon(AppView.class.getResource("/img/logo.png")));
		label.setBounds(10, 5, 52, 80);
		contentPane.add(label);

		btnMenuImageImport = new JButton("Image Imports");
		btnMenuImageImport.addActionListener(controller);
		btnMenuImageImport.setSize(new Dimension(225, 40));
		btnMenuImageImport.setPreferredSize(new Dimension(300, 23));
		btnMenuImageImport.setAlignmentX(0.5f);
		btnMenuImageImport.setBounds(10, 148, 225, 40);
		contentPane.add(btnMenuImageImport);

		popupMenuImageImport = new JPopupMenu();
		addPopup(btnMenuImageImport, popupMenuImageImport);

		mntmProduct = new JMenuItem("Product");
		mntmProduct.addActionListener(controller);
		popupMenuImageImport.add(mntmProduct);

		mntmKombiProduct = new JMenuItem("Kombiproduct");
		mntmKombiProduct.addActionListener(controller);
		popupMenuImageImport.add(mntmKombiProduct);
		
		mntmBanner = new JMenuItem("Banner");
		mntmBanner.addActionListener(controller);
		popupMenuImageImport.add(mntmBanner);

		mntmThemenwelt = new JMenuItem("Themenwelt");
		mntmThemenwelt.addActionListener(controller);
		popupMenuImageImport.add(mntmThemenwelt);
		
		mntmFromList = new JMenuItem("From list");
		mntmFromList.addActionListener(controller);
		popupMenuImageImport.add(mntmFromList);

		btnImageProcessingSandBox = new JButton("Image Processing SandBox");
		btnImageProcessingSandBox.addActionListener(controller);
		btnImageProcessingSandBox.setSize(new Dimension(225, 40));
		btnImageProcessingSandBox.setPreferredSize(new Dimension(300, 23));
		btnImageProcessingSandBox.setAlignmentX(0.5f);
		btnImageProcessingSandBox.setBounds(10, 261, 225, 40);
		contentPane.add(btnImageProcessingSandBox);
		
		btnSysInfo = new JButton("System Info");
		btnSysInfo.addActionListener(controller);
		btnSysInfo.setSize(new Dimension(225, 40));
		btnSysInfo.setPreferredSize(new Dimension(300, 23));
		btnSysInfo.setAlignmentX(0.5f);
		btnSysInfo.setBounds(124, 312, 111, 40);
		contentPane.add(btnSysInfo);
		
		btnBrands = new JButton("Brands");
		btnBrands.addActionListener(controller);
		btnBrands.setBounds(10, 199, 225, 36);
		contentPane.add(btnBrands);
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

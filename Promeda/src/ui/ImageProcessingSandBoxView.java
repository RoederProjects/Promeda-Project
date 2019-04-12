package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ImageProcessingSandBoxView extends JFrame {

	public ImageProcessingSandBoxController controller;
	public JPanel contentPane;
	public JTextField textField;
	public JButton btnOpenSrcFile;
	public JLabel lblViewportSrcFile;
	public JPanel panelSidebarLeft;
	public JPanel panelSidebarRight;
	public JTabbedPane tabbedPaneViewport;
	public JPanel panel;
	public JPanel panel_1;


	/**
	 * Create the frame.
	 */
	public ImageProcessingSandBoxView(ImageProcessingSandBoxController controller) {
		
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panelSidebarLeft = new JPanel();
		panelSidebarLeft.setPreferredSize(new Dimension(200, 10));
		panelSidebarLeft.setSize(new Dimension(150, 0));
		contentPane.add(panelSidebarLeft, BorderLayout.WEST);
		panelSidebarLeft.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		textField = new JTextField();
		textField.setEditable(false);
		panelSidebarLeft.add(textField);
		textField.setColumns(18);
		
		btnOpenSrcFile = new JButton("Browse");
		btnOpenSrcFile.setActionCommand("BrowseSrcFile");
		btnOpenSrcFile.addActionListener(controller);
		panelSidebarLeft.add(btnOpenSrcFile);
		
		panelSidebarRight = new JPanel();
		contentPane.add(panelSidebarRight, BorderLayout.EAST);
		
		tabbedPaneViewport = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPaneViewport, BorderLayout.CENTER);
		
		panel = new JPanel();
		tabbedPaneViewport.addTab("New tab", null, panel, null);
		panel.setLayout(new BorderLayout(0, 0));
		
		lblViewportSrcFile = new JLabel("");
		panel.add(lblViewportSrcFile, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		tabbedPaneViewport.addTab("New tab", null, panel_1, null);
	}

}

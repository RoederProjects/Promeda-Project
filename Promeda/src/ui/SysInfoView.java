package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class SysInfoView extends JFrame {

	public SysInfoController controller;
	public JPanel contentPane;
	public JTextPane txtpnSystemProperties;


	/**
	 * Create the frame.
	 */
	public SysInfoView(SysInfoController controller) {
		setTitle("System Info (Read Only)");
		this.controller = controller;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 635, 685);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(5, 7, 609, 622);
		contentPane.add(scrollPane);
		
		txtpnSystemProperties = new JTextPane();
		scrollPane.setViewportView(txtpnSystemProperties);
		txtpnSystemProperties.setText("System Properties");
	}

}

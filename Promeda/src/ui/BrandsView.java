package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Component;

public class BrandsView extends JFrame {

	private BrandsController controller;
	private JPanel contentPane;
	public JListBrands listBrands;
	public JList listStores;

	/**
	 * Create the frame.
	 */
	public BrandsView(BrandsController controller) {
		
		this.controller = controller;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 553, 585);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		contentPane.add(panelNorth, BorderLayout.NORTH);
		
		JPanel panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(300, 500));
		panelCenter.add(scrollPane);
		
		listBrands = new JListBrands();
		scrollPane.setViewportView(listBrands);
		
		JPanel panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);
		
		JPanel panelWest = new JPanel();
		contentPane.add(panelWest, BorderLayout.WEST);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setPreferredSize(new Dimension(200, 500));
		scrollPane_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane_1.setAlignmentY(Component.TOP_ALIGNMENT);
		panelWest.add(scrollPane_1);
		
		listStores = new JList();
		listStores.addListSelectionListener(controller);
		listStores.setAlignmentY(Component.TOP_ALIGNMENT);
		listStores.setAlignmentX(Component.LEFT_ALIGNMENT);
		listStores.setMaximumSize(new Dimension(100, 0));
		listStores.setSize(new Dimension(200, 0));
		listStores.setPreferredSize(new Dimension(150, 0));
		scrollPane_1.setViewportView(listStores);
	}

}

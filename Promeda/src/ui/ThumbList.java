package ui;

import java.awt.Component;
import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;

public class ThumbList extends JList {

	public ThumbList() {
		setCellRenderer(new CellRenderer());
		setLayoutOrientation(HORIZONTAL_WRAP);
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	protected class CellRenderer implements ListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			File file = (File) value;
			JLabel label = new JLabel(new ImageIcon(file.getPath()));
			label.setPreferredSize(new Dimension(110, 110));
			label.setBorder(new BevelBorder(1));
			return label;
		}
	}
}
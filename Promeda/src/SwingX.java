
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.UIManager;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXFrame;

public class SwingX {

    public static void main(String[] args) {
        JXFrame frame = new JXFrame("JXCollapsiblePane-Beispiel", true);
        frame.setLayout(new BorderLayout());
        JXCollapsiblePane cp = new JXCollapsiblePane();
        cp.setAnimated(true);
        JLabel label = new JLabel("Text");
        label.setForeground(Color.WHITE);
        JButton butt = new JButton("click");
        JPanel panel = new JPanel(new FlowLayout());
        panel.setOpaque(true);
        panel.setBackground(Color.DARK_GRAY);
        panel.add(label);
        panel.add(butt);
        cp.add(panel);

        Action toggleAction = cp.getActionMap().get(
                JXCollapsiblePane.TOGGLE_ACTION);
        toggleAction.putValue(JXCollapsiblePane.COLLAPSE_ICON,
                UIManager.getIcon("Tree.collapsedIcon"));
        toggleAction.putValue(JXCollapsiblePane.EXPAND_ICON,
                UIManager.getIcon("Tree.expandedIcon"));
        JToggleButton toggleButt = new JToggleButton(toggleAction);
        toggleButt.setText("");
        
        frame.add(cp, BorderLayout.NORTH);
        frame.add(toggleButt, BorderLayout.SOUTH);
        frame.setStartPosition(JXFrame.StartPosition.CenterInScreen);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
} 
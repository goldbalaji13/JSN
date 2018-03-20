package org.jsn.com.views.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToolBar;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.jsn.com.entity.UserEntity;

import lombok.Data;

@Data
public class BaseViewPanel extends JPanel {

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				this.showMenu(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				this.showMenu(e);
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	public JMenuItem mntmLogOut;
	private JLabel label;

	/**
	 * Create the panel.
	 */
	public BaseViewPanel(UserEntity logedInDetails) {
		this.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				BaseViewPanel.this.label.setText(
						"Welcome: " + logedInDetails.getName() + " [" + logedInDetails.getRole().toString() + "]");
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
			}
		});

		this.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		this.add(toolBar, BorderLayout.NORTH);

		this.label = new JLabel("");
		toolBar.add(this.label);

		JButton dropDownButton = new JButton("");
		dropDownButton.setIcon(new ImageIcon(BaseViewPanel.class.getResource("/images/drop_down_arrow_16x16.png")));
		toolBar.add(dropDownButton);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(dropDownButton, popupMenu);

		this.mntmLogOut = new JMenuItem("Log Out");
		this.mntmLogOut.setIcon(new ImageIcon(BaseViewPanel.class.getResource("/images/logout_16x16.png")));
		popupMenu.add(this.mntmLogOut);

	}
}

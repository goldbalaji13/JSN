package org.jsn.com.views.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jsn.com.dao.AbstractDao;
import org.jsn.com.entity.UserEntity;

public abstract class BaseViewPanel<T> extends JPanel {

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {

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

	private JLabel logInDetailLabel;

	protected JTable baseTable;
	protected UserEntity clientCredentials;
	protected JTextField textField;

	protected JPopupMenu bodyPopopMenu;
	private final AbstractDao<T> dao;

	private List<T> list;
	@SuppressWarnings("unchecked")
	private List<T> backupList = new ArrayList();

	/**
	 * Create the panel.
	 */
	public BaseViewPanel(UserEntity logedInDetails, AbstractDao<T> dao) {
		this.clientCredentials = logedInDetails;
		this.dao = dao;
		this.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				BaseViewPanel.this.logInDetailLabel.setText(
						"Welcome: " + logedInDetails.getName() + " [" + logedInDetails.getRole().toString() + "]");
				BaseViewPanel.this.refreshGrid();
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
		toolBar.setLayout(new BorderLayout(1, 0));
		this.add(toolBar, BorderLayout.NORTH);

		this.textField = new JTextField();
		this.textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				BaseViewPanel.this.search();
			}
		});
		this.textField.setToolTipText("Search Box");
		toolBar.add(this.textField, BorderLayout.CENTER);
		this.textField.setColumns(10);

		this.logInDetailLabel = new JLabel("");
		toolBar.add(this.logInDetailLabel, BorderLayout.NORTH);

		JButton dropDownButton = new JButton("");
		dropDownButton.setIcon(new ImageIcon(BaseViewPanel.class.getResource("/images/drop_down_arrow_16x16.png")));
		toolBar.add(dropDownButton, BorderLayout.EAST);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(dropDownButton, popupMenu);

		this.mntmLogOut = new JMenuItem("Log Out");
		this.mntmLogOut.setIcon(new ImageIcon(BaseViewPanel.class.getResource("/images/logout_16x16.png")));
		popupMenu.add(this.mntmLogOut);

		JScrollPane scrollPane = new JScrollPane();
		this.add(scrollPane, BorderLayout.CENTER);

		this.baseTable = new JTable();
		this.baseTable.setModel(this.getModel());

		this.bodyPopopMenu = new JPopupMenu();
		scrollPane.setComponentPopupMenu(this.bodyPopopMenu);

		JPopupMenu popupMenu_1 = this.getPopupMenu();
		this.baseTable.add(popupMenu_1);
		this.addPopupForGrid(this.baseTable, popupMenu_1);
		scrollPane.setViewportView(this.baseTable);

	}

	private void addPopupForGrid(JTable component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger() && BaseViewPanel.this.canShowPopup(component, e)) {
					this.showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	protected boolean canShowPopup(JTable component, MouseEvent e) {
		return component.getSelectedRowCount() > 0;
	}

	protected abstract boolean filter(T entity, String text);

	protected abstract Vector<Object> getGridVectorFromEntity(T entity);

	public List<T> getList() {
		return this.list;
	}

	protected abstract TableModel getModel();

	protected abstract JPopupMenu getPopupMenu();

	protected abstract Map<String, Object> getSearchCriteria();

	protected void refreshGrid() {
		this.list.clear();
		this.list.addAll(this.backupList);
	};

	protected void search() {
		if (this.textField.getText().isEmpty()) {
			this.refreshGrid();
		} else {
			DefaultTableModel model = (DefaultTableModel) this.baseTable.getModel();
			model.setRowCount(0);
			this.list.clear();
			this.backupList.stream().filter(entity -> this.filter(entity, this.textField.getText()))
					.peek(this.list::add).map(this::getGridVectorFromEntity).forEach(model::addRow);
		}
	}

	public void setList(List<T> list) {
		this.list = list;
		this.backupList.clear();
		this.backupList.addAll(list);
	}
}

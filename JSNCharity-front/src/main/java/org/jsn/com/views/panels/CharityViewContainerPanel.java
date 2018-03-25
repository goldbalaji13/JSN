package org.jsn.com.views.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jsn.com.dao.CharityViewDao;
import org.jsn.com.dao.DrugDao;
import org.jsn.com.entity.JoinedEntity;
import org.jsn.com.entity.UserEntity;

public class CharityViewContainerPanel extends JPanel {
	public CharityPanel panel;
	private JTable table;
	List<JoinedEntity> list = new ArrayList();

	/**
	 * Create the panel.
	 *
	 * @param logedInDetails
	 * @param dao
	 * @param drugDao
	 */
	public CharityViewContainerPanel(UserEntity logedInDetails, CharityViewDao dao, DrugDao drugDao) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JSplitPane splitPane = new JSplitPane();
		splitPane.setAlignmentY(Component.CENTER_ALIGNMENT);
		splitPane.setAlignmentX(Component.CENTER_ALIGNMENT);
		splitPane.setOneTouchExpandable(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		this.add(splitPane);

		this.panel = new CharityPanel(logedInDetails, dao, this::charityPanelEvent, drugDao);
		splitPane.setLeftComponent(this.panel);

		JPanel panel_1 = new JPanel();
		splitPane.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);

		this.table = new JTable();
		this.table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Drug Name", "Purchase Quantity",
				"Unit Price", "Days to expire", "Pharma Name", "Contact", "City", "Address" }) {
			Class[] columnTypes = new Class[] { String.class, Integer.class, Double.class, Integer.class, String.class,
					String.class, String.class, String.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return this.columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

		});
		scrollPane.setViewportView(this.table);

		JPopupMenu popupMenu = new JPopupMenu();
		this.table.add(popupMenu);
		this.addPopupForGrid(this.table, popupMenu);

		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.addActionListener(e -> {
			this.list.get(this.table.getSelectedRow()).takeBack();
			this.panel.refreshGrid();

		});
		popupMenu.add(mntmDelete);

		JButton btnNewButton = new JButton("Finish And Check Out");
		panel_1.add(btnNewButton, BorderLayout.SOUTH);
	}

	private void addPopupForGrid(JTable component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger() && component.getSelectedRowCount() > 0) {
					this.showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	private void charityPanelEvent(List<JoinedEntity> list) {
		DefaultTableModel model = (DefaultTableModel) this.table.getModel();
		model.setRowCount(0);
		this.list.clear();
		list.stream().filter(entity -> entity.getSoldQuantity() > 0).peek(this.list::add)
				.map(this::getGridVectorFromEntity).forEach(model::addRow);
	}

	private Vector<Object> getGridVectorFromEntity(JoinedEntity entity) {
		Vector<Object> modelVector = new Vector<>();
		modelVector.add(entity.getDrugName());
		modelVector.add(entity.getSoldQuantity());
		modelVector.add(entity.getUnitPrice());
		modelVector.add(LocalDate.now().until(entity.getExpiryDate()).getDays());
		modelVector.add(entity.getName());
		modelVector.add(entity.getContactNo());
		modelVector.add(entity.getCity());
		modelVector.add(entity.getAddress());
		return modelVector;
	}
}

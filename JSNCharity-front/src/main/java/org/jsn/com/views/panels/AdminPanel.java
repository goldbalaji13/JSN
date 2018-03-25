package org.jsn.com.views.panels;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.apache.commons.collections.MapUtils;
import org.jsn.com.dao.UserDao;
import org.jsn.com.entity.UserEntity;
import org.jsn.com.views.dialogues.Signup;
import org.jsn.enums.Role;

@SuppressWarnings("serial")
public class AdminPanel extends BaseViewPanel<UserEntity> {

	private JPopupMenu popupMenu;
	private JMenuItem mntmAddUser;
	private JMenuItem mntmDeleteUser;
	private UserDao dao;
	private JMenuItem mntmAddPharmacyUser;

	public AdminPanel(UserEntity userEntity, UserDao dao) {
		super(userEntity, dao);
		this.dao = dao;
	}

	@Override
	protected boolean canShowPopup(JTable component, MouseEvent e) {
		boolean result = super.canShowPopup(component, e);
		if (result) {
			this.mntmDeleteUser.setEnabled(true);
			return result;
		}
		this.mntmDeleteUser.setEnabled(false);
		return true;
	}

	@Override
	protected Vector<Object> getGridVectorFromEntity(UserEntity entity) {
		Vector<Object> modelVector = new Vector<>();
		modelVector.add(entity.getUserName());
		modelVector.add(entity.getName());
		modelVector.add(entity.getRole().toString());
		modelVector.add(entity.getCity());
		modelVector.add(entity.getContactNo());
		modelVector.add(entity.getAddress());
		return modelVector;
	}

	@Override
	protected TableModel getModel() {
		return new DefaultTableModel(new Object[][] {},
				new String[] { "User Name", "Name", "Role", "City", "Contact No", "Address" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, Short.class, String.class, String.class,
					String.class };

			@Override
			public Class getColumnClass(int columnIndex) {
				return this.columnTypes[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	@Override
	protected JPopupMenu getPopupMenu() {
		this.popupMenu = new JPopupMenu();
		this.mntmAddUser = new JMenuItem("Add Charity User");
		this.mntmAddUser.addActionListener(e -> {
			Signup addDialogue = new Signup(Role.CHARITY, this.dao);
			addDialogue.setVisible(true);
			this.refreshGrid();
		});
		this.bodyPopopMenu.add(this.mntmAddUser);

		this.mntmAddPharmacyUser = new JMenuItem("Add Pharmacy User");
		this.mntmAddPharmacyUser.addActionListener(e -> {
			Signup addDialogue = new Signup(Role.PHARMA, this.dao);
			addDialogue.setVisible(true);
			this.refreshGrid();
		});
		this.bodyPopopMenu.add(this.mntmAddPharmacyUser);

		this.mntmDeleteUser = new JMenuItem("Delete User");
		this.mntmDeleteUser.addActionListener(e -> {
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(this,
					"Do you Want to delete selected Users?.Admin account can't be deleted.", "Deletion Warning",
					dialogButton);
			DefaultTableModel model = (DefaultTableModel) AdminPanel.this.baseTable.getModel();
			List<String> deleteUserList = IntStream.of(AdminPanel.this.baseTable.getSelectedRows()).boxed()
					.filter(index -> !((Vector) model.getDataVector().elementAt(index)).elementAt(2).toString()
							.equals(Role.ADMIN.toString()))
					.map(index -> ((Vector) model.getDataVector().elementAt(index)).elementAt(0).toString())
					.collect(Collectors.toList());
			if (dialogResult == 0 && !deleteUserList.isEmpty()) {
				this.dao.deleteUser(deleteUserList);
				this.refreshGrid();
			}

		});
		this.mntmDeleteUser.setBackground(new Color(240, 128, 128));
		this.popupMenu.add(this.mntmDeleteUser);
		return this.popupMenu;
	}

	@Override
	protected Map<String, Object> getSearchCriteria() {
		return MapUtils.EMPTY_MAP;
	}

	/**
	 * Create the panel.
	 *
	 * @param userEntity
	 */
	@Override
	protected void refreshGrid() {
		this.list = AdminPanel.this.dao.getAll();
		DefaultTableModel model = (DefaultTableModel) AdminPanel.this.baseTable.getModel();
		model.setRowCount(0);
		this.list.stream().map(this::getGridVectorFromEntity).forEach(model::addRow);
	}
}

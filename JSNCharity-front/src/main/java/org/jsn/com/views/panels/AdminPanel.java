package org.jsn.com.views.panels;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

import org.jsn.com.dao.UserDao;
import org.jsn.com.entity.UserEntity;
import org.jsn.com.views.dialogues.Signup;
import org.jsn.enums.Role;

@SuppressWarnings("serial")
public class AdminPanel extends BaseViewPanel {
	private static void addPopup(JTable component, final JPopupMenu popup) {
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

	private JTable userTabel;
	private JPopupMenu popupMenu;
	private JMenuItem mntmAddUser;
	private JMenuItem mntmDeleteUser;
	private UserDao dao;
	private JMenuItem mntmAddPharmacyUser;

	public AdminPanel(UserEntity userEntity, UserDao dao) {
		super(userEntity);
		this.dao = dao;
		this.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				AdminPanel.this.refreshGrid();
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
			}

		});
		JScrollPane scrollPane = new JScrollPane();
		this.add(scrollPane);

		this.userTabel = new JTable();
		this.userTabel.setModel(new DefaultTableModel(new Object[][] {},
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
		});
		this.userTabel.getColumnModel().getColumn(1).setPreferredWidth(96);
		this.userTabel.getColumnModel().getColumn(2).setPreferredWidth(127);
		this.userTabel.getColumnModel().getColumn(3).setPreferredWidth(98);
		this.userTabel.getColumnModel().getColumn(4).setPreferredWidth(214);
		this.userTabel.getColumnModel().getColumn(5).setPreferredWidth(520);
		scrollPane.setViewportView(this.userTabel);

		this.popupMenu = new JPopupMenu();
		addPopup(this.userTabel, this.popupMenu);
		this.userTabel.add(this.popupMenu);

		this.mntmAddUser = new JMenuItem("Add Charity User");
		this.mntmAddUser.addActionListener(e -> {
			Signup addDialogue = new Signup(Role.CHARITY, dao);
			addDialogue.setVisible(true);
			this.refreshGrid();
		});
		this.popupMenu.add(this.mntmAddUser);

		this.mntmAddPharmacyUser = new JMenuItem("Add Pharmacy User");
		this.mntmAddPharmacyUser.addActionListener(e -> {
			Signup addDialogue = new Signup(Role.PHARMA, dao);
			addDialogue.setVisible(true);
			this.refreshGrid();
		});
		this.popupMenu.add(this.mntmAddPharmacyUser);

		this.mntmDeleteUser = new JMenuItem("Delete User");
		this.mntmDeleteUser.addActionListener(e -> {
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(this,
					"Do you Want to delete selected Users?.Admin account can't be deleted.", "Deletion Warning",
					dialogButton);
			DefaultTableModel model = (DefaultTableModel) AdminPanel.this.userTabel.getModel();
			List<String> deleteUserList = IntStream.of(AdminPanel.this.userTabel.getSelectedRows()).boxed()
					.filter(index -> !((Vector) model.getDataVector().elementAt(index)).elementAt(2).toString()
							.equals(Role.ADMIN.toString()))
					.map(index -> ((Vector) model.getDataVector().elementAt(index)).elementAt(0).toString())
					.collect(Collectors.toList());
			if (dialogResult == 0 && !deleteUserList.isEmpty()) {
				dao.deleteUser(deleteUserList);
				this.refreshGrid();
			}

		});
		this.mntmDeleteUser.setBackground(new Color(240, 128, 128));
		this.popupMenu.add(this.mntmDeleteUser);
	}

	/**
	 * Create the panel.
	 *
	 * @param userEntity
	 */
	private void refreshGrid() {
		List<UserEntity> list = AdminPanel.this.dao.getAll();
		DefaultTableModel model = (DefaultTableModel) AdminPanel.this.userTabel.getModel();
		model.setRowCount(0);
		list.stream().map(entity -> {
			Vector<String> modelVector = new Vector<>();
			modelVector.add(entity.getUserName());
			modelVector.add(entity.getName());
			modelVector.add(entity.getRole().toString());
			modelVector.add(entity.getCity());
			modelVector.add(entity.getContactNo());
			modelVector.add(entity.getAddress());
			return modelVector;
		}).forEach(model::addRow);
	}
}

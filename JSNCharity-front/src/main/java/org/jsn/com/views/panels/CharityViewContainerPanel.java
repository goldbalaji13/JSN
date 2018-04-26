package org.jsn.com.views.panels;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog.ModalityType;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import org.jsn.com.dao.CharityViewDao;
import org.jsn.com.dao.DrugDao;
import org.jsn.com.entity.JoinedEntity;
import org.jsn.com.entity.UserEntity;
import org.jsn.com.views.cellRenderes.NumberRenderer;
import org.jsn.com.views.dialogues.PatientDetails;
import org.jsn.com.views.dialogues.PdfViewer;

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
				"Reduced Unit Price", "Days to expire", "Pharma Name", "Contact", "City", "Address" }) {
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
		TableColumnModel cellModel = this.table.getColumnModel();

		cellModel.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellModel.getColumn(1).setCellRenderer(NumberRenderer.getIntegerRenderer());
		cellModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
		cellModel.getColumn(3).setCellRenderer(NumberRenderer.getIntegerRenderer());
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
		btnNewButton.addActionListener(e -> {
			PatientDetails detail = new PatientDetails();
			detail.setVisible(true);
			if (detail.getResult() == 1) {
				this.showPdfPrintDialogue(detail.getPatientName(), detail.getPatientAge());
				this.list.stream().map(JoinedEntity::getDrugDto).forEach(drugDao::update);
				this.panel.hardRefetch();
			}
		});
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
		modelVector.add(entity.getUnitPrice() * 0.5);
		modelVector.add(LocalDate.now().until(entity.getExpiryDate()).getDays());
		modelVector.add(entity.getName());
		modelVector.add(entity.getContactNo());
		modelVector.add(entity.getCity());
		modelVector.add(entity.getAddress());
		return modelVector;
	}

	private void showPdfPrintDialogue(String name, int age) {
		Map<String, Object> DataBeanList = new HashMap();
		DataBeanList.put("billerName", name);
		DataBeanList.put("billerAge", age);
		DataBeanList.put("hospitalName", this.panel.getHospitalName());

		List<Map<String, Object>> drugList = this.list.stream().map(entity -> {
			Map<String, Object> gridMap = new HashMap();
			gridMap.put("pharmaName", entity.getName());
			gridMap.put("drugName", entity.getDrugName());
			gridMap.put("quantity", entity.getSoldQuantity());
			gridMap.put("unitPrice", new BigDecimal(entity.getUnitPrice()));
			return gridMap;
		}).collect(Collectors.toList());
		PdfViewer pdfViewer = new PdfViewer(DataBeanList, drugList);
		JDialog frame = new JDialog();
		frame.setTitle("Bill Report");
		frame.setModalityType(ModalityType.APPLICATION_MODAL);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(pdfViewer);
		frame.pack();
		frame.setVisible(true);
	}
}

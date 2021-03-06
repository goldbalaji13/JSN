package org.jsn.com.views.panels;

import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.jsn.com.dao.DrugDao;
import org.jsn.com.entity.UserEntity;
import org.jsn.com.viewEnums.OpenMode;
import org.jsn.com.views.cellRenderes.FormatRenderer;
import org.jsn.com.views.cellRenderes.NumberRenderer;
import org.jsn.com.views.dialogues.AddDrug;
import org.jsn.dto.DrugDto;

public class PharmaPanel extends BaseViewPanel<DrugDto> {

	private JPopupMenu popupMenu;
	private final DrugDao dao;
	private JMenuItem mntmDeleteDrug;

	/**
	 * Create the panel.
	 *
	 * @param logedInDetails
	 */
	public PharmaPanel(UserEntity logedInDetails, DrugDao dao) {
		super(logedInDetails, dao);
		this.dao = dao;
		TableColumnModel cellModel = this.baseTable.getColumnModel();

		cellModel.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellModel.getColumn(1).setCellRenderer(NumberRenderer.getIntegerRenderer());
		cellModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
		cellModel.getColumn(3).setCellRenderer(FormatRenderer.getDateTimeRenderer());

		cellModel.getColumn(4).setMinWidth(0);
		cellModel.getColumn(4).setMaxWidth(0);
		cellModel.getColumn(4).setWidth(0);
	}

	@Override
	protected boolean canShowPopup(JTable component, MouseEvent e) {
		boolean result = super.canShowPopup(component, e);
		if (result) {
			this.mntmDeleteDrug.setEnabled(true);
			return result;
		}
		this.mntmDeleteDrug.setEnabled(false);
		return true;
	}

	@Override
	protected boolean filter(DrugDto entity, String text) {
		return entity.getDrugName().contains(text);
	}

	@Override
	protected Vector<Object> getGridVectorFromEntity(DrugDto entity) {
		Vector<Object> modelVector = new Vector<>();
		modelVector.add(entity.getDrugName());
		modelVector.add(entity.getQuantity());
		modelVector.add(entity.getUnitPrice());
		modelVector.add(entity.getExpiryDate());
		return modelVector;
	}

	@Override
	protected TableModel getModel() {

		return new DefaultTableModel(new Object[][] {},
				new String[] { "Drug Name", "Available Quantity", "Unit Price", "Expiry Date", "Batch No" }) {
			Class[] columnTypes = new Class[] { String.class, Integer.class, Double.class, LocalDate.class,
					Integer.class };

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

		JMenuItem mntmAddDrug = new JMenuItem("Add Drug");
		mntmAddDrug.addActionListener(e -> {
			AddDrug addDialogue = new AddDrug(OpenMode.ADD, null, this.dao, this.clientCredentials.getUserName());
			addDialogue.setVisible(true);
			this.refreshGrid();
		});
		this.bodyPopopMenu.add(mntmAddDrug);

		this.mntmDeleteDrug = new JMenuItem("Delete Drug");
		this.mntmDeleteDrug.addActionListener(e -> {

			int dialogButton = JOptionPane.YES_NO_OPTION;
			int dialogResult = JOptionPane.showConfirmDialog(this, "Do you Want to delete selected Drugs?",
					"Deletion Warning", dialogButton);
			DefaultTableModel model = (DefaultTableModel) this.baseTable.getModel();
			List<DrugDto> deleteDrugList = IntStream.of(this.baseTable.getSelectedRows()).boxed()
					.map(index -> this.getList().get(index)).collect(Collectors.toList());
			if (dialogResult == 0 && !deleteDrugList.isEmpty()) {

				this.dao.delete(deleteDrugList);
				this.refreshGrid();
			}
		});
		this.popupMenu.add(this.mntmDeleteDrug);
		return this.popupMenu;
	}

	@Override
	protected Map<String, Object> getSearchCriteria() {
		Map<String, Object> criteriaMap = new HashMap<>();
		criteriaMap.put("userName", this.clientCredentials.getUserName());
		return criteriaMap;
	}

	@Override
	protected void refreshGrid() {
		this.setList(this.dao.getPharmaDrug(this.clientCredentials.getUserName()));
		DefaultTableModel model = (DefaultTableModel) this.baseTable.getModel();
		model.setRowCount(0);
		this.getList().stream().map(this::getGridVectorFromEntity).forEach(model::addRow);
	}
}

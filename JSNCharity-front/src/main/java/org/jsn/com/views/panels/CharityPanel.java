package org.jsn.com.views.panels;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.function.Consumer;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.commons.collections.MapUtils;
import org.jsn.com.dao.CharityViewDao;
import org.jsn.com.dao.DrugDao;
import org.jsn.com.entity.JoinedEntity;
import org.jsn.com.entity.UserEntity;
import org.jsn.com.views.cellRenderes.NumberRenderer;
import org.jsn.com.views.dialogues.AddToCart;

public class CharityPanel extends BaseViewPanel<JoinedEntity> {

	private CharityViewDao dao;
	private Consumer<List<JoinedEntity>> event;
	private DrugDao drugDao;

	/**
	 * Create the panel.
	 *
	 * @param logedInDetails
	 * @param dao
	 */
	public CharityPanel(UserEntity logedInDetails, CharityViewDao dao, Consumer<List<JoinedEntity>> event,
			DrugDao drugDao) {
		super(logedInDetails, dao);
		this.dao = dao;
		this.event = event;
		this.drugDao = drugDao;
		this.setList(this.dao.getCharityDrug());
		TableColumnModel cellModel = this.baseTable.getColumnModel();

		cellModel.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellModel.getColumn(1).setCellRenderer(NumberRenderer.getIntegerRenderer());
		cellModel.getColumn(2).setCellRenderer(NumberRenderer.getCurrencyRenderer());
		cellModel.getColumn(3).setCellRenderer(NumberRenderer.getIntegerRenderer());
	}

	@Override
	protected boolean filter(JoinedEntity entity, String text) {
		return entity.getDrugName().contains(text);
	}

	@Override
	protected Vector<Object> getGridVectorFromEntity(JoinedEntity entity) {
		Vector<Object> modelVector = new Vector<>();
		modelVector.add(entity.getDrugName());
		modelVector.add(entity.getQuantity());
		modelVector.add(entity.getUnitPrice());
		modelVector.add(LocalDate.now().until(entity.getExpiryDate()).getDays());
		modelVector.add(entity.getName());
		modelVector.add(entity.getContactNo());
		modelVector.add(entity.getCity());
		modelVector.add(entity.getAddress());
		return modelVector;
	}

	public String getHospitalName() {
		return this.clientCredentials.getName();
	}

	public List<JoinedEntity> getMasterList() {
		return this.getList();
	}

	@Override
	protected TableModel getModel() {
		return new DefaultTableModel(new Object[][] {}, new String[] { "Drug Name", "Available Quantity", "Unit Price",
				"Days to expire", "Pharma Name", "Contact", "City", "Address" }) {
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

		};
	}

	@Override
	protected JPopupMenu getPopupMenu() {
		JPopupMenu menu = new JPopupMenu();

		JMenuItem mntmAddToCart = new JMenuItem("Add To Cart");
		mntmAddToCart.addActionListener(e -> {
			JoinedEntity entity = this.getList().get(this.baseTable.getSelectedRow());
			AddToCart cart = new AddToCart(entity.getQuantity());
			cart.setVisible(true);
			if (cart.ok) {
				entity.sell(cart.sellQuantity);
				CharityPanel.this.event.accept(CharityPanel.this.getList());
				this.refreshGrid();
			}
		});
		menu.add(mntmAddToCart);
		return menu;
	}

	@Override
	protected Map<String, Object> getSearchCriteria() {
		return MapUtils.EMPTY_MAP;
	}

	public void hardRefetch() {
		this.setList(this.dao.getCharityDrug());
		this.refreshGrid();
	}

	@Override
	public void refreshGrid() {
		super.refreshGrid();
		DefaultTableModel model = (DefaultTableModel) this.baseTable.getModel();
		model.setRowCount(0);
		this.getList().stream().map(this::getGridVectorFromEntity).forEach(model::addRow);
		this.event.accept(CharityPanel.this.getList());
	}

	@Override
	protected void search() {
		super.search();
		this.event.accept(CharityPanel.this.getList());
	}
}

package org.jsn.com.views.dialogues;

import java.awt.Color;
import java.awt.Font;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import org.jsn.com.dao.DrugDao;
import org.jsn.com.validator.AbstractValidator;
import org.jsn.com.validator.WantsValidationStatus;
import org.jsn.com.viewEnums.OpenMode;
import org.jsn.dto.DrugDto;
import org.springframework.util.Assert;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class AddDrug extends JDialog {

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(() -> {
	// try {
	// DrugDto dto = new DrugDto();
	// dto.setDrugName("name");
	// dto.setExpiryDate(LocalDate.now());
	// dto.setQuantity(1);
	// dto.setUnitPrice(122578.15);
	// AddDrug window = new AddDrug(OpenMode.UPDATE, dto, null, "");
	// window.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// });
	// }

	private JTextField drugName;
	private Map<String, Boolean> validationMap = new HashMap<>();
	private OpenMode mode;
	private DrugDto toBeEditDto;
	private final DrugDao drugDao;
	private final String userName;
	private JFormattedTextField detailUnitPriceTextInput;
	private JSpinner detailQuantitySpinner;
	private JDatePickerImpl epiryDatePicker;
	private JButton addEditButton;

	/**
	 * Create the application.
	 */
	public AddDrug(OpenMode mode, DrugDto toBeEdit, DrugDao drugDao, String userName) {
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setModal(true);
		if (mode.equals(OpenMode.UPDATE)) {
			this.setTitle("Edit Drug");
			Assert.notNull(toBeEdit, "Edit Dto cant be Null");
		}
		this.mode = mode;
		this.toBeEditDto = toBeEdit;
		this.drugDao = drugDao;
		this.userName = userName;
		this.setTitle("Add Drug");
		this.initialize(mode, toBeEdit);
	}

	private void checkButtonEnable() {
		int validationCount = this.mode.equals(OpenMode.ADD) ? 3 : 2;
		if (this.validationMap.entrySet().size() != validationCount || this.validationMap.entrySet().stream()
				.map(Map.Entry::getValue).filter(bool -> !bool).findAny().isPresent()) {
			this.addEditButton.setEnabled(false);
		} else {
			this.addEditButton.setEnabled(true);
		}
	}

	private void enableAmountValidation() {
		WantsValidationStatus detailUnitPriceTextInputStatus = new WantsValidationStatus() {

			@Override
			public void validateFailed() {
				AddDrug.this.validationMap.put("ammount", false);
				AddDrug.this.checkButtonEnable();
			}

			@Override
			public void validatePassed() {
				AddDrug.this.validationMap.put("ammount", true);
				AddDrug.this.checkButtonEnable();
			}
		};
		new AbstractValidator(this, this.detailUnitPriceTextInput, "Drug price can't be empty",
				detailUnitPriceTextInputStatus) {

			@Override
			protected boolean validationCriteria(JComponent c) {
				try {
					AddDrug.this.detailUnitPriceTextInput.commitEdit();
					return !AddDrug.this.detailUnitPriceTextInput.getValue().toString().isEmpty();
				} catch (ParseException e) {
					AddDrug.this.detailUnitPriceTextInput.setValue(null);
					return false;
				}

			}
		};
	}

	private void enableDateValidation() {
		WantsValidationStatus dateStatus = new WantsValidationStatus() {

			@Override
			public void validateFailed() {
				AddDrug.this.validationMap.put("date", false);
				AddDrug.this.checkButtonEnable();
			}

			@Override
			public void validatePassed() {
				AddDrug.this.validationMap.put("date", true);
				AddDrug.this.checkButtonEnable();
			}
		};
		new AbstractValidator(this, this.epiryDatePicker.getJFormattedTextField(), "Date Can't be empty", dateStatus) {

			@Override
			protected boolean validationCriteria(JComponent c) {
				try {
					AddDrug.this.epiryDatePicker.getJFormattedTextField().commitEdit();
				} catch (ParseException e) {
					AddDrug.this.epiryDatePicker.getJFormattedTextField().setValue(null);
				}
				java.util.GregorianCalendar cal = (GregorianCalendar) AddDrug.this.epiryDatePicker
						.getJFormattedTextField().getValue();
				if (Objects.nonNull(cal)) {
					if (LocalDate.now().isAfter(cal.toZonedDateTime().toLocalDate())) {
						this.setMessage("Already expired Drug");
						return false;
					}
					return true;
				} else {
					return false;
				}
			}
		};
	}

	/**
	 * Initialize the contents of the
	 *
	 * @param toBeEdit
	 */
	private void initialize(OpenMode mode, DrugDto toBeEdit) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look and feel.
		}
		this.setResizable(false);
		this.setBounds(100, 100, 450, 331);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		JLabel lblNewLabel = new JLabel("ADD DRUG");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

		JLabel lblDrugName = new JLabel("DRUG NAME");

		JLabel lblQuantity = new JLabel("QUANTITY");

		JLabel lblUnitprice = new JLabel("UNIT PRICE");

		JLabel lblNewLabel_1 = new JLabel("EXPIRY DATE");

		this.drugName = new JTextField();
		this.drugName.setColumns(10);

		this.addEditButton = new JButton("ADD");
		this.addEditButton.addActionListener(e -> {
			if (mode.equals(OpenMode.UPDATE)) {
				Calendar Calendar = (java.util.Calendar) AddDrug.this.epiryDatePicker.getJFormattedTextField()
						.getValue();
				toBeEdit.setExpiryDate(LocalDate
						.from(Instant.ofEpochMilli(Calendar.getTime().getTime()).atZone(ZoneId.systemDefault())));
				toBeEdit.setQuantity((Integer) AddDrug.this.detailQuantitySpinner.getValue());
				toBeEdit.setUnitPrice(((Number) AddDrug.this.detailUnitPriceTextInput.getValue()).doubleValue());
				this.drugDao.update(toBeEdit);
			} else {
				DrugDto dto = new DrugDto();
				dto.setDrugName(this.drugName.getText());
				dto.setUserName(this.userName);
				Calendar Calendar = (java.util.Calendar) AddDrug.this.epiryDatePicker.getJFormattedTextField()
						.getValue();
				dto.setExpiryDate(LocalDate
						.from(Instant.ofEpochMilli(Calendar.getTime().getTime()).atZone(ZoneId.systemDefault())));
				dto.setQuantity((Integer) AddDrug.this.detailQuantitySpinner.getValue());
				dto.setUnitPrice(((Number) AddDrug.this.detailUnitPriceTextInput.getValue()).doubleValue());
				this.drugDao.insert(dto);
			}
			this.setVisible(false);
			this.dispose();
		});
		this.addEditButton.setEnabled(false);
		this.addEditButton.setFont(new Font("SansSerif", Font.BOLD, 14));

		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanelImpl = new JDatePanelImpl(model);
		datePanelImpl.setBounds(109, 312, 200, 180);

		this.epiryDatePicker = new JDatePickerImpl(datePanelImpl);
		datePanelImpl.addActionListener(e -> {
			AddDrug.this.epiryDatePicker.getJFormattedTextField().getInputVerifier()
					.shouldYieldFocus(AddDrug.this.epiryDatePicker.getJFormattedTextField());

		});
		this.epiryDatePicker.setTextEditable(true);
		this.epiryDatePicker.getJFormattedTextField().setEditable(true);
		this.epiryDatePicker.getJFormattedTextField().setBackground(Color.WHITE);

		this.detailQuantitySpinner = new JSpinner();
		this.detailQuantitySpinner
				.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		NumberFormat amountDisplayFormat = NumberFormat.getCurrencyInstance();
		amountDisplayFormat.setMinimumFractionDigits(3);
		NumberFormat amountEditFormat = NumberFormat.getNumberInstance();
		this.detailUnitPriceTextInput = new JFormattedTextField(
				new DefaultFormatterFactory(new NumberFormatter(amountDisplayFormat),
						new NumberFormatter(amountDisplayFormat), new NumberFormatter(amountEditFormat)));
		GroupLayout groupLayout = new GroupLayout(this.getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup()
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
						.createSequentialGroup().addGap(37)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(114).addComponent(this.drugName,
										GroupLayout.PREFERRED_SIZE, 293, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblDrugName, GroupLayout.PREFERRED_SIZE, 117,
										GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup().addGap(37)
								.addComponent(lblQuantity, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(this.detailQuantitySpinner, GroupLayout.DEFAULT_SIZE, 294,
										Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addGap(37)
								.addComponent(lblUnitprice, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(this.detailUnitPriceTextInput, GroupLayout.DEFAULT_SIZE, 294,
										Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addGap(176).addComponent(lblNewLabel))
						.addGroup(groupLayout.createSequentialGroup().addGap(37)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(this.epiryDatePicker, GroupLayout.PREFERRED_SIZE, 293,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED, 1, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup().addGap(93).addComponent(this.addEditButton,
								GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)))
				.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(20)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(this.drugName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup().addGap(6).addComponent(lblDrugName)))
						.addGap(21)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblQuantity)
								.addComponent(this.detailQuantitySpinner, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(19)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(lblUnitprice)
								.addComponent(this.detailUnitPriceTextInput, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(15)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(this.epiryDatePicker, GroupLayout.PREFERRED_SIZE, 36,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1))
						.addGap(18)
						.addComponent(this.addEditButton, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addGap(66)));
		this.getContentPane().setLayout(groupLayout);
		if (mode.equals(OpenMode.UPDATE)) {
			lblNewLabel.setText("EDIT DRUG");
			this.setTitle("UPDATE");
			this.addEditButton.setText("UPDATE");
			this.addEditButton.setEnabled(true);
			this.drugName.setEnabled(false);
			this.drugName.setText(toBeEdit.getDrugName());
			this.detailQuantitySpinner.setValue(toBeEdit.getQuantity());
			this.detailUnitPriceTextInput.setValue(toBeEdit.getUnitPrice());
			ZonedDateTime zone = toBeEdit.getExpiryDate().atStartOfDay(ZoneId.systemDefault());
			Date date = new Date(zone.toInstant().toEpochMilli());
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			this.epiryDatePicker.getJFormattedTextField().setValue(calender);
			this.setEditValidations();
		} else {
			this.setAddValidations();
		}
	}

	private void setAddValidations() {
		this.enableDateValidation();
		this.enableAmountValidation();
		WantsValidationStatus drugNameStatus = new WantsValidationStatus() {

			@Override
			public void validateFailed() {
				AddDrug.this.validationMap.put("drugName", false);
				AddDrug.this.checkButtonEnable();

			}

			@Override
			public void validatePassed() {
				AddDrug.this.validationMap.put("drugName", true);
				AddDrug.this.checkButtonEnable();

			}
		};
		new AbstractValidator(this, this.drugName, "Drug Name Can't be empty", drugNameStatus) {

			@Override
			protected boolean validationCriteria(JComponent c) {
				if (AddDrug.this.drugName.getText().isEmpty()) {
					return false;
				}
				return true;
			}
		};

	}

	private void setEditValidations() {
		this.enableDateValidation();
		this.validationMap.put("date", true);
		this.enableAmountValidation();
		this.validationMap.put("ammount", true);
	}
}

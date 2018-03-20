package org.jsn.com.views.dialogues;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class AddDrug extends JDialog {

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// AddDrug window = new AddDrug();
	// window.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public AddDrug() {
		this.setTitle("Add Drug");
		this.initialize();
	}

	/**
	 * Initialize the contents of the
	 */
	private void initialize() {
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
		this.setBounds(100, 100, 450, 344);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("ADD DRUG");
		lblNewLabel.setBounds(187, 11, 78, 25);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		this.getContentPane().add(lblNewLabel);

		JLabel lblDrugName = new JLabel("DRUG NAME");
		lblDrugName.setBounds(37, 69, 117, 16);
		this.getContentPane().add(lblDrugName);

		JLabel lblQuantity = new JLabel("QUANTITY");
		lblQuantity.setBounds(37, 114, 78, 16);
		this.getContentPane().add(lblQuantity);

		JLabel lblNewLabel_1 = new JLabel("EXPIRY DATE");
		lblNewLabel_1.setBounds(37, 152, 95, 16);
		this.getContentPane().add(lblNewLabel_1);

		JLabel lblBatchNo = new JLabel("BATCH NO");
		lblBatchNo.setBounds(37, 207, 78, 16);
		this.getContentPane().add(lblBatchNo);

		this.textField = new JTextField();
		this.textField.setBounds(151, 63, 158, 28);
		this.getContentPane().add(this.textField);
		this.textField.setColumns(10);

		this.textField_1 = new JTextField();
		this.textField_1.setBounds(151, 108, 158, 28);
		this.getContentPane().add(this.textField_1);
		this.textField_1.setColumns(10);

		this.textField_3 = new JTextField();
		this.textField_3.setBounds(151, 201, 158, 28);
		this.getContentPane().add(this.textField_3);
		this.textField_3.setColumns(10);

		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton.setBounds(172, 242, 90, 28);
		this.getContentPane().add(btnNewButton);

		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanelImpl = new JDatePanelImpl(model);
		datePanelImpl.setBounds(109, 312, 200, 180);

		JDatePickerImpl datePickerImpl = new JDatePickerImpl(datePanelImpl);
		datePickerImpl.getJFormattedTextField().setBackground(Color.WHITE);
		datePickerImpl.setBounds(151, 152, 158, 36);
		this.getContentPane().add(datePickerImpl);

	}
}

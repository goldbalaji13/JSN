package org.jsn.com.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.DateModel;
import java.awt.Color;

public class AddDrug extends JDialog {

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddDrug window = new AddDrug();
//					window.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public AddDrug() {
		setTitle("Add Drug");
		initialize();
	}

	/**
	 * Initialize the contents of the 
	 */
	private void initialize() {
		setResizable(false);
		setBounds(100, 100, 450, 344);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ADD DRUG");
		lblNewLabel.setBounds(187, 11, 78, 25);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
		getContentPane().add(lblNewLabel);
		
		JLabel lblDrugName = new JLabel("DRUG NAME");
		lblDrugName.setBounds(37, 69, 117, 16);
		getContentPane().add(lblDrugName);
		
		JLabel lblQuantity = new JLabel("QUANTITY");
		lblQuantity.setBounds(37, 114, 78, 16);
		getContentPane().add(lblQuantity);
		
		JLabel lblNewLabel_1 = new JLabel("EXPIRY DATE");
		lblNewLabel_1.setBounds(37, 152, 95, 16);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblBatchNo = new JLabel("BATCH NO");
		lblBatchNo.setBounds(37, 207, 78, 16);
		getContentPane().add(lblBatchNo);
		
		textField = new JTextField();
		textField.setBounds(151, 63, 158, 28);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(151, 108, 158, 28);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(151, 201, 158, 28);
		getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JButton btnNewButton = new JButton("ADD");
		btnNewButton.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnNewButton.setBounds(172, 242, 90, 28);
		getContentPane().add(btnNewButton);
		
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanelImpl = new JDatePanelImpl(model);
		datePanelImpl.setBounds(109, 312, 200, 180);
		
		JDatePickerImpl datePickerImpl = new JDatePickerImpl(datePanelImpl);
		datePickerImpl.getJFormattedTextField().setBackground(Color.WHITE);
		datePickerImpl.setBounds(151, 152, 158, 36);
		getContentPane().add(datePickerImpl);
		
		
	}
}

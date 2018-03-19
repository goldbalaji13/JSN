package org.jsn.com.views;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jsn.com.validator.AbstractValidator;
import org.jsn.com.validator.WantsValidationStatus;
import org.jsn.enums.Role;

public class Signup  extends JDialog{

	private JTextField userIdTextField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextField nameTextField;
	private JTextField cityTextField;
	private JTextField contactNoTextField;
	private JTextField addressTextField;
	private Role role;
	private Map<String,Boolean> validationMap;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Signup window = new Signup(Role.CHARITY);
//					window.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}


	public Signup(Role role) {
		setModalityType(ModalityType.DOCUMENT_MODAL);
		this.role = role;
		initialize();
		setValidations();
	}
	private void setValidations() {
		WantsValidationStatus userIdStatus = new WantsValidationStatus() {
			
			public void validatePassed() {
				validationMap.put(userIdTextField.getName(), true);
				
			}
			
			public void validateFailed() {
				validationMap.put(userIdTextField.getName(), false);
				
			}
		};
		new AbstractValidator(this,userIdTextField,"userId Already registered",userIdStatus) {
			
			@Override
			protected boolean validationCriteria(JComponent c) {
				return false;
			}
		};
		
	}

	


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setTitle("Sign Up");
		setResizable(false);
		setBounds(100, 100, 450, 441);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel lblSignUp = new JLabel("ADD USER");
		lblSignUp.setBounds(192, 11, 70, 14);
		getContentPane().add(lblSignUp);
		
		JLabel lblUserName = new JLabel("USER ID");
		lblUserName.setBounds(50, 49, 80, 14);
		getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(50, 89, 80, 14);
		getContentPane().add(lblPassword);
		
		JLabel lblConfirmPassword = new JLabel("CONFIRM PASSWORD");
		lblConfirmPassword.setBounds(50, 126, 132, 14);
		getContentPane().add(lblConfirmPassword);
		
		userIdTextField = new JTextField();
		userIdTextField.setBounds(192, 42, 146, 27);
		getContentPane().add(userIdTextField);
		userIdTextField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(192, 83, 146, 27);
		getContentPane().add(passwordField);
		
		confirmPasswordField = new JPasswordField();
		confirmPasswordField.setBounds(192, 123, 146, 27);
		getContentPane().add(confirmPasswordField);
		
		JButton signUpButton = new JButton("ADD USER");
		signUpButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		signUpButton.setBounds(169, 383, 105, 23);
		getContentPane().add(signUpButton);
		JLabel nameTextLabel;
		if(this.role.equals(Role.CHARITY)) {
		nameTextLabel = new JLabel(" HOSPITAL NAME");
		}else {
			nameTextLabel = new JLabel(" PHARMACY NAME");
		}
		nameTextLabel.setBounds(50, 165, 120, 14);
		getContentPane().add(nameTextLabel);
		
		JLabel lblCity = new JLabel("CITY");
		lblCity.setBounds(50, 215, 80, -16);
		getContentPane().add(lblCity);
		
		JLabel lblCity_1 = new JLabel("CITY");
		lblCity_1.setBounds(52, 292, 56, 14);
		getContentPane().add(lblCity_1);
		
		nameTextField = new JTextField();
		nameTextField.setBounds(192, 162, 146, 27);
		getContentPane().add(nameTextField);
		nameTextField.setColumns(10);
		
		cityTextField = new JTextField();
		cityTextField.setBounds(192, 289, 146, 28);
		getContentPane().add(cityTextField);
		cityTextField.setColumns(10);
		
		JLabel lblContactNo = new JLabel("CONTACT NO");
		lblContactNo.setBounds(50, 332, 132, 14);
		getContentPane().add(lblContactNo);
		
		contactNoTextField = new JTextField();
		contactNoTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
				if(!Character.isDigit(arg0.getKeyChar())) {
					arg0.setKeyChar('\0');
				}
			}
			
		});
		contactNoTextField.setBounds(192, 329, 146, 27);
		getContentPane().add(contactNoTextField);
		contactNoTextField.setColumns(10);
		
		JLabel lblAddress = new JLabel("ADDRESS");
		lblAddress.setBounds(50, 215, 80, 14);
		getContentPane().add(lblAddress);
		
		addressTextField = new JTextField();
		addressTextField.setBounds(189, 215, 149, 66);
		getContentPane().add(addressTextField);
		addressTextField.setColumns(10);
	}
}

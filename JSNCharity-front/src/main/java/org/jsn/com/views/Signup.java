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

public class Signup extends JDialog {

	private JTextField userIdTextField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextField nameTextField;
	private JTextField cityTextField;
	private JTextField contactNoTextField;
	private JTextField addressTextField;
	private Role role;
	private Map<String, Boolean> validationMap;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// Signup window = new Signup(Role.CHARITY);
	// window.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	public Signup(Role role) {
		this.setModalityType(ModalityType.DOCUMENT_MODAL);
		this.role = role;
		this.initialize();
		this.setValidations();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setTitle("Sign Up");
		this.setResizable(false);
		this.setBounds(100, 100, 450, 441);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel lblSignUp = new JLabel("ADD USER");
		lblSignUp.setBounds(192, 11, 70, 14);
		this.getContentPane().add(lblSignUp);

		JLabel lblUserName = new JLabel("USER ID");
		lblUserName.setBounds(50, 49, 80, 14);
		this.getContentPane().add(lblUserName);

		JLabel lblPassword = new JLabel("PASSWORD");
		lblPassword.setBounds(50, 89, 80, 14);
		this.getContentPane().add(lblPassword);

		JLabel lblConfirmPassword = new JLabel("CONFIRM PASSWORD");
		lblConfirmPassword.setBounds(50, 126, 132, 14);
		this.getContentPane().add(lblConfirmPassword);

		this.userIdTextField = new JTextField();
		this.userIdTextField.setBounds(192, 42, 146, 27);
		this.getContentPane().add(this.userIdTextField);
		this.userIdTextField.setColumns(10);

		this.passwordField = new JPasswordField();
		this.passwordField.setBounds(192, 83, 146, 27);
		this.getContentPane().add(this.passwordField);

		this.confirmPasswordField = new JPasswordField();
		this.confirmPasswordField.setBounds(192, 123, 146, 27);
		this.getContentPane().add(this.confirmPasswordField);

		JButton signUpButton = new JButton("ADD USER");
		signUpButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		signUpButton.setBounds(169, 383, 105, 23);
		this.getContentPane().add(signUpButton);
		JLabel nameTextLabel;
		if (this.role.equals(Role.CHARITY)) {
			nameTextLabel = new JLabel(" HOSPITAL NAME");
		} else {
			nameTextLabel = new JLabel(" PHARMACY NAME");
		}
		nameTextLabel.setBounds(50, 165, 120, 14);
		this.getContentPane().add(nameTextLabel);

		JLabel lblCity = new JLabel("CITY");
		lblCity.setBounds(50, 215, 80, -16);
		this.getContentPane().add(lblCity);

		JLabel lblCity_1 = new JLabel("CITY");
		lblCity_1.setBounds(52, 292, 56, 14);
		this.getContentPane().add(lblCity_1);

		this.nameTextField = new JTextField();
		this.nameTextField.setBounds(192, 162, 146, 27);
		this.getContentPane().add(this.nameTextField);
		this.nameTextField.setColumns(10);

		this.cityTextField = new JTextField();
		this.cityTextField.setBounds(192, 289, 146, 28);
		this.getContentPane().add(this.cityTextField);
		this.cityTextField.setColumns(10);

		JLabel lblContactNo = new JLabel("CONTACT NO");
		lblContactNo.setBounds(50, 332, 132, 14);
		this.getContentPane().add(lblContactNo);

		this.contactNoTextField = new JTextField();
		this.contactNoTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				if (!Character.isDigit(arg0.getKeyChar())) {
					arg0.setKeyChar('\0');
				}
			}

		});
		this.contactNoTextField.setBounds(192, 329, 146, 27);
		this.getContentPane().add(this.contactNoTextField);
		this.contactNoTextField.setColumns(10);

		JLabel lblAddress = new JLabel("ADDRESS");
		lblAddress.setBounds(50, 215, 80, 14);
		this.getContentPane().add(lblAddress);

		this.addressTextField = new JTextField();
		this.addressTextField.setBounds(189, 215, 149, 66);
		this.getContentPane().add(this.addressTextField);
		this.addressTextField.setColumns(10);
	}

	private void setValidations() {
		WantsValidationStatus userIdStatus = new WantsValidationStatus() {

			@Override
			public void validateFailed() {
				Signup.this.validationMap.put(Signup.this.userIdTextField.getName(), false);

			}

			@Override
			public void validatePassed() {
				Signup.this.validationMap.put(Signup.this.userIdTextField.getName(), true);

			}
		};
		new AbstractValidator(this, this.userIdTextField, "userId Already registered", userIdStatus) {

			@Override
			protected boolean validationCriteria(JComponent c) {
				return false;
			}
		};

	}
}

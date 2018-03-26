package org.jsn.com.views.dialogues;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.jsn.com.dao.UserDao;
import org.jsn.com.entity.UserEntity;
import org.jsn.com.validator.AbstractValidator;
import org.jsn.com.validator.WantsValidationStatus;
import org.jsn.enums.Role;

public class Signup extends JDialog {

	private static final String PASS_WORD = "passWord";
	private static final String USER_ID_ALREADY_REGISTERED = "userId Already registered";
	private static final String USER_ID = "userId";

	// public static void main(String[] args) {
	// ApplicationContext cont = new
	// AnnotationConfigApplicationContext(Initiater.class);
	// new Signup(Role.CHARITY, cont.getBean(UserDao.class)).setVisible(true);
	// }

	private JTextField userIdTextField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JTextField nameTextField;
	private JTextField cityTextField;
	private JTextField contactNoTextField;
	private JTextField addressTextField;
	private Role role;
	private Map<String, Boolean> validationMap = new HashMap<>();
	private UserDao userDao;

	private JButton signUpButton;

	public Signup(Role role, UserDao userDao) {
		this.setModalityType(ModalityType.DOCUMENT_MODAL);
		this.role = role;
		this.initialize();
		this.setValidations();
		this.userDao = userDao;
	}

	/**
	 * Initialize the contents of the frame.
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

		this.signUpButton = new JButton("ADD USER");
		this.signUpButton.addActionListener(e -> {
			if (new String(Signup.this.confirmPasswordField.getPassword())
					.equals(new String(Signup.this.passwordField.getPassword()))) {
				UserEntity entity = UserEntity.builder().userName(Signup.this.userIdTextField.getText())
						.password(new String(Signup.this.confirmPasswordField.getPassword())).role(Signup.this.role)
						.city(Signup.this.cityTextField.getText()).address(this.addressTextField.getText())
						.contactNo(this.contactNoTextField.getText()).name(this.nameTextField.getText()).build();
				this.userDao.registerUser(entity);
				this.setVisible(false);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Password does'nt match", "Password Mismatch",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		this.signUpButton.setEnabled(false);
		this.signUpButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		this.signUpButton.setBounds(169, 383, 105, 23);
		this.getContentPane().add(this.signUpButton);
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
				Signup.this.validationMap.put(USER_ID, false);
				Signup.this.validationPass();

			}

			@Override
			public void validatePassed() {
				Signup.this.validationMap.put(USER_ID, true);
				Signup.this.validationPass();

			}
		};
		new AbstractValidator(this, this.userIdTextField, USER_ID_ALREADY_REGISTERED, userIdStatus) {

			@Override
			protected boolean validationCriteria(JComponent c) {
				if (Signup.this.userIdTextField.getText().isEmpty()) {
					this.setMessage("userId Cannont be blank");
					return false;
				}
				if (java.util.Objects.nonNull(c)) {
					this.setMessage(USER_ID_ALREADY_REGISTERED);
					return Signup.this.userDao.isUserNameAvilable(Signup.this.userIdTextField.getText());
				}
				return true;
			}
		};
		WantsValidationStatus passWordStatus = new WantsValidationStatus() {

			@Override
			public void validateFailed() {
				Signup.this.validationMap.put(PASS_WORD, false);
				Signup.this.validationPass();

			}

			@Override
			public void validatePassed() {
				Signup.this.validationMap.put(PASS_WORD, true);
				Signup.this.validationPass();

			}
		};
		new AbstractValidator(this, this.passwordField, "Password can't be blank", passWordStatus) {

			@Override
			protected boolean validationCriteria(JComponent c) {
				if (new String(Signup.this.passwordField.getPassword()).isEmpty()) {
					return false;
				}
				return true;
			}
		};
		WantsValidationStatus conFirmPassWordStatus = new WantsValidationStatus() {

			@Override
			public void validateFailed() {
				Signup.this.validationMap.put("confirmPassWord", false);
				Signup.this.validationPass();
			}

			@Override
			public void validatePassed() {
				Signup.this.validationMap.put("confirmPassWord", true);
				Signup.this.validationPass();
			}
		};
		new AbstractValidator(this, this.confirmPasswordField, "Confirm Password can't be blank",
				conFirmPassWordStatus) {
			@Override
			protected boolean validationCriteria(JComponent c) {
				if (new String(Signup.this.confirmPasswordField.getPassword()).isEmpty()) {
					return false;
				}
				return true;
			}
		};

		WantsValidationStatus nameStatus = new WantsValidationStatus() {

			@Override
			public void validateFailed() {
				Signup.this.validationMap.put("name", false);
				Signup.this.validationPass();
			}

			@Override
			public void validatePassed() {
				Signup.this.validationMap.put("name", true);
				Signup.this.validationPass();
			}
		};
		new AbstractValidator(this, this.nameTextField, "Name cannot be blank", nameStatus) {
			@Override
			protected boolean validationCriteria(JComponent c) {
				if (Signup.this.nameTextField.getText().isEmpty()) {
					return false;
				}
				return true;
			}
		};
		WantsValidationStatus contactStatus = new WantsValidationStatus() {

			@Override
			public void validateFailed() {
				Signup.this.validationMap.put("contact", false);
				Signup.this.validationPass();
			}

			@Override
			public void validatePassed() {
				Signup.this.validationMap.put("contact", true);
				Signup.this.validationPass();
			}
		};
		new AbstractValidator(this, this.contactNoTextField, "Contact Cannot be blank", contactStatus) {
			@Override
			protected boolean validationCriteria(JComponent c) {
				if (Signup.this.contactNoTextField.getText().isEmpty()) {
					return false;
				}
				return true;
			}
		};

		WantsValidationStatus cityStatus = new WantsValidationStatus() {

			@Override
			public void validateFailed() {
				Signup.this.validationMap.put("cityStatus", false);
				Signup.this.validationPass();
			}

			@Override
			public void validatePassed() {
				Signup.this.validationMap.put("cityStatus", true);
				Signup.this.validationPass();
			}
		};
		new AbstractValidator(this, this.cityTextField, "Contact Cannot be blank", cityStatus) {
			@Override
			protected boolean validationCriteria(JComponent c) {
				if (Signup.this.cityTextField.getText().isEmpty()) {
					return false;
				}
				return true;
			}
		};

	}

	private void validationPass() {
		if (this.validationMap.entrySet().size() != 6 || this.validationMap.entrySet().stream().map(Map.Entry::getValue)
				.filter(bool -> !bool).findAny().isPresent()) {
			this.signUpButton.setEnabled(false);
		} else {
			this.signUpButton.setEnabled(true);
		}

	}

}

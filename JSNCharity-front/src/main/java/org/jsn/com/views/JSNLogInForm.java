package org.jsn.com.views;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import org.jsn.com.dao.UserDao;
import org.jsn.com.entity.UserEntity;
import org.jsn.com.validator.AbstractValidator;
import org.jsn.com.validator.WantsValidationStatus;
import org.jsn.enums.Role;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BalajiGold
 */
public class JSNLogInForm extends JDialog {

	private UserDao dao;
	private Map<String, Boolean> validationMap = new HashMap<>();
	private UserEntity userEntity;

	/**
	 * Creates new form JSNLogInForm
	 */
	public JSNLogInForm(Frame owner,UserDao dao) {
		super(owner);
		setType(Type.POPUP);
		setModalityType(ModalityType.DOCUMENT_MODAL);
		initComponents();
		this.dao = dao;
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Log In");
		setBackground(new java.awt.Color(153, 255, 255));
		setLocationByPlatform(true);
		setResizable(false);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 30, 104, 150, 45 };
		gridBagLayout.rowHeights = new int[] { 30, 27, 33, 33, 23 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0 };
		getContentPane().setLayout(gridBagLayout);
		jLabel3 = new javax.swing.JLabel();

		jLabel3.setBackground(new java.awt.Color(204, 0, 0));
		jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel3.setText("Please Log In Or Sign Up to Continue");
		jLabel3.setToolTipText("");
		GridBagConstraints gbc_jLabel3 = new GridBagConstraints();
		gbc_jLabel3.insets = new Insets(0, 0, 5, 0);
		gbc_jLabel3.gridwidth = 2;
		gbc_jLabel3.gridx = 1;
		gbc_jLabel3.gridy = 1;
		getContentPane().add(jLabel3, gbc_jLabel3);
		jLabel1 = new javax.swing.JLabel();

		jLabel1.setText("UserName");
		GridBagConstraints gbc_jLabel1 = new GridBagConstraints();
		gbc_jLabel1.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel1.gridx = 1;
		gbc_jLabel1.gridy = 2;
		getContentPane().add(jLabel1, gbc_jLabel1);

		userNameTextField = new javax.swing.JTextField();
		GridBagConstraints gbc_userNameTextField = new GridBagConstraints();
		gbc_userNameTextField.fill = GridBagConstraints.BOTH;
		gbc_userNameTextField.insets = new Insets(0, 0, 5, 0);
		gbc_userNameTextField.gridx = 2;
		gbc_userNameTextField.gridy = 2;
		getContentPane().add(userNameTextField, gbc_userNameTextField);
		jLabel2 = new javax.swing.JLabel();

		jLabel2.setText("Password");
		GridBagConstraints gbc_jLabel2 = new GridBagConstraints();
		gbc_jLabel2.insets = new Insets(0, 0, 5, 5);
		gbc_jLabel2.gridx = 1;
		gbc_jLabel2.gridy = 3;
		getContentPane().add(jLabel2, gbc_jLabel2);
		passwordPassWordField = new javax.swing.JPasswordField();
		GridBagConstraints gbc_passwordPassWordField = new GridBagConstraints();
		gbc_passwordPassWordField.fill = GridBagConstraints.BOTH;
		gbc_passwordPassWordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordPassWordField.gridx = 2;
		gbc_passwordPassWordField.gridy = 3;
		getContentPane().add(passwordPassWordField, gbc_passwordPassWordField);
		signInButton = new javax.swing.JButton();
		signInButton.setEnabled(false);
		signInButton.addActionListener(e->{
			 userEntity = dao.authernticateUser(userNameTextField.getText(),
					new String(passwordPassWordField.getPassword()));
			if (Objects.isNull(userEntity)) {
				JOptionPane.showMessageDialog(this, "Invalid Credentials", "Log in failed", JOptionPane.ERROR_MESSAGE);
			}else {
				setVisible(false);
			}
		});

		signInButton.setText("Sign In");
		GridBagConstraints gbc_jButton1 = new GridBagConstraints();
		gbc_jButton1.insets = new Insets(0, 0, 5, 0);
		gbc_jButton1.gridx = 2;
		gbc_jButton1.gridy = 4;
		getContentPane().add(signInButton, gbc_jButton1);
		getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(
				new Component[] { jLabel1, userNameTextField, passwordPassWordField, jLabel2, jLabel3, signInButton }));

		pack();

		WantsValidationStatus userNameValidationStatus = new WantsValidationStatus() {

			@Override
			public void validatePassed() {
				validationMap.put(userNameTextField.getName(), true);
				if(validationMap.get(passwordPassWordField.getName())) {
					signInButton.setEnabled(true);
				}
			}

			@Override
			public void validateFailed() {
				validationMap.put(userNameTextField.getName(), false);
				signInButton.setEnabled(false);
			}
		};
		new AbstractValidator(this, userNameTextField, "UserName Cannot be Empty", userNameValidationStatus) {

			@Override
			protected boolean validationCriteria(JComponent c) {
				if (userNameTextField.getText().isEmpty()) {
					return false;
				} else {
					return true;
				}
			}
		};
		
		WantsValidationStatus passwordValidationStatus = new WantsValidationStatus() {
			
			@Override
			public void validatePassed() {
				validationMap.put(passwordPassWordField.getName(), true);
				if(validationMap.get(userNameTextField.getName())) {
					signInButton.setEnabled(true);
				}
			}
			
			@Override
			public void validateFailed() {
				validationMap.put(passwordPassWordField.getName(), false);
				signInButton.setEnabled(false);
			}
		};
		new AbstractValidator(this, passwordPassWordField, "Password Cannot be Empty", passwordValidationStatus ) {

			@Override
			protected boolean validationCriteria(JComponent c) {
				if (new String(passwordPassWordField.getPassword()).isEmpty()) {
					return false;
				} else {
					return true;
				}
			}
		};
		
		
	}// </editor-fold>//GEN-END:initComponents
	public UserEntity getUserEntity() {
		return userEntity;
	}
	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(JSNLogInForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(JSNLogInForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(JSNLogInForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(JSNLogInForm.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the form */
		// AnnotationConfigApplicationContext app = new
		// AnnotationConfigApplicationContext(Initiater.class);
		// dao= app.getBean(UserDao.class);
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Signup(Role.CHARITY).setVisible(true);
				// new JSNLogInForm().setVisible(true);
				System.out.println("END-END");
			}
		});
		System.out.println("END");
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton signInButton;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JPasswordField passwordPassWordField;
	private javax.swing.JTextField userNameTextField;
	// End of variables declaration//GEN-END:variables
}
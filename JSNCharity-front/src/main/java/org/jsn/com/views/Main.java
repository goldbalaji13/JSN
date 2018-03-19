package org.jsn.com.views;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import org.jsn.com.config.Initiater;
import org.jsn.com.dao.UserDao;
import org.jsn.com.entity.UserEntity;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends JFrame {

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				this.showMenu(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				this.showMenu(e);
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Main frame = new Main();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private JPanel contentPane;
	private JSNLogInForm signIn;
	private AnnotationConfigApplicationContext app;

	private UserEntity entity;

	private JLabel welcomeLabel;

	/**
	 * Create the frame.
	 */
	public Main() {
		this.app = new AnnotationConfigApplicationContext(Initiater.class);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Main.this.app.close();
			}

			@Override
			public void windowOpened(WindowEvent e) {
				Main.this.doSignInProcedure();
			}
		});
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(this.contentPane);
		this.contentPane.setLayout(null);

		this.welcomeLabel = new JLabel("Welcome:");
		this.welcomeLabel.setBounds(216, 0, 218, 27);
		this.contentPane.add(this.welcomeLabel);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(this.welcomeLabel, popupMenu);

		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.addActionListener(e -> Main.this.doSignInProcedure());
		popupMenu.add(mntmLogOut);
	}

	private void doSignInProcedure() {
		JSNLogInForm signInForm = this.getSignInForm();
		signInForm.setVisible(true);
		if (Objects.nonNull(signInForm) && Objects.nonNull(this.entity = signInForm.getUserEntity())) {
			this.welcomeLabel.setText(this.welcomeLabel.getText() + this.entity.getName());
		} else {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			this.dispose();
		}

	}

	private JSNLogInForm getSignInForm() {
		return new JSNLogInForm(this, this.app.getBean(UserDao.class));
	}
}

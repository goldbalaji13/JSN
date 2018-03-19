package org.jsn.com.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jsn.com.config.Initiater;
import org.jsn.com.dao.UserDao;
import org.jsn.com.entity.UserEntity;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.JToolBar;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import java.awt.Canvas;
import javax.swing.JToggleButton;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private JSNLogInForm signIn;
	private AnnotationConfigApplicationContext app;
	private UserEntity entity;
	private JLabel welcomeLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		app = new AnnotationConfigApplicationContext(Initiater.class);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				doSignInProcedure();
			}
			@Override
			public void windowClosing(WindowEvent e) {
				app.close();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		welcomeLabel = new JLabel("Welcome:");
		welcomeLabel.setBounds(216, 0, 218, 27);
		contentPane.add(welcomeLabel);

		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(welcomeLabel, popupMenu);

		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mntmLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				doSignInProcedure();
			}
		});
		popupMenu.add(mntmLogOut);
	}

	private void doSignInProcedure() {
		JSNLogInForm signInForm = getSignInForm();
		signInForm.setVisible(true);
		if(Objects.nonNull(signInForm) && Objects.nonNull(entity = signInForm.getUserEntity())) {
			welcomeLabel.setText(welcomeLabel.getText()+this.entity.getName());
		}else {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			dispose();
		}
		
	}
	private JSNLogInForm getSignInForm() {
		return new JSNLogInForm(this,app.getBean(UserDao.class));
	}

	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				showMenu(e);
			}

			public void mouseReleased(MouseEvent e) {
				showMenu(e);
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

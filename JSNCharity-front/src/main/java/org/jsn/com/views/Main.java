package org.jsn.com.views;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.jsn.com.config.Initiater;
import org.jsn.com.dao.CharityViewDao;
import org.jsn.com.dao.DrugDao;
import org.jsn.com.dao.UserDao;
import org.jsn.com.entity.UserEntity;
import org.jsn.com.views.dialogues.JSNLogInForm;
import org.jsn.com.views.panels.AdminPanel;
import org.jsn.com.views.panels.BaseViewPanel;
import org.jsn.com.views.panels.CharityViewContainerPanel;
import org.jsn.com.views.panels.PharmaPanel;
import org.jsn.enums.Role;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				Main frame = new Main();
				if (frame.doSignInProcedure()) {
					frame.setVisible(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private JSNLogInForm signIn;
	private AnnotationConfigApplicationContext springApplicationContext;

	private UserEntity entity;

	/**
	 * Create the frame.
	 */
	public Main() {
		this.springApplicationContext = new AnnotationConfigApplicationContext(Initiater.class);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				System.exit(NORMAL);
			}

			@Override
			public void windowClosing(WindowEvent e) {
				Main.this.springApplicationContext.close();
			}
		});
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);
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
	}

	private void addLogOutEvent(BaseViewPanel panel) {
		panel.mntmLogOut.addActionListener(event -> {
			this.doSignInProcedure();
		});

	}

	private boolean doSignInProcedure() {
		JSNLogInForm signInForm = this.getSignInForm();
		signInForm.setVisible(true);
		if (Objects.nonNull(signInForm) && Objects.nonNull(this.entity = signInForm.getUserEntity())) {
			this.makeView(this.entity.getRole());
			return true;
		} else {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
			this.dispose();
			return false;
		}

	}

	private JSNLogInForm getSignInForm() {
		return new JSNLogInForm(this, this.springApplicationContext.getBean(UserDao.class));
	}

	private void makeView(Role role) {
		JPanel panel;
		BaseViewPanel iPanel;
		switch (role) {
		case ADMIN:
			iPanel = new AdminPanel(this.entity, this.springApplicationContext.getBean(UserDao.class));
			panel = iPanel;
			break;

		case CHARITY:
			panel = new CharityViewContainerPanel(this.entity,
					this.springApplicationContext.getBean(CharityViewDao.class),
					this.springApplicationContext.getBean(DrugDao.class));
			iPanel = ((CharityViewContainerPanel) panel).panel;
			break;

		default:
			iPanel = new PharmaPanel(this.entity, this.springApplicationContext.getBean(DrugDao.class));
			panel = iPanel;
			break;
		}
		this.addLogOutEvent(iPanel);
		this.setContentPane(panel);
		this.revalidate();
	}
}

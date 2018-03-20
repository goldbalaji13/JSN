package org.jsn.com.validator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import lombok.Data;

/**
 * This class handles most of the details of validating a component, including
 * all display elements such as popup help boxes and color changes.
 *
 * @author Michael Urban
 * @version Beta 1
 * @see WantsValidationStatus
 */
@Data
public abstract class AbstractValidator extends InputVerifier implements KeyListener {
	private JDialog popup;
	private Object parent;
	private JLabel messageLabel;
	private JLabel image;
	private Point point;
	private Dimension cDim;
	private Color color;
	private WantsValidationStatus statusAcceptor;
	private JComponent component;

	private AbstractValidator() {
		this.color = new Color(243, 255, 159);
	}

	private AbstractValidator(JComponent c, String message) {
		this();
		c.addKeyListener(this);
		c.setInputVerifier(this);
		this.messageLabel = new JLabel(message + " ");
		String path = this.getClass().getClassLoader().getResource("images/exception_16x16.png").getPath();
		this.image = new JLabel(new ImageIcon(path));
		this.component = c;
	}

	/**
	 * @param parent
	 *            A JDialog that implements the ValidationCapable interface.
	 * @param c
	 *            The JComponent to be validated.
	 * @param message
	 *            A message to be displayed in the popup help tip if validation
	 *            fails.
	 */

	public AbstractValidator(JDialog parent, JComponent c, String message, WantsValidationStatus statusAcceptor) {
		this(c, message);
		this.parent = parent;
		this.popup = new JDialog(parent);
		this.initComponents();
		this.statusAcceptor = statusAcceptor;
	}

	/**
	 * @param parent
	 *            A JFrame that implements the ValidationCapable interface.
	 * @param c
	 *            The JComponent to be validated.
	 * @param message
	 *            A message to be displayed in the popup help tip if validation
	 *            fails.
	 */

	public AbstractValidator(JFrame parent, JComponent c, String message, WantsValidationStatus statusAcceptor) {
		this(c, message);
		this.parent = parent;
		this.popup = new JDialog(parent);
		this.initComponents();
		this.statusAcceptor = statusAcceptor;
	}

	private void initComponents() {
		this.popup.getContentPane().setLayout(new FlowLayout());
		this.popup.setUndecorated(true);
		this.popup.getContentPane().setBackground(this.color);
		this.popup.getContentPane().add(this.image);
		this.popup.getContentPane().add(this.messageLabel);
		this.popup.setFocusableWindowState(false);
	}

	/**
	 * @see KeyListener
	 */

	@Override
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * @see KeyListener
	 */

	@Override
	public void keyReleased(KeyEvent e) {
		this.popup.setVisible(false);
		this.verify(this.component);
	}

	/**
	 * @see KeyListener
	 */

	@Override
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Changes the message that appears in the popup help tip when a component's
	 * data is invalid. Subclasses can use this to provide context sensitive help
	 * depending on what the user did wrong.
	 *
	 * @param message
	 */

	protected void setMessage(String message) {
		this.messageLabel.setText(message);
	}

	/**
	 * Implement the actual validation logic in this method. The method should
	 * return false if data is invalid and true if it is valid. It is also possible
	 * to set the popup message text with setMessage() before returning, and thus
	 * customize the message text for different types of validation problems.
	 *
	 * @param c
	 *            The JComponent to be validated.
	 * @return false if data is invalid. true if it is valid.
	 */

	protected abstract boolean validationCriteria(JComponent c);

	/**
	 * This method is called by Java when a component needs to be validated. It
	 * should not be called directly. Do not override this method unless you really
	 * want to change validation behavior. Implement validationCriteria() instead.
	 */

	@Override
	public boolean verify(JComponent c) {
		if (!this.validationCriteria(c)) {

			if (this.parent instanceof WantsValidationStatus) {
				((WantsValidationStatus) this.parent).validateFailed();
			}
			if (Objects.nonNull(this.statusAcceptor)) {
				this.statusAcceptor.validateFailed();
			}
			c.setBackground(Color.PINK);
			this.popup.setSize(0, 0);
			this.popup.setLocationRelativeTo(c);
			this.point = this.popup.getLocation();
			this.cDim = c.getSize();
			this.popup.setLocation(this.point.x - (int) this.cDim.getWidth() / 2,
					this.point.y + (int) this.cDim.getHeight() / 2);
			this.popup.pack();
			this.popup.setVisible(true);
			return false;
		}

		c.setBackground(Color.WHITE);

		if (this.parent instanceof WantsValidationStatus) {
			((WantsValidationStatus) this.parent).validatePassed();
		}
		if (Objects.nonNull(this.statusAcceptor)) {
			this.statusAcceptor.validatePassed();
		}
		return true;
	}
}
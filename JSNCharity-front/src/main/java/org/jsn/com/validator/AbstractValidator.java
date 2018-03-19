package org.jsn.com.validator;

 
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;

import javax.swing.*;
 
/**
 * This class handles most of the details of validating a component, including
 * all display elements such as popup help boxes and color changes.
 * 
 * @author Michael Urban
 * @version Beta 1
 * @see WantsValidationStatus
 */
 
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
        color = new Color(243, 255, 159);
    }
	
    private AbstractValidator(JComponent c, String message) {
        this();
        c.addKeyListener(this);
        c.setInputVerifier(this);
        messageLabel = new JLabel(message + " ");
        String path = getClass().getClassLoader().getResource("images/exception_16x16.png").getPath();
        image = new JLabel(new ImageIcon(path));
        component = c;
    }
	
    /**
     * @param parent A JDialog that implements the ValidationCapable interface.
     * @param c The JComponent to be validated.
     * @param message A message to be displayed in the popup help tip if validation fails.
     */
	
    public AbstractValidator (JDialog parent, JComponent c, String message,WantsValidationStatus statusAcceptor) {		
        this(c, message);
        this.parent = parent;
        popup = new JDialog(parent);
        initComponents();
        this.statusAcceptor = statusAcceptor;
    }
	
    /**
     * @param parent A JFrame that implements the ValidationCapable interface.
     * @param c The JComponent to be validated.
     * @param message A message to be displayed in the popup help tip if validation fails.
     */
	
    public AbstractValidator (JFrame parent, JComponent c, String message,WantsValidationStatus statusAcceptor) {
        this(c, message);
        this.parent = parent;
        popup = new JDialog(parent);
        initComponents();
        this.statusAcceptor = statusAcceptor;
    }
	
    /**
     * Implement the actual validation logic in this method. The method should
     * return false if data is invalid and true if it is valid. It is also possible
     * to set the popup message text with setMessage() before returning, and thus
     * customize the message text for different types of validation problems.
     * 
     * @param c The JComponent to be validated.
     * @return false if data is invalid. true if it is valid.
     */
	
    protected abstract boolean validationCriteria(JComponent c);
	
    /**
     * This method is called by Java when a component needs to be validated.
     * It should not be called directly. Do not override this method unless
     * you really want to change validation behavior. Implement
     * validationCriteria() instead.
     */
	
    public boolean verify(JComponent c) {		
        if (!validationCriteria(c)) {
			
            if(parent instanceof WantsValidationStatus)
                ((WantsValidationStatus)parent).validateFailed();
			if(Objects.nonNull(statusAcceptor)) {
				statusAcceptor.validateFailed();
			}
            c.setBackground(Color.PINK);
            popup.setSize(0, 0);
            popup.setLocationRelativeTo(c);
            point = popup.getLocation();
            cDim = c.getSize();
            popup.setLocation(point.x-(int)cDim.getWidth()/2,
                point.y+(int)cDim.getHeight()/2);
            popup.pack();
            popup.setVisible(true);
            return false;
        }
        
        c.setBackground(Color.WHITE);
		
        if(parent instanceof WantsValidationStatus)
            ((WantsValidationStatus)parent).validatePassed();
        if(Objects.nonNull(statusAcceptor)) {
			statusAcceptor.validatePassed();
		}
        return true;
    }
	

   
    /**
     * Changes the message that appears in the popup help tip when a component's
     * data is invalid. Subclasses can use this to provide context sensitive help
     * depending on what the user did wrong.
     * 
     * @param message
     */
	
    protected void setMessage(String message) {
       messageLabel.setText(message);
    }
	
     /**
      * @see KeyListener
      */
	
    public void keyPressed(KeyEvent e) {}
	
    /**
     * @see KeyListener
     */
	
    public void keyTyped(KeyEvent e) {}
	
    /**
     * @see KeyListener
     */
	
    public void keyReleased(KeyEvent e) {
        popup.setVisible(false);
        verify(component);
    }
	
    private void initComponents() {
        popup.getContentPane().setLayout(new FlowLayout());
        popup.setUndecorated(true);
        popup.getContentPane().setBackground(color);
        popup.getContentPane().add(image);
        popup.getContentPane().add(messageLabel);
        popup.setFocusableWindowState(false);
    }
}
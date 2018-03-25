package org.jsn.com.views.dialogues;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class AddToCart extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public boolean ok = false;
	public int sellQuantity;
	private JSpinner spinner;

	/**
	 * Create the dialog.
	 *
	 * @param maxValue
	 */
	public AddToCart(Integer maxValue) {
		this.setType(Type.UTILITY);
		this.setModal(true);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setBounds(100, 100, 229, 108);
		this.getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		this.contentPanel.setLayout(new BorderLayout(0, 0));
		{
			this.spinner = new JSpinner();
			this.spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), maxValue, new Integer(1)));

			this.contentPanel.add(this.spinner, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new CardLayout(0, 0));
			{
				JButton btnNewButton = new JButton("Ok");
				btnNewButton.addActionListener(e -> {
					this.sellQuantity = Integer.valueOf(this.spinner.getValue().toString());
					this.ok = true;
					this.setVisible(false);
					this.dispose();
				});
				buttonPane.add(btnNewButton);
			}
		}
	}

}

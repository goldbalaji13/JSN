package org.jsn.com.views.dialogues;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class PatientDetails extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PatientDetails dialog = new PatientDetails();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private final JPanel contentPanel = new JPanel();

	private JTextField textField;

	private JSpinner spinner;

	private int result;

	/**
	 * Create the dialog.
	 */
	public PatientDetails() {
		this.setTitle("Patient Details");
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setType(Type.UTILITY);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setModal(true);
		this.setBounds(100, 100, 483, 171);
		this.getContentPane().setLayout(new BorderLayout());
		this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.getContentPane().add(this.contentPanel, BorderLayout.CENTER);
		this.contentPanel.setLayout(new FormLayout(
				new ColumnSpec[] { FormSpecs.LABEL_COMPONENT_GAP_COLSPEC, ColumnSpec.decode("226px"),
						ColumnSpec.decode("226px"), },
				new RowSpec[] { FormSpecs.LINE_GAP_ROWSPEC, RowSpec.decode("23px"), RowSpec.decode("12px"),
						FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("max(18dlu;default)"), }));
		{
			JLabel lblNewLabel_1 = new JLabel("Patient Name");
			this.contentPanel.add(lblNewLabel_1, "2, 2, fill, fill");
		}
		{
			this.textField = new JTextField();
			this.contentPanel.add(this.textField, "3, 2, fill, fill");
			this.textField.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Patient Age");
			this.contentPanel.add(lblNewLabel, "2, 5, fill, fill");
		}
		{
			this.spinner = new JSpinner();
			this.spinner.setModel(new SpinnerNumberModel(0, 0, 120, 1));
			this.contentPanel.add(this.spinner, "3, 5, fill, fill");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			this.getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(e -> {
					PatientDetails.this.setVisible(false);
					this.result = 1;
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				this.getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(e -> {
					PatientDetails.this.setVisible(false);
					this.result = 0;
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public int getPatientAge() {
		return ((Number) this.spinner.getValue()).intValue();
	}

	public String getPatientName() {
		return this.textField.getText();
	}

	public int getResult() {
		return this.result;
	}

}

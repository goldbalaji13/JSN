package org.jsn.com.views.dialogues;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import com.google.common.base.CharMatcher;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PdfViewer extends JPanel {
	private static enum Navigation {
		GO_FIRST_PAGE, FORWARD, BACKWARD, GO_LAST_PAGE, GO_N_PAGE
	}

	private class PageNavigationListener implements ActionListener {

		private final Navigation navigation;

		private PageNavigationListener(Navigation navigation) {
			this.navigation = navigation;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (PdfViewer.this.pdfFile == null) {
				return;
			}

			int numPages = PdfViewer.this.pdfFile.getNumPages();
			if (numPages <= 1) {
				PdfViewer.this.disableAllNavigationButton();
			} else {
				if (this.navigation == Navigation.FORWARD && this.hasNextPage(numPages)) {
					this.goPage(PdfViewer.this.currentPage, numPages);
				}

				if (this.navigation == Navigation.GO_LAST_PAGE) {
					this.goPage(numPages, numPages);
				}

				if (this.navigation == Navigation.BACKWARD && this.hasPreviousPage()) {
					this.goPage(PdfViewer.this.currentPage, numPages);
				}

				if (this.navigation == Navigation.GO_FIRST_PAGE) {
					this.goPage(FIRST_PAGE, numPages);
				}

				if (this.navigation == Navigation.GO_N_PAGE) {
					String text = PdfViewer.this.txtGoPage.getText();
					boolean isValid = false;
					if (!isNullOrEmpty(text)) {
						boolean isNumber = POSITIVE_DIGITAL.matchesAllOf(text);
						if (isNumber) {
							int pageNumber = Integer.valueOf(text);
							if (pageNumber >= 1 && pageNumber <= numPages) {
								this.goPage(Integer.valueOf(text), numPages);
								isValid = true;
							}
						}
					}

					if (!isValid) {
						JOptionPane.showMessageDialog(PdfViewer.this,
								format("Invalid page number '%s' in this document", text));
						PdfViewer.this.txtGoPage
								.setText(format(GO_PAGE_TEMPLATE, PdfViewer.this.currentPage, numPages));
					}
				}
			}
		}

		private void goPage(int pageNumber, int numPages) {
			PdfViewer.this.currentPage = pageNumber;
			PDFPage page = PdfViewer.this.pdfFile.getPage(PdfViewer.this.currentPage);
			PdfViewer.this.pagePanel.showPage(page);
			boolean notFirstPage = this.isNotFirstPage();
			PdfViewer.this.btnFirstPage.setEnabled(notFirstPage);
			PdfViewer.this.btnPreviousPage.setEnabled(notFirstPage);
			PdfViewer.this.txtGoPage.setText(format(GO_PAGE_TEMPLATE, PdfViewer.this.currentPage, numPages));
			boolean notLastPage = this.isNotLastPage(numPages);
			PdfViewer.this.btnNextPage.setEnabled(notLastPage);
			PdfViewer.this.btnLastPage.setEnabled(notLastPage);
		}

		private boolean hasNextPage(int numPages) {
			return ++PdfViewer.this.currentPage <= numPages;
		}

		private boolean hasPreviousPage() {
			return --PdfViewer.this.currentPage >= FIRST_PAGE;
		}

		private boolean isNotFirstPage() {
			return PdfViewer.this.currentPage != FIRST_PAGE;
		}

		private boolean isNotLastPage(int numPages) {
			return PdfViewer.this.currentPage != numPages;
		}
	}

	private static final CharMatcher POSITIVE_DIGITAL = CharMatcher.anyOf("0123456789");

	private static final String GO_PAGE_TEMPLATE = "%s of %s";
	private static final int FIRST_PAGE = 1;

	public static String format(String template, Object... args) {
		template = String.valueOf(template); // null -> "null"
		// start substituting the arguments into the '%s' placeholders
		StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
		int templateStart = 0;
		int i = 0;
		while (i < args.length) {
			int placeholderStart = template.indexOf("%s", templateStart);
			if (placeholderStart == -1) {
				break;
			}
			builder.append(template.substring(templateStart, placeholderStart));
			builder.append(args[i++]);
			templateStart = placeholderStart + 2;
		}
		builder.append(template.substring(templateStart));

		// if we run out of placeholders, append the extra args in square braces
		if (i < args.length) {
			builder.append(" [");
			builder.append(args[i++]);
			while (i < args.length) {
				builder.append(", ");
				builder.append(args[i++]);
			}
			builder.append(']');
		}

		return builder.toString();
	}

	public static void main(String[] args) throws IOException {
		Map<String, Object> DataBeanList = new HashMap();
		DataBeanList.put("billerName", "JJOx");
		DataBeanList.put("billerAge", 96);
		DataBeanList.put("hospitalName", "JJ OX");
		Map<String, Object> gridMap = new HashMap();
		gridMap.put("pharmaName", "Pharma");
		gridMap.put("drugName", "p250");
		gridMap.put("quantity", 5);
		gridMap.put("unitPrice", new BigDecimal(5));
		List<Map<String, Object>> drugList = new ArrayList<>();
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);
		drugList.add(gridMap);

		PdfViewer pdfViewer = new PdfViewer(DataBeanList, drugList);
		JDialog frame = new JDialog();
		frame.setTitle("Bill Report");
		frame.setModalityType(ModalityType.APPLICATION_MODAL);
		frame.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		frame.getContentPane().add(pdfViewer);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Prints with a print preview dialog.
	 */
	private static void printWithDialog(PDDocument document) throws IOException, PrinterException {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPageable(new PDFPageable(document));

		if (job.printDialog()) {
			job.print();
		}
	}

	private int currentPage = FIRST_PAGE;
	private JButton btnFirstPage;
	private JButton btnPreviousPage;
	private JTextField txtGoPage;

	private JButton btnNextPage;

	private JButton btnLastPage;

	private PagePanel pagePanel;

	private PDFFile pdfFile;

	public PdfViewer(Map<String, Object> dataBeanList, List<Map<String, Object>> drugList) {
		this.addAncestorListener(new AncestorListener() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				PDFPage page = PdfViewer.this.pdfFile.getPage(0);
				PdfViewer.this.pagePanel.showPage(page);
			}

			@Override
			public void ancestorMoved(AncestorEvent event) {
			}

			@Override
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		this.initial(dataBeanList, drugList);
	}

	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setPreferredSize(new Dimension(55, 20));

		return button;
	}

	private void disableAllNavigationButton() {
		this.btnFirstPage.setEnabled(false);
		this.btnPreviousPage.setEnabled(false);
		this.btnNextPage.setEnabled(false);
		this.btnLastPage.setEnabled(false);
	}

	public PagePanel getPagePanel() {
		return this.pagePanel;
	}

	private void initial(Map<String, Object> dataBeanList, List<Map<String, Object>> drugList) {
		this.setLayout(new BorderLayout(0, 0));
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		this.add(topPanel, BorderLayout.NORTH);
		this.btnFirstPage = this.createButton("|<<");
		topPanel.add(this.btnFirstPage);
		this.btnPreviousPage = this.createButton("<<");
		topPanel.add(this.btnPreviousPage);

		JButton btnPrint = new JButton("Print");
		btnPrint.addActionListener(e -> {
			try {
				PDDocument doc = PDDocument.load(new File("temp.pdf"));
				printWithDialog(doc);
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}

		});
		topPanel.add(btnPrint);
		this.txtGoPage = new JTextField(10);
		this.txtGoPage.setHorizontalAlignment(JTextField.CENTER);
		topPanel.add(this.txtGoPage);
		this.btnNextPage = this.createButton(">>");
		topPanel.add(this.btnNextPage);
		this.btnLastPage = this.createButton(">>|");
		topPanel.add(this.btnLastPage);
		JScrollPane scrollPane = new JScrollPane();
		this.add(scrollPane, BorderLayout.CENTER);
		JPanel viewPanel = new JPanel(new BorderLayout(0, 0));
		scrollPane.setViewportView(viewPanel);

		this.pagePanel = new PagePanel();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.pagePanel.setPreferredSize(screenSize);
		viewPanel.add(this.pagePanel, BorderLayout.CENTER);

		this.disableAllNavigationButton();

		this.btnFirstPage.addActionListener(new PageNavigationListener(Navigation.GO_FIRST_PAGE));
		this.btnPreviousPage.addActionListener(new PageNavigationListener(Navigation.BACKWARD));
		this.btnNextPage.addActionListener(new PageNavigationListener(Navigation.FORWARD));
		this.btnLastPage.addActionListener(new PageNavigationListener(Navigation.GO_LAST_PAGE));
		this.txtGoPage.addActionListener(new PageNavigationListener(Navigation.GO_N_PAGE));

		// --report--//
		try {
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("dataset", drugList);
			JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(Arrays.asList(dataBeanList));
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			InputStream stream = PdfViewer.class.getResourceAsStream("/reports/Invoice_Table_Based.jasper");
			JasperFillManager.fillReportToStream(stream, outputStream, parameters, beanColDataSource);
			ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
			outputStream = new ByteArrayOutputStream();
			JasperExportManager.exportReportToPdfStream(inputStream, outputStream);
			PDFFile file = new PDFFile(ByteBuffer.wrap(outputStream.toByteArray()));
			FileOutputStream sr = new FileOutputStream("temp.pdf");
			sr.write(outputStream.toByteArray());
			this.setPDFFile(file);
		} catch (JRException | IOException exception) {

		}
	}

	private boolean isMoreThanOnePage(PDFFile pdfFile) {
		return pdfFile.getNumPages() > 1;
	}

	public void setPDFFile(PDFFile pdfFile) {
		this.pdfFile = pdfFile;
		this.currentPage = FIRST_PAGE;
		this.disableAllNavigationButton();
		this.txtGoPage.setText(format(GO_PAGE_TEMPLATE, FIRST_PAGE, pdfFile.getNumPages()));
		boolean moreThanOnePage = this.isMoreThanOnePage(pdfFile);
		this.btnNextPage.setEnabled(moreThanOnePage);
		this.btnLastPage.setEnabled(moreThanOnePage);
	}

}

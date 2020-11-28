package views;

/*
 * Customer Management GUI
 * Actions: search by client Id, last name or type
 * Actions: clear search
 * Actions: modify customer data
 */
 

//import java.awt.EventQueue;

import javax.swing.JFrame;
//import java.awt.GridBagLayout;
import javax.swing.JLabel;
//import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
//import javax.swing.border.MatteBorder;
import java.awt.Color;
//import javax.swing.border.SoftBevelBorder;
//import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import ViewControllers.CustViewListener;
import ViewControllers.GuiListener;

import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
//import javax.swing.JTextPane;
//import javax.swing.DropMode;
import javax.swing.JList;
//import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
//import javax.swing.event.ListSelectionListener;
//import javax.swing.event.ListSelectionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

public class CustomerView {

	public JFrame frame;
	private JTextField searchInputField;
	private JTextField idField;
	private JTextField fNameField;
	private JTextField lNameField;
	private JTextField addressField;
	private JTextField postalCodeField;
	private JTextField phoneNoField;
	private JTextField typeField;
	
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JScrollPane scrollPane_1;
	
	private JRadioButton idButton;
	private JRadioButton lastNameButton;
	private JRadioButton cTypeButton;
	private JTable resultsTable;
	
	private JButton searchButton;
	private JButton clearSearchButton;
	private JButton saveChangesButton;
	private JButton deleteRowButton;
	private JButton addRowButton;
	private JButton clearChangesButton;



	/**
	 * Create the application.
	 */
	public CustomerView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
//	@SuppressWarnings("serial")
	public void initialize() {
		
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1020, 736);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Customer Management");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setBounds(78, 16, 805, 49);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Search Customers", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(15, 94, 692, 220);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		idButton = new JRadioButton("Customer ID");
		buttonGroup.add(idButton);
		idButton.setBounds(11, 37, 155, 29);
		panel.add(idButton);
		
		lastNameButton = new JRadioButton("Last Name");
		buttonGroup.add(lastNameButton);
		lastNameButton.setBounds(11, 75, 155, 29);
		panel.add(lastNameButton);
		
		cTypeButton = new JRadioButton("Customer Type");
		buttonGroup.add(cTypeButton);
		cTypeButton.setBounds(11, 112, 155, 29);
		panel.add(cTypeButton);
		
		searchInputField = new JTextField();
		searchInputField.setBounds(15, 174, 146, 26);
		panel.add(searchInputField);
		searchInputField.setColumns(10);
		
		
		//********************************************//
		searchButton = new JButton("Search");
		searchButton.setBounds(174, 173, 115, 29);
		panel.add(searchButton);
		
		clearSearchButton = new JButton("Clear");
		clearSearchButton.setBounds(309, 173, 115, 29);
		panel.add(clearSearchButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Add Customer", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(717, 94, 287, 570);
		frame.getContentPane().add(panel_1);
		
		JLabel lblNewLabel_1 = new JLabel("Customer ID:");
		lblNewLabel_1.setBounds(10, 44, 83, 20);
		panel_1.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("First Name:");
		lblNewLabel_1_1.setBounds(10, 108, 83, 20);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Last Name:");
		lblNewLabel_1_2.setBounds(10, 172, 83, 20);
		panel_1.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("Address:");
		lblNewLabel_1_3.setBounds(10, 236, 83, 20);
		panel_1.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("Postal Code:");
		lblNewLabel_1_4.setBounds(10, 300, 83, 20);
		panel_1.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("Phone Number:");
		lblNewLabel_1_5.setBounds(10, 364, 83, 20);
		panel_1.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("Customer Type");
		lblNewLabel_1_6.setBounds(10, 428, 83, 20);
		panel_1.add(lblNewLabel_1_6);
		
		addRowButton = new JButton("Add");
		addRowButton.setBounds(10, 530, 115, 29);
		panel_1.add(addRowButton);
		
		clearChangesButton = new JButton("Clear");
		clearChangesButton.setBounds(145, 530, 115, 29);
		panel_1.add(clearChangesButton);
		
		idField = new JTextField();
		idField.setBounds(114, 44, 146, 26);
		panel_1.add(idField);
		idField.setColumns(10);
		
		fNameField = new JTextField();
		fNameField.setColumns(10);
		fNameField.setBounds(114, 108, 146, 26);
		panel_1.add(fNameField);
		
		lNameField = new JTextField();
		lNameField.setColumns(10);
		lNameField.setBounds(114, 172, 146, 26);
		panel_1.add(lNameField);
		
		addressField = new JTextField();
		addressField.setColumns(10);
		addressField.setBounds(114, 236, 146, 26);
		panel_1.add(addressField);
		
		postalCodeField = new JTextField();
		postalCodeField.setColumns(10);
		postalCodeField.setBounds(114, 300, 146, 26);
		panel_1.add(postalCodeField);
		
		phoneNoField = new JTextField();
		phoneNoField.setColumns(10);
		phoneNoField.setBounds(114, 364, 146, 26);
		panel_1.add(phoneNoField);
		
		typeField = new JTextField();
		typeField.setColumns(10);
		typeField.setBounds(114, 428, 146, 26);
		panel_1.add(typeField);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(15, 336, 692, 283);
		frame.getContentPane().add(scrollPane_1);
		
		resultsTable = new JTable();
		resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_1.setViewportView(resultsTable);
		
		saveChangesButton = new JButton("Save Changes");
		saveChangesButton.setBounds(15, 630, 115, 29);
		frame.getContentPane().add(saveChangesButton);
		
		deleteRowButton = new JButton("Delete Row");
		deleteRowButton.setBounds(153, 630, 115, 29);
		frame.getContentPane().add(deleteRowButton);
		
		
		//Connect to GuiListener class
		CustViewListener actListener = new CustViewListener(this);
		searchButton.addActionListener(actListener);
		clearSearchButton.addActionListener(actListener);
		saveChangesButton.addActionListener(actListener);
		deleteRowButton.addActionListener(actListener);
		addRowButton.addActionListener(actListener);
		clearChangesButton.addActionListener(actListener);
		

		
	}
	
	/*
	 * getters for action buttons (searchButton, clearSearchButton, saveChangesButton, 
	 * 							   deleteRowButton, addRowButton, clearChangesButton)
	 */
	
	public String getSearchInputField() {
		return searchInputField.getText();
	}
	
	public JButton getSearchButton() {
		return searchButton;
	}
	
	public JButton getClearSearchButton() {
		return clearSearchButton;
	}
	
	public JButton getSaveChangesButton() {
		return saveChangesButton;
	}
	
	public JButton getDeleteRowButton() {
		return deleteRowButton;
	}
	
	public JButton getAddRowButton() {
		return addRowButton;
	}
	
	public JButton getClearChangesButton() {
		return clearChangesButton;
	}
	
	public JTable getResultsTable() {
		return resultsTable;
	}
	
	
	/*
	 * getters for search type radio buttons (idButton, lastNameButton, cTypeButton)
	 */
	public JRadioButton getIdButton() {
		return idButton;
	}
	
	public JRadioButton getlastNameButton() {
		return lastNameButton;
	}
	
	public JRadioButton getCustomerTypeButton() {
		return cTypeButton;
	}

	/*
	 * getters for text input fields for adding new row (customer)
	 */
	public JTextField getFName() {
		return fNameField;
	}
	
	public JTextField getLName() {
		return lNameField;
	}
	
	public JTextField getAddress() {
		return addressField;
	}
	
	public JTextField getPostalCode() {
		return postalCodeField;
	}
	
	public JTextField getPhoneNo() {
		return phoneNoField;
	}
	
	public JTextField getType() {
		return typeField;
	}
	
}
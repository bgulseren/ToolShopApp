package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import client.CustViewListener;
import client.InvViewListener;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ButtonGroup;

public class InventoryView {

	public JFrame frame;
	private JTextField searchTextField;
	private JTable resultsTable;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	private JRadioButton searchToolNameButton;
	private JRadioButton searchByToolIdButton;
	
	private JButton searchToolButton;
	private JButton displayAllToolsButton;
	private JButton decreaseQuantityButton;
	private JButton displayTodaysOrderButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventoryView window = new InventoryView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InventoryView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1020, 736);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Tool Management");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblNewLabel.setBounds(78, 16, 805, 49);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Search Tool", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(15, 94, 692, 220);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		searchToolNameButton = new JRadioButton("Tool Name");
		buttonGroup.add(searchToolNameButton);
		searchToolNameButton.setBounds(18, 41, 109, 23);
		panel.add(searchToolNameButton);
		
		searchByToolIdButton = new JRadioButton("Tool ID");
		buttonGroup.add(searchByToolIdButton);
		searchByToolIdButton.setBounds(18, 77, 109, 23);
		panel.add(searchByToolIdButton);
		
		searchTextField = new JTextField();
		searchTextField.setBounds(15, 174, 146, 20);
		panel.add(searchTextField);
		searchTextField.setColumns(10);
		
		searchToolButton = new JButton("Search");
		searchToolButton.setBounds(174, 173, 115, 23);
		panel.add(searchToolButton);
		
		displayAllToolsButton = new JButton("Display All Tools");
		displayAllToolsButton.setBounds(311, 173, 146, 23);
		panel.add(displayAllToolsButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 336, 692, 283);
		frame.getContentPane().add(scrollPane);
		
		resultsTable = new JTable();
		resultsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(resultsTable);
		
		decreaseQuantityButton = new JButton("Decrease Quantity");
		decreaseQuantityButton.setBounds(15, 642, 164, 23);
		frame.getContentPane().add(decreaseQuantityButton);
		
		displayTodaysOrderButton = new JButton("Display Today's Order");
		displayTodaysOrderButton.setBounds(189, 642, 174, 23);
		frame.getContentPane().add(displayTodaysOrderButton);
		
		//Connect to InvViewListener class
		InvViewListener actListener = new InvViewListener(this);
		searchToolButton.addActionListener(actListener);
		displayAllToolsButton.addActionListener(actListener);
		decreaseQuantityButton.addActionListener(actListener);
		displayTodaysOrderButton.addActionListener(actListener);
		
	}
	
	
	/*
	 * getters for action buttons (searchToolButton, displayAllToolsButton, decreaseQuantityButton, displayTodaysOrderButton)
	 */
	
	public JButton getSearchButton() {
		return searchToolButton;
	}
	
	public JButton getDisplayAllButton(){
		return displayAllToolsButton;
	}
	
	public JButton getDecreaseQuantityButton(){
		return decreaseQuantityButton;
	}
	
	public JButton getDisplayOrderButton() {
		return displayTodaysOrderButton;
	}
	
	
	/*
	 * getter for results table
	 */
	public JTable getResultsTable() {
		return resultsTable;
	}
	
	/*
	 * getter for search text field
	 */
	public String getSearchTextField() {
		return searchTextField.getText();
	}
	
	/*
	 * getter for radio button for defining search type (toolname, toolid)
	 */
	public JRadioButton getSearchToolNameButton() {
		return searchToolNameButton;
	}
	
	public JRadioButton getSearchToolIdButton() {
		return searchByToolIdButton;
	}
}

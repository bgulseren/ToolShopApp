package client;

import views.CustomerView;

import javax.swing.table.DefaultTableModel;

public class CustViewController {
	
	private CustomerView cv;
	private CustViewListener cl;
	private MainController mc;
	
	//For populating results table (JTable)
	String[] columnNames = {"ID", "Type", "First Name", "Last Name", "Address", "Postal Code", "Phone #"};

	private String[][] searchResult;
	
	public CustViewController() {
		cl = new CustViewListener();
		cv = new CustomerView(cl);
		cl.setCustomerView(this, cv);
	}
	
	public void setMainController(MainController mc) {
		this.mc = mc;
	}
	
	/**
	 * 
	 * @return CustomerView
	 */
	public CustomerView getCv() {
		return cv;
	}
	
	/**
	 * 
	 * @return CustViewListener
	 */
	public CustViewListener getCl() {
		return cl;
	}

	/**
	 * Searches for customers
	 * 
	 */
	public void searchCustomer(String searchKey, String request) {
		if (request.contentEquals("BYID")) {
			mc.searchCustomer(searchKey, 0);
		} else if (request.contentEquals("BYNAME")) {
			mc.searchCustomer(searchKey, 3);
		} else if (request.contentEquals("BYTYPE")) {
			mc.searchCustomer(searchKey, 1);
		}
	}
	
	/**
	 * Adds customer
	 * 
	 */
	public void addCustomer(String[] customerInfo) {
		mc.addCustomer(customerInfo);
	}
	
	/**
	 * Edits customer
	 * 
	 */
	public void editCustomer(String[] customerInfo) {
		mc.editCustomerInfo(customerInfo);
	}
	
	/**
	 * Deletes customer
	 * 
	 */
	public void deleteCustomer(String id) {
		mc.deleteCustomer(id);
	}
	
	/**
	 * Returns search results array
	 * 
	 */
	public String[][] getSearchResult(){
		return searchResult;
	}
	
	/**
	 * Switches pages
	 * 
	 */
	public void displayInvMgmtView() {
		mc.activateInventoryView();
	}
	
	/*
	 * called by ModelController to update customerList 2D String up search results
	 */
	public void updateView(String[][] searchResult) {
		this.searchResult = searchResult;
		cv.getResultsTable().setModel(new DefaultTableModel(getSearchResult(), columnNames));
	}
		
}
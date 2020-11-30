package client;

import views.CustomerView;

import javax.swing.table.DefaultTableModel;

public class CustViewController {
	
	private CustomerView cv;
	private CustViewListener cl;
	private MainController mc;
		
	private String id;
	private String fName;
	private String lName;
	private String address;
	private String postalCode;
	private String phoneNo;
	private String type;
	private String searchKey;
	
	//For populating results table (JTable)
	String[] columnNames = {"ID", "Type", "First Name", "Last Name", "Address", "Postal Code", "Phone #"};
	
	// true = fail
	// false = success
	private boolean error;
	
	private String errorMessage;

	private String[][] searchResult;
	
	public CustViewController() {
		cl = new CustViewListener();
		cv = new CustomerView(cl);
		cl.setCustomerView(this, cv);
	}
	
	public void setMainController(MainController mc) {
		this.mc = mc;
	}
	
	public CustomerView getCv() {
		return cv;
	}
	
	public CustViewListener getCl() {
		return cl;
	}

	public void searchCustomer(String searchKey, String request) {
		this.searchKey = searchKey;
		if (request.contentEquals("BYID")) {
			mc.searchCustomer(getSearchKey(), 0);
		} else if (request.contentEquals("BYNAME")) {
			mc.searchCustomer(getSearchKey(), 3);
		} else if (request.contentEquals("BYTYPE")) {
			mc.searchCustomer(getSearchKey(), 1);
		}
	}
	
	public void editCustomer(String id, String fName, String lName, String address, String postalCode, String phoneNo, String type) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNo = phoneNo;
		this.type = type;
		mc.editCustomerInfo(getSearchKey());
	}
	
	public void deleteCustomer(String id) {
		this.id = id;
		mc.deleteCustomer();
	}
	
	public void addCustomer(String id, String fName, String lName, String address, String postalCode, String phoneNo, String type) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNo = phoneNo;
		this.type = type;
		mc.addCustomer();
	}
	
	public String[][] getSearchResult(){
		return searchResult;
	}
	
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

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getfName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public String getlName() {
		return lName;
	}


	public void setlName(String lName) {
		this.lName = lName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public boolean isError() {
		return error;
	}


	public void setError(boolean error) {
		this.error = error;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

	public String getSearchKey() {
		return searchKey;
	}


	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}	
		
}
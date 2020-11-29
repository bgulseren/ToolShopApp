package client;


import javax.swing.table.DefaultTableModel;

import views.InventoryView;

public class InvViewController {
	
	private InventoryView iv;
	private InvViewListener il;
	private String request = "";
	
	private String id;
	private String name;
	private String currentQuantity;
	private String searchKey;
	
	public InventoryView getIv() {
		return iv;
	}
	
	String[] columnNames = {"ID", "Name", "Quantity", "Price", "Type", "Power", "SupplierID"};
	
	
	public InvViewListener getListerner() {
		return il;
	}
	
	
	// true = fail
	// false = success
	//private boolean error;
	
	//private String errorMessage;
	
	private String[][] searchResult;
	
	public InvViewController() {
		il = new InvViewListener();
		iv = new InventoryView(il);
		il.setInventoryView(this, iv);
	}
	
	public void searchTool(String searchKey, String request) {
		this.searchKey = searchKey;
		this.request = request;
	}
	
	public void displayAllTools() {
		this.request = "INVDISPLAYALL";
	}
	
	public void decreaseQuantity(String searchKey, String currentQuantity) {
		this.searchKey = searchKey;
		this.id = searchKey;
		this.currentQuantity = currentQuantity;
		this.request = "INVDECREASEQUANTITY";
	}
	
	public void displayOrder() {
		this.request = "INVDISPLAYORDER";
	}
	
	public String getRequest() {
		String out = request;
		clearRequest();
		return out;
	}
	
	public void clearRequest() {
		request = "NONE";
	}
	
	public String[][] getSearchResult(){
		return searchResult;
	}
	
	public void displayCustMgmtView() {
		request = "ACTIVATECUSTMGMT";
	}
	
	/*
	 * called by ModelController to update customerList 2D String up search results
	 */
	public void updateView(String[][] searchResult) {
		this.searchResult = searchResult;
		iv.getResultsTable().setModel(new DefaultTableModel(getSearchResult(), columnNames));
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentQuantity() {
		return currentQuantity;
	}

	public void setCurrentQuantity(String currentQuantity) {
		this.currentQuantity = currentQuantity;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

}
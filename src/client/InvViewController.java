package client;


import javax.swing.table.DefaultTableModel;

import views.InventoryView;

public class InvViewController {
	
	private InventoryView iv;
	private InvViewListener il;
	private MainController mc;
	
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
	
	public void setMainController(MainController mc) {
		this.mc = mc;
	}
	
	public void searchTool(String searchKey, String request) {
		this.searchKey = searchKey;
		if (request.contentEquals("BYID")) {
			mc.searchItem(getSearchKey(), 0);
		} else if (request.contentEquals("BYNAME")) {
			mc.searchItem(getSearchKey(), 1);
		}
	}
	
	public void displayAllTools() {
		mc.displayAllItems();
	}
	
	public void decreaseQuantity(String searchKey, String currentQuantity) {
		this.searchKey = searchKey;
		this.id = searchKey;
		this.currentQuantity = currentQuantity;
		mc.decreaseItemQuantity();
	}
	
	public void displayOrder() {
		mc.printOrder();
	}
	
	public String[][] getSearchResult(){
		return searchResult;
	}
	
	public void displayCustMgmtView() {
		mc.activateCustomerView();
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
package client;


import javax.swing.table.DefaultTableModel;

import views.InventoryView;

public class InvViewController {
	
	private InventoryView iv;
	private InvViewListener il;
	private MainController mc;
	
	private String id;
	private String name;
	
	public InventoryView getIv() {
		return iv;
	}
	
	String[] columnNames = {"ID", "Name", "Quantity", "Price", "Type", "Power", "SupplierID"};
	
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
		if (request.contentEquals("BYID")) {
			mc.searchItem(searchKey, 0);
		} else if (request.contentEquals("BYNAME")) {
			mc.searchItem(searchKey, 1);
		}
	}
	
	public void displayAllTools() {
		mc.displayAllItems();
	}
	
	public void decreaseQuantity(String searchKey) {
		mc.decreaseItemQuantity(searchKey, 1);
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


}
package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

import views.InventoryView;

public class InvViewListener implements ActionListener{

	private InventoryView iv;
	private InvViewController ivCtr;
	private String searchKey;
	
	//for clearing search results table
	DefaultTableModel emptyTableModel = new DefaultTableModel();
	
	//column headers
	String[] columnNames = {"ID", "Name", "Quantity", "Price", "Type", "Power", "SupplierID"};
	
	
	public void setInventoryView(InvViewController ivCtr, InventoryView pView) {
		this.ivCtr = ivCtr;
		this.iv = pView;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == iv.getSearchButton()) {
			searchKey = iv.getSearchTextField();
			searchTools(searchKey);
		}
		else if(e.getSource() == iv.getDisplayAllButton()) {
			displayAllTools();
		}
		else if(e.getSource() == iv.getDecreaseQuantityButton()) {
			decreaseToolQuantity();
		}
		else if(e.getSource() == iv.getDisplayOrderButton()) {
			displayOrder();
			iv.displayMessage("Your order was printed as txt file at the program root folder.");
		}
		else if(e.getSource() == iv.getCustMgmtButton()) {
			displayCustMgmtView();
		}
	}
	
	/*
	 * update gui text pane with list of customers
	 * asks CustViewController to run search of customers based on user input (customer ID, last name, customer type)
	 */
	public void searchTools(String searchText) {	
		if(iv.getSearchToolNameButton().isSelected()) {
			ivCtr.searchTool(searchText, "BYNAME");
		}
		else if(iv.getSearchToolIdButton().isSelected()) {
			ivCtr.searchTool(searchText, "BYID");
		}
	}
	
	public void updateView() {
		iv.getResultsTable().setModel(new DefaultTableModel(ivCtr.getSearchResult(), columnNames));
	}
	
	public void displayAllTools() {
		ivCtr.displayAllTools();
	}
	
	public void decreaseToolQuantity() {
		int row = iv.getResultsTable().getSelectedRow();
		if(row != -1) {
			String selectedID = iv.getResultsTable().getModel().getValueAt(row,0).toString();
			ivCtr.decreaseQuantity(selectedID);
		} else {
			iv.displayMessage("No row selected!");
		}
	}
	
	public void displayOrder() {
		ivCtr.displayOrder();
	}
	
	//switch to inventory management view
	public void displayCustMgmtView() {
		iv.frame.setVisible(false);
		ivCtr.displayCustMgmtView();
	}
	
}


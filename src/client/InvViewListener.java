package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

import views.InventoryView;

public class InvViewListener implements ActionListener{

	private final InventoryView iv;
	private final InvViewController ivCtr;
	
	//for clearing search results table
	DefaultTableModel emptyTableModel = new DefaultTableModel();
	
	String[] columnNames = {"ID", "Name", "Quantity", "Price", "Type", "Power", "SupplierID"};
	
	public InvViewListener(InventoryView pView) {
		this.iv = pView;
		this.ivCtr = iv.getIvCtr();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == iv.getSearchButton()) {
			searchTools(iv.getSearchTextField());
		}
		else if(e.getSource() == iv.getDisplayAllButton()) {
			displayAllTools();
		}
		else if(e.getSource() == iv.getDecreaseQuantityButton()) {
			decreaseToolQuantity();
		}
		else if(e.getSource() == iv.getDisplayOrderButton()) {
			//displayOrder();
		}
	}
	
	/*
	 * update gui text pane with list of customers
	 * asks CustViewController to run search of customers based on user input (customer ID, last name, customer type)
	 */
	public void searchTools(String searchText) {	
		if(iv.getSearchToolNameButton().isSelected()) {
			ivCtr.searchItem(searchText);
			//iv.getResultsTable().setModel(new DefaultTableModel(ivCtr.getSearchResult(), columnNames));
		}
		else if(iv.getSearchToolIdButton().isSelected()) {
			ivCtr.searchItem(Integer.parseInt(searchText));
			//iv.getResultsTable().setModel(new DefaultTableModel(ivCtr.getSearchResult(), columnNames));
		}
	}
	
	public void displayAllTools() {
		ivCtr.update();
	}
	
	public void decreaseToolQuantity() {
		int row = iv.getResultsTable().getSelectedRow();
		String selectedID = iv.getResultsTable().getModel().getValueAt(row,0).toString();
		ivCtr.reduceItem(Integer.parseInt(selectedID), 1);
	}
	
	
}


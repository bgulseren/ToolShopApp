package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import views.CustomerView;

public class CustViewListener implements ActionListener{

	private CustomerView cv ;
	private CustViewController cvCtr;
	public String searchKey;
	
	
	//for clearing search results (List and table)
	@SuppressWarnings("rawtypes")
	DefaultListModel emptyListModel = new DefaultListModel();
	DefaultTableModel emptyTableModel = new DefaultTableModel();
	
	
	public void setCustomerView(CustViewController cvCtr, CustomerView pView) {
		this.cvCtr = cvCtr;
		this.cv = pView;
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cv.getSearchButton()) {
			searchCustomers(cv.getSearchInputField());
		}
		else if(e.getSource() == cv.getClearSearchButton()) {
			clearSearch();
		}
		else if(e.getSource() == cv.getSaveChangesButton()) {
			updateRow();
		}
		else if(e.getSource() == cv.getDeleteRowButton()) {
			deleteRow();
		}
		else if(e.getSource() == cv.getAddRowButton()) {
			addRow();
		}
		else if(e.getSource() == cv.getClearChangesButton()) {
			clearNewRowInput();
		}
		else if(e.getSource() == cv.getInvMgmtButton()) {
			displayInvMgmtView();
		}

	}

	/*
	 * update GUI text pane with list of customers
	 * asks CustViewController to run search of customers
	 * based on user input (customer ID, last name, customer type)
	 */
	public void searchCustomers(String searchText) {	
		if(cv.getIdButton().isSelected()) {
			cvCtr.searchCustomer(searchText, "BYID");
		}
		else if(cv.getlastNameButton().isSelected()) {
			cvCtr.searchCustomer(searchText, "BYNAME");
		}
		else if(cv.getCustomerTypeButton().isSelected()) {
			cvCtr.searchCustomer(searchText, "BYTYPE");
		}
	}
	
	//clear search results
	public void clearSearch() {
		cv.getResultsTable().setModel(emptyTableModel);
	}
	
	//update selected row in database
	public void updateRow() {
		int row = cv.getResultsTable().getSelectedRow();
		String selectedID = cv.getResultsTable().getModel().getValueAt(row, 0).toString();
		String type = cv.getResultsTable().getModel().getValueAt(row, 1).toString();
		String fName = cv.getResultsTable().getModel().getValueAt(row, 2).toString();
		String lName = cv.getResultsTable().getModel().getValueAt(row, 3).toString();
		String address = cv.getResultsTable().getModel().getValueAt(row, 4).toString();
		String postalCode = cv.getResultsTable().getModel().getValueAt(row, 5).toString();
		String phoneNo = cv.getResultsTable().getModel().getValueAt(row, 6).toString();
		cvCtr.editCustomer(selectedID, fName, lName, address, postalCode, phoneNo, type);
	}
	
	//delete selected row
	public void deleteRow() {
		int row = cv.getResultsTable().getSelectedRow();
		String selectedID = cv.getResultsTable().getModel().getValueAt(row, 0).toString();
		cvCtr.deleteCustomer(selectedID);
	}
		
	//add row (customer)
	public void addRow() {
		String id = cv.getID().getText();
		String fName = cv.getFName().getText();
		String lName = cv.getLName().getText();
		String address = cv.getAddress().getText();
		String postalCode = cv.getPostalCode().getText();
		String phoneNo = cv.getPhoneNo().getText();
		String type = cv.getType().getText();
		cvCtr.addCustomer(id, fName, lName, address, postalCode, phoneNo, type);
	}
	
	//switch to inventory management view
	public void displayInvMgmtView() {
		cv.frame.setVisible(false);
		cvCtr.displayInvMgmtView();
	}
	
	//clear new customer fields
	public void clearNewRowInput() {
		cv.getFName().setText("");
		cv.getLName().setText("");
		cv.getAddress().setText("");
		cv.getPostalCode().setText("");
		cv.getPhoneNo().setText("");
		cv.getType().setText("");
	}
	
}

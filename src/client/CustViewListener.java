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
		if (row == -1) {
			cv.displayMessage("No row selected!");
			return;
		}
		String[] customerInfo = new String[7];
		for (int i = 0; i < 7; i++)
			customerInfo[i] = cv.getResultsTable().getModel().getValueAt(row, i).toString();
		cvCtr.editCustomer(customerInfo);
	}
	
	//delete selected row
	public void deleteRow() {
		int row = cv.getResultsTable().getSelectedRow();
		if (row == -1) {
			cv.displayMessage("No row selected!");
			return;
		}
		String selectedID = cv.getResultsTable().getModel().getValueAt(row, 0).toString();
		cvCtr.deleteCustomer(selectedID);
	}
		
	//add row (customer)
	public void addRow() {
		
		String[] customerInfo = new String[7];
		
		if (	!cv.getID().getText().isEmpty() &&
				!cv.getType().getText().isEmpty() &&
				!cv.getFName().getText().isEmpty() &&
				!cv.getLName().getText().isEmpty() &&
				!cv.getAddress().getText().isEmpty() &&
				!cv.getPostalCode().getText().isEmpty() &&
				!cv.getPhoneNo().getText().isEmpty())
		
				
				
			{
			customerInfo[0] = cv.getID().getText();
			customerInfo[1] = cv.getType().getText();
			customerInfo[2] = cv.getFName().getText();
			customerInfo[3] = cv.getLName().getText();
			customerInfo[4] = cv.getAddress().getText();
			customerInfo[5] = cv.getPostalCode().getText();
			customerInfo[6] = cv.getPhoneNo().getText();
		
		cvCtr.addCustomer(customerInfo);
		} else {
			cv.displayMessage("Empty fields not allowed!");
		}
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

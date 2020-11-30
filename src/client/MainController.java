package client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import customerModel.CustomerList;
import inventoryModel.*;

public class MainController {
	
	private Inventory inventory;
	private CustomerList customerList;
	private ClientController clCnt;
	private InvViewController IVC;
	private CustViewController CVC;
	private String[][] searchResult;
	
	public MainController(InvViewController IVC, CustViewController CVC) throws IOException {
		//Connection controller
		this.clCnt = new ClientController(this);
		
		//Inventory
		this.inventory = null;
		this.IVC = IVC;
		IVC.setMainController(this);
		
		//Customer
		this.customerList = null;
		this.CVC = CVC;
		CVC.setMainController(this);
	}
	
	public InvViewController getIvc() {
		return IVC;
	}
	
	public InvViewController getInvView() {
		return IVC;
	}
	
	public CustViewController getCvc() {
		return CVC;
	}
	
	public CustViewController getCusView() {
		return CVC;
	}
	
	public CustomerList getCustomerList() {
		return customerList;
	}
	
	public void setCustomerList(CustomerList customerList) {
		this.customerList = customerList;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public ClientController getClientController() {
		return clCnt;
	}
	
	public void setClientController(ClientController client) {
		this.clCnt = client;
	}
	
	
	public String[][] getSearchResult(){
		return searchResult;
	}	
	
	public String[][] searchTable(String[][] databaseQuery, String searchText, int selCol){
		int rows = 0;
		for(int r = 0; r < databaseQuery.length; r++) {
			if(databaseQuery[r][selCol].contentEquals(searchText)) {
				rows += 1;
			}
		}
		String[][] search = new String[rows][7];
		int row = 0;
		
		for(int r = 0; r < databaseQuery.length; r++) {
			if(databaseQuery[r][selCol].contentEquals(searchText)) {
				search[row][0] = databaseQuery[r][0];
				search[row][1] = databaseQuery[r][1];
				search[row][2] = databaseQuery[r][2];
				search[row][3] = databaseQuery[r][3];
				search[row][4] = databaseQuery[r][4];
				search[row][5] = databaseQuery[r][5];
				search[row][6] = databaseQuery[r][6];
				row += 1;
			}
		}
		return search;
	}

	
	public void printOrder() {
		
		File file = new File("reports/orderSummary.txt");
        
		if (inventory.getOrder() != null) {
	        String data = inventory.getOrder().toString();
	        
	        try(FileOutputStream fos = new FileOutputStream(file);
	            BufferedOutputStream bos = new BufferedOutputStream(fos)) {
	        	
	            //convert string to byte array
	            byte[] bytes = data.getBytes();
	            
	            //write byte array to file
	            bos.write(bytes);
	            bos.close();
	            fos.close();
	            
	            System.out.print("Order written to file successfully.");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		} else {
        	System.out.println("No order to print!");
        }
	}
	
	public void displayAllItems() {
		try {
			getClientController().getInventoryFromSrv();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[][] invDatabaseQuery = getInventory().listAllItems();
		getIvc().updateView(invDatabaseQuery);
	}
	
	public void searchItem(String searchKey, int colNum) {
		try {
			getClientController().getInventoryFromSrv();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String[][] invDatabaseQuery = getInventory().listAllItems();
		String[][] searchResult = searchTable(invDatabaseQuery, searchKey, colNum);
		getIvc().updateView(searchResult);
	}
	

	
	public void decreaseItemQuantity() {
		try {
			getClientController().reduceItemFromSrv(getIvc().getId(), 1);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[][] invDatabaseQuery = getInventory().listAllItems();
		getIvc().updateView(invDatabaseQuery);
	}
	
	
	public void searchCustomer(String searchKey, int colNum) {
		try {
			getClientController().getCustomersFromSrv();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[][] custDatabaseQuery = getCustomerList().listAllCustomers();
		String[][] searchResult = searchTable(custDatabaseQuery, searchKey, colNum);
		getCvc().updateView(searchResult);
	}
	
	
	public void editCustomerInfo(String searchKey) {
		try {
			getClientController().editCustomerFromSrv();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[][] custDatabaseQuery = getCustomerList().listAllCustomers();
		String[][] searchResult = searchTable(custDatabaseQuery, searchKey, 0);
		getCvc().updateView(searchResult);
	}
	
	public void deleteCustomer() {
		String customerId = getCvc().getId();
		try {
			getClientController().deleteCustomerFromSrv(customerId);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[][] custDatabaseQuery = getCustomerList().listAllCustomers();
		getCvc().updateView(custDatabaseQuery);
	}
	
	public void addCustomer() {
		try {
			getClientController().addCustomerFromSrv();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[][] custDatabaseQuery = getCustomerList().listAllCustomers();
		getCvc().updateView(custDatabaseQuery);
	}
	
	public void activateInventoryView() {
		getIvc().getIv().frame.setVisible(true);
	}
	
	public void activateCustomerView() {
		getCvc().getCv().frame.setVisible(true);
	}
	
	//*****************Main*******************//
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		InvViewController IVC = new InvViewController();
		CustViewController CVC = new CustViewController();
		MainController app = new MainController(IVC, CVC);

		app.getCvc().getCv().frame.setVisible(true);

		while(true) {
			
		}		
	}
}	

	 


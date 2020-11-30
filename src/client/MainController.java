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
	
	public CustViewController getCvc() {
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
		
		String[][] searchResult = getInventory().search(searchKey, colNum);
		getIvc().updateView(searchResult);
	}
	

	
	public void decreaseItemQuantity(String id, int qty) {
		try {
			getClientController().reduceItemFromSrv(id, qty);
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
		String[][] searchResult = getCustomerList().search(searchKey, colNum);
		getCvc().updateView(searchResult);
	}
	
	public void addCustomer(String[] customerInfo) {
		try {
			getClientController().addCustomerFromSrv(customerInfo);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[][] custDatabaseQuery = getCustomerList().listAllCustomers();
		getCvc().updateView(custDatabaseQuery);
	}
	
	public void editCustomerInfo(String[] customerInfo) {
		try {
			getClientController().editCustomerFromSrv(customerInfo);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[][] custDatabaseQuery = getCustomerList().listAllCustomers();
		getCvc().updateView(custDatabaseQuery);
	}
	
	public void deleteCustomer(String customerId) {
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
	
	public void activateInventoryView() {
		getIvc().getIv().frame.setVisible(true);
	}
	
	public void activateCustomerView() {
		getCvc().getCv().frame.setVisible(true);
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

	 


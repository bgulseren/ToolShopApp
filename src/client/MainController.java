package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import customerModel.CustomerList;
import inventoryModel.*;

public class MainController {
	
	private Inventory inventory;
	private CustomerList customerList;
	private ClientController clCnt;
	private InvViewController invView;
	private CustViewController cusView;
	
	public MainController() throws IOException {
		//Connection controller
		this.clCnt = new ClientController(this);
		
		//Inventory
		this.inventory = null;
		this.invView = new InvViewController(this);
		
		//Customer
		
		this.customerList = null;
		//testing this.cusView = new CustViewController(this);
	}
	
	public InvViewController getInvView() {
		return invView;
	}
	
	public CustViewController getCusView() {
		return cusView;
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
	

	
	/**
	 * Main
	 * @throws ClassNotFoundException
	 */
	public static void main (String [] args) throws IOException, ClassNotFoundException {
		System.out.println("Press ENTER to connect to the tool shop.");
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		stdin.readLine();
		
		MainController theApp = new MainController();
		
		//testing//
		System.out.println("Testing getting the inventory from server (will trigger the DB to create a new model first and then passed to the client side");
		theApp.getClientController().getInventoryFromSrv();
		
		//testing - get new list//
		System.out.println(theApp.getInventory().toString());
		
		theApp.getClientController().getCustomersFromSrv();
		
		System.out.println(theApp.getCustomerList().toString());
		
	}
	
}


package client;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

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
	
	/**
	 * Main
	 * @throws ClassNotFoundException
	 */
	public static void main (String [] args) throws IOException, ClassNotFoundException {
		System.out.println("Press ENTER to connect to the tool shop.");
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		stdin.readLine();
		
		MainController theApp = new MainController();
		
		// ==== Test case === //
		System.out.println("Test: Read inventory from server");
		theApp.getClientController().getInventoryFromSrv();
		System.out.println(theApp.getInventory().toString());
		
		// ==== Test case === //
		System.out.println("Test: Reducing item 1005 Twinkies by 1");
		theApp.invView.itemId = 1005;
		theApp.invView.itemQty = 1;
		theApp.getClientController().reduceItemFromSrv();
		System.out.println(theApp.getInventory().toString());
		
		// ==== Test case === //
//		System.out.println("Test: Adding item 1160 Twinkies Extended by 60");
//		theApp.invView.itemId = 1160;
//		theApp.invView.itemQty = 60;
//		theApp.invView.itemName = "Twinkies Extended";
//		theApp.invView.itemPrice = 59.99;
//		theApp.invView.itemType = "nonelectric";
//		theApp.invView.itemSupplierId = 8015;
		
//		theApp.getClientController().addItemFromSrv();
//		System.out.println(theApp.getInventory().toString());
		
		// ==== Test case === //
		System.out.println("Test: Deleting item id 1005");
		theApp.getClientController().deleteItemFromSrv(1005);
		System.out.println(theApp.getInventory().toString());
		
		// ==== Test case === //
		System.out.println("Test: Read customer list from server");
		theApp.getClientController().getCustomersFromSrv();
		System.out.println(theApp.getCustomerList().toString());
		
		// ==== Test case === //
		System.out.println("Test: Delete customer 8009 Jacob from server");
		theApp.getClientController().deleteCustomerFromSrv(8009);
		System.out.println(theApp.getCustomerList().toString());
		
		// ==== Test case === //
		System.out.println("Test: Generating order report as txt");
		theApp.printOrder();
		
	}
	
}


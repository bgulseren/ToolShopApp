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
	private InvViewController IVC;
	private CustViewController CVC;
	private String[][] searchResult;
	
	public MainController(InvViewController IVC, CustViewController CVC) throws IOException {
		//Connection controller
		this.clCnt = new ClientController(this);
		
		//Inventory
		this.inventory = null;
		this.IVC = IVC;
		
		//Customer
		this.customerList = null;
		this.CVC = CVC;
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
	
	public String[][] searchTool(String[][] databaseQuery, String searchText, String request){
		int rows = 0;
		if(request == "INVSEARCHBYNAME") {
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][1].contentEquals(searchText)) {
					rows += 1;
				}
			}
			String[][] search = new String[rows][7];
			int row = 0;
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][1].contentEquals(searchText)) {
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
			searchResult = search;
		}
		
		else if(request == "INVSEARCHBYID") {
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][0].contentEquals(searchText)) {
					rows += 1;
				}
			}
			String[][] search = new String[rows][7];
			int row = 0;
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][0].contentEquals(searchText)) {
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
			searchResult = search;
		}
		return searchResult;
	}
	
	
	public String[][] searchCust(String[][] databaseQuery, String searchText, String request){
		int rows = 0;
		if(request == "CUSTSEARCHBYID") {
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][0].contentEquals(searchText)) {
					rows += 1;
				}
			}
			String[][] search = new String[rows][7];
			int row = 0;
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][0].contentEquals(searchText)) {
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
			searchResult = search;
		}
		
		else if(request == "CUSTSEARCHBYNAME") {
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][3].contentEquals(searchText)) {
					rows += 1;
				}
			}
			String[][] search = new String[rows][7];
			int row = 0;
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][3].contentEquals(searchText)) {
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
			searchResult = search;
		}
		
		else if(request == "CUSTSEARCHBYTYPE") {
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][1].contentEquals(searchText)) {
					rows += 1;
				}
			}
			String[][] search = new String[rows][7];
			int row = 0;
			for(int r = 0; r < databaseQuery.length; r++) {
				if(databaseQuery[r][1].contentEquals(searchText)) {
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
			searchResult = search;
		}
		return searchResult;
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
			
			// Mock results from database.extractTable() method
//			String[][] invDatabaseQuery = {
//					{"1001", "Hammer", "55", "95.99", "Nonelectrical","0", "8005"},
//					{"1002", "Hand-held saw", "15", "50.99", "Nonelectrical", "0", "8006"},
//					{"1003", "Leaf blower", "10", "199.99", "Electrical", "120V", "8007"},
//					{"1004", "Nail Gun", "10", "350.95", "Electrical", "120V", "8007"},
//					{"1005", "Wrench", "20", "65.99", "Nonelectrical", "0", "8008"}
//			};
			
//			String[][] custDatabaseQuery = {
//					{"8001", "R", "Noah", "Andrew", "Lloydminster AB T9V5V5", "H1F3B2", "403-664-4505"},
//					{"8002", "R", "Mohammed", "Abdullah", "Giza St Cairo EG", "D8S90S7", "228-192-1827"},
//					{"8003", "C", "Ali", "Barakat", "Shehab St Alexandria EG", "KD7S93D", "232-938-2498"}
//			};
			
			InvViewController IVC = new InvViewController();
			CustViewController CVC = new CustViewController();
			MainController app = new MainController(IVC, CVC);
			
			String messageInvCtr = "";
			String messageCustCtr = "";

			app.getCvc().getCv().frame.setVisible(true);

		
			while(true) {
				
				//get request message from inventory controller
				messageInvCtr = app.getIvc().getRequest();
				
				//get request message from customer controller
				messageCustCtr = app.getCvc().getRequest();
				
				//***Inventory GUI***//
				
				//Search item by name
				if(messageInvCtr.contentEquals("INVSEARCHBYNAME")) {
					
					app.getClientController().getInventoryFromSrv();
					
					String[][] invDatabaseQuery = app.getInventory().listAllItems();
					
					String searchKey = app.getIvc().getSearchKey();
					String[][] searchResult = app.searchTool(invDatabaseQuery, searchKey, "INVSEARCHBYNAME");
					app.getIvc().updateView(searchResult);
					
					app.getIvc().clearRequest();
				}
				
				//Search item by ID
				else if(messageInvCtr == "INVSEARCHBYID") {
					
					app.getClientController().getInventoryFromSrv();
					
					String[][] invDatabaseQuery = app.getInventory().listAllItems();
					
					String searchKey = app.getIvc().getSearchKey();
					String[][] searchResult = app.searchTool(invDatabaseQuery, searchKey, "INVSEARCHBYID");
					app.getIvc().updateView(searchResult);
					app.getIvc().clearRequest();
				}
				
				//Display all items
				else if(messageInvCtr == "INVDISPLAYALL") {
					app.getClientController().getInventoryFromSrv();
					
					String[][] invDatabaseQuery = app.getInventory().listAllItems();
					
					app.getIvc().updateView(invDatabaseQuery);
					app.getIvc().clearRequest();
				}
				
				//Decrease item quantity
				else if(messageInvCtr == "INVDECREASEQUANTITY") {
					app.getClientController().reduceItemFromSrv(app.getIvc().getId(), 1);
					
					String[][] invDatabaseQuery = app.getInventory().listAllItems();
					
					app.getIvc().updateView(invDatabaseQuery);
					app.getIvc().clearRequest();
					//app.getIvc().decreaseQuantity(app.getIvc().getId(), app.getIvc().getCurrentQuantity());
				}
				
				//***Customer GUI***//
				
				//Search customer by ID
				else if(messageCustCtr.contentEquals("CUSTSEARCHBYID")) {
					
					app.getClientController().getCustomersFromSrv();
					
					String[][] custDatabaseQuery = app.getCustomerList().listAllCustomers();
					
					String searchKey = app.getCvc().getSearchKey();
					String[][] searchResult = app.searchCust(custDatabaseQuery, searchKey, "CUSTSEARCHBYID");
					app.getCvc().updateView(searchResult);
					app.getCvc().clearRequest();
				}
				
				//Search customer by last name
				else if(messageCustCtr.contentEquals("CUSTSEARCHBYNAME")) {
					
					app.getClientController().getCustomersFromSrv();
					
					String[][] custDatabaseQuery = app.getCustomerList().listAllCustomers();
					
					String searchKey = app.getCvc().getSearchKey();
					String[][] searchResult = app.searchCust(custDatabaseQuery, searchKey, "CUSTSEARCHBYNAME");
					app.getCvc().updateView(searchResult);
					app.getCvc().clearRequest();
				}
				
				//Search customer by type
				else if(messageCustCtr.contentEquals("CUSTSEARCHBYTYPE")) {
					app.getClientController().getCustomersFromSrv();
					
					String[][] custDatabaseQuery = app.getCustomerList().listAllCustomers();
					
					String searchKey = app.getCvc().getSearchKey();
					String[][] searchResult = app.searchCust(custDatabaseQuery, searchKey, "CUSTSEARCHBYTYPE");
					app.getCvc().updateView(searchResult);
					app.getCvc().clearRequest();
				}
				
				//Edit customer
				else if(messageCustCtr.contentEquals("CUSTUPDATE")) {
			
					app.getClientController().editCustomerFromSrv();
					
					String[][] custDatabaseQuery = app.getCustomerList().listAllCustomers();
					
					String searchKey = app.getCvc().getSearchKey();
					String[][] searchResult = app.searchCust(custDatabaseQuery, searchKey, "CUSTSEARCHBYID");
					app.getCvc().updateView(searchResult);
					app.getCvc().clearRequest();
					
				}
				
				//Delete customer
				else if(messageCustCtr.contentEquals("CUSTDELETE")) {
					String customerId = app.getCvc().getId();
					app.getClientController().deleteCustomerFromSrv(customerId);
					
					String[][] custDatabaseQuery = app.getCustomerList().listAllCustomers();
					
					app.getCvc().updateView(custDatabaseQuery);
					app.getCvc().clearRequest();
					
				}
				
				//Add customer
				else if(messageCustCtr.contentEquals("CUSTADD")) {
					
					app.getClientController().addCustomerFromSrv();
					
					String[][] custDatabaseQuery = app.getCustomerList().listAllCustomers();
					
					app.getCvc().updateView(custDatabaseQuery);
					app.getCvc().clearRequest();
				}
				
				//Switching views
				else if(messageCustCtr.contentEquals("ACTIVATEINVMGMT")) {
					app.getIvc().getIv().frame.setVisible(true);
					app.getCvc().getCv().frame.setVisible(false);
				}
				else if(messageInvCtr.contentEquals("ACTIVATECUSTMGMT")) {
					app.getIvc().getIv().frame.setVisible(false);
					app.getCvc().getCv().frame.setVisible(true);
				}
				
				
				else {
					System.out.print("");
				}
				
			}		
		}
	}	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Main
	 * @throws ClassNotFoundException

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

	 */


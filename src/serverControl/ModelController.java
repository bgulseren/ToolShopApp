package serverControl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import customerModel.CustomerList;
import inventoryModel.*;

public class ModelController implements Runnable {
	
	private Inventory inventory;
	private CustomerList customerList;
	
	private DatabaseController dbControl;
	private ServerController srvControl;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private Socket clientSocket;
	
	public ModelController() {
		this.dbControl = new DatabaseController("inventorydb");
		this.inventory = null; //new Inventory();
		this.customerList = null;
		socketIn = null;
		socketOut = null;
	}
	
	public void setSrvControl(ServerController srvControl) {
		this.srvControl = srvControl;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public CustomerList getCustomerList() {
		return customerList;
	}
	
	public ServerController getSrvControl() {
		return srvControl;
	}
	
	public DatabaseController getDb() {
		return dbControl;
	}
	
	// ==================================================================== //
	//							INVENTORY									//
	// ==================================================================== //
	/**
	 * updates the inventory from db (sets)
	 */
	public void getNewInventory() {
		
		this.inventory = new Inventory();
		
		loadSuppliersTable(); // Get the suppliers table from db into model
		loadItemsTable(); // Get items table from db into model
		loadOrderTable(); // Get orderlines table from db into model
	}
	
	public void loadSuppliersTable() {
		String[][] suppliersTable = dbControl.extractTable("suppliertable");
		
		if (suppliersTable == null) {
			System.out.println("No suppliers found in db");
			return;
		}
		
		for (int i = 0; i < suppliersTable.length; i++) {
			int id = Integer.parseInt(suppliersTable[i][0]);
			String type = suppliersTable[i][1];
			String contact = suppliersTable[i][2];
			String address = suppliersTable[i][3];
			String name = suppliersTable[i][4];
			int tax = Integer.parseInt(suppliersTable[i][5]);
			
			this.inventory.getSuppliers().addSupplier(id, type, name, address, contact, tax);
		}
	}
	
	
	public void loadItemsTable() {
		String[][] itemsTable = dbControl.extractTable("tooltable");
		
		if (itemsTable == null) {
			System.out.println("No items found in db");
			return;
		}
		
		for (int i = 0; i < itemsTable.length; i++) {
			
			int itemId = Integer.parseInt(itemsTable[i][0]);
			String itemName = itemsTable[i][1];
			int itemQty = Integer.parseInt(itemsTable[i][2]);
			double itemPrice = Double.parseDouble(itemsTable[i][3]);
			String itemType = itemsTable[i][4];
			int itemPower = Integer.parseInt(itemsTable[i][5]);
			int itemSupId = Integer.parseInt(itemsTable[i][6]);
			
			this.inventory.addItem(itemType, itemId, itemName, itemQty, itemPrice, itemSupId, itemPower);
		}
	}
	
	public void loadOrderTable() {
		String[][] ordersTable = dbControl.extractTable("orderlinetable");
		
		if (ordersTable == null) {
			System.out.println("No orderlines found in db");
			return;
		}
		
		//now check orderlines to be associated to item
		for (int i = 0; i < ordersTable.length; i++) {
			
			//int orderId = Integer.parseInt(ordersTable[i][0]);
			int itemId = Integer.parseInt(ordersTable[i][1]);
			int itemQty = Integer.parseInt(ordersTable[i][2]);
			//int supId = Integer.parseInt(ordersTable[i][3]);
			
			// find the matching item in inventory and create orderline in that item
			
			Item foundItem = this.inventory.searchItem(itemId);
			if (foundItem != null) {
				foundItem.createOrderLine(itemQty);
			}
		}
	}
	
	/**
	 * Searches the inventory and adds an item to it if no item with specified parameters found.
	 * If item already exists, then increases the item's quantity only (other args are ignored).
	 * 
	 * @param itemType type of the item to add.
	 * @param itemId id number of the item to add.
	 * @param itemName name of the item to add.
	 * @param itemQty quantity of the item to add (for existing items, this is used to increase the actual quantity by).
	 * @param itemPrice price of the item to add.
	 * @param itemSupId supplier id number of the item to add.
	 * @param itemPower power of the item (if not electrical, should be 0)
	 */
	public int addItem(String itemType, int itemId, String itemName, int itemQty, double itemPrice, int itemSupId, int itemPower) {
		String[] newRow = new String[7];
		
		newRow[0] = itemType;
		newRow[1] = Integer.toString(itemId);
		newRow[2] = itemName;
		newRow[3] = Integer.toString(itemQty);
		newRow[4] = Double.toString(itemPrice);
		newRow[5] = Integer.toString(itemSupId);
		newRow[6] = Integer.toString(itemPower);
				
		int result = dbControl.addRow("tooltable", newRow);
		getNewInventory(); //get updated inventory
		return result;
	}
	
	public int deleteItem(int itemId) {
		
		int result = dbControl.deleteRow("tooltable", itemId);
		getNewInventory(); //get updated inventory
		return result;
	}
	
	public int deleteCustomer(int customerId) {
		
		int result = dbControl.deleteRow("customertable", customerId);
		getNewCustomerList(); //get updated customers list
		return result;
	}
	
	public int updateItem(String itemType, int itemId, String itemName, int itemQty, double itemPrice, int itemSupId, int itemPower) {
		
		int result = dbControl.updateToolRow(itemId, itemName, itemQty, itemPrice, itemType, itemPower);
		getNewInventory(); //get updated inventory
		return result;
	}
	
	public int updateCustomer(String[] customerInfo) {
		
		int id = Integer.parseInt(customerInfo[0]);
		String cType = customerInfo[1];
		String fName = customerInfo[2];
		String lName = customerInfo[3];
		String address = customerInfo[4];
		String postalCode = customerInfo[5];
		String phoneNo = customerInfo[6];
		
		int result = dbControl.updateCustomerRow(id, fName, lName, address, postalCode, phoneNo, cType);
		getNewCustomerList(); //get updated customers list
		return result;
		
	}
	
	/**
	 * Reduces quantity of the specified item from the inventory.
	 * If item reduction results an order line, then writes that into the db as well.
	 * 
	 * @param itemId id number of the item to reduce.
	 * @param itemQty quantity to reduce from the item.
	 */
	public void reduceItem(int itemId, int itemQty) {
		getNewInventory(); //get latest inventory info from db into model
		
		//this has to go thru inventory to trigger the orderline logic.
		Item item = inventory.reduceItem(itemId, itemQty);
		
		if (item != null) {
			getDb().updateQuant("tooltable", item.getId(), item.getQty());
			
			if (item.getOrderLine() != null) {
				String[] newRow = new String[4];
				
				newRow[0] = Integer.toString(item.getOrderLine().getId());
				newRow[1] = Integer.toString(item.getOrderLine().getQty());
				newRow[2] = item.getOrderLine().getName();
				newRow[3] = Integer.toString(item.getOrderLine().getSupplierId());
				
				getDb().addRow("orderlinetable", newRow);
			}
		}
		//update item on the db
		
		
		getNewInventory(); //get latest inventory info from db into model
	}
	
	
	// ==================================================================== //
	//							CUSTOMERS									//
	// ==================================================================== //
	
	/**
	 * gets the customers table from db into model
	 */
	public void getNewCustomerList() {
		// Get the customers table from db into model
		String[][] customersTable = dbControl.extractTable("customertable");
		
		if (customersTable == null) {
			System.out.println("No customers found in db");
			return;
		}
		
		this.customerList = new CustomerList(customersTable);
	}
	
	// ==================================================================== //
	//							CONNECTIONS									//
	// ==================================================================== //
	public void setSocketIn(ObjectInputStream socketIn) {
		this.socketIn = socketIn;
	}
	
	public void setSocketOut(ObjectOutputStream socketOut) {
		this.socketOut = socketOut;
	}
	
	public void setClientSocket(Socket cliSocket) {
		this.clientSocket = cliSocket;
	}
	
	@Override
	public void run() {
		//listening to client
		while (true) {
			String message = "";
			
			try {
				message = (String) socketIn.readObject();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
				/*============== UPDATE ==============*/
			if (message.contains("UPDATEITEMS%")) {

				getNewInventory();
				try {
					socketOut.writeObject(getInventory());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				/*============== SEARCHBYNAME ==============*/
			} else if (message.contains("SEARCHBYNAME%")) {
				message.replace("SEARCHBYNAME%", ""); //remove message header
				
				//get result from inventory
				getNewInventory();
				getInventory().searchItem(message);
				
				//send back the updated inventory to the client
				try {
					socketOut.writeObject(getInventory());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*============== SEARCHBYID ==============*/
			} else if (message.contains("SEARCHBYID%")) {
				message.replace("SEARCHBYID%", ""); //remove message header
				
				//get result from inventory
				getNewInventory();
				getInventory().searchItem(Integer.parseInt(message));
				
				//send back the updated inventory to the client
				try {
					socketOut.writeObject(getInventory());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*============== ADDITEM ==============*/
			} else if (message.contains("ADDITEM%")) {
				message.replace("ADDITEM%", ""); //remove message header
				
				//parse incoming message to item related info
				String[] itemInfo = message.split("%");
				
				String itemType = itemInfo[0];
				int itemId = Integer.parseInt(itemInfo[1]);
				String itemName = itemInfo[2];
				int itemQty = Integer.parseInt(itemInfo[3]);
				double itemPrice = Double.parseDouble(itemInfo[4]);
				int itemSupId = Integer.parseInt(itemInfo[5]);
				int itemPower = Integer.parseInt(itemInfo[6]);
				
				// add item to the db
				addItem(itemType, itemId, itemName, itemQty, itemPrice, itemSupId, itemPower);
				
				//send back the updated inventory to the client
				try {
					socketOut.writeObject(getInventory());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				/*============== REDUCEITEM ==============*/
			} else if (message.contains("REDUCEITEM%")) {
				message.replace("REDUCEITEM%", ""); //remove message header
				
				//parse incoming message to item related info
				String[] itemInfo = message.split("%");
				int itemId = Integer.parseInt(itemInfo[0]);
				int itemQty = Integer.parseInt(itemInfo[1]);
				
				// reduce item from db
				reduceItem(itemId, itemQty);
				
				//send back the updated inventory to the client
				try {
					socketOut.writeObject(getInventory());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*============== GET NEW CUSTOMERS LIST ==============*/
			} else if (message.contains("UPDATECUSTOMERS%")) {
				getNewCustomerList();
				try {
					socketOut.writeObject(getCustomerList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*============== EDIT CUSTOMER ==============*/
			} else if (message.contains("EDITCUSTOMER%")) {
				message.replace("EDITCUSTOMER%", ""); //remove message header
				
				//parse incoming message to customer related info
				String[] customerInfo = message.split("%");
				
				updateCustomer(customerInfo);
				
				try {
					socketOut.writeObject(getCustomerList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*============== DELETE CUSTOMER ==============*/
			} else if (message.contains("DELETECUSTOMER%")) {
				message.replace("DELETECUSTOMER%", ""); //remove message header
				
				deleteCustomer(Integer.parseInt(message));
				
				try {
					socketOut.writeObject(getCustomerList());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				/*============== EXIT ==============*/
			} else if (message.contains("DISCONNECT%")) {
				try {
					socketIn.close();
					socketOut.close();
					
					//clientSocket.close(); //todo: try this one as well
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("Server: A client was disconnected");
				break;
			}
		}
	}

}

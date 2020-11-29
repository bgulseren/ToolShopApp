package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import customerModel.CustomerList;
import inventoryModel.*;

/**
 * 
 * @author B.Gulseren, K.Behairy
 * @version 1.0
 * @since October 27th, 2020
 */

public class ClientController {
	
	private Socket aSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	private MainController mainCtr;
	
	private String serverName;
	private int portNumber;
	
	/**
	 * Default constructor
	 * @throws IOException 
	 */
	public ClientController (MainController mainCtr) throws IOException {
		serverName = "localhost";
		portNumber = 9898;
		
		this.mainCtr = mainCtr;
		socketOut = null;
		socketIn = null;
		aSocket = null;
	}
	
	/**
	 * Starts the connection with the server
	 */
	public void connect () {
		
		try {
            // establish socket connection to server
			aSocket = new Socket (serverName, portNumber);
			
			if (aSocket.isConnected()) {
				socketOut = new ObjectOutputStream(aSocket.getOutputStream());
				socketIn = new ObjectInputStream(aSocket.getInputStream());
				System.out.println("Client: Connected to the server");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	// ==================================================================== //
	//							INVENTORY QUERIES							//
	// ==================================================================== //
	public void getInventoryFromSrv() throws IOException, ClassNotFoundException {
		connect();
		
		// sending command to server
		String messageOut = "UPDATEITEMS%";
		socketOut.writeObject(messageOut);
		readInventoryFromSrv();
	}
	
	
	public void searchItemByNameFromSrv () throws ClassNotFoundException, IOException {
		connect();
		
		String itemName = mainCtr.getInvView().itemName;
		
		String messageOut = "SEARCHBYNAME" + "%" + itemName;
		socketOut.writeObject(messageOut);
		readInventoryFromSrv();
	}
	
	public void searchItemByIdFromSrv () throws ClassNotFoundException, IOException {
		connect();
		
		int itemId = mainCtr.getInvView().itemId;
		
		String messageOut = "SEARCHBYID" + "%" + itemId;
		socketOut.writeObject(messageOut);
		readInventoryFromSrv();
	}
	
	public void reduceItemFromSrv () throws ClassNotFoundException, IOException {
		connect();
		
		int itemId = mainCtr.getInvView().itemId;
		int itemQty = mainCtr.getInvView().itemQty;
		
		String messageOut = "REDUCEITEM" + "%" + itemId + "%" + itemQty;
		socketOut.writeObject(messageOut);
		readInventoryFromSrv();
	}
	
	public void deleteItemFromSrv () throws ClassNotFoundException, IOException {
		connect();
		
		int itemId = mainCtr.getInvView().itemId;
		
		String messageOut = "DELETEITEM" + "%" + itemId;
		socketOut.writeObject(messageOut);
		readInventoryFromSrv();
	}
	
	public void deleteItemFromSrv (int itemId) throws ClassNotFoundException, IOException {
		connect();
		
		String messageOut = "DELETEITEM" + "%" + itemId;
		socketOut.writeObject(messageOut);
		readInventoryFromSrv();
	}
	
	public void addItemFromSrv () throws ClassNotFoundException, IOException {
		connect();
		
		String itemType = mainCtr.getInvView().itemType;
		String itemName = mainCtr.getInvView().itemName;
		int itemId = mainCtr.getInvView().itemId;
		double itemPrice = mainCtr.getInvView().itemPrice;
		int itemQty = mainCtr.getInvView().itemQty;
		int itemSupplierId = mainCtr.getInvView().itemSupplierId;
		int itemPower = mainCtr.getInvView().itemPower;
		
		String messageOut = "ADDITEM" + "%" +
				itemId + "%" +
				itemName + "%" +
				itemQty + "%" +
				itemPrice + "%" +
				itemType + "%" +
				itemPower + "%" +
				itemSupplierId;

		socketOut.writeObject(messageOut);
		readInventoryFromSrv();
	}
	
	private void readInventoryFromSrv() throws ClassNotFoundException, IOException {
		// receiving the new inventory model from server
        Inventory inv = (Inventory) socketIn.readObject();
        this.mainCtr.setInventory(inv);
        
        System.out.println("Received new Inventory from server");
        
		disconnect();
	}
	
	// ==================================================================== //
	//							CUSTOMER QUERIES							//
	// ==================================================================== //
	
	public void getCustomersFromSrv() throws IOException, ClassNotFoundException {
		connect();
		
		// sending command to server
		String messageOut = "UPDATECUSTOMERS%";
		socketOut.writeObject(messageOut);
		readCustomersFromSrv();
	}
	
	public void editCustomerFromSrv () throws ClassNotFoundException, IOException {
		connect();
		
		String id = mainCtr.getCusView().getId();
		String type = mainCtr.getCusView().getType();
		String fName = mainCtr.getCusView().getfName();
		String lName = mainCtr.getCusView().getlName();
		String addr = mainCtr.getCusView().getAddress();
		String pCode = mainCtr.getCusView().getPostalCode();
		String phone = mainCtr.getCusView().getPhoneNo();
		
		String messageOut = "EDITCUSTOMER" + "%" +
							id + "%" +
							type + "%" +
							fName + "%" +
							lName + "%" +
							addr + "%" +
							pCode + "%" +
							phone;
		
		socketOut.writeObject(messageOut);
		readCustomersFromSrv();
	}
	
	public void deleteCustomerFromSrv () throws ClassNotFoundException, IOException {
		connect();
		
		String customerId = mainCtr.getCusView().getId();
		
		String messageOut = "DELETECUSTOMER" + "%" + customerId;
		socketOut.writeObject(messageOut);
		readCustomersFromSrv();
	}
	
	// Overloaded version
	public void deleteCustomerFromSrv (int customerId) throws ClassNotFoundException, IOException {
		connect();
		
		String messageOut = "DELETECUSTOMER" + "%" + customerId;
		socketOut.writeObject(messageOut);
		readCustomersFromSrv();
	}
	
	
	private void readCustomersFromSrv() throws ClassNotFoundException, IOException {
		// receiving the new customers model from server
		CustomerList customers = (CustomerList) socketIn.readObject();
        this.mainCtr.setCustomerList(customers);
        
        System.out.println("Received new customers from server");
        
		disconnect();
	}
	
	

        
//		while (!finished) {
//			String invViewReq = mainCtr.getInvView().getRequest();
//			
//			
//			if (invViewReq.contentEquals("UPDATE") ) {
//
//		        
//			} else if (invViewReq.contentEquals("SEARCHBYNAME") ) {
//
//		        
//			} else if (invViewReq.contentEquals("SEARCHBYID") ) {
//
//		        
//			} else if (invViewReq.contentEquals("ADDITEM") ) {
//
//		    
//			}  else if (invViewReq.contentEquals("REDUCEITEM") ) {
//				
//			}
//			
//			
//		}
		
	private void disconnect () throws IOException {
		String messageOut = "DISCONNECT%";
		socketOut.writeObject(messageOut);
		closeSocket();
	}
	
	/**
	 * Closes all the sockets when app terminates
	 */
	private void closeSocket () {
		
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}

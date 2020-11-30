package client;

import java.io.IOException;
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

	
	public void reduceItemFromSrv (String itemId, int itemQtyToReduce) throws ClassNotFoundException, IOException {
		connect();
		
		String messageOut = "REDUCEITEM" + "%" + itemId + "%" + itemQtyToReduce;
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
		
//		String itemType = mainCtr.getInvView().itemType;
//		String itemName = mainCtr.getInvView().itemName;
//		int itemId = mainCtr.getInvView().itemId;
//		double itemPrice = mainCtr.getInvView().itemPrice;
//		int itemQty = mainCtr.getInvView().itemQty;
//		int itemSupplierId = mainCtr.getInvView().itemSupplierId;
//		int itemPower = mainCtr.getInvView().itemPower;
//		
//		String messageOut = "ADDITEM" + "%" +
//				itemId + "%" +
//				itemName + "%" +
//				itemQty + "%" +
//				itemPrice + "%" +
//				itemType + "%" +
//				itemPower + "%" +
//				itemSupplierId;

//		socketOut.writeObject(messageOut);
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
	
	public void addCustomerFromSrv (String[] customerInfo) throws ClassNotFoundException, IOException {
		connect();
		String messageOut = "ADDCUSTOMER%";
		for (int i = 0; i < customerInfo.length; i++) {
			messageOut += customerInfo[i] + "%";
		}

		socketOut.writeObject(messageOut);
		readCustomersFromSrv();
	}
	
	public void editCustomerFromSrv (String[] customerInfo) throws ClassNotFoundException, IOException {
		connect();
		String messageOut = "EDITCUSTOMER%";
		for (int i = 0; i < customerInfo.length; i++) {
			messageOut += customerInfo[i] + "%";
		}
		
		socketOut.writeObject(messageOut);
		readCustomersFromSrv();
	}
	
	public void deleteCustomerFromSrv (String customerId) throws ClassNotFoundException, IOException {
		connect();
		
		String messageOut = "DELETECUSTOMER" + "%" + customerId;
		socketOut.writeObject(messageOut);
		readCustomersFromSrv();
	}
	
	
	private void readCustomersFromSrv() throws ClassNotFoundException, IOException {
		// receiving the new customers model from server
		CustomerList customers = (CustomerList) socketIn.readObject();
        this.mainCtr.setCustomerList(customers);
        
        System.out.println("Received new customers list from server");
        
		disconnect();
	}

		
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

package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
	private ClientModel model;
	
	/**
	 * Default constructor
	 * @throws IOException 
	 */
	public ClientController () throws IOException {
		model = null;
		socketOut = null;
		socketIn = null;
		aSocket = null;
	}
	
	/**
	 * Starts the connection with the server
	 * @param serverName ip address of the server
	 * @param portNumber portnumber to connect
	 */
	public void connect (String serverName, int portNumber) {
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
	
	
	/**
	 * Starts the communication with the server
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public void communicate () throws ClassNotFoundException, IOException {
		boolean finished = false;

		// sending command to server
		String messageOut = "UPDATE";
		socketOut.writeObject(messageOut);
        
		// receiving the inventory model from server
        Inventory inv = (Inventory) socketIn.readObject();
        
        // constructing the client side model
        this.model = new ClientModel(inv);
        this.model.setClient(this);
        
		while (!finished) {
			String invViewReq = model.getInvView().getRequest();
			String itemType = model.getInvView().itemType;
			String itemName = model.getInvView().itemName;
			int itemId = model.getInvView().itemId;
			double itemPrice = model.getInvView().itemPrice;
			int itemQty = model.getInvView().itemQty;
			int itemSupplierId = model.getInvView().itemSupplierId;
			int itemPower = model.getInvView().itemPower;
			
			if (invViewReq.contentEquals("UPDATE") ) {
				messageOut = "UPDATE%";
				socketOut.writeObject(messageOut);
		        
				// receiving the inventory model from server
		        inv = (Inventory) socketIn.readObject();
		        
		        // constructing the client side model
		        this.model.setInventory(inv);
		        this.model.getInvView().updateView();
		        
			} else if (invViewReq.contentEquals("SEARCHBYNAME") ) {
				messageOut = "SEARCHBYNAME" + "%" + itemName;
				socketOut.writeObject(messageOut);
		        
				// receiving the inventory model from server
		        inv = (Inventory) socketIn.readObject();
		        
		        // constructing the client side model
		        this.model.setInventory(inv);
		        this.model.getInvView().updateView();
		        
			} else if (invViewReq.contentEquals("SEARCHBYID") ) {
				messageOut = "SEARCHBYID" + "%" + itemId;
				socketOut.writeObject(messageOut);
		        
				// receiving the inventory model from server
		        inv = (Inventory) socketIn.readObject();
		        
		        // constructing the client side model
		        this.model.setInventory(inv);
		        this.model.getInvView().updateView();
		        
			} else if (invViewReq.contentEquals("ADDITEM") ) {
				messageOut = 	"ADDITEM" + "%" +
								itemType + "%" +
								itemId + "%" +
								itemName + "%" +
								itemQty + "%" +
								itemPrice + "%" +
								itemSupplierId + "%" +
								itemPower;
				
				socketOut.writeObject(messageOut);
		        
				// receiving the inventory model from server
		        inv = (Inventory) socketIn.readObject();
		        
		        // constructing the client side model
		        this.model.setInventory(inv);
		        this.model.getInvView().updateView();
		        
			}  else if (invViewReq.contentEquals("REDUCEITEM") ) {
				
				messageOut = "REDUCEITEM" + "%" + itemId + "%" + itemQty;
		
				socketOut.writeObject(messageOut);
		        
				// receiving the inventory model from server
		        inv = (Inventory) socketIn.readObject();
		        
		        // constructing the client side model
		        this.model.setInventory(inv);
		        this.model.getInvView().updateView();
			}
			
            try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
			
		//close resources
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
	
	
	
	/**
	 * Main
	 * @throws ClassNotFoundException
	 */
	public static void main (String [] args) throws IOException, ClassNotFoundException {
		System.out.println("Press ENTER to connect to the tool shop.");
		
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		stdin.readLine();
		
		ClientController aClient = new ClientController();
		aClient.connect("localhost", 9898);

		aClient.communicate();
	}

}

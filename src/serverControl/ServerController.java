package serverControl;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

import inventoryModel.Inventory;
import inventoryModel.Item;


/**
 * Implementation of the Server Controller
 * @version 1.0
 * @since October 19th, 2020
 */


public class ServerController {

	private ExecutorService pool;
	
	private ServerSocket serverSocket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;

	private ModelController model;

	public ServerController() {
		try {
			serverSocket = new ServerSocket(9898);
			
			pool = Executors.newFixedThreadPool(10);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    

	public void runServer () throws ClassNotFoundException, IOException {
		
		Socket cliSocket = new Socket();
			
		try {
			cliSocket = serverSocket.accept();
			System.out.println("Server: incoming client connection...");
		} catch (IOException e) {
			System.out.println("Server: socket failed");
		}
		
		if (cliSocket.isConnected()) {
			socketIn = new ObjectInputStream(cliSocket.getInputStream());
			socketOut = new ObjectOutputStream (cliSocket.getOutputStream());
			model = new ModelController();
			model.setSrvControl(this);
			System.out.println("Server: a new client is connected");
		}
				
		//listening to client
		while (true) {
			String message = (String) socketIn.readObject();
			
			if (message.contains("UPDATE%")) {
				//todo: maybe update inv on server side from db first?
				socketOut.writeObject(this.model.getInventory());
				
			} else if (message.contains("SEARCHBYNAME%")) {
				message.replace("SEARCHBYNAME%", ""); //remove message header
				
				//get result from inventory
				this.model.getInventory().searchItem(message);
				
				//send back the updated inventory to the client
				socketOut.writeObject(this.model.getInventory());
				
			} else if (message.contains("SEARCHBYID%")) {
				message.replace("SEARCHBYID%", ""); //remove message header
				
				//get result from inventory
				this.model.getInventory().searchItem(Integer.parseInt(message));
				
				//send back the updated inventory to the client
				socketOut.writeObject(this.model.getInventory());
				
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
				
				//pass item to the inventory
				this.model.getInventory().addItem(itemType, itemId, itemName, itemQty, itemPrice, itemSupId, itemPower);
				
				//send back the updated inventory to the client
				socketOut.writeObject(this.model.getInventory());
				
			} else if (message.contains("REDUCEITEM%")) {
				message.replace("REDUCEITEM%", ""); //remove message header
				
				//parse incoming message to item related info
				String[] itemInfo = message.split("%");
				int itemId = Integer.parseInt(itemInfo[0]);
				int itemQty = Integer.parseInt(itemInfo[1]);
				
				//pass item to the inventory
				this.model.getInventory().removeItem(itemId, itemQty);
				
				//send back the updated inventory to the client
				socketOut.writeObject(this.model.getInventory());
				
			} else if (message.contains("EXIT%")) {
				cliSocket.close();
				break;
			}
			
			pool.execute(model);
		}
		
		pool.shutdown();
		
		System.out.println("Server: Server closed");
	}
	
	/**
	 * Main method, which instantiates the server
	 * 
	 * @param args system arguments
	 * @throws IOException uses standard input to read lines from the CLI, which can throw an exception.
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {

		ServerController myServer = new ServerController ();
		System.out.println("Server: Ready, awaiting client connections...");
		
		
		myServer.runServer();
	}
	
}

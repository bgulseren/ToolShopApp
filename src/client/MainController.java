package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import inventoryModel.*;

public class MainController {
	
	private Inventory inventory;
	private ClientController clCnt;
	private InvViewController invView;
	private CustViewController cusView;
	
	public MainController() throws IOException {
		//Connection controller
		this.clCnt = new ClientController(this);
		
		//Inventory gui and controller
		this.inventory = null;
		this.invView = new InvViewController(this);
		
		//Customer gui and controller
		//this.cusView = new CustViewController(this);
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
	
	public InvViewController getInvView() {
		return invView;
	}
	
	public CustViewController getCusView() {
		return cusView;
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
		theApp.getClientController().getInventoryFromSrv();
		
		System.out.println(theApp.getInventory().toString());
		
		//testing//
		
		System.out.println("Testing second update after first update");
		theApp.getClientController().getInventoryFromSrv();
		
		System.out.println(theApp.getInventory().toString());
	}
	
}


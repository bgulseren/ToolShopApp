package client;

import inventoryModel.*;

public class ClientModel {
	
	private Inventory inventory;
	private ClientController client;
	private InvViewController invView;
	private CustViewController cusView;
	
	public ClientModel(Inventory inv) {
		this.inventory = inv;
		this.invView = new InvViewController(this);
		this.cusView = new CustViewController(this);
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public ClientController getClient() {
		return client;
	}
	
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public void setClient(ClientController client) {
		this.client = client;
	}
	
	public InvViewController getInvView() {
		return invView;
	}
	
	public CustViewController getCusView() {
		return cusView;
	}
	
}


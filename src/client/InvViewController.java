package client;

public class InvViewController {
	
	private ClientModel model;
	
	public String itemType;
	public String itemName;
	public int itemId;
	public double itemPrice;
	public int itemQty;
	public int itemSupplierId;
	public int itemPower;
	
	private String request;
	
	public InvViewController(ClientModel model) {
		this.model = model;
		request = "NONE";
	}
	
	public void update() {
		request = "UPDATE";
	}
	
	public void searchItem(String itemName) {
		this.itemName = itemName;
		request = "SEARCHBYNAME";
	}
	
	public void searchItem(int itemId) {
		this.itemId = itemId;
		request = "SEARCHBYID";
	}
	
	public void addItem(String itemType,
						String itemName,
						int itemId,
						double itemPrice,
						int itemQty,
						int itemSupplierId,
						int itemPower) {
		
		this.itemType = itemType;
		this.itemName = itemName;
		this.itemId = itemId;
		this.itemPrice = itemPrice;
		this.itemQty = itemQty;
		this.itemSupplierId = itemSupplierId;
		this.itemPower = itemPower;

		request = "ADD";
	}
	
	public void removeItem(int itemId, int itemQty) {
		this.itemId = itemId;
		this.itemQty = itemQty;
		request = "REDUCE";
	}
	
	public String getRequest() {
		String out = request;
		clearRequest();
		return out;
	}
	
	public void clearRequest() {
		request = "NONE";
	}
	
	public void updateView() {
		
		itemName = this.model.getInventory().getSelectedItem().getName();
		//method to update inform the gui that it can use the new values to populate stuff
	}

}

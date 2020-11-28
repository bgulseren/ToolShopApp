package serverControl;

import java.io.Serializable;

import inventoryModel.*;

public class ModelController implements Runnable, Serializable {
	
	private static final long serialVersionUID = 1L;
	private Inventory inventory;
	private DatabaseController dbControl;
	private ServerController srvControl;
	
	public ModelController() {
		this.inventory = new Inventory();
		this.inventory.setController(this);
	}
	
	public void setSrvControl(ServerController srvControl) {
		this.srvControl = srvControl;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public ServerController getSrvControl() {
		return srvControl;
	}
	
	
	public DatabaseController getDb() {
		return dbControl;
	}
	
	/**
	 * updates the inventory from db (sets)
	 */
	public void getNewInventory() {
		
		this.inventory = new Inventory();
		this.inventory.setController(this);
		
		loadSuppliersTable(); // First get the suppliers table
		loadItemsTable(); // Get items table
		loadOrderTable();
		
	}
	
	
	public void loadSuppliersTable() {
		String[][] suppliersTable = dbControl.extractTable("suppliertable");
		
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
		
		for (int i = 0; i < itemsTable.length; i++) {
			
			int itemId = Integer.parseInt(itemsTable[i][0]);
			String itemName = itemsTable[i][1];
			int itemQty = Integer.parseInt(itemsTable[i][2]);
			double itemPrice = Double.parseDouble(itemsTable[i][3]);
			int itemSupId = Integer.parseInt(itemsTable[i][4]);
			String itemType = itemsTable[i][5];
			int itemPower = Integer.parseInt(itemsTable[i][6]);
			
			this.inventory.addItem(itemType, itemId, itemName, itemQty, itemPrice, itemSupId, itemPower);
		}
	}
	
	public void loadOrderTable() {
		String[][] ordersTable = dbControl.extractTable("orderlinetable");
		
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
	
	public int updateItem(String itemType, int itemId, String itemName, int itemQty, double itemPrice, int itemSupId, int itemPower) {
		
		int result = dbControl.updateToolRow(itemId, itemName, itemQty, itemPrice, itemType, itemPower);
		getNewInventory(); //get updated inventory
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
		inventory.reduceItem(itemId, itemQty);
		
		getNewInventory(); //get latest inventory info from db into model
	}
	

	@Override
	public void run() {
		
		inventory.addItem("electric", 0, "rasprberry pi", 5, 10, 0, 220);
		inventory.addItem("nonelectric", 1, "hammer and nail", 3, 12, 1, 2202);
	//	this.dbControl = new DatabaseController();
	}
	
	

}

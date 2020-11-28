package serverControl;

import inventoryModel.*;

public class ModelController implements Runnable {
	
	private Inventory inventory;
	private DatabaseController dbControl;
	private ServerController srvControl;
	
	public ModelController() {
		this.dbControl = new DatabaseController("inventorydb");
		this.inventory = new Inventory();
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
	

	@Override
	public void run() {
		

	}
	
	

}

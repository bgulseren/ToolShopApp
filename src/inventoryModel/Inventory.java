/**
 * Implementation of the Inventory Class for the Retail Store.
 * 
 * @author B.Gulseren
 * @version 1.0
 * @since October 9th, 2020
 */
package inventoryModel;
import java.io.Serializable;
import java.util.ArrayList;


public class Inventory implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** the array list of items belonging to this inventory*/
	private SupplierList suppliers;
	
	/** the array list of items belonging to this inventory*/
	private ArrayList<Item> items;
	
	/** the active order for the day */
	private Order order;
	
	/** the selected item (ie result of a search)*/
	private Item selectedItem;
	
	
	/**
	 * Default Inventory Class Constructor
	 * Initializes member variables
	 */
	public Inventory() {
		selectedItem = null;
		
		this.order = null;
		this.items = new ArrayList<Item>();
		this.suppliers = new SupplierList();
	}
	
	/**
	 * Overloaded Inventory Class Constructor
	 * Initializes member variables and gets the list of items from argument
	 * @param items the list of items to be passed into the constructor 
	 */
	public Inventory(ArrayList<Item> items) {
		this.items = items;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public SupplierList getSuppliers() {
		return suppliers;
	}
	
	/**
	 * Searches and returns the item in the inventory by its name
	 * 
	 * @param itemName the name of the item to look for
	 * @return the item which was found or null if it was not found
	 */
	public Item searchItem(String itemName) {
		for (int i = 0; i < items.size(); i++) {
			if (itemName.toLowerCase().contentEquals(items.get(i).getName().toLowerCase())) {
				selectedItem = items.get(i); //matching name found in the item list, return it.
				return selectedItem;
			}
		}
		selectedItem = null;
		return null; //item not found, return null
	}
	
	/**
	 * Searches and returns the item in the inventory by its id number
	 * 
	 * @param itemId the id number of the item to look for
	 * @return the item which was found or null if it was not found
	 */
	public Item searchItem(int itemId) {
		for (int i = 0; i < items.size(); i++) {
			if (itemId == items.get(i).getId()) {
				selectedItem = items.get(i); //matching name found in the item list, return it.
				return selectedItem;
			}
		}
		selectedItem = null;
		return null; //item not found, return null
	}
	
	/**
	 * Searches the inventory for an item by its id number and returns the quantity of the item.
	 * If not found or item quantity was 0, returns 0.
	 * 
	 * @param itemId id number of the item to search for.
	 * @return the quantity of the item found (or 0 if not found).
	 */
	public int checkQty(int itemId) {
		Item foundItem = this.searchItem(itemId);
		
		if (foundItem != null)
			return foundItem.getQty();
		
		return 0;
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
	public void addItem(String itemType, int itemId, String itemName, int itemQty, double itemPrice, int itemSupId, int itemPower) {
		Item foundItem = this.searchItem(itemId);
		
		if (foundItem == null) {
			//item id does not exist, therefore add it as a new item
			Item item;
			if (itemType.contentEquals("electric")) {
				item = new ElectricalItem();
				item.setPower(itemPower);
			} else {
				item = new RegularItem();
			}
			
			item.setId(itemId);
			item.setName(itemName);
			item.setQty(itemQty);
			item.setPrice(itemPrice);
			
			//assign supplier to the item
			item.setSupplier(suppliers.searchSupplier(itemSupId));
			
			this.items.add(item);
		} else {
			// item id already exists, therefore only add the quantity on top of the existing.
			// the other parameters will be ignored.
			
			foundItem.setQty(foundItem.getQty() + itemQty);
		}
		
	}
	
	/**
	 * Reduces quantity of the specified item from the inventory.
	 * If item reduction results an order line, then captures it into
	 * the order lines array of the inventory.
	 * 
	 * @param itemId id number of the item to reduce.
	 * @param itemQty quantity to reduce from the item.
	 */
	public Item reduceItem(int itemId, int itemQty) {
		Item foundItem = this.searchItem(itemId);
		
		if (foundItem != null) {
			
			boolean isNewOrderResulted = foundItem.reduceQty(itemQty); //reduce item quantity 
			
			if (isNewOrderResulted) {
				if (order == null) {
					order = new Order();
				}
				order.addOrderLine(foundItem.getOrderLine());
			}
		}
		
		return foundItem;
	}
	
	/**
	 * Creates a new order and assigns order lines from the items into the active order.
	 * Clears the order lines from the items as they are passed into an order.
	 */
	public void createOrder() {
		if (this.order == null) {
			this.order = new Order();
			for(Item i : items) {
				this.order.addOrderLine(i.getOrderLine());
				i.clearOrderLine();
			}
		}
	}
	
	/**
	 * Returns the active order for this inventory.
	 * 
	 * @return the active order
	 */
	public Order getOrder() {
		return this.order;
	}
	
	
	/**
	 * Outputs a string representation of this inventory.
	 * 
	 * @return String representation of the inventory.
	 */
	@Override
	public String toString() {
		String outString = new String();
		for (int i = 0; i < items.size(); i++) {
			outString = outString + items.get(i).toString() + "\n";
		}
		return outString;
	}

}

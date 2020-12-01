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
	 * If item reduction results an order line, then assigns it to the
	 * active order.
	 * 
	 * @param itemId id number of the item to reduce.
	 * @param itemQty quantity to reduce from the item.
	 */
	public Item reduceItem(int itemId, int itemQty) {
		Item foundItem = this.searchItem(itemId);
		
		if (foundItem != null) {
			
			boolean newOrderLine = foundItem.reduceQty(itemQty); //reduce item quantity 
			
			if (newOrderLine) {
				if (getOrder() == null) {
					this.order = new Order();
				}
				getOrder().addOrderLine(foundItem.getOrderLine());
			}
		}
		
		return foundItem;
	}

	/**
	 * Sets the order coming from externally (ie db).
	 */
	public void setOrder(int orderId, java.sql.Date orderDate) {
		this.order = new Order(orderId, orderDate);
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
	 * list all items in the inventory into a 2d string array
	 * 
	 */
	public String[][] listAllItems() {
		int rows = getItems().size();
		String[][] searchResult = new String[rows][7];
		
		for(int r = 0; r<rows; r++) {
			searchResult[r][0] = Integer.toString(getItems().get(r).getId());
			searchResult[r][1] = getItems().get(r).getName();
			searchResult[r][2] = Integer.toString(getItems().get(r).getQty());
			searchResult[r][3] = Double.toString(getItems().get(r).getPrice());
			searchResult[r][4] = getItems().get(r).getItemType();
			searchResult[r][5] = Integer.toString(getItems().get(r).getPower());
			searchResult[r][6] = Integer.toString(getItems().get(r).getSupplier().getId());
			
		}
		return searchResult;
	}
	
	/**
	 * list all items in the inventory which match the search criteria into a 2d string array
	 * 
	 */
	public String[][] search(String searchText, int selCol){
		String[][] table = listAllItems();
		int rows = 0;
		for(int r = 0; r < table.length; r++) {
			if(table[r][selCol].contentEquals(searchText)) {
				rows += 1;
			}
		}
		String[][] search = new String[rows][7];
		int row = 0;
		
		for(int r = 0; r < table.length; r++) {
			if(table[r][selCol].contentEquals(searchText)) {
				search[row][0] = table[r][0];
				search[row][1] = table[r][1];
				search[row][2] = table[r][2];
				search[row][3] = table[r][3];
				search[row][4] = table[r][4];
				search[row][5] = table[r][5];
				search[row][6] = table[r][6];
				row += 1;
			}
		}
		return search;
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

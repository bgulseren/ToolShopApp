/**
 * Implementation of the Supplier Class for the Retail Store.
 * 
 * @author B.Gulseren
 * @version 1.0
 * @since October 9th, 2020
 */
package inventoryModel;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Supplier implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** the type of the supplier, "L" local, "I" international */
	private String supplierType;

	/** the id number of the supplier */
	private int id;
	
	/** the name of the supplier */
	private String name;
	
	/** the address of the supplier */
	private String address;

	/** the contact person of the supplier */
	private String contact;
	
	/** the items related to the supplier */
	private ArrayList<Item> items;
	
	/**
	 * Supplier Class Constructor
	 *
	 * @param id id number to be set for this supplier
	 * @param name name to be set for this supplier
	 * @param address address to be set for this supplier
	 * @param contact contact name to be set for this supplier
	 */
	public Supplier(int id, String name, String address, String contact) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.contact = contact;
		this.items = new ArrayList<Item>();
	}

	/**
	 * Returns the address of the supplier
	 * 
	 * @return the address of the supplier
	 */
	public String getAddress() {
		return address;
	}
	
	/**
	 * Returns the contact name of the supplier
	 * 
	 * @return the contact name of the supplier
	 */
	public String getContact() {
		return contact;
	}
	
	/**
	 * Returns the id number of the supplier
	 * 
	 * @return the id number of the supplier
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the name of the supplier
	 * 
	 * @return the name of the supplier
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the list of items related to this supplier
	 * 
	 * @return items list
	 */
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public String getSupplierType() {
		return supplierType;
	}
	
	public void setSupplierType(String supplierType) {
		this.supplierType = supplierType;
	}
	
	/**
	 * Adds item to this supplier
	 * 
	 */
	public void appendItem(Item item) {
		items.add(item);
	}
	
	/**
	 * Removes all items associated with this supplier
	 * 
	 */
	public void removeItems() {
		items.clear();
	}
	
	
	/**
	 * Outputs a string representation of this supplier.
	 * 
	 * @return String representation of the supplier.
	 */
	@Override
	public String toString() {
		String output = "ID: " + getId() + " Name: " + getName() + " Address: " + getAddress() + " Contact: " + getContact(); 
		return output;
	}
	
	
}

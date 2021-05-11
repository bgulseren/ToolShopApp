/**
 * Implementation of the Order Line Class for the Retail Store.
 * 
 * @author B.Gulseren
 * @version 1.0
 * @since October 9th, 2020
 */
package inventoryModel;

import java.io.Serializable;

public class OrderLine implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** the id number of the item to be ordered */
	private int id;
	
	/** the name of the item to be ordered */
	private String name;
	
	/** the quantity of the item to be ordered */
	private int qty;

	/** the supplier Id of the item to be ordered */
	private int supplierId;
	
	/** the supplier name of the item to be ordered */
	private String supplierName;

	/**
	 * Returns the id of the item to be ordered.
	 * 
	 * @return the id of the item to be ordered.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the name of the item to be ordered.
	 * 
	 * @return the name of the item to be ordered.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the quantity of the item to be ordered.
	 * 
	 * @return the quantity of the item to be ordered.
	 */
	public int getQty() {
		return qty;
	}
	
	/**
	 * Returns the supplier id of the item to be ordered.
	 * 
	 * @return the supplier id of the item to be ordered.
	 */
	public int getSupplierId() {
		return supplierId;
	}
	
	/**
	 * Returns the supplier name of the item to be ordered.
	 * 
	 * @return the supplier name of the item to be ordered.
	 */
	public String getSupplierName() {
		return supplierName;
	}
	
	/**
	 * Sets the id for the item.
	 * 
	 * @param id id to be set for the item.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Sets the name for the item.
	 * 
	 * @param name name to be set for the item.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the quantity for the item.
	 * 
	 * @param qty quantity to be set for the item.
	 */
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	/**
	 * Sets the supplier Id for the item.
	 * 
	 * @param supplierId supplier Id to be set for the item.
	 */
	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}
	
	/**
	 * Sets the supplier name for the item.
	 * 
	 * @param supplierName supplier name to be set for the item.
	 */
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
}

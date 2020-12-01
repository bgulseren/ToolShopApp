/**
 * Implementation of the Item Class for the Retail Store.
 * 
 * @author B.Gulseren
 * @version 1.0
 * @since October 9th, 2020
 */
package inventoryModel;

import java.io.Serializable;

public abstract class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** the type of the item, "electric" or "nonelectric" */
	private String itemType;
	
	/** the id number of the item */
	private int id;
	
	/** the quantity of the item */
	private int qty;

	/** the unit price of the item */
	private double price;
	
	/** the name of the item */
	private String name;

	/** the active orderLine for the item */
	private OrderLine orderLine;
	
	/** the supplier of the item */
	private Supplier supplier;
	
	protected abstract void setPower(int itemPower);
	
	/**
	 * Item Class Constructor
	 *
	 */
	public Item() {
		this.orderLine = null;
	}
	
	/**
	 * Returns the id number of the item.
	 * 
	 * @return the id number of the item.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the name of the item.
	 * 
	 * @return the name of the item.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the price of the item.
	 * 
	 * @return the price of the item.
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Returns the quantity of the item.
	 * 
	 * @return the quantity of the item.
	 */
	public int getQty() {
		return qty;
	}
	
	/**
	 * Returns the supplier of the item.
	 * 
	 * @return the supplier of the item.
	 */
	public Supplier getSupplier() {
		return supplier;
	}
	
	
	public String getItemType() {
		return itemType;
	}
	
	public int getPower() {
		return 0;
	}
	
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	
	/**
	 * Sets the id number for the item.
	 * 
	 * @param id id number to be set for the item.
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
	 * Sets the price for the item.
	 * 
	 * @param price price to be set for the item.
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	
	/**
	 * Sets the quantity for the item.
	 * 
	 * @param qty quantity to be set for the item. Cannot be a negative integer.
	 */
	public void setQty(int qty) {
		if (qty >= 0)
			this.qty = qty;
	}
	
	/**
	 * Sets the supplier for the item.
	 * 
	 * @param supplier supplier to be assigned for the item.
	 */
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
		this.supplier.appendItem(this);
	}
	
	/**
	 * Returns the order line for the item.
	 * 
	 * @return order line of the item
	 */
	public OrderLine getOrderLine() {
		return orderLine;
	}
	
	public void createOrderLine(int qty) {
		orderLine = new OrderLine();
		
		orderLine.setId(this.getId());
		orderLine.setName(this.getName());
		orderLine.setSupplierId(this.supplier.getId());
		orderLine.setSupplierName(this.supplier.getName());
		orderLine.setQty(qty);
	}
	
	/**
	 * Sets the order line for this item
	 * 
	 */
	public void setOrderLine(OrderLine ol) {
		this.orderLine = ol;
	}
	
	/**
	 * Reduces the quantity for the item by amount entered.
	 * As a result of reduction, if item quantity drops below 40,
	 * then generates an order line for the item.
	 * 
	 * @param qty quantity to be reduced for the item.
	 * @return result of a creation of an orderline (true, reduction caused a new orderline, false, no orderline).
	 */
	public boolean reduceQty(int qty) {
		if (this.qty >= qty && qty > 0) {
			//do not allow reducing item qty below 0. 
			this.qty = this.qty - qty;
		}
		
		if (this.qty < 40 && orderLine == null) {
			//The default quantity ordered by each item = 50 – number of existing items
			createOrderLine(50 - this.getQty());
			return true;
		}
		return false;
	}
	
	/**
	 * Outputs a string representation of this item object.
	 * 
	 * @return String representation of the item.
	 */
	@Override
	public String toString() {
		String output = getItemType() + " " +
						getId() + " " +
						getName() + " " +
						getQty() + " " +
						getPrice() + " " +
						getSupplier().getId();
		return output;
	}

	
}

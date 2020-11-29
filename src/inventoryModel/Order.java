package inventoryModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Implementation of the Order Class for the Retail Store.
 * 
 * @author B.Gulseren
 * @version 1.0
 * @since October 9th, 2020
 */

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** the id number of the order */
	private int id;
	
	/** the date stamp of the order */
	private java.sql.Date date;
	
	/** the order lines associated with the order */
	private ArrayList<OrderLine> orderLines;
	
	/**
	 * Order Class Constructor
	 * @param ol an ArrayList of OrderLine can be passed to set the member order lines for this object.
	 */
	public Order() {
		
		this.orderLines = new ArrayList<OrderLine>();
		
		Random random = new Random();
		this.id = random.nextInt(99999); //create a 5 digit random number
		
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(today.getTime());
		
		this.date = sqlDate; //add today's time stamp to the order
		
	}
	
	public Order(int orderId, java.sql.Date orderDate) {
		this.id = orderId;
		this.date = orderDate;
	}
	
	
	/**
	 * Adds the order line to the order..
	 * @param orderLine OrderLine which will be added to the list.
	 */
	public void addOrderLine(OrderLine orderLine) {
		this.orderLines.add(orderLine);
	}
	
	/**
	 * Sets the order lines for the order.
	 * @param orderLines an ArrayList of OrderLine which will be set as the member order lines for this object.
	 */
	public void setOrderLines(ArrayList<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	
	/**
	 * Returns the order lines for this order.
	 * @return orderLines of this object.
	 */
	public ArrayList<OrderLine> getOrderLines() {
		return this.orderLines;
	}
	
	/**
	 * Returns the id number for this order.
	 * @return id number for this order.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the date for this order.
	 * @return date for this order.
	 */
	public java.sql.Date getDate() {
		return date;
	}
	
	/**
	 * Clears the order lines from order when order is submit.
	 */
	public void clearOrderLines() {
		this.orderLines = new ArrayList<OrderLine>();
	}


	/**
	 * Outputs a string representation of this order.
	 * 
	 * @return String representation of the order.
	 */
	@Override
	public String toString() {
		String st = "";
		if (this.getOrderLines().size() == 0) {
			st += "No order lines are present.";
			return st;
		}
		
		st += "*********************************\n";
	    	
		st += "ORDER ID: " + getId() + "\n";
	    st += "Date Ordered: " + getDate().toString() + "\n";
	    st += "\n";
	    
	    //add order-lines by iterating over them
		for (OrderLine ol : this.getOrderLines()) {
			st += "Item description: " + ol.getName() + "\n";
			st += "Amount ordered: " + ol.getQty() + "\n";
			st += "Supplier: " + ol.getSupplierName() + "\n";
			st += "\n";
		}
		st += "*********************************\n";
		return st;
	}

}

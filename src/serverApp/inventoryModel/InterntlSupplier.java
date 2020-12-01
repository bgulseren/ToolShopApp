package inventoryModel;

import java.io.Serializable;

public class InterntlSupplier extends Supplier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int tax;
	
	/**
	 * InterntlSupplier Class Constructor
	 *
	 * @param id id number to be set for this supplier
	 * @param name name to be set for this supplier
	 * @param address address to be set for this supplier
	 * @param contact contact name to be set for this supplier
	 */
	public InterntlSupplier(int id, String name, String address, String contact, int tax) {
		super(id, name, address, contact);
		this.setSupplierType("I");
		this.setTax(tax);
	}
	
	public void setTax(int tax) {
		this.tax = tax;
	}
	
	public int getTax() {
		return tax;
	}
}

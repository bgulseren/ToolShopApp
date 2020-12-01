package inventoryModel;

import java.io.Serializable;

public class DomesticSupplier extends Supplier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * DomesticSupplier Class Constructor
	 *
	 * @param id id number to be set for this supplier
	 * @param name name to be set for this supplier
	 * @param address address to be set for this supplier
	 * @param contact contact name to be set for this supplier
	 */
	public DomesticSupplier(int id, String name, String address, String contact) {
		super(id, name, address, contact);
		this.setSupplierType("L");
	}
	
}

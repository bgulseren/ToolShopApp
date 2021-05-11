package customerModel;

import java.io.Serializable;

public class CommercialCustomer extends Customer implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CommercialCustomer(int id, String fName, String lName, String address, String postalCode, String phoneNo, String type) {
		super(id, fName, lName, address, postalCode, phoneNo, type);
	}
	
}

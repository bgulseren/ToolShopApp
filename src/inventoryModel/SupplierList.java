package inventoryModel;

import java.io.Serializable;
import java.util.ArrayList;

/* This class contains methods to print all supplier information to console
 * 
 * Edited by:  Khaled Behairy
 * @since 10/13/2020
*/

public class SupplierList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList <Supplier> suppliers;
//	private Supplier supplier;
	
	public SupplierList (ArrayList <Supplier> supplierList) {
		this.setSupplierList(supplierList);
	}

	public int getNumberOfSuppliers() {
		return suppliers.size();
	}
	
	public ArrayList <Supplier> getSupplierList() {
		return suppliers;
	}

	public void setSupplierList(ArrayList <Supplier> supplierList) {
		this.suppliers = supplierList;
	}
	
	/**
	 * Searches and returns the supplier among the suppliers list by its name.
	 * 
	 * @param name the name of the supplier to look for
	 * @return the supplier which was found or null if it was not found
	 */
	public Supplier searchSupplier(String name) {
		for (int i = 0; i < suppliers.size(); i++) {
			if (name.toLowerCase() == suppliers.get(i).getName().toLowerCase())
				return suppliers.get(i); //matching name found in the item list, return it.
		}
		return null; //item not found, return null
	}
	
	/**
	 * Searches and returns the supplier among the suppliers list by its id number.
	 * 
	 * @param idNum the id number of the supplier to look for
	 * @return the supplier which was found or null if it was not found
	 */
	public Supplier searchSupplier(int idNum) {
		for (int i = 0; i < suppliers.size(); i++) {
			if (idNum == suppliers.get(i).getId()) {
				return suppliers.get(i); //matching id number found in the supplier list
			}
		}
		return null; //supplier not found, return null
	}
	
	
	/**
	 * Searches the suppliers list and adds a supplier to it if no supplier with specified id found.
	 * If supplier already exists, then does nothing (prints a msg to console).
	 * 
	 * @param id id number of the supplier to add.
	 * @param name name of the supplier to add.
	 * @param address address of the supplier to add.
	 * @param contact contact of the item to add.
	 * @param tax tax rate of the supplier (applies to International suppliers)
	 */
	public void addSupplier(int id, String type, String name, String address, String contact, int tax) {
		Supplier foundSupplier = this.searchSupplier(id);
		
		if (foundSupplier == null) {
			//supplier id does not exist, therefore we can add it as a new supplier
			Supplier sup;
			if (type.contentEquals("L")) {
				sup = new DomesticSupplier(id, name, address, contact);
			} else {
				sup = new InterntlSupplier(id, name, address, contact, tax);
			}
			this.suppliers.add(sup);
		} else {
			System.out.println("Supplier already exists, cannot add!");
		}
		
	}
	
	public void removeItemsFromSuppliers() {
		for (int i = 0; i < suppliers.size(); i++) {
			suppliers.get(i).removeItems();
		}
	}
	
}


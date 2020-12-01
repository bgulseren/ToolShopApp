package customerModel;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Customer> customerList;
	
	
	/**
	 * construct CustomerList class
	 * @Params: customerListFromDB - 2D String array from database export
	 * calls the readCustomerFromDB() method which reads the 2D String array and returns an arrayList of customers
	 * Sets customerList arrayList with customer objects created from database customertable
	 */

	public CustomerList(String[][] customerListFromDB) {
		this.customerList = readCustomersFromDB(customerListFromDB);
	}

	
	/**
	 * read customers data from database
	 * @Params: customerListFromDB - 2D String array of customer data
	 * 			this 2D String array can be obtained using the database controller extractTable() method
	 * Reads String data and creates a ResidentialCustomer or CommercialCustomer based on the customer type
	 * adds the created customer to the customerList arrayList
	 */
	public ArrayList<Customer> readCustomersFromDB(String[][] customerListFromDB){
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		int rows = customerListFromDB.length;
		for(int r = 0; r < rows; r++) {
			Customer newCustomer = null;
			int ID = Integer.parseInt(customerListFromDB[r][0]);
			String type = customerListFromDB[r][1];
			String fName = customerListFromDB[r][2];
			String lName = customerListFromDB[r][3];
			String address = customerListFromDB[r][4];
			String postalCode = customerListFromDB[r][5];
			String phoneNo = customerListFromDB[r][6];
			
			if(type.contentEquals("R"))
				newCustomer = new ResidentialCustomer(ID,fName,lName,address,postalCode,phoneNo,type);
			else if(type.contentEquals("C"))
				newCustomer = new CommercialCustomer(ID,fName,lName,address,postalCode,phoneNo,type);
			customerList.add(newCustomer);
		}
		return customerList;
	}
	
	/**
	 * lists all customers
	 */
	public String[][] listAllCustomers() {
		int rows = customerList.size();
		String[][] searchResult = new String[rows][7];
		for(int r = 0; r<rows; r++) {
			searchResult[r][0] = Integer.toString(customerList.get(r).getID());
			searchResult[r][1] = customerList.get(r).getType();
			searchResult[r][2] = customerList.get(r).getFirstName();
			searchResult[r][3] = customerList.get(r).getLastName();
			searchResult[r][4] = customerList.get(r).getAddress();
			searchResult[r][5] = customerList.get(r).getPostalCode();
			searchResult[r][6] = customerList.get(r).getPhoneNo();
		}
		return searchResult;
	}
	
	/**
	 * Search customers by key and returns a table of matching results
	 * @param key the key to be searched
	 * @param r	the column number to be searched on
	 * @return 2d array of strings which contain results matching the search criteria
	 */
	public String[][] search(String key, int colNum) {
		int rows = 0;
		String[][] table = listAllCustomers();
		for(int r = 0; r < table.length; r++) {
			if(table[r][colNum].contentEquals(key)) {
				rows += 1;
			}
		}
		String[][] results = new String[rows][7];
		int row = 0;
		
		for(int r = 0; r < table.length; r++) {
			if(table[r][colNum].contentEquals(key)) {
				results[row][0] = table[r][0];
				results[row][1] = table[r][1];
				results[row][2] = table[r][2];
				results[row][3] = table[r][3];
				results[row][4] = table[r][4];
				results[row][5] = table[r][5];
				results[row][6] = table[r][6];
				row += 1;
			}
		}
		return results;
	}
	
	
	@Override
	public String toString() {
		String outString = new String();
		for (int i = 0; i < customerList.size(); i++) {
			outString = outString + customerList.get(i).toString() + "\n";
		}
		return outString;
	}
	
}

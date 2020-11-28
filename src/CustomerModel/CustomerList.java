package customerModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class CustomerList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Customer> customerList;
	private String[][] searchResult;
	
	
	/*
	 * construct CustomerList class
	 * @Params: customerListFromDB - 2D String array from database export
	 * calls the readCustomerFromDB() method which reads the 2D String array and returns an arrayList of customers
	 * Sets customerList arrayList with customer objects created from database customertable
	 */

	public CustomerList(String[][] customerListFromDB) {
		this.customerList = readCustomersFromDB(customerListFromDB);
	}

	
	/*
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
	
	/*
	 * list all customers
	 */
	public String[][] listAllCustomers() {
		int rows = customerList.size();
		String[][] searchResult = new String[rows][7];
		for(int r = 0; r<rows; r++) {
			searchResult[r][0] = Integer.toString(customerList.get(r).getID());
			searchResult[r][1] = customerList.get(r).getFirstName();
			searchResult[r][2] = customerList.get(r).getLastName();
			searchResult[r][3] = customerList.get(r).getAddress();
			searchResult[r][4] = customerList.get(r).getPostalCode();
			searchResult[r][5] = customerList.get(r).getPhoneNo();
			searchResult[r][6] = customerList.get(r).getType();
		}
		System.out.println(Arrays.deepToString(searchResult));
		return searchResult;
	}
	
	/*
	 * search customers by last name
	 * @Params: lastName - lastName of customer you are searching for
	 */
	public String[][] searchByName(String lastName) {
		int rows = 0;
		for(Customer c : customerList) {
			if(c.getLastName().contentEquals(lastName)) {
				rows+=1;
			}	
		}
		String[][] searchResult = new String[rows][7];
		int r = 0;
		for(Customer c : customerList) {
			if(c.getLastName().contentEquals(lastName)) {
				searchResult[r][0] = Integer.toString(c.getID());
				searchResult[r][1] = c.getFirstName();
				searchResult[r][2] = c.getLastName();
				searchResult[r][3] = c.getAddress();
				searchResult[r][4] = c.getPostalCode();
				searchResult[r][5] = c.getPhoneNo();
				searchResult[r][6] = c.getType();
				r+=1;
			}	
		}
		System.out.println(Arrays.deepToString(searchResult));
		return searchResult;
	}
	
	/*
	 * search customers by ID
	 */
	public String[][] searchByID(String ID) {
		int rows = 0;
		for(Customer c : customerList) {
			if(Integer.toString(c.getID()).contentEquals(ID)) {
				rows+=1;
			}	
		}
		String[][] searchResult = new String[rows][7];
		int r = 0;
		for(Customer c : customerList) {
			if(Integer.toString(c.getID()).contentEquals(ID)) {
				searchResult[r][0] = Integer.toString(c.getID());
				searchResult[r][1] = c.getFirstName();
				searchResult[r][2] = c.getLastName();
				searchResult[r][3] = c.getAddress();
				searchResult[r][4] = c.getPostalCode();
				searchResult[r][5] = c.getPhoneNo();
				searchResult[r][6] = c.getType();
				r+=1;
			}	
		}
		System.out.println(Arrays.deepToString(searchResult));
		return searchResult;
	}
	
	/*
	 * search customers by type
	 */
	public String[][] searchByType(String type) {
		int rows = 0;
		for(Customer c : customerList) {
			if(c.getType().contentEquals(type)) {
				rows+=1;
			}	
		}
		String[][] searchResult = new String[rows][7];
		int r = 0;
		for(Customer c : customerList) {
			if(c.getType().contentEquals(type))  {
				searchResult[r][0] = Integer.toString(c.getID());
				searchResult[r][1] = c.getFirstName();
				searchResult[r][2] = c.getLastName();
				searchResult[r][3] = c.getAddress();
				searchResult[r][4] = c.getPostalCode();
				searchResult[r][5] = c.getPhoneNo();
				searchResult[r][6] = c.getType();
				r+=1;
			}	
		}
		System.out.println(Arrays.deepToString(searchResult));
		return searchResult;
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

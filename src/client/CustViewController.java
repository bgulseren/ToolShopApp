package client;

//import CustomerVIew from Views package
import views.CustomerView;
import java.awt.EventQueue;

public class CustViewController {
	
//	private ClientModel model;
	
	
	private CustomerView cv;
	private String request;
	
	private String id;
	private String fName;
	private String lName;
	private String address;
	private String postalCode;
	private String phoneNo;
	private String type;
	private String searchKey;
	
	// true = fail
	// false = success
	private boolean error;
	
	private String errorMessage;

	private String[][] searchResult;

	
	String[][] searchQuery = {
		{"1001", "John", "Bones", "123 Long St Calgary", "T2M1H8", "403-234-9987", "Residential"},
		{"1002", "Ahmed", "Zaki", "456 Short St Calgary", "T2M1A4", "403-827-1231", "Commercial"}
	};
	
	public void displayCustomerView() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					cv = new CustomerView();
					cv.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public void searchCustomer(String searchkey, String request) {
		this.setSearchKey(searchkey);
		this.request = request;
		updateView(searchQuery);
	}
	
	public void editCustomer(String id, String fName, String lName, String address, String postalCode, String phoneNo, String type) {
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNo = phoneNo;
		this.type = type;
		System.out.println(fName + lName + address + postalCode + phoneNo +  type);
		request = "UPDATE";
	}
	
	public void deleteCustomer(String id) {
		this.id = id;
		System.out.println(id);
		request = "DELETE";
	}
	
	public void addCustomer(String fName, String lName, String address, String postalCode, String phoneNo, String type) {
		this.fName = fName;
		this.lName = lName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNo = phoneNo;
		this.type = type;
		System.out.println(fName + lName + address + postalCode + phoneNo +  type);
		request = "ADD";
	}

	public String getRequest() {
		String out = request;
		clearRequest();
		return out;
	}
	
	public void clearRequest() {
		request = "NONE";
	}
	
	public String[][] getSearchResult(){
		return searchResult;
	}
	
	/*
	 * called by ModelController to update customerList 2D String up search results
	 */
	public void updateView(String[][] searchResult) {
		this.searchResult = searchResult;
	}
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getfName() {
		return fName;
	}


	public void setfName(String fName) {
		this.fName = fName;
	}


	public String getlName() {
		return lName;
	}


	public void setlName(String lName) {
		this.lName = lName;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public String getPhoneNo() {
		return phoneNo;
	}


	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public boolean isError() {
		return error;
	}


	public void setError(boolean error) {
		this.error = error;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

	public String getSearchKey() {
		return searchKey;
	}


	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}	
	
	//***MAIN FOR TESTING***///
	public static void main(String[] args) {
		
		CustViewController app = new CustViewController();
		app.displayCustomerView();
	}

	
}

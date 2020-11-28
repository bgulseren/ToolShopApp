package CustomerModel;

abstract public class Customer {
	
	private int ID;
	private String firstName;
	private String lastName;
	private String address;
	private String postalCode;
	private String phoneNo;
	private String type;
	
	
	public Customer(int id, String fName, String lName, String address, String postalCode, String phoneNo, String type) {
		this.ID = id;
		this.firstName = fName;
		this.lastName = lName;
		this.address = address;
		this.postalCode = postalCode;
		this.phoneNo = phoneNo;
		this.type = type;
	}
		
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
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

}

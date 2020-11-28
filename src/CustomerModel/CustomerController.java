package customerModel;

public class CustomerController {
	
	private CustomerList cl;
	
	public String[][] searchByName(String lastName){
		String[][] searchResult = cl.searchByName(lastName);
		return searchResult;
	}
	
	public String[][] searchByID(String ID){
		String[][] searchResult = cl.searchByID(ID);
		return searchResult;
	}
	
	public String[][] searchByType(String type){
		String[][] searchResult = cl.searchByType(type);
		return searchResult;
	}

}

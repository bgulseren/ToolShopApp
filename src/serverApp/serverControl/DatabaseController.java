package serverControl;

import java.sql.SQLException;

import database.Database;

public class DatabaseController {

	//reference to Database entity 
	private Database db;
	
	private int message;
	
	//ctor of controller constructs a Database entity
	//(ctor of Database connects to provided database)
	public DatabaseController(String databaseName) {
		db = new Database(databaseName);
	}
	
	//extract given table to a 2D array String
	public String[][] extractTable(String tableName) {
		String[][] tableContent;
		tableContent = db.extractTable(tableName);
		
		return tableContent;
	}
	
	//add new row to a table
	public int addRow(String tableName, String[] newRow) {
		try {
			message = db.addRow(tableName, newRow);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return message;
	}
	
	//delete customer or tool from customertable/tooltable
	public int deleteRow(String tableName, int id) {
		message = db.deleteRow(tableName, id);
		return message;
	}

	//update tool quantity
	public int updateQuant(String tableName, int toolID, int newQuantity) {
		message = db.updateQuant(tableName, toolID, newQuantity);
		return message;
	}
	
	//update customer in customertable	
	public int updateCustomerRow(String[] customerInfo) {
		int id = Integer.parseInt(customerInfo[0]);
		String cType = customerInfo[1];
		String fName = customerInfo[2];
		String lName = customerInfo[3];
		String address = customerInfo[4];
		String postalCode = customerInfo[5];
		String phoneNo = customerInfo[6];
		message = db.updateCustomerRow(id, fName, lName, address, postalCode, phoneNo, cType);
		return message;
	}
	
	//update tool in tooltable
	public int updateToolRow(int id, String toolName, int quantity, double price, String toolType, int power) {
		message = db.updateToolRow(id, toolName, quantity, price, toolType, power);
		return message;
	}
	
	//close connection
	public void closeDBConnection() {
		try {
			db.jdbc_connection.close();
		} 
		catch (SQLException e) { e.printStackTrace(); }
		finally
		{
			System.out.println("Connection to DB closed");
		}
	}
	
}
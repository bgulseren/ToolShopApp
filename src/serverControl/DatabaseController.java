package serverControl;

import java.sql.SQLException;

import database.Database;

public class DatabaseController {

	//reference to Database entity 
	private Database db;
	
	private int message;
	
	//ctor of controller constructs a Database entity (ctor of Database connects to provided database)
	public DatabaseController(String databaseName) {
		db = new Database(databaseName);
	}
	
	//extract given table to a 2D array String
	public String[][] extractTable(String tableName) {
		String[][] tableContent;
		tableContent = db.extractTable(tableName);
		
		System.out.println("Extracted db table: " + tableName);
		return tableContent;
	}
	
	//add new row to a table
	public int addRow(String tableName, String[] newRow) {
		message = db.addRow(tableName, newRow);
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
	public int updateCustomerRow(int id, String fName, String lName, String address, String postalCode, String phoneNo, String cType) {
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
			System.out.println("\nConnection Terminated");
		}
	}
	
}
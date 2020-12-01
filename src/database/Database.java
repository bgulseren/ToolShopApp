package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database {

	public Connection jdbc_connection;
	public PreparedStatement preparedStatement;
	public String databaseName;
	public int message;

	public String connectionInfo = "jdbc:mysql://localhost:3306/",  
			  login          = "root",
			  password       = "fE3tJQz#^XBQ9E";
	
	
	public Database(String databaseName){
		this.databaseName = databaseName;
		connectDB();
	}
	
	/*
	 * Connect to database
	 */
	
	public void connectDB() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			jdbc_connection = DriverManager.getConnection(connectionInfo, login, password);
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	/*
	 * extract table contents into 2D String array
	 */
	
	public String[][] extractTable(String tableName ) {
		try {
			String sql_selectDB = "USE " + databaseName;
			String sql = "SELECT * FROM " + tableName;
			PreparedStatement pStat_selectDB = jdbc_connection.prepareStatement(sql_selectDB);
			PreparedStatement pStat = jdbc_connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pStat_selectDB.execute();
			ResultSet tools = pStat.executeQuery(sql);
			
			//go to last row and get the row count
			tools.last();
			int rows = tools.getRow();
			
			if(rows == 0) {
				System.err.println(tableName + " is empty");
				return null;
			}
			
			//get column count
			int cols = tools.getMetaData().getColumnCount();
			
			//create 2D array of strings using number of rows and 5 columns
			String[][] tableContent = new String[rows][cols];

			//go back to first row of table
			tools.first();
			
			//append first row
			for(int c = 0; c < cols; c++) {
				tableContent[0][c] = tools.getString(c+1);
			}
			
			//iterate through rest of rows of table
			int i = 0;
			while(tools.next()){
				i += 1;
				//iterate through columns of row
				for(int c = 0; c < cols; c++) {
					//append element to 2d array
					tableContent[i][c] = tools.getString(c+1);
				}
			}
			tools.close();
			return tableContent;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}	
	
	/*
	 * add new row to database table - works for any table
	 */
	
	public int addRow(String tableName, String[] newRow) throws SQLException {
		String sql_selectDB = "USE " + databaseName;
		
		message = 0;
		int cols = newRow.length;
		String sql = "INSERT INTO " + tableName +
				 " VALUES ( " + newRow[0] + ", ";
		
		for (int c = 1; c < cols - 1; c++) {
			sql += "'" + newRow[c] + "', ";
		}
		
		sql += "'" + newRow[cols - 1] + "');";

		try{
			PreparedStatement pStat_selectDB = jdbc_connection.prepareStatement(sql_selectDB);
			PreparedStatement pStat = jdbc_connection.prepareStatement(sql);
			pStat_selectDB.execute();
			pStat.executeUpdate(sql);
		}
		catch(SQLException e){
			message = 1;
		}
		return message;
	}
	
	/*
	 * update quantity for specified tool in database table
	 */
	
	public int updateQuant(String tableName, int toolID, int newQuantity) {
		message = 0;
		try {
			
			String query = "UPDATE `inventorydb`.`" + tableName + 
						   "` SET `QUANTITY` = '" + newQuantity +
						   "' WHERE (`TOOLID` = '" + toolID + "')";
			
			PreparedStatement pStat = jdbc_connection.prepareStatement(query);
			pStat.executeUpdate();
		} catch(SQLException e) {
			message = 1;
			System.err.println("ERROR: cant find specified item");
			return message;
		}
		return message;
	}
	
	/*
	 * edit existing customer row in customertable
	 */
	public int updateCustomerRow(int id, String fName, String lName, String address, String postalCode, String phoneNo, String cType) {
		message = 0;
		try {
			String sql_selectDB = "USE " + databaseName;
			PreparedStatement pStat_selectDB = jdbc_connection.prepareStatement(sql_selectDB);
			pStat_selectDB.execute();
			
			String updateQuery = "UPDATE customertable SET CUSTOMERTYPE=?, FNAME=?, LNAME=?, ADDRESS=?, POSTALCODE=?, PHONENO=? WHERE ID=?";
			
			PreparedStatement pStat = jdbc_connection.prepareStatement(updateQuery);

			pStat.setString(1, cType);
			pStat.setString(2, fName);
			pStat.setString(3, lName);
			pStat.setString(4, address);
			pStat.setString(5, postalCode);
			pStat.setString(6, phoneNo);
			pStat.setInt(7, id);
			pStat.executeUpdate();
		
		} catch(SQLException e) {
			message = 1;
			System.err.println("ERROR");
			e.printStackTrace();
		}
		
		return message;
	}
	
	/*
	 * edit existing tool row in tooltable
	 */
	public int updateToolRow(int id, String toolName, int quantity, double price, String toolType, int power) {
		message = 0;
		try {
			
			String sql_selectDB = "USE " + databaseName;
			PreparedStatement pStat_selectDB = jdbc_connection.prepareStatement(sql_selectDB);
			pStat_selectDB.execute();
			
			String updateQuery = "UPDATE tooltable SET TOOLNAME=?, QUANTITY=?, PRICE=?, TOOLTYPE=?, POWER=? WHERE TOOLID=?";
			
			PreparedStatement pStat = jdbc_connection.prepareStatement(updateQuery);

			pStat.setString(1, toolName);
			pStat.setInt(2, quantity);
			pStat.setDouble(3, price);
			pStat.setString(4, toolType);
			pStat.setInt(5, power);
			pStat.setInt(6, id);
			pStat.executeUpdate();
		
		} catch(SQLException e) {
			message = 1;
			System.err.println("ERROR");
			e.printStackTrace();
		}
		
		return message;
	}
	
	/*
	 * delete row from either customertable or tooltable
	 */
	
	public int deleteRow(String tableName, int id) {
		message = 0;
		String updateQuery="";
		try {		
			
			if(tableName.contentEquals("tooltable")) {
				updateQuery = "DELETE FROM tooltable WHERE TOOLID=?";
			}
			else if(tableName.contentEquals("customertable")) {
				updateQuery = "DELETE FROM customertable WHERE ID=?";
			}
			
			String sql_selectDB = "USE " + databaseName;
			PreparedStatement pStat_selectDB = jdbc_connection.prepareStatement(sql_selectDB);
			pStat_selectDB.execute();
			
			PreparedStatement pStat = jdbc_connection.prepareStatement(updateQuery);

			pStat.setInt(1, id);
			pStat.executeUpdate();
		
		} catch(SQLException e) {
			message = 1;
			System.err.println("ERROR");
			e.printStackTrace();
		}
		
		return message;
	}
		
}
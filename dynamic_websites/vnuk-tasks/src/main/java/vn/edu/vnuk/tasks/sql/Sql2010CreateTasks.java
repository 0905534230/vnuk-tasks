package vn.edu.vnuk.tasks.sql;

import java.sql.Connection;
import java.sql.SQLException;

public class Sql2010CreateTasks {
	private final Connection connection;
	private final String sqlQuery;
	
	public Sql2010CreateTasks(Connection connection) {
		this.connection = connection;
		
		this.sqlQuery = "CREATE TABLE IF NOT EXISTS tasks ("
				+ 	"id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
				+ 	"description VARCHAR(255), "
				+ 	"is_complete BOOLEAN DEFAULT 0, "
				+ 	"date_of_completion DATE "
				+ ") CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
			;
	}
	
	public void run() throws SQLException {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(">  Sql2010CreateTasks started");
		
		try {
	        connection.prepareStatement(sqlQuery).execute();
	        System.out.println("   TABLE \'tasks\' successfully created");
		
		}
		
		catch (Exception e) {
	        e.printStackTrace();
	        connection.close();
		}
		
		finally {
			System.out.println("<  Sql2010CreateTasks ended");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("");
		}
			
	}
}

package com.quizapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
	public static final String DB_URL ="jdbc:mysql://localhost:3306/quizdb";
	public static final String DB_USER = "root";
	public static final String DB_PASSWORD = "root@2881";
	
	
	private Connection connection;
	
	public DatabaseConnector(){
		try {
			connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
		}
		catch(SQLException e){
			e.printStackTrace();
			
		}
	}

	public Connection getConnection() {
		return connection;
		
	}
	
	public void closeConnection() {
		try {
			if(connection != null) {
				connection.close();
			}	
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}

package com.revature.project1.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
	
	public static Connection getConnection () {
		try {
			Properties props = new Properties();
			InputStream fileInputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream("connection.properties");
		    props.load(fileInputStream);
		    String details = props.getProperty("condetails");
		    Class.forName("org.postgresql.Driver");
		    Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/project1?user=postgres&password=postgres");
		    //Connection connection = DriverManager.getConnection(details);
		    return connection;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			// error handling
			return null;
		}
	}

}

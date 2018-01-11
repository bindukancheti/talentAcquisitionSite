package com.TalentAcquisitionPortal.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.TalenAcquisitionPortal.Dto.Credentials;

public class Login {

	private static Credentials credentials = new Credentials();
	public static Credentials getcredentials(String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

//			String sql = "insert into authenticate values (?,?,?)";
//			PreparedStatement statement = con.prepareStatement(sql);    
//			statement.setString(1, "Pavani");    
//			statement.setString(2, "Amazon");
//			statement.setString(3, "Hiree");
//			statement.execute();
			String query = "select * from authenticate where userName = ?";
			//String query = "select * from authenticate";
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setString(1,username);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				credentials.setUserName(resultSet.getString("userName"));
				credentials.setPassword(resultSet.getString("password"));
				System.out.format("\n" + " " +credentials.getUserName() + " " + credentials.getPassword() + " "+ credentials.getUserName().length());
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		return credentials;
	}
}

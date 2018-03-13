package com.TalentAcquisitionPortal.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalenAcquisitionPortal.Dto.HireeJobDetails;

public class Login {

	private static Credentials credentials = new Credentials();
	public static Credentials getcredentials(String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String query = "select * from authenticate where userName = ?";
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setString(1,username);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				credentials.setUserName(resultSet.getString("userName"));
				credentials.setPassword(resultSet.getString("password"));
				credentials.setRole(resultSet.getString("userrole"));
				System.out.format("\n" + " " +credentials.getUserName() + " " + credentials.getPassword() + " "+ credentials.getUserName().length());
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		return credentials;
	}

	public static String insertUser(Credentials credentials) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String sql = "insert into authenticate values (?,?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);    
			statement.setString(1, credentials.getUserName());    
			statement.setString(2, credentials.getPassword());
			statement.setString(3, credentials.getRole());
			statement.setString(4, credentials.getFirstName());
			statement.setString(5, credentials.getLastName());
			statement.execute();
			statement.close();

			String insertUser = "insert into user values(?,?,?,?,?,?,?)";
			PreparedStatement statement1 = con.prepareStatement(insertUser);    
			statement1.setString(6, credentials.getUserName());    
			statement1.setString(1, credentials.getFirstName());
			statement1.setString(2, credentials.getLastName());
			statement1.setString(3, null);
			statement1.setString(4, null);
			statement1.setString(5, null);
			statement1.setString(7, null);
			statement1.execute();
			statement1.close();
		} catch (SQLException e2) {
			if(e2.getSQLState().startsWith("23")) {
				System.out.println("Duplicate");
				return "duplicate";
			}
			System.out.println(e2.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "failed";
		}
		return "success";
	}
	public static List<String> getTalentmanagers() {
		List<String> talentManagers = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String talentManager="Talent Manager";

			String query = "select username from authenticate where userrole=?";
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setString(1,talentManager);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				talentManagers.add(resultSet.getString("username"));
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		return talentManagers;
	}
	public static String changePassword(String newPassword, String userName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String sql = "update authenticate set password = ? where username=?";
			PreparedStatement statement = con.prepareStatement(sql);    

			statement.setString(1, newPassword);
			statement.setString(2, userName);

			statement.executeUpdate();
			statement.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "failed";
		}	
		return "success";
	}
}

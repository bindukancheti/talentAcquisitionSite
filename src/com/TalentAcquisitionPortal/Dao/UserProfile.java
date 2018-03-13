package com.TalentAcquisitionPortal.Dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalenAcquisitionPortal.Dto.HireeJobDetails;
import com.TalenAcquisitionPortal.Dto.JobDetails;
import com.TalenAcquisitionPortal.Dto.User;

public class UserProfile {

	public static void insertPersonalProfile(User user, String userName) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String sql= "update user set gender=?, address=? where email=?";
			PreparedStatement statement = con.prepareStatement(sql);    
			statement.setString(1, user.getGender());    
			statement.setString(2, user.getAddress());
			statement.setString(3, userName);
			statement.execute();
			statement.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void insertProfessionalProfile(User user, String userName) {
		try {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/TapDB", "root", "root");
		//FacesContext context = FacesContext.getCurrentInstance();
		//Credentials credentials = (Credentials) context.getApplication().createValueBinding("#{credentials}").getValue(context);
		
		String sql = "update user set resume = ?, profilesummary=? where email=?";
		PreparedStatement statement = con.prepareStatement(sql);    
		Part uploadedFile=user.getFile();
		InputStream inputStream = uploadedFile.getInputStream();
		
		if (inputStream!=null) {
			statement.setBlob(1, inputStream);
			statement.setString(2, user.getProfileSummary());
			statement.setString(3, userName);
		}
		statement.executeUpdate();
		statement.close();
	} catch (Exception ex) {
		System.out.println(ex.getMessage());
	}
	}
	public static Boolean checkForResume(String userName) {
		Boolean resumeExists = false;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			
			String query = "select resume from user where email=?";
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				if (resultSet.getBlob("resume") != null) {
					resumeExists=true;
				}
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		return resumeExists;
	}
	public static User getCurrentUser(String userName) {
		User currentUser = new User();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			//FacesContext context = FacesContext.getCurrentInstance();
			//Credentials credentials = (Credentials) context.getApplication().createValueBinding("#{credentials}").getValue(context);
			String query = "select firstname, lastname, email from user where email=?";
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				currentUser.setFirstName(resultSet.getString("firstname"));
				currentUser.setLastName(resultSet.getString("lastname"));
				currentUser.setEmailId(resultSet.getString("email"));
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		return currentUser;
	}
}

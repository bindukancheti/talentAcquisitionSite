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
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String sql= "update user set gender=?, address=? where email=?";
			statement = con.prepareStatement(sql);    
			statement.setString(1, user.getGender());    
			statement.setString(2, user.getAddress());
			statement.setString(3, userName);
			statement.execute();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (resultSet != null) {
		        try {
		            resultSet.close();
		        } catch (SQLException e) { System.out.println(e.getMessage());}
		    } 
			if (statement != null) {
			        try {
			            statement.close();
			        } catch (SQLException e) { System.out.println(e.getMessage());}
			    }
			    if (con != null) {
			        try {
			            con.close();
			        } catch (SQLException e) { System.out.println(e.getMessage());}
			    }
		}
	}

	public static void insertProfessionalProfile(User user, String userName) {
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/TapDB", "root", "root");
		//FacesContext context = FacesContext.getCurrentInstance();
		//Credentials credentials = (Credentials) context.getApplication().createValueBinding("#{credentials}").getValue(context);
		
		String sql = "update user set resume = ?, profilesummary=? where email=?";
		statement = con.prepareStatement(sql);    
		Part uploadedFile=user.getFile();
		InputStream inputStream = uploadedFile.getInputStream();
		
		if (inputStream!=null) {
			statement.setBlob(1, inputStream);
			statement.setString(2, user.getProfileSummary());
			statement.setString(3, userName);
		}
		statement.executeUpdate();
	} catch (Exception ex) {
		System.out.println(ex.getMessage());
	} finally {
		if (resultSet != null) {
	        try {
	            resultSet.close();
	        } catch (SQLException e) { System.out.println(e.getMessage());}
	    } 
		if (statement != null) {
		        try {
		            statement.close();
		        } catch (SQLException e) { System.out.println(e.getMessage());}
		    }
		    if (con != null) {
		        try {
		            con.close();
		        } catch (SQLException e) { System.out.println(e.getMessage());}
		    }
	}
	}
	public static Boolean checkForResume(String userName) {
		Boolean resumeExists = false;
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			
			String query = "select resume from user where email=?";
			statement = con.prepareStatement(query); 
			statement.setString(1, userName);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				if (resultSet.getBlob("resume") != null) {
					resumeExists=true;
				}
			}
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		} finally {
			if (resultSet != null) {
		        try {
		            resultSet.close();
		        } catch (SQLException e) { System.out.println(e.getMessage());}
		    } 
			if (statement != null) {
			        try {
			            statement.close();
			        } catch (SQLException e) { System.out.println(e.getMessage());}
			    }
			    if (con != null) {
			        try {
			            con.close();
			        } catch (SQLException e) { System.out.println(e.getMessage());}
			    }
		}
		return resumeExists;
	}
	public static User getCurrentUser(String userName) {
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		User currentUser = new User();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			//FacesContext context = FacesContext.getCurrentInstance();
			//Credentials credentials = (Credentials) context.getApplication().createValueBinding("#{credentials}").getValue(context);
			String query = "select firstname, lastname, email from user where email=?";
			statement = con.prepareStatement(query); 
			statement.setString(1, userName);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				currentUser.setFirstName(resultSet.getString("firstname"));
				currentUser.setLastName(resultSet.getString("lastname"));
				currentUser.setEmailId(resultSet.getString("email"));
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		} finally {
			if (resultSet != null) {
		        try {
		            resultSet.close();
		        } catch (SQLException e) { System.out.println(e.getMessage());}
		    } 
			if (statement != null) {
			        try {
			            statement.close();
			        } catch (SQLException e) { System.out.println(e.getMessage());}
			    }
			    if (con != null) {
			        try {
			            con.close();
			        } catch (SQLException e) { System.out.println(e.getMessage());}
			    }
		}
		return currentUser;
	}
	public static InputStream getUserResume(String userName) {
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		InputStream resume=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String query = "select resume from user where email=?";
			statement = con.prepareStatement(query); 
			statement.setString(1, userName);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				resume=resultSet.getBinaryStream("resume");
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		} finally {
			if (resultSet != null) {
		        try {
		            resultSet.close();
		        } catch (SQLException e) { System.out.println(e.getMessage());}
		    } 
			if (statement != null) {
			        try {
			            statement.close();
			        } catch (SQLException e) { System.out.println(e.getMessage());}
			    }
			    if (con != null) {
			        try {
			            con.close();
			        } catch (SQLException e) { System.out.println(e.getMessage());}
			    }
		}
		return resume;
	}
}

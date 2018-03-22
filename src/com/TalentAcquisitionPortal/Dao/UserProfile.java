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

import com.TalenAcquisitionPortal.Dto.Comparission;
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

			String sql= "update user set gender=?, address=?, phone=? where email=?";
			statement = con.prepareStatement(sql);    
			statement.setString(1, user.getGender());    
			statement.setString(2, user.getAddress());
			statement.setString(3, user.getPhone());
			statement.setString(4, userName);
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

	public static void insertProfessionalProfile(User user, String userName, String experience) {
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/TapDB", "root", "root");
		//FacesContext context = FacesContext.getCurrentInstance();
		//Credentials credentials = (Credentials) context.getApplication().createValueBinding("#{credentials}").getValue(context);
		
		String sql = "update user set resume = ?, profilesummary=?, qualification =?, specialization=?, technologies=?, aggregate=?, experience=? where email=?";
		statement = con.prepareStatement(sql);    
		Part uploadedFile=user.getFile();
		InputStream inputStream = uploadedFile.getInputStream();
		
		if (inputStream!=null) {
			statement.setBlob(1, inputStream);
			statement.setString(2, user.getProfileSummary());
			statement.setString(3, user.getQualification());
			statement.setString(4, user.getSpecialization());
			statement.setString(5, user.getTechnologies());
			statement.setString(6, user.getAggregate());
			statement.setString(7, experience);
			statement.setString(8, userName);
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
			String query = "select firstname, lastname, email, phone from user where email=?";
			statement = con.prepareStatement(query); 
			statement.setString(1, userName);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				currentUser.setFirstName(resultSet.getString("firstname"));
				currentUser.setLastName(resultSet.getString("lastname"));
				currentUser.setEmailId(resultSet.getString("email"));
				currentUser.setPhone(resultSet.getString("phone"));
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

	public static Comparission getComparission(int jobid, String email) {
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		Comparission comparission = new Comparission();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String query = "select a.technologies as requiredTechnologies, a.aggregate as requiredAggregate, a.experience as requiredExperience, b.technologies , b.aggregate, b.experience from jobdetails a, user b where a.jobid=? and b.email=?";
			statement = con.prepareStatement(query); 
			statement.setInt(1, jobid);
			statement.setString(2, email);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				comparission.setRequiredAggregate(resultSet.getString("requiredAggregate"));
				comparission.setRequiredExperience(resultSet.getString("requiredExperience"));
				comparission.setRequiredTechnologies(resultSet.getString("requiredTechnologies"));
				comparission.setAggregate(resultSet.getString("aggregate"));
				comparission.setExperience(resultSet.getString("experience"));
				comparission.setTechnologies(resultSet.getString("technologies"));
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
		return comparission;
	}
}

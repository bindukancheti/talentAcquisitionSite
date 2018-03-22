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
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String query = "select * from authenticate where userName = ?";
			statement = con.prepareStatement(query); 
			statement.setString(1,username);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				credentials.setUserName(resultSet.getString("userName"));
				credentials.setPassword(resultSet.getString("password"));
				credentials.setRole(resultSet.getString("userrole"));
				System.out.format("\n" + " " +credentials.getUserName() + " " + credentials.getPassword() + " "+ credentials.getUserName().length());
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
		return credentials;
	}

	public static String insertUser(Credentials credentials, String phone) {
		Connection con=null;
		PreparedStatement statement=null;
		PreparedStatement statement1=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String sql = "insert into authenticate values (?,?,?,?,?,?)";
			statement = con.prepareStatement(sql);    
			statement.setString(1, credentials.getUserName());    
			statement.setString(2, credentials.getPassword());
			statement.setString(3, credentials.getRole());
			statement.setString(4, credentials.getFirstName());
			statement.setString(5, credentials.getLastName());
			statement.setString(6, credentials.getCompany());
			statement.execute();

			String insertUser = "insert into user values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			statement1 = con.prepareStatement(insertUser);    
			statement1.setString(6, credentials.getUserName());    
			statement1.setString(1, credentials.getFirstName());
			statement1.setString(2, credentials.getLastName());
			statement1.setString(3, null);
			statement1.setString(4, null);
			statement1.setString(5, null);
			statement1.setString(7, null);
			statement1.setString(8, phone);
			statement1.setString(9, null);
			statement1.setString(10, null);
			statement1.setString(11, null);
			statement1.setString(12, null);
			statement1.setString(13, null);
			statement1.execute();
		} catch (SQLException e2) {
			if(e2.getSQLState().startsWith("23")) {
				System.out.println("Duplicate");
				return "duplicate";
			}
			System.out.println(e2.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "failed";
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
			if (statement1 != null) {
		        try {
		            statement1.close();
		        } catch (SQLException e) { System.out.println(e.getMessage());}
		    }
			    if (con != null) {
			        try {
			            con.close();
			        } catch (SQLException e) { System.out.println(e.getMessage());}
			    }
		}
		return "success";
	}
	public static List<String> getTalentmanagers(String company) {
		List<String> talentManagers = new ArrayList<String>();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String talentManager="Talent Manager";

			String query = "select username from authenticate where userrole=? and company=?";
			statement = con.prepareStatement(query); 
			statement.setString(1,talentManager);
			statement.setString(2, company);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				talentManagers.add(resultSet.getString("username"));
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
		return talentManagers;
	}
	public static String changePassword(String newPassword, String userName) {
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String sql = "update authenticate set password = ? where username=?";
			statement = con.prepareStatement(sql);    

			statement.setString(1, newPassword);
			statement.setString(2, userName);

			statement.executeUpdate();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "failed";
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
		return "success";
	}

	public static String getCompany(String userName) {
		String company=null;
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String query = "select company from authenticate where userName=?";
			statement = con.prepareStatement(query); 
			statement.setString(1,userName);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				company=resultSet.getString("company");
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
		return company;
	}
}

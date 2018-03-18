package com.TalentAcquisitionPortal.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.TalenAcquisitionPortal.Dto.JobDetails;
import com.TalenAcquisitionPortal.Dto.User;

public class Jobs {
	
	public static List<JobDetails> getJobs(String userName) {
		List<JobDetails> jobDetails = new ArrayList<JobDetails>();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			
			String query = "select * from jobdetails where not exists (select * from applicationstatus where applicationstatus.jobid=jobdetails.jobid and hireename=?) and jobactive=?";
			statement = con.prepareStatement(query); 
			statement.setString(1, userName);
			statement.setString(2, "Y");
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				JobDetails jobDetail = new JobDetails();
				jobDetail.setJobId(resultSet.getInt("JobId"));
				jobDetail.setJobDescription(resultSet.getString("JobDescription"));
				System.out.println("Job Description:" + jobDetail.getJobDescription());
				jobDetail.setEligibility(resultSet.getString("Eligibility"));
				jobDetail.setCompany(resultSet.getString("company"));
				jobDetails.add(jobDetail);
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
		return jobDetails;
	}
	public static String postJob(String jobDescription, String eligibility, String company) {
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String sql = "insert into jobdetails(jobDescription, eligibility, company,jobactive) values (?,?,?,?)";
			statement = con.prepareStatement(sql);    
			statement.setString(1, jobDescription);    
			statement.setString(2, eligibility);
			statement.setString(3, company);
			statement.setString(4, "Y");
			statement.execute();
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
	public static List<JobDetails> getActivejobs(String company) {
		List<JobDetails> jobDetails = new ArrayList<JobDetails>();
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			
			String query = "select * from jobdetails where jobactive=? and company=?";
			statement = con.prepareStatement(query); 
			statement.setString(1, "Y");
			statement.setString(2, company);
			resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				JobDetails jobDetail = new JobDetails();
				jobDetail.setJobId(resultSet.getInt("JobId"));
				jobDetail.setJobDescription(resultSet.getString("JobDescription"));
				jobDetail.setEligibility(resultSet.getString("Eligibility"));
				jobDetail.setCompany(resultSet.getString("company"));
				jobDetail.setJobActive("jobActive");
				jobDetails.add(jobDetail);
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
		return jobDetails;
	}
	
	public static void closeJob(int jobId) {
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet resultSet=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String sql= "update jobDetails set jobActive=? where jobId=?";
			statement = con.prepareStatement(sql);    
			statement.setString(1, "N");    
			statement.setInt(2, jobId);
			statement.execute();
			statement.close();
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
}
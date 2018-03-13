package com.TalentAcquisitionPortal.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.TalenAcquisitionPortal.Dto.JobDetails;

public class Jobs {
	
	public static List<JobDetails> getJobs(String userName) {
		List<JobDetails> jobDetails = new ArrayList<JobDetails>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			
			String query = "select * from jobdetails where not exists (select * from applicationstatus where applicationstatus.jobid=jobdetails.jobid and hireename=?)";
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				JobDetails jobDetail = new JobDetails();
				jobDetail.setJobId(resultSet.getInt("JobId"));
				jobDetail.setJobDescription(resultSet.getString("JobDescription"));
				System.out.println("Job Description:" + jobDetail.getJobDescription());
				jobDetail.setEligibility(resultSet.getString("Eligibility"));
				jobDetails.add(jobDetail);
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		return jobDetails;
	}
	public static String postJob(String jobDescription, String eligibility) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String sql = "insert into jobdetails(jobDescription, eligibility) values (?,?)";
			PreparedStatement statement = con.prepareStatement(sql);    
			statement.setString(1, jobDescription);    
			statement.setString(2, eligibility);
			statement.execute();
			statement.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return "failed";
		}
		return "success";
	}
}
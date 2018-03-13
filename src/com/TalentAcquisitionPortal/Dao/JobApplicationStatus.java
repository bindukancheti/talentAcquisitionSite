package com.TalentAcquisitionPortal.Dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import com.TalenAcquisitionPortal.Dto.ApplicantStatus;
import com.TalenAcquisitionPortal.Dto.Applicants;
import com.TalenAcquisitionPortal.Dto.ApplicantsToRespond;
import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalenAcquisitionPortal.Dto.HireeJobDetails;
import com.TalenAcquisitionPortal.Dto.JobStatus;

public class JobApplicationStatus {

	public static void insertJobStatus(HireeJobDetails hireeJobDetails) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");

			String sql = "insert into applicationstatus values (?,?,?,?,?)";
			PreparedStatement statement = con.prepareStatement(sql);    
			statement.setString(1, hireeJobDetails.getHireeName());    
			statement.setInt(2, hireeJobDetails.getJobId());
			statement.setString(3, hireeJobDetails.getJobStatus());
			statement.setString(4, null);
			statement.setString(5, null);
			statement.execute();
			statement.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public static List<JobStatus> getJobApplicationStatus(String username) {
		List<JobStatus> jobStatus = new ArrayList<JobStatus>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String query = "select a.JobDescription, b.Status from jobdetails a, applicationstatus b where a.jobId=b.jobId and hireename=?";
			//String query = "select * from authenticate";
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setString(1,username);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				JobStatus jobAppStatus = new JobStatus();
				jobAppStatus.setJobDescription(resultSet.getString("JobDescription"));
				jobAppStatus.setStatus(resultSet.getString("Status"));
				jobStatus.add(jobAppStatus);
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		return jobStatus;
	}
	public static ApplicantStatus getApplicantsStatus() {
		String selected = "Applicant Selected";
		String rejected = "Applicant Rejected";
		String awaitingManager = "Awaiting Approval From Talent Manager";
		String awaitingHR = "Awaiting Approval From HR Manager";
		ApplicantStatus applicantStatus =new ApplicantStatus();	

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String query = "select sum(status=?) as SelectedCount, sum(status=?) as RejectCount, sum(status=?) as AwaitingHR, sum(status=?) as AwaitingManager from applicationstatus";
			PreparedStatement statement = con.prepareStatement(query); 
			statement.setString(1,selected);
			statement.setString(2,rejected);
			statement.setString(3,awaitingHR);
			statement.setString(4,awaitingManager);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				applicantStatus.setApplicantsSelected(resultSet.getInt("SelectedCount"));
				applicantStatus.setApplicantsRejected(resultSet.getInt("RejectCount"));
				applicantStatus.setApplicantsAwaitingHR(resultSet.getInt("AwaitingHR"));
				applicantStatus.setApplicantsAwaitingManager(resultSet.getInt("AwaitingManager"));
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}
		return applicantStatus;
	}
	public static List<Applicants> getApplicantsAwaitingHR() {
		String awaitingHR = "Awaiting Approval From HR Manager";
		List<Applicants> applicantList = new ArrayList<Applicants>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String query = "select firstname,lastname,hireename,JobDescription,j.jobid from jobdetails j join applicationstatus ap on j.JobId=ap.JobId join authenticate a on a.userName=ap.HireeName where ap.Status=?";
			PreparedStatement statement = con.prepareStatement(query); 

			statement.setString(1,awaitingHR);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				Applicants applicant = new Applicants();
				applicant.setFirstName(resultSet.getString("firstname"));
				applicant.setJobDescription(resultSet.getString("jobDescription"));
				applicant.setEmail(resultSet.getString("hireename"));
				applicant.setLastName(resultSet.getString("lastname"));
				applicant.setJobid(resultSet.getShort("jobid"));
				applicantList.add(applicant);
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}	
		return applicantList;
	}
	public static void updateApplicationStatusToAwaitingTalentManager(String email, int jobId, String talentManagerSelected) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String sql = "update applicationstatus set status = ?, talentManager=? where hireename=? and jobid=?";
			PreparedStatement statement = con.prepareStatement(sql);    

			statement.setString(1, "Awaiting Approval From Talent Manager");
			statement.setString(2, talentManagerSelected);
			statement.setString(3, email);
			statement.setInt(4, jobId);

			statement.executeUpdate();
			statement.close();
		} catch (SQLException e2) {
			if(e2.getSQLState().startsWith("23")) {
				//return "duplicate";
				System.out.println("duplicate");
			}
			System.out.println(e2.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			//return "failed";
		}
	}
	public static List<ApplicantsToRespond> getApplicantsToRespond() {

		String applicantRejected = "Applicant Rejected";
		String applicantSelected = "Applicant Selected";
		List<ApplicantsToRespond> applicantsToRespond = new ArrayList<ApplicantsToRespond>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String query = "select firstname,lastname,hireename,JobDescription,j.jobid,status from jobdetails j join applicationstatus ap on j.JobId=ap.JobId join authenticate a on a.userName=ap.HireeName where ap.Status in (?,?)";
			PreparedStatement statement = con.prepareStatement(query); 

			statement.setString(1,applicantSelected);
			statement.setString(2,applicantRejected);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				ApplicantsToRespond applicantToRespond = new ApplicantsToRespond();
				applicantToRespond.setAplicationStatus(resultSet.getString("status"));
				applicantToRespond.setFirstName(resultSet.getString("firstname"));
				applicantToRespond.setJobDescription(resultSet.getString("jobdescription"));
				applicantToRespond.setLastName(resultSet.getString("lastname"));
				applicantToRespond.setEmail(resultSet.getString("hireename"));
				applicantToRespond.setJobId(resultSet.getInt("jobid"));
				applicantsToRespond.add(applicantToRespond);
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}	
		return applicantsToRespond;
	}
	public static void updateApplicationStatusToResponded(String email, int jobId) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String sql = "update applicationstatus set status = ? where hireename=? and jobid=?";
			PreparedStatement statement = con.prepareStatement(sql);    

			statement.setString(1, "Responded");
			statement.setString(2, email);
			statement.setInt(3, jobId);

			statement.executeUpdate();
			statement.close();
		} catch (SQLException e2) {
			if(e2.getSQLState().startsWith("23")) {
				//return "duplicate";
				System.out.println("duplicate");
			}
			System.out.println(e2.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			//return "failed";
		}		
	}
	public static List<Applicants> getApplicantsAwaitingTalentManager() {
		String awaitingTalentManager = "Awaiting Approval From Talent Manager";
		List<Applicants> applicantList = new ArrayList<Applicants>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String query = "select firstname,lastname,hireename,JobDescription,j.jobid from jobdetails j join applicationstatus ap on j.JobId=ap.JobId join authenticate a on a.userName=ap.HireeName where ap.Status=?";
			PreparedStatement statement = con.prepareStatement(query); 

			statement.setString(1,awaitingTalentManager);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				Applicants applicant = new Applicants();
				applicant.setFirstName(resultSet.getString("firstname"));
				applicant.setJobDescription(resultSet.getString("jobDescription"));
				applicant.setEmail(resultSet.getString("hireename"));
				applicant.setLastName(resultSet.getString("lastname"));
				applicant.setJobid(resultSet.getShort("jobid"));
				applicantList.add(applicant);
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}	
		return applicantList;
	}
	public static void approveOrRejectApplicant(String email, int jobId, String comments, String action) {
		try {
			String approve="Approve";
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String sql = "update applicationstatus set status = ?, talentManagercomments=? where hireename=? and jobid=?";
			PreparedStatement statement = con.prepareStatement(sql);    

			if(action.equals(approve)) {
				statement.setString(1, "Applicant Selected");
			} else {
				statement.setString(1, "Applicant Rejected");
			}
			statement.setString(2, comments);
			statement.setString(3, email);
			statement.setInt(4, jobId);

			statement.executeUpdate();
			statement.close();
		} catch (SQLException e2) {
			if(e2.getSQLState().startsWith("23")) {
				//return "duplicate";
				System.out.println("duplicate");
			}
			System.out.println(e2.getMessage());
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			//return "failed";
		}		
	}
	public static List<ApplicantsToRespond> getHistoricApplicants(String hireeName) {

		String applicantRejected = "Applicant Rejected";
		String applicantSelected = "Applicant Selected";
		String applicantsResponded = "Responded";
		List<ApplicantsToRespond> historicApplicants = new ArrayList<ApplicantsToRespond>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/TapDB", "root", "root");
			String query = "select firstname,lastname,hireename,JobDescription,j.jobid,status from jobdetails j join applicationstatus ap on j.JobId=ap.JobId join authenticate a on a.userName=ap.HireeName where ap.Status in (?,?,?) and ap.talentManager=?";
			PreparedStatement statement = con.prepareStatement(query); 

			statement.setString(1,applicantSelected);
			statement.setString(2,applicantRejected);
			statement.setString(3,applicantsResponded);
			statement.setString(4, hireeName);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next())
			{
				ApplicantsToRespond historicApplicant = new ApplicantsToRespond();
				historicApplicant.setAplicationStatus(resultSet.getString("status"));
				historicApplicant.setFirstName(resultSet.getString("firstname"));
				historicApplicant.setJobDescription(resultSet.getString("jobdescription"));
				historicApplicant.setLastName(resultSet.getString("lastname"));
				historicApplicant.setEmail(resultSet.getString("hireename"));
				historicApplicant.setJobId(resultSet.getInt("jobid"));
				historicApplicants.add(historicApplicant);
			}
			statement.close();
		} catch (Exception e2) {
			System.out.println(e2.getMessage());
		}	
		return historicApplicants;
	}
}
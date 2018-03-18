package com.TalenAcquisitionPortal.Dto;

import java.util.ArrayList;
import java.util.List;

public class Applicants {
	private String firstName;
	private String lastName;
	private String jobDescription;
	private List<String> talentManagers=new ArrayList<String>();
	private String email;
	private int jobid;
	private String comments;
	private String company;
	private String professionalSummary;
	
	public String getProfessionalSummary() {
		return professionalSummary;
	}
	public void setProfessionalSummary(String professionalSummary) {
		this.professionalSummary = professionalSummary;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getJobid() {
		return jobid;
	}
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public List<String> getTalentManagers() {
		return talentManagers;
	}
	public void setTalentManagers(List<String> talentManagers) {
		this.talentManagers = talentManagers;
	}
}

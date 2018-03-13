package com.TalenAcquisitionPortal.Dto;

public class ApplicantsToRespond {
	private String firstName;
	private String lastName;
	private String jobDescription;
	private String aplicationStatus;
	private int jobId;
	private String email;
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
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
	public String getAplicationStatus() {
		return aplicationStatus;
	}
	public void setAplicationStatus(String aplicationStatus) {
		this.aplicationStatus = aplicationStatus;
	}
}

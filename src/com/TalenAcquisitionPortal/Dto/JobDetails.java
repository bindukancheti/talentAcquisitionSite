package com.TalenAcquisitionPortal.Dto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "jobDetails", eager = true)
@SessionScoped
public class JobDetails {
	private int jobId;
	private String jobDescription;
	private String eligibility;
	private String company;
	private String jobActive;
	
	public String getJobActive() {
		return jobActive;
	}
	public void setJobActive(String jobActive) {
		this.jobActive = jobActive;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getEligibility() {
		return eligibility;
	}
	public void setEligibility(String eligibility) {
		this.eligibility = eligibility;
	}
}

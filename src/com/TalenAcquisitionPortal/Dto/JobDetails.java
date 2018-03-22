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
	private String experienceYears;
	private String experienceMonths;
	private String aggregate;
	private String technologies;

	public String getExperienceYears() {
		return experienceYears;
	}
	public void setExperienceYears(String experienceYears) {
		this.experienceYears = experienceYears;
	}
	public String getExperienceMonths() {
		return experienceMonths;
	}
	public void setExperienceMonths(String experienceMonths) {
		this.experienceMonths = experienceMonths;
	}
	public String getAggregate() {
		return aggregate;
	}
	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}
	public String getTechnologies() {
		return technologies;
	}
	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}
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

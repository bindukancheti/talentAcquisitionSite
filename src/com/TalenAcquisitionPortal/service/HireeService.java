package com.TalenAcquisitionPortal.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalenAcquisitionPortal.Dto.HireeJobDetails;
import com.TalenAcquisitionPortal.Dto.JobDetails;
import com.TalentAcquisitionPortal.Dao.JobApplicationStatus;
import com.TalentAcquisitionPortal.Dao.Jobs;
import com.TalentAcquisitionPortal.Dao.UserProfile;

@ManagedBean(name = "hireeService", eager = true)
@RequestScoped
public class HireeService {

	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;
	private List<JobDetails> jobDetails; 
	private Boolean resumeAlert;
	
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public List<JobDetails> getJobDetails() {
		return jobDetails;
	}
	public void setJobDetails(List<JobDetails> jobDetails) {
		this.jobDetails = jobDetails;
	}
	public Boolean getResumeAlert() {
		return resumeAlert;
	}
	public void setResumeAlert(Boolean resumeAlert) {
		this.resumeAlert = resumeAlert;
	}
	@PostConstruct
	public void init() {
		if (this.jobDetails == null){
			this.jobDetails = getAllJobs();
		}
		resumeAlert=false;
	}
	public List<JobDetails> getAllJobs(){
		List<JobDetails> jobs = new ArrayList<JobDetails>();
		jobs = Jobs.getJobs(credentials.getUserName());	
		return jobs;
	}
	public void applyJob(int jobId) {
		System.out.println("Inside Apply Job");
		Boolean resumeExists = UserProfile.checkForResume(credentials.getUserName());
		if (resumeExists) {
			HireeJobDetails hireeJobDetails= new HireeJobDetails();
			hireeJobDetails.setHireeName(credentials.getUserName());
			hireeJobDetails.setJobId(jobId);
			hireeJobDetails.setJobStatus("Awaiting Approval From HR Manager");
			JobApplicationStatus.insertJobStatus(hireeJobDetails);
		}else {
			this.resumeAlert=true;
		}
		this.jobDetails = getAllJobs();
	}
	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().invalidateSession();
		try {
			context.getExternalContext().redirect("index.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
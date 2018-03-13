package com.TalenAcquisitionPortal.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.TalenAcquisitionPortal.Dto.Applicants;
import com.TalenAcquisitionPortal.Dto.ApplicantsToRespond;
import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalenAcquisitionPortal.Dto.JobDetails;
import com.TalentAcquisitionPortal.Dao.JobApplicationStatus;
import com.TalentAcquisitionPortal.Dao.Jobs;
import com.TalentAcquisitionPortal.Dao.Login;

@ManagedBean(name = "talentManagerService", eager = true)
@RequestScoped
public class TalentManagerService {
	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;
	private List<Applicants> applicants;
	private String[] action = new String[100];
	private String[] comments = new String[100];
	private JobDetails jobDetails= new JobDetails();
	private List<ApplicantsToRespond> historicApplicants;

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public List<ApplicantsToRespond> getHistoricApplicants() {
		return historicApplicants;
	}

	public void setHistoricApplicants(List<ApplicantsToRespond> historicApplicants) {
		this.historicApplicants = historicApplicants;
	}

	public JobDetails getJobDetails() {
		return jobDetails;
	}

	public void setJobDetails(JobDetails jobDetails) {
		this.jobDetails = jobDetails;
	}

	public String[] getComments() {
		return comments;
	}

	public void setComments(String[] comments) {
		this.comments = comments;
	}

	public List<Applicants> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<Applicants> applicants) {
		this.applicants = applicants;
	}

	public String[] getAction() {
		return action;
	}

	public void setAction(String[] action) {
		this.action = action;
	}

	@PostConstruct
	public void init() {
		if (this.applicants == null) {
			this.applicants = getApplicantsAwaitingManager();
		}
		if (this.historicApplicants==null) {
			this.historicApplicants=getHistoricApplicantsFromDB();
		}
	}

	private List<ApplicantsToRespond> getHistoricApplicantsFromDB() {
		return JobApplicationStatus.getHistoricApplicants(credentials.getUserName());
	}

	public List<Applicants> getApplicantsAwaitingManager() {
		List<Applicants> applicantsList= new ArrayList<Applicants>();
		applicantsList = JobApplicationStatus.getApplicantsAwaitingTalentManager();
		List<String> approveReject = new ArrayList<String>();
		approveReject.add("Approve");
		approveReject.add("Reject");
		for (Applicants applicant:applicantsList) {
			applicant.setTalentManagers(approveReject);
		}
		return applicantsList;
	}

	public void updateApplicationStatusandComments(Applicants applicant, int index) {
		JobApplicationStatus.approveOrRejectApplicant(applicant.getEmail(), applicant.getJobid(), this.comments[index], this.action[index]);
		this.applicants = getApplicantsAwaitingManager();
		if(this.applicants.size()==0) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("There are no applicants!!"));
		}
		this.comments = new String[100];
		this.action = new String[100];
	}

	public void postJob() {
		String success="success";
		FacesContext context = FacesContext.getCurrentInstance();
		String status = Jobs.postJob(this.jobDetails.getJobDescription(), this.jobDetails.getEligibility());
		if(status.equals(success)) {
			context.addMessage(null, new FacesMessage("Job Posted Successfully!!"));
		}else {
			context.addMessage(null, new FacesMessage("Job Posting Failed, please try after some time"));
		}
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
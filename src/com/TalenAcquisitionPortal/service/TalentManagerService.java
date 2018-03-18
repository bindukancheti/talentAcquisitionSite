package com.TalenAcquisitionPortal.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.TalenAcquisitionPortal.Dto.Applicants;
import com.TalenAcquisitionPortal.Dto.ApplicantsToRespond;
import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalenAcquisitionPortal.Dto.JobDetails;
import com.TalentAcquisitionPortal.Dao.JobApplicationStatus;
import com.TalentAcquisitionPortal.Dao.Jobs;
import com.TalentAcquisitionPortal.Dao.Login;
import com.TalentAcquisitionPortal.Dao.UserProfile;

@ManagedBean(name = "talentManagerService", eager = true)
@RequestScoped
public class TalentManagerService {
	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;
	private List<Applicants> applicants;
	private String[] action = new String[100];
	private String[] comments = new String[100];
	private JobDetails jobDetails= new JobDetails();
	private List<JobDetails> activeJobs;
	private List<ApplicantsToRespond> historicApplicants;
	private boolean jobActive;

	public boolean isJobActive() {
		return jobActive;
	}

	public void setJobActive(boolean jobActive) {
		this.jobActive = jobActive;
	}

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

	public List<JobDetails> getActiveJobs() {
		return activeJobs;
	}

	public void setActiveJobs(List<JobDetails> activeJobs) {
		this.activeJobs = activeJobs;
	}

	@PostConstruct
	public void init() {
		if (this.applicants == null) {
			this.applicants = getApplicantsAwaitingManager();
		}
		if (this.historicApplicants==null) {
			this.historicApplicants=getHistoricApplicantsFromDB();
		}
		if (this.activeJobs==null) {
			this.activeJobs=getAllActiveJobs();
		}
	}

	private List<JobDetails> getAllActiveJobs() {
		String company = Login.getCompany(credentials.getUserName());
		return Jobs.getActivejobs(company);
	}

	private List<ApplicantsToRespond> getHistoricApplicantsFromDB() {
		return JobApplicationStatus.getHistoricApplicants(credentials.getUserName());
	}

	public List<Applicants> getApplicantsAwaitingManager() {
		List<Applicants> applicantsList= new ArrayList<Applicants>();
		applicantsList = JobApplicationStatus.getApplicantsAwaitingTalentManager(credentials.getUserName());
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
		String company = Login.getCompany(credentials.getUserName());
		String status = Jobs.postJob(this.jobDetails.getJobDescription(), this.jobDetails.getEligibility(), company);
		if(status.equals(success)) {
			context.addMessage(null, new FacesMessage("Job Posted Successfully!!"));
		}else {
			context.addMessage(null, new FacesMessage("Job Posting Failed, please try after some time"));
		}
	}
	
	public String closeJob(JobDetails activeJob) {
		System.out.println(this.jobActive);
		Jobs.closeJob(activeJob.getJobId());
		this.activeJobs=getAllActiveJobs();
		return "closeJob";
	}
	
	public void download(Applicants applicant) throws IOException {
	    FacesContext fc = FacesContext.getCurrentInstance();
	    ExternalContext ec = fc.getExternalContext();
	    String fileName="Resume";
	    String contentType="text/plain";
	    ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
	    ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
	  //  ec.setResponseContentLength(contentLength); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
	    ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

	    OutputStream output = ec.getResponseOutputStream();
	    InputStream resume=UserProfile.getUserResume(applicant.getEmail());
	    byte[] buffer = new byte[1024];
	    int len;
	    while ((len = resume.read(buffer)) != -1) {
	        output.write(buffer, 0, len);
	    }

	    fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
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
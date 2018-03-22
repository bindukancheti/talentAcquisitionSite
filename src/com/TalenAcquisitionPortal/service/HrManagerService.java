package com.TalenAcquisitionPortal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.TalenAcquisitionPortal.Dto.ApplicantStatus;
import com.TalenAcquisitionPortal.Dto.Applicants;
import com.TalenAcquisitionPortal.Dto.ApplicantsToRespond;
import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalentAcquisitionPortal.Dao.JobApplicationStatus;
import com.TalentAcquisitionPortal.Dao.Login;
import com.TalentAcquisitionPortal.Dao.UserProfile;

@ManagedBean(name = "hrManagerService", eager = true)
@RequestScoped
public class HrManagerService {
	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;
	private ApplicantStatus applicantStatus;
	private Credentials newTalentManager = new Credentials();
	private String emailId;
	private List<Applicants> applicants;
	private String[] talentManagerSelected=new String[100];
	private String[] action=new String[100];
	private List<ApplicantsToRespond> aplicantsToRespond;
	private String senderEmailId="smartflints@gmail.com";
	private String senderPassword="fIN#1402";
	private String phone;
	
	
	public String[] getAction() {
		return action;
	}

	public void setAction(String[] action) {
		this.action = action;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public Credentials getNewTalentManager() {
		return newTalentManager;
	}

	public void setNewTalentManager(Credentials newTalentManager) {
		this.newTalentManager = newTalentManager;
	}

	public List<ApplicantsToRespond> getAplicantsToRespond() {
		return aplicantsToRespond;
	}

	public void setAplicantsToRespond(List<ApplicantsToRespond> aplicantsToRespond) {
		this.aplicantsToRespond = aplicantsToRespond;
	}

	public String[] getTalentManagerSelected() {
		return talentManagerSelected;
	}

	public void setTalentManagerSelected(String[] talentManagerSelected) {
		this.talentManagerSelected = talentManagerSelected;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public ApplicantStatus getApplicantStatus() {
		return applicantStatus;
	}

	public void setApplicantStatus(ApplicantStatus applicantStatus) {
		this.applicantStatus = applicantStatus;
	}
	
	public List<Applicants> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<Applicants> applicants) {
		this.applicants = applicants;
	}

	@PostConstruct
	public void init() {
		if (this.applicantStatus == null){
			this.applicantStatus = getAppsStatus();
		}
		if (this.applicants == null) {
			this.applicants = getApplicantsList();
		}
		if(this.aplicantsToRespond==null) {
			this.aplicantsToRespond = getApplicantsToRespond();
		}
	}

	public ApplicantStatus getAppsStatus() {
		return JobApplicationStatus.getApplicantsStatus();
	}
	
	public void addTalentManager() {
		FacesContext context = FacesContext.getCurrentInstance();
		newTalentManager.setRole("Talent Manager");
		newTalentManager.setPassword("default");
		String status=Login.insertUser(newTalentManager, phone);
		if(status=="success") {
			context.addMessage(null, new FacesMessage("Talent Manager is registered!!!"));
			newTalentManager= new Credentials();
		}else if(status=="duplicate") {
			context.addMessage(null, new FacesMessage("Talent manager already exisits"));
		}else {
			context.addMessage(null, new FacesMessage("Registration failed due to technical dificulties, please try after some time"));
		}
		phone=null;
	}
	
	public List<Applicants> getApplicantsList() {
		List<Applicants> applicantsList= new ArrayList<Applicants>();
		applicantsList = JobApplicationStatus.getApplicantsAwaitingHR();
		for(Applicants applicant:applicantsList) {
			applicant.setTalentManagers(Login.getTalentmanagers(applicant.getCompany()));
			applicant.setComparission(UserProfile.getComparission(applicant.getJobid(), applicant.getEmail()));
		}
		return applicantsList;
	}
	
	public void updateApplicationStatus(Applicants applicant, int index) {
		String status;
		String approve="Approve";
		if(this.action[index].equals(approve)) {
			status="Awaiting Approval From Talent Manager";
		}else {
			status="Applicant Rejected";
		}
		JobApplicationStatus.updateApplicationStatusToAwaitingTalentManager(applicant.getEmail(), applicant.getJobid(), this.talentManagerSelected[index], status);
		this.applicants= getApplicantsList();
		this.talentManagerSelected=new String[100];
		this.action=new String[100];
	}
	
	public List<ApplicantsToRespond> getApplicantsToRespond(){
		return JobApplicationStatus.getApplicantsToRespond();
	}
	
	public void updateApplicationStatusToResponded(ApplicantsToRespond applicantToRespond) {
		sendEmail(applicantToRespond.getEmail(), applicantToRespond.getAplicationStatus(), applicantToRespond.getJobDescription());
		JobApplicationStatus.updateApplicationStatusToResponded(applicantToRespond.getEmail(), applicantToRespond.getJobId());
		this.aplicantsToRespond=getApplicantsToRespond();
	}
	
	private void sendEmail(String receiverEmail, String status, String jobDescription) {
		String subject=null;
		String messageText=null;
		String applicantSelected = "Applicant Selected";
		String applicantRejected = "Applicant Rejected";
		System.out.println("\n" + receiverEmail);
		if ((status.trim()).equals(applicantSelected)) {
			subject= "Offer Letter";
			messageText="Hi, \n\n You are selected for " + jobDescription + " position."; 
		}else if ((status.trim()).equals(applicantRejected)) {
			subject= "Rejected";
			messageText="Hi, \n\n You are not selected for " + jobDescription + " position.";
		}
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmailId, senderPassword);
			}
		  });
		
		try { 			
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(senderEmailId));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(receiverEmail));
			message.setSubject(subject);
			message.setText(messageText);

			Transport.send(message);
			System.out.println("Email send successfully."); 
	    } catch (Exception e) {
		e.printStackTrace();
	    System.err.println("Error in sending email.");
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

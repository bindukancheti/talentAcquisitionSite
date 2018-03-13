package com.TalenAcquisitionPortal.service;

import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalenAcquisitionPortal.Dto.JobDetails;
import com.TalenAcquisitionPortal.Dto.JobStatus;
import com.TalentAcquisitionPortal.Dao.JobApplicationStatus;


@ManagedBean(name = "hireeAppStatus", eager = true)
@RequestScoped
public class HireeAppStatus {
	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;
	private List<JobStatus> jobStatus;

	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public List<JobStatus> getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(List<JobStatus> jobStatus) {
		this.jobStatus = jobStatus;
	}

	@PostConstruct
	public void init() {
		if (this.jobStatus == null){
			this.jobStatus = getAppliedJobStatus();
		}
	}

/*	private void sendEmailSSL() {
		String receiverEmail = "ne.nandu@gmail.com";
		String subject = "Test Email";
		String messageText="Hi Sreeyansh, \n\n This is test Email.";
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
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
	}*/
	public List<JobStatus> getAppliedJobStatus() {
		jobStatus=JobApplicationStatus.getJobApplicationStatus(credentials.getUserName());
		return jobStatus;
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

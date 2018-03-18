package com.TalenAcquisitionPortal.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalenAcquisitionPortal.Dto.User;
import com.TalentAcquisitionPortal.Dao.UserProfile;

@ManagedBean(name = "hireeProfileService", eager = true)
@RequestScoped
public class HireeProfileService {
	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;
	private User user = new User();

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	@PostConstruct
	public void init() {
		this.user = getUserFromDB();
	}

	private User getUserFromDB() {
		return UserProfile.getCurrentUser(credentials.getUserName());
	}
	public String uploadPersonalDetails() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (user.isTerms()) {
			UserProfile.insertPersonalProfile(user, credentials.getUserName());
			context.addMessage(null, new FacesMessage("Your personal details are updated"));
		}else {
			context.addMessage(null, new FacesMessage("Please declare that details furnished are correct!!!"));
		}

		return null;
	}
	public String uploadProfessionalDetails(){
		FacesContext context = FacesContext.getCurrentInstance();
		if (user.isTerms()) {
			UserProfile.insertProfessionalProfile(user, credentials.getUserName());
			context.addMessage(null, new FacesMessage("Your professional details are updated"));
		}else {
			context.addMessage(null, new FacesMessage("Please declare that details furnished are correct!!!"));
		}	
		return null;
	}
	public void fileValidator(FacesContext ctx,
			UIComponent comp,
			Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Part file = (Part)value;
		if (file.getSize() > 1048575) {
			msgs.add(new FacesMessage("File is too big"));
		}
//		if (!"text/plain".equals(file.getContentType())) {
//			msgs.add(new FacesMessage("Please attach text file"));
//		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
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

package com.TalenAcquisitionPortal.service;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalentAcquisitionPortal.Dao.Login;

@ManagedBean(name = "passwordService", eager = true)
@RequestScoped
public class passwordService {
	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	
	
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public void changePassword() {
		FacesContext context = FacesContext.getCurrentInstance();
		String status = null;
		String success="success";
		if(oldPassword.equals(credentials.getPassword())) {
			if(newPassword.equals(confirmPassword)) {
				status=Login.changePassword(newPassword, credentials.getUserName());
				if(status.equals(success)) {
					context.addMessage(null, new FacesMessage("Password Changed Successfully!!"));
				} else {
					context.addMessage(null, new FacesMessage("Password was not changed, please try after some time"));
				}
			}else {
				context.addMessage(null, new FacesMessage("Confirm Password should be same as New Password"));
			}
		}else {
			context.addMessage(null, new FacesMessage("Old Password entered is not valid"));
		}
	}
}

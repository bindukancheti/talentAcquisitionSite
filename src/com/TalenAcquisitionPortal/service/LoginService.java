package com.TalenAcquisitionPortal.service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalentAcquisitionPortal.Dao.Login;

@ManagedBean(name = "loginService", eager = true)
@RequestScoped
public class LoginService {
	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;

	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String UservalidOrnot() {
		System.out.println("inside Validation");
		Credentials credentialsFromTable = Login.getcredentials(credentials.getUserName());
		if (credentials.getUserName().equals(credentialsFromTable.getUserName())) {
		//if (credentials.getUserName().equals("Bindu")) {
			return "success";
		} else {
			return "failure";
		}
	}
	
	public String registration() {
		System.out.println("inside Registration");
		return "success";
	}
}

package com.TalenAcquisitionPortal.service;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.TalenAcquisitionPortal.Dto.Credentials;
import com.TalentAcquisitionPortal.Dao.Login;

@ManagedBean(name = "loginService", eager = true)
@RequestScoped
public class LoginService {
	@ManagedProperty(value = "#{credentials}")
	private Credentials credentials;
	private Credentials register= new Credentials();
	private Boolean imNotRobot;

	public Boolean getImNotRobot() {
		return imNotRobot;
	}
	public void setImNotRobot(Boolean imNotRobot) {
		this.imNotRobot = imNotRobot;
	}
	public Credentials getRegister() {
		return register;
	}
	public void setRegister(Credentials register) {
		this.register = register;
	}
	public Credentials getCredentials() {
		return credentials;
	}
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public String UservalidOrnot() {
		System.out.println("inside Validation");
		String hiree="Hiree";
		String hrManager="HR Manager";
		Credentials credentialsFromTable = Login.getcredentials(credentials.getUserName());
		if (credentials.getUserName().equals(credentialsFromTable.getUserName()) &&
				credentials.getPassword().equals(credentialsFromTable.getPassword()) && imNotRobot) {
			if(((credentialsFromTable.getRole()).trim()).equals(hrManager)) {
				return "hrManager";
			}else if(((credentialsFromTable.getRole()).trim()).equals(hiree)) {
				return "hiree";
			}else {
				return "talentManager";
			}
			
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			if(imNotRobot) {
				context.addMessage(null, new FacesMessage("Please Enter Valid Credentials!!!"));
			}else {
				context.addMessage(null, new FacesMessage("Please confirm that you are not a Robot!!"));
			}		
			return "index";
		}
	}
	
	public String registration() {
		System.out.println("inside Registration");
		register.setRole("Hiree");
		String status=Login.insertUser(register);
		FacesContext context = FacesContext.getCurrentInstance();
		if (status=="success") {
			context.addMessage(null, new FacesMessage("You are registered!! Please login to apply for jobs."));
		}else if(status=="duplicate") {
			context.addMessage(null, new FacesMessage("You are already registered"));
		}else {
			context.addMessage(null, new FacesMessage("Registered failed due to technical difficulties. Please try after some time!!"));
		}
		return "index";
	}
}

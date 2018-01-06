package com.TalenAcquisitionPortal.DTO;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "register", eager = true)
@RequestScoped
public class Register {
	String firstName;
	String lastName;
	String eMailId;
	String password;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String geteMailId() {
		return eMailId;
	}
	public void seteMailId(String eMailId) {
		this.eMailId = eMailId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

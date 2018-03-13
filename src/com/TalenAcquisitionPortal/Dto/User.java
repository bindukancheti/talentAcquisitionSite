package com.TalenAcquisitionPortal.Dto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

import com.TalentAcquisitionPortal.Dao.UserProfile;

import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "user", eager = true)
@SessionScoped
public class User {
	private String firstName;
	private String lastName;
	private String gender;
	private String address;
	private String profileSummary;
	private String emailId;
	private boolean terms;
	private Part file;
	
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProfileSummary() {
		return profileSummary;
	}
	public void setProfileSummary(String profileSummary) {
		this.profileSummary = profileSummary;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public boolean isTerms() {
		return terms;
	}
	public void setTerms(boolean terms) {
		this.terms = terms;
	}
	
	public Part getFile() {
		return file;
	}
	public void setFile(Part file) {
		this.file = file;
	}
}

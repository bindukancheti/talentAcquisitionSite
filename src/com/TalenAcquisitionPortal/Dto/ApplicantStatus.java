package com.TalenAcquisitionPortal.Dto;

public class ApplicantStatus {
	private int applicantsAwaitingHR;
	private int applicantsAwaitingManager;
	private int applicantsSelected;
	private int applicantsRejected;
	public int getApplicantsAwaitingHR() {
		return applicantsAwaitingHR;
	}
	public void setApplicantsAwaitingHR(int applicantsAwaitingHR) {
		this.applicantsAwaitingHR = applicantsAwaitingHR;
	}
	public int getApplicantsAwaitingManager() {
		return applicantsAwaitingManager;
	}
	public void setApplicantsAwaitingManager(int applicantsAwaitingManager) {
		this.applicantsAwaitingManager = applicantsAwaitingManager;
	}
	public int getApplicantsSelected() {
		return applicantsSelected;
	}
	public void setApplicantsSelected(int applicantsSelected) {
		this.applicantsSelected = applicantsSelected;
	}
	public int getApplicantsRejected() {
		return applicantsRejected;
	}
	public void setApplicantsRejected(int applicantsRejected) {
		this.applicantsRejected = applicantsRejected;
	}
}

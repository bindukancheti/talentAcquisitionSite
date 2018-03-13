package com.TalenAcquisitionPortal.Dto;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "hireeJobDetails", eager = true)
@RequestScoped
public class HireeJobDetails {
	private String hireeName;
	private int jobId;
	private String jobStatus;
	public String getHireeName() {
		return hireeName;
	}
	public void setHireeName(String hireeName) {
		this.hireeName = hireeName;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
}

package com.exercise6.model;

public class ContactInfo {
	
	private Integer id;
	private String infoType;
	private String infoDetail;

	public ContactInfo() {}
	public ContactInfo(String infoType, String infoDetail) {
		this.infoType = infoType;
		this.infoDetail = infoDetail;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public String getInfoDetail() {
		return this.infoDetail;
	}

	public void setInfoType(String input) {
		this.infoType = input;
	}
	
}
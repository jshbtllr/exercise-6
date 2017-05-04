package com.exercise6.core.model;

public class ContactInfo {
	
	private Integer contactId;
	private String infoType;
	private String infoDetail;

	public ContactInfo() {}
	public ContactInfo(String infoType, String infoDetail) {
		this.infoType = infoType;
		this.infoDetail = infoDetail;
	}

	public Integer getContactId() {
		return this.contactId;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public String getInfoDetail() {
		return this.infoDetail;
	}

	public void setContactId(Integer input) {
		this.contactId = input;
	}

	public void setInfoType(String input) {
		this.infoType = input;
	}

	public void setInfoDetail(String input) {
		this.infoDetail = input;
	}
	
}
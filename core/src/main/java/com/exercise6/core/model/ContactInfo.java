package com.exercise6.core.model;

public class ContactInfo {
	
	private Long id;
	private String infoType;
	private String infoDetail;

	public ContactInfo() {}
	public ContactInfo(String infoType, String infoDetail) {
		this.infoType = infoType;
		this.infoDetail = infoDetail;
	}

	public Long getId() {
		return this.id;
	}

	public String getInfoType() {
		return this.infoType;
	}

	public String getInfoDetail() {
		return this.infoDetail;
	}

	public void setId(Long input) {
		this.id = input;
	}

	public void setInfoType(String input) {
		this.infoType = input;
	}

	public void setInfoDetail(String input) {
		this.infoDetail = input;
	}
	
}
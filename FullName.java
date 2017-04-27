package com.exercise6.model

public class FullName {

	private Integer id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String suffix;
	private String title;

	public Name() {}
	public Name(String firstName, String lastName, String middleName, String suffix, String title) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.middleName = middleName;
		this.suffix = suffix;
		this.title = title;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public String getSuffix() {
		return this.suffix;
	}

	public String getTitle() {
		return this.title;
	}

	public void setFirstName(String input) {
		this.firstName = input;
	}

	public void setLastName(String input) {
		this.lastName = input;
	}	

	public void setMiddleName(String input) {
		this.middleName = input;
	}

	public void setSuffix(String input) {
		this.suffix = input;
	}

	public void setTitle(String input) {
		this.title = input;
	}
}
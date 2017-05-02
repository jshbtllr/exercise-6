package com.exercise6.model;

import java.util.Set;
import java.util.Date;

public class Employee {
	private Integer employeeId;
	private String lastName;
	private String firstName;
	private String middleName;
	private String suffix;
	private String title;
	private Address address;
	private Date birthday;
	private Float gradeWeightAverage;
	private Date hireDate;
	private Boolean employed;
	private ContactInfo contactInfo; 
	private Roles role;

	public Employee() {}
	public Employee(String lastName, String firstName, String middleName, String suffix, String title, 
					Address address, Date birthday, Float gradeWeightAverage, Date hireDate, Boolean employed, 
					ContactInfo contactInfo, Roles role) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.suffix = suffix;
		this.title = title;
		this.address = address;
		this.birthday = birthday;
		this.gradeWeightAverage = gradeWeightAverage;
		this.hireDate = hireDate;
		this.employed = employed;
		this.contactInfo = contactInfo;
		this.role = role;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getFirstName() {
		return this.firstName;
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

	public Address getAddress() {
		return this.address;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public Float getGradeWeightAverage() {
		return this.gradeWeightAverage;
	}

	public Date getHireDate() {
		return this.hireDate;
	}

	public Boolean getEmployed() {
		return this.employed;
	}

	public ContactInfo getContactInfo() {
		return this.contactInfo;
	}

	public Roles getRoles() {
		return this.role;
	}

	public void setLastName(String input) {
		this.lastName = input;
	}

	public void setFirstName(String input) {
		this.firstName = input;
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

	public void setAddress(String input) {
		this.address = input;
	}

	public void setBirthday(String input) {
		this.birthday = input;
	}

	public void setGradeWeightAverage(String input) {
		this.gradeWeightAverage = input;
	}

	public void setHireDate(String input) {
		this.hireDate = input;
	}

	public void setEmployed(String input) {
		this.employed = input;
	}

	public void setContactInfo(String input) {
		this.contactInfo = input;
	}

	public void setRoles(String input) {
		this.role = input;
	}

}
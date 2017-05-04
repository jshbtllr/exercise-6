package com.exercise6.core.model;

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
	private Set <ContactInfo> contactInfo; 
	private Set <Roles> role;

	public Employee() {}
	public Employee(String lastName, String firstName, String middleName, String suffix, String title, 
					Address address, Date birthday, Float gradeWeightAverage, Date hireDate, Boolean employed, 
					Set <ContactInfo> contactInfo, Set <Roles> role) {
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

	public Integer getEmployeeId() {
		return this.employeeId;
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

	public Set <ContactInfo> getContactInfo() {
		return this.contactInfo;
	}

	public Set <Roles> getRole() {
		return this.role;
	}

	public void setEmployeeId(Integer input) {
		this.employeeId = input;
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

	public void setAddress(Address input) {
		this.address = input;
	}

	public void setBirthday(Date input) {
		this.birthday = input;
	}

	public void setGradeWeightAverage(Float input) {
		this.gradeWeightAverage = input;
	}

	public void setHireDate(Date input) {
		this.hireDate = input;
	}

	public void setEmployed(Boolean input) {
		this.employed = input;
	}

	public void setContactInfo(Set <ContactInfo> input) {
		this.contactInfo = input;
	}

	public void setRole(Set <Roles> input) {
		this.role = input;
	}

}
package com.exercise6.core.model;

public class Address {
	
	private Long id;
	private String streetNumber;
	private String barangay;
	private String city;
	private String zipcode;
	private String country;

	public Address() {}
	public Address(String streetNumber, String barangay, String city, String country, String zipcode) {
		this.streetNumber = streetNumber;
		this.barangay = barangay;
		this.city = city;
		this.zipcode = zipcode;
		this.country = country;
	}

	private Long getId() {
		return this.id;
	}

	public String getStreetNumber() {
		return this.streetNumber;
	}

	public String getBarangay() {
		return this.barangay;
	}

	public String getCity() {
		return this.city;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public String getCountry() {
		return this.country;
	}

	public void setId(Long input) {
		this.id = input;
	}

	public void setStreetNumber(String input) {
		this.streetNumber = input;
	}

	public void setBarangay(String input) {
		this.barangay = input;
	}

	public void setCity(String input) {
		this.city = input;
	}

	public void setZipcode(String input) {
		this.zipcode = input;
	}

	public void setCountry(String input) {
		this.country = input;
	}
}
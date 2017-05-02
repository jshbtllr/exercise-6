package com.exercise6.model;

public class Address {
	
	private Integer addressId;
	private String streetNumber;
	private String barangay;
	private String city;
	private String zipcode;

	public Address() {}
	public Address(String streetNumber, String barangay, String city, String zipcode) {
		this.streetNumber = streetNumber;
		this.barangay = barangay;
		this.city = city;
		this.zipcode = zipcode;
	}

	private Integer getAddressId() {
		return this.addressId;
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

	public void setAddressId(String input) {
		this.addressId = input;
	}

	public void setStreetNumber(String input) {
		this.streetNumber = input;
	}

	public void setBarangay(String input) {
		this.barangay = input;
	}

	public void setCity(String input) {
		this.city = city;
	}

	public void setZipcode(String input) {
		this.zipcode = zipcode;
	}
}
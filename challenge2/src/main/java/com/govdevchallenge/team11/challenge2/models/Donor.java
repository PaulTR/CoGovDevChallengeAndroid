package com.govdevchallenge.team11.challenge2.models;

/**
 * Created by PaulTR on 5/18/14.
 */
public class Donor {
	private String firstName;
	private String lastName;
	private String phoneNumber = "6172830332";

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}

package com.govdevchallenge.team11.challenge2.models;

/**
 * Created by PaulTR on 5/18/14.
 */
public class Center {
	private String donationCenterLocation;
	private String donationCenterAddress1;
	private float donationCenterGeoLat;
	private float donationCenterGeoLong;

	public Center() {

	}

	public String getDonationCenterLocation() {
		return donationCenterLocation;
	}

	public void setDonationCenterLocation(String donationCenterLocation) {
		this.donationCenterLocation = donationCenterLocation;
	}

	public String getDonationCenterAddress1() {
		return donationCenterAddress1;
	}

	public void setDonationCenterAddress1(String donationCenterAddress1) {
		this.donationCenterAddress1 = donationCenterAddress1;
	}

	public float getDonationCenterGeoLat() {
		return donationCenterGeoLat;
	}

	public void setDonationCenterGeoLat(float donationCenterGeoLat) {
		this.donationCenterGeoLat = donationCenterGeoLat;
	}

	public float getDonationCenterGeoLong() {
		return donationCenterGeoLong;
	}

	public void setDonationCenterGeoLong(float donationCenterGeoLong) {
		this.donationCenterGeoLong = donationCenterGeoLong;
	}
}

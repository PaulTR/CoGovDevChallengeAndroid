package com.govdevchallenge.team11.challenge2.models;

/**
 * Created by PaulTR on 5/18/14.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PaulTR on 5/18/14.
 */
public class CenterList {
	private String status;
	private List<Center> data;
	public CenterList() {

	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCenterList(List<Center> data) {
		this.data = data;
	}

	public List<Center> getCenterList() {
		return data;
	}
}

package com.govdevchallenge.team11.challenge2.events;

import com.govdevchallenge.team11.challenge2.models.Donor;

/**
 * Created by PaulTR on 5/17/14.
 */
public class AddDonorEvent {
	public Donor donor;

	public AddDonorEvent( Donor donor ) {
		this.donor = donor;
	}
}

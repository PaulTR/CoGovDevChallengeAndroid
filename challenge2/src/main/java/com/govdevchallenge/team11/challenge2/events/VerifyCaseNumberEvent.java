package com.govdevchallenge.team11.challenge2.events;

/**
 * Created by PaulTR on 5/18/14.
 */
public class VerifyCaseNumberEvent {
	public boolean isVerified;
	public String caseNum;

	public VerifyCaseNumberEvent( boolean isVerified, String caseNum ) {
		this.isVerified = isVerified;
		this.caseNum = caseNum;
	}
}

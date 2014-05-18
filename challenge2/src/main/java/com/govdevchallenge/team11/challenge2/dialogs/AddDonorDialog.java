package com.govdevchallenge.team11.challenge2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.events.AddDonorEvent;
import com.govdevchallenge.team11.challenge2.models.Donor;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;

/**
 * Created by PaulTR on 5/17/14.
 */
public class AddDonorDialog extends DialogFragment {

	private EditText mFirstName;
	private EditText mLastName;
	private EditText mAddress1;
	private EditText mAddress2;
	private EditText mCity;
	private EditText mState;
	private EditText mZipCode;
	private EditText mPhoneNum;
	private EditText mEmail;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from( getActivity() );
		View view = inflater.inflate( R.layout.dialog_add_donor, null );
		mFirstName = (EditText) view.findViewById( R.id.first_name );
		mLastName = (EditText) view.findViewById( R.id.last_name );
		mAddress1 = (EditText) view.findViewById( R.id.address_1 );
		mAddress2 = (EditText) view.findViewById( R.id.address_2 );
		mCity = (EditText) view.findViewById( R.id.city );
		mState = (EditText) view.findViewById( R.id.state );
		mZipCode = (EditText) view.findViewById( R.id.zip );
		mPhoneNum = (EditText) view.findViewById( R.id.phone );
		mEmail = (EditText) view.findViewById( R.id.email );

		AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
		builder.setView( view );
		builder.setPositiveButton( "Add Item", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				addItem();
				getDialog().dismiss();
			}
		});
		builder.setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				getDialog().dismiss();
			}
		});
		return builder.create();
	}

	public void addItem() {
		Donor donor = new Donor();
		if( !TextUtils.isEmpty( mFirstName.getText().toString() ) )
			donor.setFirstName( mFirstName.getText().toString() );
		if( !TextUtils.isEmpty( mLastName.getText().toString() ) )
			donor.setLastName( mLastName.getText().toString() );
		if( !TextUtils.isEmpty( mPhoneNum.getText().toString() ) )
			donor.setPhoneNumber( mPhoneNum.getText().toString() );

		NavigationBus.getInstance().post( new AddDonorEvent( donor ) );
	}
}

package com.govdevchallenge.team11.challenge2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.events.VerifyCaseNumberEvent;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;

/**
 * Created by PaulTR on 5/18/14.
 */
public class VerifyCaseNumberDialog extends DialogFragment {

	private EditText mCaseNum;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from( getActivity() );
		View view = inflater.inflate( R.layout.dialog_verify, null );
		mCaseNum = (EditText) view.findViewById( R.id.case_num );

		AlertDialog.Builder builder = new AlertDialog.Builder( getActivity() );
		builder.setView( view );
		builder.setPositiveButton( "Verify", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				verifyCaseNumber();
				getDialog().dismiss();
			}
		});
		return builder.create();
	}

	private void verifyCaseNumber() {
		Log.e( "VerifyCaseNumberDialog", "verifyCaseNumber" );
		NavigationBus.getInstance().register( this );
		if( TextUtils.isEmpty( mCaseNum.getText() ) || mCaseNum.getText().toString().equalsIgnoreCase( "1" ) )
			NavigationBus.getInstance().post( new VerifyCaseNumberEvent( false, "0" ) );
		else {
			NavigationBus.getInstance().post( new VerifyCaseNumberEvent( true,  mCaseNum.getText().toString() ) );
		}
		NavigationBus.getInstance().unregister( this );
	}
}

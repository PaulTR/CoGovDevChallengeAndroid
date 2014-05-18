package com.govdevchallenge.team11.challenge2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.govdevchallenge.team11.challenge2.R;

/**
 * Created by PaulTR on 5/17/14.
 */
public class AddDonorDialog extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater inflater = LayoutInflater.from( getActivity() );
		View view = inflater.inflate( R.layout.dialog_add_donor, null );
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

	}
}

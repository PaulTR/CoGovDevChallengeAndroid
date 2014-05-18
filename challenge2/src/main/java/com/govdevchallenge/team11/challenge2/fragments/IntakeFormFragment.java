package com.govdevchallenge.team11.challenge2.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.dialogs.AddDonorDialog;
import com.govdevchallenge.team11.challenge2.dialogs.AddItemDialog;

/**
 * Created by PaulTR on 5/17/14.
 */
public class IntakeFormFragment extends Fragment implements View.OnClickListener {

	private Button mAddDonorButton;
	private Button mAddDonatedItemButton;
	private Button mSubmitButton;
	private TextView mDonorNameTextView;

	public static IntakeFormFragment getInstance() {
		IntakeFormFragment fragment = new IntakeFormFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate( R.layout.fragment_intake, container, false );
		mAddDonatedItemButton = (Button) view.findViewById( R.id.add_item );
		mAddDonorButton = (Button) view.findViewById( R.id.add_donor );
		mSubmitButton = (Button) view.findViewById( R.id.submit );
		mDonorNameTextView = (TextView) view.findViewById( R.id.donor_name );

		mAddDonatedItemButton.setOnClickListener( this );
		mAddDonorButton.setOnClickListener( this );
		mSubmitButton.setOnClickListener( this );

		mDonorNameTextView.setVisibility( View.GONE );
		return view;
	}

	@Override
	public void onClick(View view) {
		switch( view.getId() ) {
			case R.id.submit: {

				break;
			}
			case R.id.add_donor: {
				showAddDonorDialog();
				break;
			}
			case R.id.add_item: {
				showAddItemDialog();
				break;
			}
		}
	}

	private void showAddItemDialog() {
		AddItemDialog dialog = new AddItemDialog();
		FragmentManager fm = getFragmentManager();
		dialog.setCancelable( false );
		dialog.show(fm, "add item dialog");
	}

	private void showAddDonorDialog() {
		AddDonorDialog dialog = new AddDonorDialog();
		FragmentManager fm = getFragmentManager();
		dialog.setCancelable( false );
		dialog.show( fm, "add donor dialog" );
	}
}

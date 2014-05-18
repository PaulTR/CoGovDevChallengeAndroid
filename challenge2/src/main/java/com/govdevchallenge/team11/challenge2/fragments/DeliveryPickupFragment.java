package com.govdevchallenge.team11.challenge2.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.adapters.ItemAdapter;
import com.govdevchallenge.team11.challenge2.dialogs.AddDonorDialog;
import com.govdevchallenge.team11.challenge2.dialogs.AddItemDialog;
import com.govdevchallenge.team11.challenge2.dialogs.VerifyCaseNumberDialog;
import com.govdevchallenge.team11.challenge2.events.AddItemEvent;
import com.govdevchallenge.team11.challenge2.events.VerifyCaseNumberEvent;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;
import com.squareup.otto.Subscribe;

/**
 * Created by PaulTR on 5/17/14.
 */
public class DeliveryPickupFragment extends Fragment implements View.OnClickListener {

	private TextView mCaseNumText;
	private Button mVerifyCaseNumButton;
	private Button mAddItemButton;
	private Button mSubmitButton;
	private ListView mList;

	private ItemAdapter mAdapter;

	public static DeliveryPickupFragment getInstance() {
		DeliveryPickupFragment fragment = new DeliveryPickupFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate( R.layout.fragment_delivery_pickup, container, false );
		mCaseNumText = (TextView) view.findViewById( R.id.case_num );
		mVerifyCaseNumButton = (Button) view.findViewById( R.id.verify_case_num );
		mAddItemButton = (Button) view.findViewById( R.id.add_item );
		mSubmitButton = (Button) view.findViewById( R.id.submit );
		mList = (ListView) view.findViewById( R.id.list );

		mList.setVisibility( View.GONE );
		mSubmitButton.setVisibility( View.GONE );
		mAddItemButton.setVisibility( View.GONE );

		mVerifyCaseNumButton.setOnClickListener( this );
		mSubmitButton.setOnClickListener( this );
		mAddItemButton.setOnClickListener( this );

		mAdapter = new ItemAdapter( getActivity() );
		mList.setAdapter( mAdapter );

		return view;
	}

	@Override
	public void onClick(View view) {
		switch( view.getId() ) {
			case R.id.submit: {
				submitList();
				break;
			}
			case R.id.add_item: {
				showAddItemDialog();
				break;
			}
			case R.id.verify_case_num: {
				showVerifyCaseNumDialog();
				break;
			}
		}
	}

	private void submitList() {
		( (ItemAdapter) mList.getAdapter() ).clear();
		mCaseNumText.setVisibility( View.GONE );
		mList.setVisibility( View.GONE );
		mSubmitButton.setVisibility( View.GONE );
		mAddItemButton.setVisibility( View.GONE );
		mVerifyCaseNumButton.setVisibility( View.VISIBLE );
	}

	private void showAddItemDialog() {
		AddItemDialog dialog = new AddItemDialog();
		FragmentManager fm = getFragmentManager();
		dialog.setCancelable( false );
		dialog.show(fm, "add item dialog");
	}

	private void showVerifyCaseNumDialog() {
		VerifyCaseNumberDialog dialog = new VerifyCaseNumberDialog();
		FragmentManager fm = getFragmentManager();
		dialog.setCancelable( false );
		dialog.show( fm, "Verify Case Number Dialog" );
	}

	@Subscribe
	public void onVerifyCaseNumberEvent( VerifyCaseNumberEvent event ) {
		Log.e("DeliveryPickupFragment", "onVerifyCaseNumberEvent");
		if( event == null || !event.isVerified )
			return;
		else {
			mList.setVisibility( View.VISIBLE );
			mSubmitButton.setVisibility( View.VISIBLE );
			mAddItemButton.setVisibility( View.VISIBLE );
			mVerifyCaseNumButton.setVisibility( View.GONE );
			mCaseNumText.setText( event.caseNum );
		}
	}

	@Subscribe
	public void onAddItemEvent( AddItemEvent event ) {
		Log.e("IntakeFormFragment", "onAddItemEvent" );

		if( event == null || event.item == null )
			return;

		mAdapter.add( event.item );
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume() {
		super.onResume();
		NavigationBus.getInstance().register(this);
	}

	@Override
	public void onStop() {
		super.onPause();
		try {
			NavigationBus.getInstance().unregister(this);
		} catch( IllegalArgumentException e ) {

		}
	}
}

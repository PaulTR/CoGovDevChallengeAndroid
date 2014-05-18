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
import com.govdevchallenge.team11.challenge2.events.AddDonorEvent;
import com.govdevchallenge.team11.challenge2.events.AddItemEvent;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;
import com.squareup.otto.Subscribe;

/**
 * Created by PaulTR on 5/17/14.
 */
public class IntakeFormFragment extends Fragment implements View.OnClickListener {

	private Button mAddDonorButton;
	private Button mAddDonatedItemButton;
	private Button mSubmitButton;
	private TextView mDonorNameTextView;
	private ListView mListView;
	private ItemAdapter mAdapter;

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
		mListView = (ListView) view.findViewById( R.id.list );

		mAddDonatedItemButton.setOnClickListener( this );
		mAddDonorButton.setOnClickListener( this );
		mSubmitButton.setOnClickListener( this );

		mAdapter = new ItemAdapter( getActivity() );
		mListView.setAdapter( mAdapter );
		return view;
	}

	@Override
	public void onClick(View view) {
		switch( view.getId() ) {
			case R.id.submit: {
				submitList();
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

	private void submitList() {
		Toast.makeText( getActivity(), "Donation Received!", Toast.LENGTH_SHORT ).show();
		( (ItemAdapter) mListView.getAdapter() ).clear();
	}

	@Subscribe
	public void onAddItemEvent( AddItemEvent event ) {
		Log.e("IntakeFormFragment", "onAddItemEvent" );

		if( event == null || event.item == null )
			return;

		mAdapter.add( event.item );
		mAdapter.notifyDataSetChanged();
	}

	@Subscribe
	public void onAddDonorEvent( AddDonorEvent event ) {
		if( event == null )
			return;
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

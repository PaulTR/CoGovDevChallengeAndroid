package com.govdevchallenge.team11.challenge2.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.govdevchallenge.team11.challenge2.events.ClearAdapterEvent;
import com.govdevchallenge.team11.challenge2.models.Donor;
import com.govdevchallenge.team11.challenge2.models.Item;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;
import com.squareup.otto.Subscribe;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
	public List<Item> mList = new ArrayList<Item>();

	private Donor mDonor;

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
		mDonorNameTextView.setText( "Anonymous" );
		Toast.makeText( getActivity(), "Donation Received!", Toast.LENGTH_SHORT ).show();
		for( int i = 0; i < mListView.getAdapter().getCount(); i++ ) {
			mList.add( (Item) mListView.getAdapter().getItem( i ) );
		}
		PostIntakeTask task = new PostIntakeTask();
		task.execute( new URL[1] );
		( (ItemAdapter) mListView.getAdapter() ).clear();
		((ItemAdapter) mListView.getAdapter()).notifyDataSetChanged();
	}

	private class PostIntakeTask extends AsyncTask<URL, Integer, String> {
		protected String doInBackground(URL... urls) {
			postItems();
			return "Result";
		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(String result) {
			Log.e( "TAG", "onPostExecute" );
		}
	}

	private void postItems() {

		Item item = (Item) mList.get( 0 );
		/*
		String array = "{ [" +
				"           itemName: \"" + item.getName() + "\"," +
				"           itemCategoryName: \"" + item.getCategory() + "\"," +
				"           itemQuantity: \"" + item.getQuantity() + "\"" +
				"       ] }";
		Log.e( "Intakeformfragment", "array: " + array);
		*/
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://162.222.181.217:3000/rest/intake");

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			nameValuePairs.add(new BasicNameValuePair("donorPhone", "6172830332" ));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

	}

	@Subscribe
	public void onAddItemEvent( AddItemEvent event ) {
		Log.e("IntakeFormFragment", "onAddItemEvent" );

		if( event == null || event.item == null )
			return;

		if( event.item.getQuantity() == null )
			event.item.setQuantity( "1" );

		mAdapter.add( event.item );
		mAdapter.notifyDataSetChanged();
	}

	@Subscribe
	public void onAddDonorEvent( AddDonorEvent event ) {
		if( event == null || event.donor == null)
			return;

		mDonor = event.donor;

		String name = "";
		if( !TextUtils.isEmpty( event.donor.getFirstName() ) ) {
			name += event.donor.getFirstName();
		}
		if( !TextUtils.isEmpty( event.donor.getLastName() ) ) {
			if( !TextUtils.isEmpty( name ) )
				name += " ";
			name += event.donor.getLastName();
		}

		if( !TextUtils.isEmpty( name ) )
			mDonorNameTextView.setText( name );
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

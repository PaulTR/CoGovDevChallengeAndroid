package com.govdevchallenge.team11.challenge2.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.adapters.NavigationDrawerAdapter;
import com.govdevchallenge.team11.challenge2.events.DrawerNavigationEvent;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;

import java.util.Arrays;
import java.util.List;

/**
 * Created by PaulTR on 5/17/14.
 */
public class NavigationDrawerFragment extends ListFragment {

	public static NavigationDrawerFragment getInstance() {
		NavigationDrawerFragment fragment = new NavigationDrawerFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NavigationDrawerAdapter adapter = new NavigationDrawerAdapter( getActivity() );
		List<String> itemTitles = Arrays.asList(getResources().getStringArray( R.array.navigation_items ) );
		adapter.addAll( itemTitles );
		setListAdapter( adapter );
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		String navigationItemTitle = l.getAdapter().getItem( position ).toString();
		NavigationBus.getInstance().register( this );
		NavigationBus.getInstance().post( new DrawerNavigationEvent( navigationItemTitle ) );
		NavigationBus.getInstance().unregister( this );
	}
}

package com.govdevchallenge.team11.challenge2.activities;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.events.DrawerNavigationEvent;
import com.govdevchallenge.team11.challenge2.fragments.DeliveryPickupFragment;
import com.govdevchallenge.team11.challenge2.fragments.DonationMapFragment;
import com.govdevchallenge.team11.challenge2.fragments.IntakeFormFragment;
import com.govdevchallenge.team11.challenge2.fragments.NavigationDrawerFragment;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;
import com.squareup.otto.Subscribe;

public class MainActivity extends FragmentActivity {

	private String mCurFragmentName;
	private Fragment mDrawerFragment;

	private DrawerLayout mDrawerLayout;
	private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );

		mDrawerFragment = NavigationDrawerFragment.getInstance();
		mDrawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );
		initActionBar();
		initNavigationDrawer();

		displayInitialFragment();
    }

	private void displayInitialFragment() {
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.content_container, DonationMapFragment.getInstance())
				.commit();

		mCurFragmentName = getString( R.string.navigation_donation_map );
	}

	private void initActionBar() {
		if( getActionBar() == null )
			return;

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	private void initNavigationDrawer() {
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.drawer_container, mDrawerFragment )
				.commit();

		mDrawerToggle = new ActionBarDrawerToggle( this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_closed ) {

			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				if( getActionBar() == null )
					return;

				getActionBar().setTitle( R.string.navigation_drawer_closed );
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				if( getActionBar() == null )
					return;

				getActionBar().setTitle( R.string.navigation_drawer_open );
				invalidateOptionsMenu();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Subscribe
	public void onDrawerNavigationEvent( DrawerNavigationEvent event ) {
		mDrawerLayout.closeDrawers();

		if( event.section == null )
			return;

		if( event.section.equalsIgnoreCase( mCurFragmentName ) )
			return;
		else {
			mCurFragmentName = event.section;
		}

		if( event.section.equalsIgnoreCase( getString( R.string.navigation_donation_map ) ) ){
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.content_container, DonationMapFragment.getInstance())
					.commit();
		}
		if( event.section.equalsIgnoreCase( getString( R.string.navigation_intake ) ) ) {
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.content_container, IntakeFormFragment.getInstance())
					.commit();
		}
		if( event.section.equalsIgnoreCase( getString( R.string.navigation_pickup ) ) ) {
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.content_container, DeliveryPickupFragment.getInstance())
					.commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			NavigationBus.getInstance().register(this);
		} catch( IllegalArgumentException e ) {

		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			NavigationBus.getInstance().unregister(this);
		} catch( IllegalArgumentException e ) {

		}
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if ( id == R.id.action_settings ) {
			return true;
		} else if ( mDrawerToggle.onOptionsItemSelected( item ) ) {
			return true;
		} else {
			return super.onOptionsItemSelected(item);
		}
    }

}

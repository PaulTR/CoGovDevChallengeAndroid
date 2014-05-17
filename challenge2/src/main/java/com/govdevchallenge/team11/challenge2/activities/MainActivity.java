package com.govdevchallenge.team11.challenge2.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.events.DrawerNavigationEvent;
import com.govdevchallenge.team11.challenge2.fragments.DeliveryPickupFragment;
import com.govdevchallenge.team11.challenge2.fragments.DonationMapFragment;
import com.govdevchallenge.team11.challenge2.fragments.IntakeFormFragment;
import com.govdevchallenge.team11.challenge2.fragments.NavigationDrawerFragment;
import com.govdevchallenge.team11.challenge2.fragments.WalkInPickupFragment;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;
import com.squareup.otto.Subscribe;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main );

		initNavigationDrawer();
		initAuthenticationFragment();
    }

	private void initNavigationDrawer() {
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.drawer_container, NavigationDrawerFragment.getInstance())
				.commit();
	}

	private void initAuthenticationFragment() {
		//Load authentication fragment into R.id.container
	}

	@Subscribe
	public void onDrawerNavigationEvent( DrawerNavigationEvent event ) {
		if( event.section == null )
			return;

		if( event.section == getString( R.string.navigation_donation_map ) ) {
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.drawer_container, DonationMapFragment.getInstance())
					.commit();
		}
		if( event.section == getString( R.string.navigation_intake_title ) ) {
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.drawer_container, IntakeFormFragment.getInstance())
					.commit();
		}
		if( event.section == getString( R.string.navigation_pickup ) ) {
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.drawer_container, DeliveryPickupFragment.getInstance())
					.commit();
		}
		if( event.section == getString( R.string.navigation_walk_in ) ) {
			getFragmentManager()
					.beginTransaction()
					.replace(R.id.drawer_container, WalkInPickupFragment.getInstance())
					.commit();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		NavigationBus.getInstance().register( this );
	}

	@Override
	protected void onPause() {
		super.onPause();
		NavigationBus.getInstance().unregister( this );
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

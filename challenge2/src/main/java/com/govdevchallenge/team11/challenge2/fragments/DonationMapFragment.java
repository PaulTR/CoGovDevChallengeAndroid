package com.govdevchallenge.team11.challenge2.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.govdevchallenge.team11.challenge2.R;
import com.govdevchallenge.team11.challenge2.events.DonationMapSpinnerItemEvent;
import com.govdevchallenge.team11.challenge2.utils.NavigationBus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by PaulTR on 5/17/14.
 */
public class DonationMapFragment extends Fragment implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {
	private Spinner mCategorySpinner;
	private GoogleMap mGoogleMap;
	private LocationClient mLocationClient;
	private MapFragment mMapFragment;
	private List<Marker> mMarkerLocations = new ArrayList<Marker>();

	public static DonationMapFragment getInstance() {
		DonationMapFragment fragment = new DonationMapFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mLocationClient = new LocationClient( getActivity(), this, this );
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate( R.layout.fragment_donation_map, container, false );
		mMapFragment = (MapFragment) getFragmentManager().findFragmentById( R.id.map );
		mGoogleMap = mMapFragment.getMap();
		mCategorySpinner = (Spinner) view.findViewById( R.id.category_spinner );

		initSpinner();
		return view;
	}

	private void initSpinner() {
		List<String> items = new ArrayList<String>();
		items.add("Food");
		items.add("Water");
		items.add("Cleaning Supplies");
		items.add("Blankets");

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
		mCategorySpinner.setAdapter(adapter);

		mCategorySpinner.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Log.e( "DonationMapFragment", "onItemSelected" );
				NavigationBus.getInstance().post(new DonationMapSpinnerItemEvent(adapterView.getItemAtPosition(i).toString()));
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) {
				//do nothing
			}
		});
	}

	@Subscribe
	public void onDonationMapSpinnerItemEvent( DonationMapSpinnerItemEvent event ) {
		//check category, populate markers
		if( mMarkerLocations == null || mGoogleMap == null )
			return;

		mMarkerLocations.clear();
		mGoogleMap.clear();
		Random random = new Random();
		addMarker( Integer.toString(Math.abs(random.nextInt(9999))), 40.10, -105.25, "Boulder Shelter", "Address" );
		addMarker( Integer.toString( Math.abs( random.nextInt( 9999 ) ) ), 40, -105.10, "Longmont Shelter", "Address" );
		addMarker( Integer.toString( Math.abs( random.nextInt( 9999 ) ) ), 39.75, -104.87, "Denver Shelter", "Address" );
	}

	private void addMarker(String num, double lat, double lng, String title, String address) {
		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		Bitmap bmp = Bitmap.createBitmap(150, 50, conf);
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();
		paint.setColor( getResources().getColor( android.R.color.holo_purple ) );
		paint.setTextSize( 50 );
		paint.setFakeBoldText( true );
		canvas.drawText(num, 0, 50, paint );
		Marker marker = mGoogleMap.addMarker(new MarkerOptions()
				.position( new LatLng( lat, lng ) )
				.title( title )
				.icon(BitmapDescriptorFactory.fromBitmap(bmp))
				.snippet(address) );
		mMarkerLocations.add( marker );
	}


	@Override
	public void onStart() {
		super.onStart();
		NavigationBus.getInstance().register( this );
		if( mLocationClient != null )
			mLocationClient.connect();
		if( mMapFragment != null )
			mGoogleMap = mMapFragment.getMap();

	}

	@Override
	public void onStop() {
		super.onStop();
		if( mLocationClient != null )
			mLocationClient.disconnect();
		mGoogleMap = null;
	}

	@Override
	public void onPause() {
		super.onPause();
		NavigationBus.getInstance().unregister( this );
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		try {
			if (getFragmentManager() == null)
				return;
			Fragment fragment = (getFragmentManager().findFragmentById(R.id.map));
			if (fragment == null)
				return;
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.remove(fragment);
			ft.commit();
		} catch( IllegalStateException e ) {

		}
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initMap();
	}

	private void initMap() {
		if( mGoogleMap == null ) {
			mGoogleMap = ( (MapFragment) getFragmentManager().findFragmentById( R.id.map ) ).getMap();
		}

		mGoogleMap.setTrafficEnabled(false);
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mGoogleMap.setMyLocationEnabled(true);
		mGoogleMap.setIndoorEnabled(false);
		mGoogleMap.setMyLocationEnabled(true);
		mGoogleMap.setOnMarkerClickListener( new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				if( marker == null )
					return false;
				else {
					marker.showInfoWindow();
					return true;
				}
			}
		});
	}

	private void setInitialCameraPosition() {
		double lng, lat;
		float tilt, bearing, zoom;

		lng = mLocationClient.getLastLocation().getLongitude();
		lat = mLocationClient.getLastLocation().getLatitude();
		zoom = 9;
		bearing = 0;
		tilt = 0;

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target( new LatLng( lat, lng) )
				.zoom( zoom )
				.bearing( bearing )
				.tilt( tilt )
				.build();

		if( cameraPosition == null || mGoogleMap == null )
			return;

		mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
	}

	@Override
	public void onConnected(Bundle bundle) {
		setInitialCameraPosition();
		requestCategories();
	}

	private void requestCategories() {

	}

	@Override
	public void onDisconnected() {
		mGoogleMap = null;
		mMapFragment = null;
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	}
}

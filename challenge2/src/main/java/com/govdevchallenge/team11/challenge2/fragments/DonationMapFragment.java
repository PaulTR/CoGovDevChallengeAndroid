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
		Log.e("DonationMapFragment", "onDonationMapSpinnerItemEvent");


		Bitmap.Config conf = Bitmap.Config.ARGB_8888;
		Bitmap bmp = Bitmap.createBitmap(100, 50, conf);
		Canvas canvas = new Canvas(bmp);
		Paint paint = new Paint();
		paint.setColor( getResources().getColor( android.R.color.holo_purple ) );
		paint.setTextSize( 50 );
		paint.setFakeBoldText( true );
		canvas.drawText("423", 0, 50, paint );
		Marker marker = mGoogleMap.addMarker(new MarkerOptions()
				.position( new LatLng( 40.10, -105.25 ) )
				.title("Boulder Shelter")
				.icon(BitmapDescriptorFactory.fromBitmap( bmp ) )
				.snippet("Address goes here") );
		mMarkerLocations.add( marker );

		conf = Bitmap.Config.ARGB_8888;
		bmp = Bitmap.createBitmap(100, 50, conf);
		canvas = new Canvas(bmp);
		paint = new Paint();
		paint.setColor( getResources().getColor( android.R.color.holo_purple ) );
		paint.setTextSize( 50 );
		paint.setFakeBoldText( true );
		canvas.drawText("13", 0, 50, paint );
		marker = mGoogleMap.addMarker(new MarkerOptions()
				.position(new LatLng(40, -105.10))
				.icon(BitmapDescriptorFactory.fromBitmap( bmp ) )
				.title( "Longmont Shelter" )
				.snippet( "Address goes here" ) );
		mMarkerLocations.add( marker );

		conf = Bitmap.Config.ARGB_8888;
		bmp = Bitmap.createBitmap(100, 50, conf);
		canvas = new Canvas(bmp);
		paint = new Paint();
		paint.setColor( getResources().getColor( android.R.color.holo_purple ) );
		paint.setTextSize( 50 );
		paint.setFakeBoldText( true );
		canvas.drawText("763", 0, 50, paint );
		marker = mGoogleMap.addMarker( new MarkerOptions()
				.position( new LatLng( 39.75, -104.87 ) )
				.icon(BitmapDescriptorFactory.fromBitmap( bmp ) )
				.title("Denver Shelter")
				.snippet( "Address goes here" ) );
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
	public void onPause() {
		super.onPause();
		NavigationBus.getInstance().unregister( this );
		if( getFragmentManager() == null )
			return;
		Fragment fragment = ( getFragmentManager().findFragmentById( R.id.map ) );
		if( fragment == null )
			return;
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.remove(fragment);
		ft.commit();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if( mLocationClient != null )
			mLocationClient.disconnect();
		mGoogleMap = null;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		initMap();
	}

	private void initMap() {
		if( mGoogleMap == null )
			return;

		mGoogleMap.setTrafficEnabled( false );
		mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL );
		mGoogleMap.setMyLocationEnabled(true);
		mGoogleMap.setIndoorEnabled( false );
		mGoogleMap.setMyLocationEnabled( true );

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
		tilt = 30;

		CameraPosition cameraPosition = new CameraPosition.Builder()
				.target( new LatLng( lat, lng) )
				.zoom( zoom )
				.bearing( bearing )
				.tilt( tilt )
				.build();

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

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
	}
}

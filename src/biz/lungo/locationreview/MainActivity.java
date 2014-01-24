package biz.lungo.locationreview;

import java.io.IOException;
import java.util.List;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity  {
	LocationManager locationManager;
	LocationProvider GPSProvider;
	LocationProvider NetworkProvider;
	LocationProvider PassiveProvider;
	private LocationListener ll;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//locationService();
		geoCoding();		
	}

	private void geoCoding() {
		Geocoder geocoder = new Geocoder(this);
		List<Address> fromLocation = null;
		try {
			fromLocation = geocoder.getFromLocation(30.52, 50.45, 10);
		} catch (IOException e) {
			
		}
		for (Address ad:fromLocation){
			String adminArea = ad.getAdminArea();
			String countryCode = ad.getCountryCode();
			String countryName = ad.getCountryName();
			String featureName = ad.getFeatureName();
			String addressLine = ad.getAddressLine(0);
			Toast.makeText(this, addressLine, Toast.LENGTH_SHORT).show();
		}
	}

	private void locationService() {
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		boolean isGPSEnabled = false;
		boolean isNetworkEnabled = false;
		boolean isPassiveEnabled = false;
		try {
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			isPassiveEnabled = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
		} catch (Exception e){
			e.printStackTrace();
		}
		if (!isGPSEnabled){
			Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(i);
		}
		List<String> allProviders = locationManager.getAllProviders();
		List<String> providersTrue = locationManager.getProviders(true);
		List<String> providersFalse = locationManager.getProviders(false);
		GPSProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
		NetworkProvider = locationManager.getProvider(LocationManager.NETWORK_PROVIDER);
		PassiveProvider = locationManager.getProvider(LocationManager.PASSIVE_PROVIDER);
		ll = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				double latitude = location.getLatitude();
				double longitude = location.getLongitude();
				Toast.makeText(getApplicationContext(), "Lat: " + latitude + 
						" Long: " + longitude, Toast.LENGTH_LONG).show();
			}
		};
		Toast.makeText(this, "GPS: " + isGPSEnabled + 
							" Network: " + isNetworkEnabled + 
							" Passive: " + isPassiveEnabled, 
							Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onResume() {		
		super.onResume();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 10, ll);
	}
	@Override
	protected void onPause() {		
		super.onPause();
	}

}
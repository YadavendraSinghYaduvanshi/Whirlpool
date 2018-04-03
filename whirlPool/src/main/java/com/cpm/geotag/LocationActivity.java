package com.cpm.geotag;

/*public class LocationActivity
{}*/

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cpm.Constants.CommonString1;
import com.cpm.Constants.TMSCommonString;
import com.cpm.GetterSetter.Storenamebean;
import com.cpm.whirlpool.R;
import com.cpm.database.GSKDatabase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity
        implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener, LocationListener {
    protected static final String PHOTO_TAKEN = "photo_taken";
    LocationManager locationManager;
    Geocoder geocoder;
    protected FloatingActionButton _buttonsave;
    public Camera camera;
    File file;
    protected ImageView _image;
    protected boolean _taken;
    ImageView capture_1;
    public String text;
    public View view;
    Location location;
    GeotaggingBeans data = new GeotaggingBeans();
    private LocationManager locmanager = null;
    protected String diskpath = "";
    protected String _path;
    boolean enabled;
    protected String _pathforcheck = "";
    public static ArrayList<Storenamebean> storedetails = new ArrayList<Storenamebean>();
    String storeid;
    String status;
    String storelatitude = "0";
    String storelongitude = "0";
    protected int resultCode;
    int abc;
    private GoogleMap mMap;
    double lat;
    double longitude;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    String store_cd, visit_date, username, intime;
    Marker currLocationMarker;
    LatLng latLng;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private static int UPDATE_INTERVAL = 1000; // 10 sec
    private static int FATEST_INTERVAL = 500; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters
    Location mLastLocation = null;
    private SharedPreferences preferences = null;
    String currentdate;
    MarkerOptions markerOptions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gpslocationscreen);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentdate = preferences.getString(TMSCommonString.KEY_ISD_DATE, "");
        storeid = preferences.getString(CommonString1.KEY_STORE_CD, null);
        visit_date = preferences.getString(CommonString1.KEY_DATE, null);
        username = preferences.getString(CommonString1.KEY_USERNAME, null);
        intime = preferences.getString(CommonString1.KEY_STORE_IN_TIME, "");
        _image = (ImageView) findViewById(R.id.image);
        _buttonsave = (FloatingActionButton) findViewById(R.id.savedetails);
        capture_1 = (ImageView) findViewById(R.id.StoreFront);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //for crate home button
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapview);
        mapFragment.getMapAsync(this);
        GSKDatabase data1 = new GSKDatabase(getApplicationContext());
        data1.open();
        storedetails = new ArrayList<>();
        ImageView img = new ImageView(getApplicationContext());
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        geocoder = new Geocoder(this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        if (checkPlayServices()) {
            buildGoogleApiClient();
            createLocationRequest();
        }

        _buttonsave.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (_pathforcheck != null &&!_pathforcheck.equals("")) {
                    if (!(storelatitude.equals("0")) && !(storelongitude.equals("0"))) {
                        lat = Double.parseDouble(storelatitude);
                        longitude = Double.parseDouble(storelongitude);
                    } else {
                        lat = data.getLatitude();
                        longitude = data.getLongitude();
                    }
                    if (ImageUploadActivity.CheckGeotagImage(_pathforcheck)) {
                        status = "Y";
                        GSKDatabase data = new GSKDatabase(getApplicationContext());
                        data.open();
                        data.updateOutTime(status, store_cd, visit_date);
                        data.InsertStoregeotagging(storeid, lat, longitude, _pathforcheck, status);
                        data.close();
                        if (isNetworkOnline()) {
                            Intent intent2 = new Intent(LocationActivity.this, UploadGeotaggingActivity.class);
                            startActivity(intent2);
                            finish();
                        }
                    } else {
                        Snackbar.make(_buttonsave, "Please take Store Front image", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(_buttonsave, "Please take Store Front image", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        capture_1.setOnClickListener(new ButtonClickHandler());
        locmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        enabled = locmanager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!enabled) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LocationActivity.this);
            // Setting Dialog Title
            alertDialog.setTitle("GPS IS DISABLED...");
            // Setting Dialog Message
            alertDialog.setMessage("Click ok to enable GPS.");
            // Setting Positive "Yes" Button
            alertDialog.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    });

            // Setting Negative "NO" Button
            alertDialog.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to invoke NO event
                            dialog.cancel();
                        }
                    });
            // Showing Alert Message
            alertDialog.show();
        }
    }
/*
    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (ImageUploadActivity.CheckGeotagImage(_pathforcheck)) {
            capture_1.setBackgroundResource(R.mipmap.camera_green);
        }
    }*/

    protected void startCameraActivity() {
        Log.i("MakeMachine", "startCameraActivity()");
        file = new File(diskpath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(intent, 0);
    }

    public boolean isNetworkOnline() {
        boolean status = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED) {
                status = true;
            } else {
                netInfo = cm.getNetworkInfo(1);
                if (netInfo != null && netInfo.getState() == NetworkInfo.State.CONNECTED)
                    status = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return status;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MakeMachine", "resultCode: " + resultCode);
        switch (resultCode) {
            case 0:
                Log.i("MakeMachine", "User cancelled");
                break;
            case -1:
                onPhotoTaken();
                if (ImageUploadActivity.CheckGeotagImage(_pathforcheck)) {
                    capture_1.setImageResource(R.mipmap.camera_green);
                }
                break;
        }
    }

    protected void onPhotoTaken() {
        Log.i("MakeMachine", "onPhotoTaken");
        _taken = true;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
    }

/*
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i("MakeMachine", "onRestoreInstanceState()");
        if (savedInstanceState.getBoolean(PHOTO_TAKEN)) {
            onPhotoTaken();
            if (ImageUploadActivity.CheckGeotagImage(_pathforcheck)) {
                capture_1.setBackgroundResource(R.mipmap.camera_orange);
            }
        }
        if (ImageUploadActivity.CheckGeotagImage(_pathforcheck)) {
            capture_1.setBackgroundResource(R.mipmap.camera_orange);

        }

    }
*/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(PHOTO_TAKEN, _taken);
    }


    public class ButtonClickHandler implements OnClickListener {
        LocationActivity loc = new LocationActivity();

        public void onClick(View view) {
           // _pathforcheck = storeid + "front.jpg";
            if (!(storelatitude.equals("0")) && !(storelongitude.equals("0"))) {
                if (view.getId() == R.id.StoreFront) {
                    diskpath = CommonString1.FILE_PATH + storeid + "front.jpg";
                    _path = storeid + "front.jpg";
                    _pathforcheck=_path;
                    Log.i("MakeMachine", "ButtonClickHandler.onClick()");
                    abc = 03;
                    startCameraActivity();
                }
            } else if (data.getLatitude() == 0 && data.getLongitude() == 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Wait For Geo Location").setCancelable(false).setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            } else {
                if (view.getId() == R.id.StoreFront) {
                    diskpath = CommonString1.FILE_PATH + storeid + "front.jpg";
                    _path = storeid + "front.jpg";
                    _pathforcheck=_path;
                    Log.i("MakeMachine", "ButtonClickHandler.onClick()");
                    abc = 03;
                    startCameraActivity();
                }
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        // TODO Auto-generated method stub
        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getMaxZoomLevel();
        mMap.getMinZoomLevel();
        mMap.getUiSettings();
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomOut());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(20));


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult arg0) {
        // TODO Auto-generated method stub
    }

    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            storelatitude = String.valueOf(mLastLocation.getLatitude());
            storelongitude = String.valueOf(mLastLocation.getLongitude());
            markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = null;
            String alladress = "";
            try {
                addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                String cityName = addresses.get(0).getAddressLine(0);
                String stateName = addresses.get(0).getAddressLine(1);
                String postal = addresses.get(0).getPostalCode();
                alladress = cityName + stateName + postal;
            } catch (IOException e) {
                e.printStackTrace();
            }
            markerOptions.title(alladress);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mMap.clear();
            currLocationMarker = mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000); //5 seconds
        mLocationRequest.setFastestInterval(3000); //3 seconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        // TODO Auto-generated method stub
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        mLastLocation = location;
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device is not supported.", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FATEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    protected void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        super.onStart();

    }

    protected void onStop() {
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    public void onBackPressed() {
        overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
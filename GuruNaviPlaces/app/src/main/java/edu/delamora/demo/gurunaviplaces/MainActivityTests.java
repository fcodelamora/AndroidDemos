package edu.delamora.demo.gurunaviplaces;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.util.ArrayList;

import edu.delamora.demo.gurunaviplaces.interfaces.TaskCallbacks;
import edu.delamora.demo.gurunaviplaces.models.GuruPlace;
import edu.delamora.demo.gurunaviplaces.utils.AdapterGuruPlacesList;
import edu.delamora.demo.gurunaviplaces.utils.FragmentPlacesListViewRetained;
import edu.delamora.demo.gurunaviplaces.utils.TaskDownloadImages;
import edu.delamora.demo.gurunaviplaces.utils.TaskGetNearbyPlaces;

public class MainActivityTests extends AppCompatActivity
        implements TaskCallbacks, OnNavigationItemSelectedListener, ConnectionCallbacks,
        OnConnectionFailedListener, LocationListener {

    private int REQUEST_CHECK_SETTINGS = 1;

    private Location lastLocation;
    private Location currentLocation;
    private LocationRequest locationRequest;

    private FragmentPlacesListViewRetained dataFragment;

    private ArrayList<GuruPlace> placesArray;

    private GoogleApiClient googleApiClient;
    ProgressDialog progressDialog;

    private int rangeValue; // 1:300m、2:500m、3:1000m、4:2000m、5:3000m. Default 2 (500m)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_tests);

        //Recover Places lists if available
        FragmentManager fm = getFragmentManager();
        dataFragment = (FragmentPlacesListViewRetained) fm.findFragmentByTag("placesListData");
        // create the fragment and data the first time
        if (dataFragment == null) {
            // add the fragment
            dataFragment = new FragmentPlacesListViewRetained();
            fm.beginTransaction().add(dataFragment, "placesListData").commit();
        } else {
            placesArray = dataFragment.getData();
            updatePlacesListUI(placesArray);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Spinner rangeSpinner = (Spinner) findViewById(R.id.spinner_range);
        rangeSpinner.setSelection(1); //defaults to 500m (2)
        rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rangeValue = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                ; //DoNothing
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //TODO Personalize or auto-dismiss on search finished.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPlacesFromGuruNavi();
            }
        });


        //Code from android template
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Code from android template finishes

        //Set up Google API services
        buildGoogleAPIServices();

        //Get Location
        createLocationRequest();

        //Recover Data
        updateValuesFromBundle(savedInstanceState);
    }

    //UI Stuff Starts

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_tests, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

// UI Stuff ENDS
//Life Cycle Stuff STARTS
    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "MainActivityTests Page", // TODO: Define a title for the content shown.
                Uri.parse("http://host/path"),
                Uri.parse("android-app://edu.delamora.demo.gurunaviplaces/http/host/path")
        );
        AppIndex.AppIndexApi.start(googleApiClient, viewAction);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "MainActivityTests Page",
                Uri.parse("http://host/path"),
                Uri.parse("android-app://edu.delamora.demo.gurunaviplaces/http/host/path")
        );

        //Disconnect googleApi
        AppIndex.AppIndexApi.end(googleApiClient, viewAction);
        googleApiClient.disconnect();
    }

    @Override
    protected void onDestroy() {
        dataFragment.setData(placesArray);
        super.onDestroy();
        // SaveData
    }

    protected void stopLocationUpdates() {
        //TODO Check if location services still available
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, this);
        }

    }


    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
        //TODO Disable elements that require online connectivity

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), getString(R.string.network_error),
                Toast.LENGTH_SHORT).show();
    }

    //Own functions
    private void buildGoogleAPIServices() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /*OnConnectionFailedListener*/)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(AppIndex.API)
                .build();
    }

    protected void createLocationRequest() {
        locationRequest = new LocationRequest()
                .setInterval(60000) //TODO balance interval
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Check location settings
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        builder.build());


        //checkresult
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                // final LocationSettingsStates = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied.
                        // But could be fixed by showing the user a dialog.

                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    MainActivityTests.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        //TODO
                        Toast.makeText(getApplicationContext(), "SETTINGS_CHANGE_UNAVAILABLE",
                                Toast.LENGTH_SHORT).show();
                        setDummyLocation();
                        break;
                }
            }
        });
    }

    protected void startLocationUpdates() {

        //Get last know location as a starting point, first we check if persmission is granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("@startLocationUpdates", "Location Permissions are Revoked!");
            return;
        }
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (lastLocation != null) {
            currentLocation = lastLocation;
        }
        //Get Location updates


        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    startLocationUpdates();
                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(getApplicationContext(), getString(
                            R.string.failed_to_acquire_location), Toast.LENGTH_SHORT).show();
                    setDummyLocation();
                    break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    public void setDummyLocation() {
        //If GPS is not available, we use a dummy location to allow testing
        currentLocation = new Location("dummyProvider");
        currentLocation.setLatitude(35.04977407795522);
        currentLocation.setLongitude(135.78202314875932);
    }

    //Saving app state
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable("LAST_KNOWN_LOCATION", currentLocation);
        //TODO list of places
        super.onSaveInstanceState(savedInstanceState);
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // Update the value of currentLocation from the Bundle and update the
            // UI to show the correct latitude and longitude.
            if (savedInstanceState.keySet().contains("LAST_KNOWN_LOCATION")) {
                // Since LOCATION_KEY was found in the Bundle, we can be sure that
                // mCurrentLocationis not null.
                currentLocation = savedInstanceState.getParcelable("LAST_KNOWN_LOCATION");
            }
        }
    }


    public void updatePlacesListUI(ArrayList<GuruPlace> places) {

        if (places != null) {
            placesArray = places;
            dataFragment.setData(placesArray);

            // Construct the data source
            ArrayList<GuruPlace> placesArray = new ArrayList<GuruPlace>();
            // Create the adapter to convert the array to views
            AdapterGuruPlacesList adapter = new AdapterGuruPlacesList(this, placesArray);
            // Attach the adapter to a ListView
            ListView placesListView = (ListView) findViewById(
                    R.id.main_activity_tests_places_listview);
            placesListView.setAdapter(adapter);
            adapter.addAll(places);
            placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                    Intent intent = new Intent(MainActivityTests.this,
                            GuruPlaceInformationScrollingActivity.class);
                    GuruPlace rowPlace = (GuruPlace) parent.getItemAtPosition(position);

                    //TODO implement cache so we can prevent losing this data
                    rowPlace.setBitmap_image_url__shop_image1(null);
                    rowPlace.setBitmap_image_url__shop_image2(null);


                    intent.putExtra("GURU_PLACE", rowPlace);
                    intent.putExtra("LATITUDE", currentLocation.getLatitude());
                    intent.putExtra("LONGITUDE", currentLocation.getLongitude());
                    startActivity(intent);
                }
            });
        }

    }

    public void getPlacesFromGuruNavi() {
       if(progressDialog == null){
           progressDialog = new ProgressDialog(this);
       }
        progressDialog.setMessage(getString(R.string.searching));
        progressDialog.setCancelable(false);
        progressDialog.show();

        new TaskGetNearbyPlaces(MainActivityTests.this, rangeValue).execute(currentLocation);
    }

    @Override
    public void taskError() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void taskProcessPlacesCallback(ArrayList<GuruPlace> places) {
        if(progressDialog != null){
            progressDialog.setMessage(getString(R.string.loading));
        }
        new TaskDownloadImages(MainActivityTests.this).execute(places);
    }

    @Override
    public void taskDownloadImagesCallback(ArrayList<GuruPlace> places) {
        //If no drawable is defined, photos get overwritten on scroll, we fix this setting a default image
        Bitmap defaultImage = BitmapFactory.decodeResource(getResources(), R.drawable.ic_food);
        for(GuruPlace place : places){
            if ((place.getFormattedImage_url__shop_image(place.getImage_url__shop_image1())) == null){
                place.setBitmap_image_url__shop_image1(defaultImage);
            }
        }
        if(progressDialog != null){
            progressDialog.dismiss();
        }
        updatePlacesListUI(places);
    }
}

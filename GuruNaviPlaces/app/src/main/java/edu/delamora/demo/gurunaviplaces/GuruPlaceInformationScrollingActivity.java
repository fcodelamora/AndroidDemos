package edu.delamora.demo.gurunaviplaces;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import edu.delamora.demo.gurunaviplaces.interfaces.TaskCallbacks;
import edu.delamora.demo.gurunaviplaces.models.GuruPlace;
import edu.delamora.demo.gurunaviplaces.utils.TaskDownloadImages;

public class GuruPlaceInformationScrollingActivity extends AppCompatActivity
        implements TaskCallbacks {

    private SupportMapFragment mapFragment;
    private GuruPlace place;
    private GoogleMap placeMap;
    Location currentLocation = new Location("");

    private GoogleApiClient client;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Receive the serializable object from the previous activity, plus location
        Intent i = getIntent();
        place = (GuruPlace) i.getSerializableExtra("GURU_PLACE");
        currentLocation.setLatitude(i.getDoubleExtra("LATITUDE", 0));
        currentLocation.setLongitude(i.getDoubleExtra("LONGITUDE", 0));

        setContentView(R.layout.activity_guru_place_information_scrolling);

        //TODO As we are not using the cache yet, the images must be re-downloaded
        ArrayList<GuruPlace> tmpArray = new ArrayList<>();
        tmpArray.add(place);
        new TaskDownloadImages(GuruPlaceInformationScrollingActivity.this).execute(tmpArray);

        //Set Title
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(place.getName());

        //TODO: Make place_photo1 the background of the Toolbar/Actionbar

        //Find UI elements and fill them up with their information.

        //First Card
        TextView website    = (TextView) findViewById(R.id.textview_place_website);
        TextView access     = (TextView) findViewById(R.id.textview_place_access);
        TextView budget     = (TextView) findViewById(R.id.textview_place_budget);
        TextView phone      = (TextView) findViewById(R.id.textview_place_phone);
        TextView address    = (TextView) findViewById(R.id.textview_place_address);

        //3rd Card
        TextView placeHours = (TextView) findViewById(R.id.textview_place_open_hours);
        TextView pr         = (TextView) findViewById(R.id.textview_place_pr);

        ImageView coupon    = (ImageView) findViewById(R.id.imageview_place_coupon);

        website.setText(place.getUrl_mobile());
        //Make it a link
        Linkify.addLinks(website, Linkify.WEB_URLS);
        website.setMovementMethod(LinkMovementMethod.getInstance());

        phone.setText(place.getTel());
        //Make it a link
        Linkify.addLinks(phone, Linkify.PHONE_NUMBERS);
        phone.setMovementMethod(LinkMovementMethod.getInstance());

        address.setText(place.getAddress());

        access.setText(checkNullText(place.getFormattedAccess(true,true,true,true)));
        pr.setText(checkNullText(place.getFormattedLongText(place.getPr__pr_long())));
        placeHours.setText(checkNullText(place.getFormattedLongText(place.getOpentime())));

        budget.setText( place.getBudget() > 0 ?    //Format 3000 as 3,000, etc
                ""+ NumberFormat.getNumberInstance(Locale.JAPANESE).format( place.getBudget()) :
                getResources().getString(R.string.no_information));

        if (findViewById(R.id.guruplace_map_fragment) != null) {
            mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(
                    R.id.guruplace_map_fragment));
            if (mapFragment != null) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap rcvMap) {
                        loadPlaceMap(rcvMap);
                    }
                });
            } else {
                Toast.makeText(this, getString(R.string.null_map_fragment),
                        Toast.LENGTH_SHORT).show();
            }
        }
        //No mobile coupon? reduce icon alpha
        if((place.getFlags__mobile_coupon()).compareTo("0") == 0){
            coupon.getDrawable().setAlpha(75);
        }
        //TODO Move to an AsyncTask - Initializing google API Client
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //Show user location and place location
    public void loadPlaceMap(GoogleMap gMap) {
        placeMap = gMap;
        //Move camera close to subject
        placeMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(
                currentLocation.getLatitude(), currentLocation.getLongitude()) , 16.0f) );
        placeMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //TODO: Custom Icons!
                //TODO: Recover original bounds after unselecting the marker
                //Add marker for the place
                placeMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getLatitude(), place.getLongitude()))
                        .title(place.getName()));
                placeMap.setMyLocationEnabled(true);
                //Adding and removing features as designed
                placeMap.getUiSettings().setMyLocationButtonEnabled(false);
                placeMap.getUiSettings().setAllGesturesEnabled(false);
                placeMap.getUiSettings().setCompassEnabled(true);

                //Define bounds to zoom to the area that makes place and user visible
                LatLngBounds.Builder b = new LatLngBounds.Builder();
                b.include(new LatLng(currentLocation.getLatitude(),
                        currentLocation.getLongitude()));
                b.include(new LatLng(place.getLatitude(), place.getLongitude()));
                LatLngBounds bounds = b.build();

                //TODO check New permission checks for Android
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 75);
                placeMap.animateCamera(cu);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "GuruPlaceInformationScrolling Page",  Uri.parse("http://host/path"),
                Uri.parse("android-app://edu.delamora.demo.gurunaviplaces/http/host/path") );

        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                "GuruPlaceInformationScrolling Page",  Uri.parse("http://host/path"),
                Uri.parse("android-app://edu.delamora.demo.gurunaviplaces/http/host/path"));
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void taskError() {

    }

    @Override
    public void taskProcessPlacesCallback(ArrayList<GuruPlace> places) {
        //Do Nothing
    }

    @Override
    public void taskDownloadImagesCallback(ArrayList<GuruPlace> placesImages) {
        setDownloadedImages(placesImages.get(0));
    }
    public void setDownloadedImages(GuruPlace place){
        ImageView placeImage1 = (ImageView) findViewById(R.id.imageview_place_image_1);
        ImageView placeImage2 = (ImageView) findViewById(R.id.imageview_place_image_2);

        if(place.getBitmap_image_url__shop_image1() != null){
            placeImage1.setImageBitmap(place.getBitmap_image_url__shop_image1());
        }
        if(place.getBitmap_image_url__shop_image2() != null){
            placeImage2.setImageBitmap(place.getBitmap_image_url__shop_image2());
        }
    }
    public String checkNullText(String text){
        if(text != null){
            return text;
        }else{
            return getString(R.string.no_available_information);
        }
    }

}

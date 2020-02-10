package com.mapolbs.vizibeebritannia.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mapolbs.vizibeebritannia.R;
import com.mapolbs.vizibeebritannia.Utilities.LatLngInterpolator;
import com.mapolbs.vizibeebritannia.Utilities.MarkerAnimation;
import com.mapolbs.vizibeebritannia.Utilities.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;


public class MapScreen extends FragmentActivity
        implements OnMapReadyCallback{

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 5445;

    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentLocationMarker;
    private Location currentLocation;
    private boolean firstTimeFlag = true;
    private ImageButton currentlocation;
    ProgressDialog pDialog;
    /*    private GoogleMap mMap;
        private GoogleApiClient mGoogleApiClient;*/
    Button btngetlocation;
    TextView txtlatitude;
    TextView txtlongitude;
    TextView maplocation;
    ImageView imgclose;
    double latitude = 0, longitude = 0;
    /* private OnLocationChangedListener mMapLocationListener = null;
     LocationRequest mLocationRequest;*/
    // location accuracy settings
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    private final LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            if (locationResult.getLastLocation() == null)
                return;
            currentLocation = locationResult.getLastLocation();
            if (firstTimeFlag && googleMap != null) {
                animateCamera(currentLocation);
                firstTimeFlag = false;
            }
            showMarker(currentLocation);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_screen);
        this.setFinishOnTouchOutside(false);


        init();
        initcallback();
        showdialouge(true);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        currentlocation = (ImageButton)findViewById(R.id.currentLocationImageButton);

        currentlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (googleMap != null && currentLocation != null)
                    animateCamera(currentLocation);
            }
        });
       /* mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();*/


    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    *//*@Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }*//*

    @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.disconnect();
    }*/

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        /*map.setLocationSource(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);*/

    }

   /* @Override
    public void onLocationChanged(Location location) {

        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            txtlatitude.setText("Latitude : "+latitude);
            txtlongitude.setText("Longitude : "+longitude);
            //moving the map to location
            moveMap();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getCurrentLocation();


    }


    @Override
    public void onConnectionSuspended(int cause) {
        // Do nothing
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Do nothing
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mMapLocationListener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mMapLocationListener = null;
    }

    //Getting current location
    private void getCurrentLocation() {
        mMap.clear();
        //Creating a location object
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            //Getting longitude and latitude
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            txtlatitude.setText("Latitude : "+latitude);
            txtlongitude.setText("Longitude : "+longitude);
            //moving the map to location
            moveMap();
        }
    }

    private void moveMap() {
        //String to display current latitude and longitude
        String msg = latitude + ", "+longitude;

        //Creating a LatLng Object to store Coordinates
        LatLng latLng = new LatLng(latitude, longitude);
        mMap.clear();
        mMap.getUiSettings().setScrollGesturesEnabled(false);

        // Enable / Disable zooming controls
        mMap.getUiSettings().setZoomControlsEnabled(false);

        // Enable / Disable my location button
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        // Enable / Disable Compass icon
        mMap.getUiSettings().setCompassEnabled(false);

        // Enable / Disable Rotate gesture
        mMap.getUiSettings().setRotateGesturesEnabled(false);

        // Enable / Disable zooming functionality
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        //Adding marker to map
        mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)));


        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(latLng, 16);
        mMap.animateCamera(yourLocation);

    }*/

    public void initcallback()
    {
        btngetlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //getCurrentLocation();
                    txtlatitude.setTag(latitude+","+longitude);
                    if(!txtlatitude.getTag().toString().equalsIgnoreCase("0.0,0.0"))
                        maplocation.setText("Latitude: "+latitude+"\nLongitude: "+longitude);
                    else
                        maplocation.setText("");
                    txtlatitude.setText("Latitude : "+latitude);
                    txtlongitude.setText("Longitude : "+longitude);

                    MyApplication.getInstance().setMapaddress(txtlatitude.getTag().toString().equalsIgnoreCase("0.0,0.0")?"":txtlatitude.getTag().toString());
                    JSONArray formarray = MyApplication.getInstance().getMapformarray();
                    for (int k = 0; k < formarray.length(); k++) {
                        JSONArray questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                        for (int l = 0; l < questionsarray.length(); l++) {
                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(MyApplication.getInstance().getMapuniqueid())) {
                                questionsarray.getJSONObject(l).put("answer_id", MyApplication.getInstance().getMapaddress());
                                questionsarray.getJSONObject(l).put("answer", MyApplication.getInstance().getMapaddress());
                                questionsarray.getJSONObject(l).put("is_other", "");
                            }
                        }
                        formarray.getJSONObject(k).put("questions", questionsarray);
                    }
                    finish();
                    SurveyForm surveyform = new SurveyForm();
                    surveyform.mappagerule();
                }
                catch (Exception ex)
                {
                    Log.e("Address Tag",ex.getMessage().toString());
                }
            }
        });

        imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.getInstance().setMapaddress("");
                try {
                    JSONArray formarray = MyApplication.getInstance().getMapformarray();
                    for (int k = 0; k < formarray.length(); k++) {
                        JSONArray questionsarray = null;
                        questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                        for (int l = 0; l < questionsarray.length(); l++) {
                            if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(MyApplication.getInstance().getMapuniqueid())) {
                                questionsarray.getJSONObject(l).put("answer_id", MyApplication.getInstance().getMapaddress());
                                questionsarray.getJSONObject(l).put("answer", MyApplication.getInstance().getMapaddress());
                                questionsarray.getJSONObject(l).put("is_other", "");
                            }
                        }
                        formarray.getJSONObject(k).put("questions", questionsarray);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    //onBackKeyPressed
    @Override
    public void onBackPressed() {

        super.onBackPressed();

        MyApplication.getInstance().setMapaddress("");
        try {
            JSONArray formarray = MyApplication.getInstance().getMapformarray();
            for (int k = 0; k < formarray.length(); k++) {
                JSONArray questionsarray = null;
                questionsarray = new JSONArray(formarray.getJSONObject(k).getString("questions"));
                for (int l = 0; l < questionsarray.length(); l++) {
                    if (questionsarray.getJSONObject(l).getString("unique_id").toString().equalsIgnoreCase(MyApplication.getInstance().getMapuniqueid())) {
                        questionsarray.getJSONObject(l).put("answer_id", MyApplication.getInstance().getMapaddress());
                        questionsarray.getJSONObject(l).put("answer", MyApplication.getInstance().getMapaddress());
                        questionsarray.getJSONObject(l).put("is_other", "");
                    }
                }
                formarray.getJSONObject(k).put("questions", questionsarray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        finish();
    }

    public void init()
    {
        maplocation = MyApplication.getInstance().getTxtlatlong();
        txtlatitude = (TextView)findViewById(R.id.txtlatitude);
        txtlongitude = (TextView)findViewById(R.id.txtlongitude);
        btngetlocation = (Button)findViewById(R.id.btngetlocation);
        imgclose = (ImageView)findViewById(R.id.img_close);
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status)
            return true;
        else {
            if (googleApiAvailability.isUserResolvableError(status))
                Toast.makeText(this, "Please Install google play services to use this application", Toast.LENGTH_LONG).show();
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED)
                Toast.makeText(this, "Permission denied by uses", Toast.LENGTH_SHORT).show();
            else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                startCurrentLocationUpdates();
        }
    }

    private void animateCamera(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(16).build();
    }

    private void showMarker(@NonNull Location currentLocation) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        if (currentLocationMarker == null) {
            currentLocationMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.flag)).position(latLng));
            googleMap.getUiSettings().setScrollGesturesEnabled(false);

            // Enable / Disable zooming controls
            googleMap.getUiSettings().setZoomControlsEnabled(false);

            // Enable / Disable my location button
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            // Enable / Disable Compass icon
            googleMap.getUiSettings().setCompassEnabled(false);

            // Enable / Disable Rotate gesture
            googleMap.getUiSettings().setRotateGesturesEnabled(false);

            // Enable / Disable zooming functionality
            googleMap.getUiSettings().setZoomGesturesEnabled(false);
        }
        else
            MarkerAnimation.animateMarkerToGB(currentLocationMarker, latLng, new LatLngInterpolator.Spherical());

        latitude = currentLocation.getLatitude();
        longitude = currentLocation.getLongitude();
        txtlatitude.setTag(latitude+","+longitude);
        txtlatitude.setText("Latitude : "+latitude);
        txtlongitude.setText("Longitude : "+longitude);
        showdialouge(false);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isGooglePlayServicesAvailable()) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            startCurrentLocationUpdates();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient = null;
        googleMap = null;
    }

    private void startCurrentLocationUpdates() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(3000);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MapScreen.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                return;
            }
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    private void showdialouge(boolean isshow)
    {
        if(isshow) {
            pDialog = new ProgressDialog(MapScreen.this, R.style.MyAlertDialogStyle);
            pDialog.setMessage("Please Wait getting your Location...");
            pDialog.setIndeterminate(false);
            pDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.vizilogo));
            pDialog.setCanceledOnTouchOutside(false);
            pDialog.show();
        }else
            pDialog.dismiss();
    }
}
package com.example.my.myapplication.IOT.map.apiplace;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.my.myapplication.IOT.map.mapClickDetail.TestMapFragment;
import com.example.my.myapplication.IOT.map.MapsActivity;
import com.example.my.myapplication.R;
import com.example.my.myapplication.IOT.map.dinhvichiduong.ConfigApp;
import com.example.my.myapplication.IOT.map.dinhvichiduong.GPSTracker;
import com.example.my.myapplication.IOT.map.dinhvichiduong.ViTri;
import com.example.my.myapplication.IOT.map.mapapidirection.MapseActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class Maps3Activity extends Fragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private View rootView;
    LayoutInflater inflater;

    Spinner mSprPlaceType;

    String[] mPlaceType = null;
    String[] mPlaceTypeName = null;


    GPSTracker gps;
    SharedPreferences sharedPreferences;

    private GoogleMap mMap;
    double latitude;
    double longitude;
    private int PROXIMITY_RADIUS = 1000;
    private float PROXIMITY_RADIUSPaint = 1000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    ArrayList<LatLng> MarkerPoints;
    String theloai = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.inflater = inflater;
        rootView = inflater.inflate(R.layout.activity_maps, container, false);
        //gps htmynguyen
        gps = new GPSTracker(getContext());

        if (gps.canGetLocation()) {
            Log.d("toadoLAgps==", "" + latitude);
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("toadoLONgps==", "" + longitude);
            String lati = latitude + "";
            String loti = longitude + "";
            createSessionVITRIGPS(lati, loti);
            Toast.makeText(
                    getContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
        MarkerPoints = new ArrayList<>();

        //end gps
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
            Log.d("onCreate", "Finishing test case since Google Play Services are not available");
            getActivity().finish();
        } else {
            Log.d("onCreate", "Google Play Services available.");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        return rootView;
    }

    private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(getContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(getActivity(), result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Array of place types
        mPlaceType = getResources().getStringArray(R.array.place_type);

        // Array of place type names
        mPlaceTypeName = getResources().getStringArray(R.array.place_type_name);

        // Creating an array adapter with an array of Place types
        // to populate the spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, mPlaceTypeName);

        // Getting reference to the Spinner
        mSprPlaceType = (Spinner) rootView.findViewById(R.id.spr_place_type);

        // Setting adapter on Spinner to set place types
        mSprPlaceType.setAdapter(adapter);

        Button btnFind;

        // Getting reference to Find Button
        btnFind = (Button) rootView.findViewById(R.id.btn_find);
        // Setting click event lister for the find button
        btnFind.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int selectedPosition = mSprPlaceType.getSelectedItemPosition();
                String type = mPlaceType[selectedPosition];
                Log.d("find===", type);
                Log.d("onClick", "Button is Clicked");
                mMap.clear();
                theloai = type;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }
                String url = getUrl(latitude, longitude, type);
                //my
//                String url = getUrl(latitudegps, longitudegps, School);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = url;
                Log.d("onClick", url);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);
                Toast.makeText(getContext(), "Nearby " + type, Toast.LENGTH_LONG).show();
                LatLng dinhvitoado = new LatLng(latitude, longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(dinhvitoado);
                markerOptions.title("Current Position");
                //toa do dinh vi
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                Marker marker = mMap.addMarker(markerOptions);
                marker.showInfoWindow();
                CircleOptions options = new CircleOptions();
                options.center(dinhvitoado);
                //Radius in meters
                //        options.radius(100000);
                options.radius(PROXIMITY_RADIUSPaint);
                options.fillColor(getResources()
                        .getColor(R.color.fill_color));
                options.strokeColor(getResources()
                        .getColor(R.color.stroke_color));
                options.strokeWidth(10);
                //        //end ve duong tron
                mMap.addCircle(options);

                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dinhvitoado, 15));
            }
        });


        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        Button map2 = (Button) rootView.findViewById(R.id.maptest);
        map2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new MapsActivity();
                FragmentManager manager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, mFragment).commit();

            }
        });
        Button mapdirection = (Button) rootView.findViewById(R.id.buttondirection);
        mapdirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new MapseActivity();
                FragmentManager manager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, mFragment).commit();
            }
        });

        Button buttondTestMapDevice = (Button) rootView.findViewById(R.id.buttondTestMapDevice);
        buttondTestMapDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment mFragment = new TestMapFragment();
                FragmentManager manager = ((FragmentActivity) getContext()).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.container, mFragment).commit();

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //              LatLng dinhvi = new LatLng(10.848183, 106.772388);
                LatLng dinhvi = new LatLng(latitude, longitude);
                LatLng diemden = marker.getPosition();
                String x = marker.getTitle();
                Log.d("dinhvi=========", dinhvi.latitude + "--" + dinhvi.longitude);
                Log.d("timduongdi=========", diemden.latitude + "--" + diemden.longitude);
                mMap.clear();
                Log.d("TheLoai", theloai);

                String urlbando = getUrl(latitude, longitude, theloai);
                //my
//                String url = getUrl(latitudegps, longitudegps, School);
                Object[] DataTransfer = new Object[2];
                DataTransfer[0] = mMap;
                DataTransfer[1] = urlbando;
                Log.d("onClick", urlbando);
                GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
                getNearbyPlacesData.execute(DataTransfer);

                String url = getUrlchiduong(dinhvi, diemden);
                FetchUrl FetchUrl = new FetchUrl();
                FetchUrl.execute(url);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(diemden);
                markerOptions.title(x);
                //toa do dinh vi
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                Marker marker1 = mMap.addMarker(markerOptions);
                marker1.showInfoWindow();
//thong tin dinh vi gps
                LatLng dinhvitoado = new LatLng(latitude, longitude);
                MarkerOptions markerOptionss = new MarkerOptions();
                markerOptions.position(dinhvitoado);
                markerOptions.title("Current Position");
                //toa do dinh vi
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                Marker markers = mMap.addMarker(markerOptions);
                marker1.showInfoWindow();
                CircleOptions options = new CircleOptions();
                options.center(dinhvitoado);
                //Radius in meters
                //        options.radius(100000);
                options.radius(PROXIMITY_RADIUSPaint);
                options.fillColor(getResources()
                        .getColor(R.color.fill_color));
                options.strokeColor(getResources()
                        .getColor(R.color.stroke_color));
                options.strokeWidth(10);
                //        //end ve duong tron
                mMap.addCircle(options);
                return true;
            }
        });

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    //  https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=10.848183,106.772388&radius=1000000&type=hospital&sensor=true&key=AIzaSyDmeF_gnUCRdVLV9JXGPs4BJ99v-Z0p2zU
    private String getUrl(double latitude, double longitude, String nearbyPlace) {

        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
//        googlePlacesUrl.append("location=" + "10.848183" + "," + "106.772388");
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        //googlePlacesUrl.append("&key=" + "AIzaSyDmeF_gnUCRdVLV9JXGPs4BJ99v-Z0p2zU");
        googlePlacesUrl.append("&key=" + "AIzaSyDD9PhWNv_d8TEczH0l5HjkwNcmj90JwWM");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        //toa do dinh vi
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        Marker marker = mCurrLocationMarker = mMap.addMarker(markerOptions);
        mMap.setInfoWindowAdapter(new MyInfoWindowAdapter(inflater));
        marker.showInfoWindow();
        //move map camera
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
//htmynguyen
        LatLng dinhvitoado = new LatLng(latitude, longitude);

        CircleOptions options = new CircleOptions();
        options.center(dinhvitoado);
        //Radius in meters
        //        options.radius(100000);
        options.radius(PROXIMITY_RADIUSPaint);
        options.fillColor(getResources()
                .getColor(R.color.fill_color));
        options.strokeColor(getResources()
                .getColor(R.color.stroke_color));
        options.strokeWidth(10);
        //        //end ve duong tron
        mMap.addCircle(options);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dinhvitoado, 15));
        //end htmynguyen
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    // tao session vitriGPS
    public void createSessionVITRIGPS(String laGPS, String loGPS) {
        sharedPreferences = getActivity().getSharedPreferences(ConfigApp.getVITRIGPS(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ViTri vitriGPS = new ViTri(laGPS, loGPS);
        editor.putString("VITRIGPSla", laGPS);
        editor.putString("VITRIGPSlo", loGPS);
        Log.d("toadoLAgps11========", laGPS);
        Log.d("toadoLONgps====", loGPS);
        editor.commit();
    }

    private String getUrlchiduong(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        return url;

    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask", jsonData[0].toString());
                com.example.my.myapplication.IOT.map.mapapidirection.DataParser parser = new com.example.my.myapplication.IOT.map.mapapidirection.DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask", "Executing routes");
                Log.d("ParserTask", routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask", e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);

                Log.d("onPostExecute", "onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
            } else {
                Log.d("onPostExecute", "without Polylines drawn");
            }
        }
    }


}



package com.example.my.myapplication.IOT.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.my.myapplication.R;
import com.example.my.myapplication.IOT.map.dinhvichiduong.ConfigApp;
import com.example.my.myapplication.IOT.map.dinhvichiduong.GPSTracker;
import com.example.my.myapplication.IOT.map.dinhvichiduong.ViTri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends Fragment implements OnMapReadyCallback {
    private View rootView;

    private GoogleMap mMap;
    GPSTracker gps;
    SharedPreferences sharedPreferences;

    double latitude;
    double longitude;
    double latitudegps;
    double longitudegps;
    private int PROXIMITY_RADIUS = 10000;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.activity_maps, container, false);

        gps = new GPSTracker(getContext());

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("toadoLAgps==", "" + latitude);
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
        //end gps

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        return rootView;
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

        CircleOptions options = new CircleOptions();
        LatLng sydney = new LatLng(latitude, longitude);

        options.center(sydney);
        //Radius in meters
        options.radius(1000);
//        options.radius(PROXIMITY_RADIUS);
        options.fillColor(getResources()
                .getColor(R.color.fill_color));
        options.strokeColor(getResources()
                .getColor(R.color.stroke_color));
        options.strokeWidth(10);
//        //end ve duong tron
        mMap.addMarker(new MarkerOptions().position(sydney).title("This is My"));
        mMap.addCircle(options);

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20));
    }
    // tao session vitriGPS
    public void createSessionVITRIGPS(String laGPS, String loGPS) {
        sharedPreferences = getActivity().getSharedPreferences(ConfigApp.getVITRIGPS(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("VITRIGPSla", laGPS);
        editor.putString("VITRIGPSlo", loGPS);
        Log.d("toadoLAgps11========", laGPS);
        Log.d("toadoLONgps====", loGPS);
        editor.commit();
    }
}

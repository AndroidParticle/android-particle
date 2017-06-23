package com.example.my.myapplication.IOT.map.mapClickDetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my.myapplication.IOT.IOTDeviceListFragment;
import com.example.my.myapplication.IOT.IOTGridviewFragment;
import com.example.my.myapplication.IOT.IOTSearchListFragment;
import com.example.my.myapplication.IOT.LoginActivity;
import com.example.my.myapplication.IOT.map.dinhvichiduong.ConfigApp;
import com.example.my.myapplication.IOT.map.dinhvichiduong.GPSTracker;
import com.example.my.myapplication.IOT.map.dinhvichiduong.ViTri;
import com.example.my.myapplication.IOT.materialprofile.InfoDeviceFragment;
import com.example.my.myapplication.IOT.server.models.Device;
import com.example.my.myapplication.IOT.server.service.ServiceDevice;
import com.example.my.myapplication.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TestMapFragmentTemp extends Fragment implements OnMapReadyCallback {
    private View rootView;
    //gps
    GPSTracker gps;
    SharedPreferences sharedPreferences;
    private ServiceDevice serviceDevice;
    ArrayList<Device> listDevice = null;
    double latitude;
    double longitude;
    //end gps
    // GoogleMap mMap;
    // MapView mMapView;
    // Context context;

    //demo2
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private Button infoButton1, infoButton2;
    private OnInfoWindowElemTouchListener infoButtonListener;
    private OnInfoWindowElemTouchListener infoButtonListener1;

    //static final LatLng latlng1 = new LatLng(10.81,106.9);
    // static final LatLng latlng2 = new LatLng(10.81, 106.6);
    LatLng locationGPS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //context = getContext();
        String strtext = "";
        try {
            strtext = getArguments().getString("message");
            Log.e("TAG", "Inflate exception-----------------" + strtext);
        } catch (Exception x) {
        }

        try {
            rootView = inflater.inflate(R.layout.fragment_test_map, container, false);
        } catch (InflateException e) {
            Log.e("TAG", "Inflate exception");
        }
        if (serviceDevice == null) {
            serviceDevice = new ServiceDevice();
        }

        listDevice = IOTSearchListFragment.checkSessionListDevice(getContext(), sharedPreferences);
        if (listDevice == null) {
            listDevice = LoginActivity.createSeesionListDevice(getContext(), sharedPreferences, serviceDevice, "");
            Log.d("listcreate", listDevice.get(0).getNameDevice());

        }
        Log.d("listdevice", listDevice.get(0).getNameDevice());

        //gps htmynguyen
        gps = new GPSTracker(getContext());

        if (gps.canGetLocation()) {
//            double latitude = gps.getLatitude();
//            double longitude = gps.getLongitude();
//            latitudegps = gps.getLatitude();
//            longitudegps = gps.getLongitude();
            Log.d("toadoLAgps==", "" + latitude);
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Log.d("toadoLONgps==", "" + longitude);
            String lati = latitude + "";
            String loti = longitude + "";
            createSessionVITRIGPS(lati, loti);
            locationGPS = new LatLng(latitude, longitude);
            Toast.makeText(
                    getContext(),
                    "Your Location is -\nLat: " + latitude + "\nLong: "
                            + longitude, Toast.LENGTH_LONG).show();
        } else {
            gps.showSettingsAlert();
        }
        // MarkerPoints = new ArrayList<>();
//        LatLng point = new LatLng(gps.getLatitude(), gps.getLongitude());
//        MarkerPoints.add(point);

        //end gps

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapclickmore);

        //final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout) rootView.findViewById(R.id.map_relative_layout);
        final GoogleMap googleMap = mapFragment.getMap();

        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(googleMap, getPixelsFromDp(getContext(), 39 + 20));

        // We want to reuse the info window for all the markers,
        // so let's create only one class member instance
        // this.infoWindow = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_infowindow, null);
        this.infoWindow = (ViewGroup) inflater.inflate(R.layout.custom_infowindow, null);
        this.infoTitle = (TextView) infoWindow.findViewById(R.id.nameTxt);
        this.infoSnippet = (TextView) infoWindow.findViewById(R.id.addressTxt);
        this.infoButton1 = (Button) infoWindow.findViewById(R.id.btnOne);
        this.infoButton2 = (Button) infoWindow.findViewById(R.id.btnTwo);

        // Setting custom OnTouchListener which deals with the pressed state
        // so it shows up
        this.infoButtonListener1 = new OnInfoWindowElemTouchListener(infoButton1, getResources().getDrawable(R.color.colorPrimary), getResources().getDrawable(R.color.nliveo_white)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                String nameDevice = marker.getTitle();
                Device device = getDeviceByName(listDevice, nameDevice);
                IOTDeviceListFragment.createSeesionDeviceInfo(getContext(), sharedPreferences, device);
                reloadIOTGridviewFragment(getContext());
                Toast.makeText(getContext(), nameDevice, Toast.LENGTH_LONG).show();
            }
        };
        this.infoButton1.setOnTouchListener(infoButtonListener1);

        this.infoButtonListener = new OnInfoWindowElemTouchListener(infoButton2, getResources().getDrawable(R.color.colorPrimary), getResources().getDrawable(R.color.nliveo_white)) {
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                String nameDevice = marker.getTitle();
                Device device = getDeviceByName(listDevice, nameDevice);
                IOTDeviceListFragment.createSeesionDeviceInfo(getContext(), sharedPreferences, device);
                InfoDeviceFragment.reloadInfoDeviceFragment(getContext());
                Toast.makeText(getContext(), nameDevice, Toast.LENGTH_LONG).show();
            }
        };
        infoButton2.setOnTouchListener(infoButtonListener);

        /*infoWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "click on infowindow", Toast.LENGTH_LONG).show();
            }
        });*/

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Setting up the infoWindow with current's marker info
                infoTitle.setText(marker.getTitle());
                infoSnippet.setText(marker.getSnippet());
                infoButtonListener.setMarker(marker);
                infoButtonListener1.setMarker(marker);

                // We must call this to set the current marker and infoWindow references
                // to the MapWrapperLayout
                mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);
                return infoWindow;
            }
        });

        // Let's add a couple of markers
        Log.i("ListDEVICEGPS", locationGPS.latitude + " : " + locationGPS.longitude);

        googleMap.addMarker(new MarkerOptions()
                .position(locationGPS)
                .title("THIS IS MY")
                .snippet(locationGPS.latitude + " : " + locationGPS.longitude)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        int len = listDevice.size();
        for (int i = 0; i < len; i++) {
            Log.i("ListDEVICEDir", listDevice.get(i).getNameDevice() + " - " + listDevice.get(i).getLatitude() + " : " + listDevice.get(i).getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(listDevice.get(i).getLatitude(), listDevice.get(i).getLongitude()))
                    .title(listDevice.get(i).getNameDevice())
                    .snippet(listDevice.get(i).getLocation())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }
      /*  googleMap.addMarker(new MarkerOptions()
                .position(latlng1)
                .title("Source")
                .snippet("Comapny Name")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        googleMap.addMarker(new MarkerOptions()
                .position(latlng2)
                .title("Destination")
                .snippet("AmisunXXXXXX")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));*/

        //googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationGPS, 10));
        return rootView;

    }

    public static Device getDeviceByName(ArrayList<Device> listDevice, String nameDevice) {
        int len = listDevice.size();
        for (int i = 0; i < len; i++) {
            if (listDevice.get(i).getNameDevice().equals(nameDevice))
                return listDevice.get(i);
        }
        return null;
    }

    public static void reloadIOTGridviewFragment(Context context) {
        Fragment mFragment;
        mFragment = new IOTGridviewFragment();
        FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, mFragment).commit();
    }

    // tao session vitriGPS
    public void createSessionVITRIGPS(String laGPS, String loGPS) {
        sharedPreferences = getActivity().getSharedPreferences(ConfigApp.getVITRIGPS(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ViTri vitriGPS = new ViTri(laGPS, loGPS);
        //  editor.putString(ConfigApp.getVitriGPSla(), vitriGPS);
        editor.putString("VITRIGPSla", laGPS);
        editor.putString("VITRIGPSlo", loGPS);
        Log.d("toadoLAgps11========", laGPS);
        Log.d("toadoLONgps====", loGPS);
        editor.commit();
    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
/*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getContext();
        String strtext = "";
        try {
            strtext = getArguments().getString("message");
            Log.e("TAG", "Inflate exception-----------------" + strtext);
        } catch (Exception x) {
        }

        try {
            rootView = inflater.inflate(R.layout.fragment_test_map, container, false);
            MapsInitializer.initialize(this.getActivity());
            mMapView = (MapView) rootView.findViewById(R.id.mapgms);
            mMapView.onCreate(savedInstanceState);
            mMapView.getMapAsync(this);
        } catch (InflateException e) {
            Log.e("TAG", "Inflate exception");
        }
        TextView textViewmap = (TextView) rootView.findViewById(R.id.textViewmap);
        textViewmap.setText(strtext);
        return rootView;
    }*/

    @Override
    public void onPause() {
        super.onPause();
        // mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // mMapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //  mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        //  mMapView.onLowMemory();
    }

    @Override
    public void onResume() {
        super.onResume();
        // mMapView.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
//todo
//        mMap = googleMap;
        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
 /*
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4233438, -122.0728817))
                .title("LinkedIn")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4629101, -122.2449094))
                .title("Facebook")
                .snippet("Facebook HQ: Menlo Park"));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.3092293, -122.1136845))
                .title("Apple"));

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));
        mMap = googleMap;
*/
    }


}

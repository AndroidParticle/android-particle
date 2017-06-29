package com.example.my.myapplication.IOT.map.apiplace;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.my.myapplication.R;

//import com.example.my.map.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by MY on 8/10/2016.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater context;

    public MyInfoWindowAdapter(LayoutInflater inflater ) {
        this.context = inflater;
    }

    @Override
    public View getInfoContents(Marker arg0) {
        View v = this.context.inflate(R.layout.custom_info, null);
        LatLng latLng = arg0.getPosition();
// Getting reference to the TextView to set latitude
        TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
// Getting reference to the TextView to set longitude
        TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);
        TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
// Setting the latitude
        tvLat.setText("Latitude:" + latLng.latitude);
// Setting the longitude
        tvLng.setText("Longitude:" + latLng.longitude);
        tvTitle.setText(arg0.getTitle());
        final EditText comment= (EditText) v.findViewById(R.id.comment);
        final String comments=comment.getText().toString();
        Button buttonComment= (Button) v.findViewById(R.id.sendComment);
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return v;
    }

    @Override
    public View getInfoWindow(Marker arg0) {
        return null;
    }


}

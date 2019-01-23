package com.example.anonymous.tomatoesapp.UI.fragments;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.anonymous.tomatoesapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.nio.BufferUnderflowException;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {
    private static final int DEFAULT_ZOOM = 15;
    private GoogleMap mMap;
private Button sendmessageBtn;
private EditText resultsEdittext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        sendmessageBtn=(Button) findViewById(R.id.sendmessageBtn);
        resultsEdittext=(EditText) findViewById(R.id.messagetxt);
        sendmessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(resultsEdittext.getText().toString()!=""){
                    Intent emailintent =new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","tomatoe@info.com",null));
                    emailintent.putExtra(Intent.EXTRA_SUBJECT,"Customer Feedback " );
                    emailintent.putExtra(Intent.EXTRA_TEXT,resultsEdittext.getText().toString());
                    startActivity(Intent.createChooser(emailintent,"Choose am a mail sender"));
                }
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
        
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-0.395541, 36.962156);
        mMap.addMarker(new MarkerOptions().position(sydney).title("TomatoesCompany: Kenya,Nyeri,Alexandria Hostel"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(sydney.latitude,
                        sydney.longitude), DEFAULT_ZOOM));
    }
}

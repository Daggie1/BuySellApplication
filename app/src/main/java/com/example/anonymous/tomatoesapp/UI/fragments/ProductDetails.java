package com.example.anonymous.tomatoesapp.UI.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.model.TomatoesModel;
import com.example.anonymous.tomatoesapp.mpesa.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ProductDetails extends Fragment implements OnMapReadyCallback{

TextView product_name,product_price,product_description,product_marker;
ImageView product_image;
Button buy_btn;




double lat,longt;
    String selectedproduct_id,marker;
    private GoogleMap mMap;
    public ProductDetails() {
        // Required empty public constructor
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("khkj"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedproduct_id=getActivity().getIntent().getStringExtra("selectedproduct_id");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_product_details, container, false);


       product_name=(TextView) view.findViewById(R.id.details_product_name);
        product_description=(TextView) view.findViewById(R.id.details_product_desc);
        product_price=(TextView) view.findViewById(R.id.details_product_price);
        buy_btn=(Button) view.findViewById(R.id.details_buy_btn);
        product_image=(ImageView) view.findViewById(R.id.details_product_image);
product_marker=(TextView) view.findViewById(R.id.markertxt);


        SupportMapFragment mapFragment = (SupportMapFragment)getActivity().getSupportFragmentManager()
                .findFragmentById(R.id.details_product_map);
       mapFragment.getMapAsync(this);
        loadContent(selectedproduct_id);
       return view;
    }



public void loadContent(String selectedproduct){
    Query dbRef= FirebaseDatabase.getInstance().getReference("PostedProducts").orderByChild("productId").equalTo(selectedproduct);
    dbRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.exists()){
                for(DataSnapshot tomatoSnap:dataSnapshot.getChildren()){
                TomatoesModel tomatoesModel=tomatoSnap.getValue(TomatoesModel.class);

                    product_name.setText(tomatoesModel.getProductName());
                    product_price.setText(tomatoesModel.getPrice());
                    product_description.setText(tomatoesModel.getDescription());
                    lat=Double.parseDouble(tomatoesModel.getLat());
                    longt=Double.parseDouble(tomatoesModel.getLongt());
                    marker=tomatoesModel.getMapmarker_name();
                    product_marker.setText(tomatoesModel.getMapmarker_name());
                    Glide.with(getActivity()).load(tomatoesModel.getProduct_pictureUrl()).into(product_image);

                    Toast.makeText(getActivity(),selectedproduct_id,Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

}



}

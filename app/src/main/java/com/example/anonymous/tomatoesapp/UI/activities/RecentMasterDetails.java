package com.example.anonymous.tomatoesapp.UI.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.UI.fragments.ProductDetails;
import com.example.anonymous.tomatoesapp.adapters.fragmentActivityLinker;
import com.example.anonymous.tomatoesapp.model.TomatoesModel;
import com.example.anonymous.tomatoesapp.model.User;
import com.example.anonymous.tomatoesapp.mpesa.MainActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RecentMasterDetails extends fragmentActivityLinker  implements OnMapReadyCallback{
    double lat,longt;
    String selectedproduct_id,marker;
    private GoogleMap mMap;
    TomatoesModel mTomatoesModel;
    TextView product_name,product_price,product_description,product_marker,quantity;
    ImageView product_image;
    Button buy_btn,chatBtn;
    private static final int DEFAULT_ZOOM = 15;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selectedproduct_id=getIntent().getStringExtra("selectedproduct_id");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_product_details);
        product_name=(TextView) findViewById(R.id.details_product_name);
        product_description=(TextView)findViewById(R.id.details_product_desc);
        product_price=(TextView) findViewById(R.id.details_product_price);
        buy_btn=(Button) findViewById(R.id.details_buy_btn);
        product_image=(ImageView) findViewById(R.id.details_product_image);
        product_marker=(TextView) findViewById(R.id.markertxt);
        quantity=(TextView) findViewById(R.id.quantitytxt);
chatBtn=(Button)findViewById(R.id.details_chat_btn);
        loadContent(selectedproduct_id);
        buy_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent newintent=new Intent(RecentMasterDetails.this, MainActivity.class);
                        newintent.putExtra("selectedproduct_name",mTomatoesModel.getProductName());
                        newintent.putExtra("selectedproduct_price",mTomatoesModel.getPrice());
                        newintent.putExtra("selectedproduct_desc",mTomatoesModel.getDescription());
                        newintent.putExtra("selectedproduct_sellersloc",mTomatoesModel.getMapmarker_name());
                        newintent.putExtra("selectedproduct_picurl",mTomatoesModel.getProduct_pictureUrl());
                        newintent.putExtra("selectedproduct_qty",mTomatoesModel.getQuantity());
                        startActivity(newintent);
                    }
                });
        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("uuid").equalTo(mTomatoesModel.getSellerId());
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      if(dataSnapshot.exists()){
                          for(DataSnapshot userSnap:dataSnapshot.getChildren()){
                              User user =userSnap.getValue(User.class);
                              String url="https://api.whatsapp.com/send?phone="+user.getPhone();
                              Intent i=new Intent(Intent.ACTION_VIEW);
                              i.setData(Uri.parse(url));
                              startActivity(i);
                          /*(Intent emailintent =new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",user.getEmail(),null));
                          emailintent.putExtra(Intent.EXTRA_SUBJECT,"Tomatoe App product "+mTomatoesModel.getProductName() );
                              emailintent.putExtra(Intent.EXTRA_TEXT,"hey lets talk about your product "+mTomatoesModel.getProductName());
                              startActivity(Intent.createChooser(emailintent,"Choose am a mail sender"));*/

                          }
                      }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.details_product_map);
        mapFragment.getMapAsync(this);


    }








    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(lat, longt);
        mMap.addMarker(new MarkerOptions().position(sydney).title(marker));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(sydney.latitude,
                        sydney.longitude), DEFAULT_ZOOM));
    }





    public void loadContent(String selectedproduct){
        Query dbRef= FirebaseDatabase.getInstance().getReference("PostedProducts").orderByChild("productId").equalTo(selectedproduct);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot tomatoSnap:dataSnapshot.getChildren()){
                        TomatoesModel tomatoesModel=tomatoSnap.getValue(TomatoesModel.class);
mTomatoesModel=tomatoesModel;
                        product_name.setText(tomatoesModel.getProductName());
                        product_price.setText(tomatoesModel.getPrice());
                        product_description.setText(tomatoesModel.getDescription());
                        lat=Double.parseDouble(tomatoesModel.getLat());
                        String qq=tomatoesModel.getQuantity()+"Kgs";
                        quantity.setText(qq);
                        longt=Double.parseDouble(tomatoesModel.getLongt());
                        marker=tomatoesModel.getMapmarker_name();
                        product_marker.setText(tomatoesModel.getMapmarker_name());
                        Glide.with(RecentMasterDetails.this).load(tomatoesModel.getProduct_pictureUrl()).into(product_image);

                        Toast.makeText(RecentMasterDetails.this,selectedproduct_id,Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



}




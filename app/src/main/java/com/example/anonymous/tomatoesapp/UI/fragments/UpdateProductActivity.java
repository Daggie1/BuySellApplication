package com.example.anonymous.tomatoesapp.UI.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.UI.activities.HomeActivity;
import com.example.anonymous.tomatoesapp.model.TomatoesModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateProductActivity extends Fragment {


    public UpdateProductActivity() {
        // Required empty public constructor
    }
        TextView product_nametxt, product_pricetxt,product_descriptiontxt;
        ImageView product_image;
        Button update_btn,del_Btn;


    String selectedproduct;


        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
          selectedproduct=getActivity().getIntent().getStringExtra("selectedproduct_Id");
            loadContent(selectedproduct);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view= inflater.inflate(R.layout.fragment_update_product, container, false);
            product_nametxt=(TextView) view.findViewById(R.id.update_product_name);
            product_descriptiontxt=(TextView) view.findViewById(R.id.update_product_desc);
            product_pricetxt=(TextView) view.findViewById(R.id.update_product_price);
            update_btn=(Button) view.findViewById(R.id.update_btn);
            del_Btn=(Button) view.findViewById(R.id.delete_btn);
            product_image=(ImageView) view.findViewById(R.id.update_product_image);
del_Btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        deleteAproduct();
    }
});
            update_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updatingProducts();
                }
            });

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
                            product_nametxt.setText(tomatoesModel.getProductName());
                            product_pricetxt.setText(tomatoesModel.getPrice());
                            product_descriptiontxt.setText(tomatoesModel.getDescription());
                            Glide.with(getActivity()).load(tomatoesModel.getProduct_pictureUrl()).into(product_image);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

public void updatingProducts(){

            final String product_name=product_nametxt.getText().toString();
            final String product_id=product_nametxt.getText().toString();
            final String product_urll="url";
            final String product_price=product_pricetxt.getText().toString();
            final String product_desc=product_descriptiontxt.getText().toString();
            final Query dbquery=FirebaseDatabase.getInstance().getReference("PostedProducts").orderByChild("productId").equalTo(selectedproduct);
            dbquery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot productsnapshot:dataSnapshot.getChildren()){

                            updateproduct(dbquery.getRef(),product_id,product_name,product_desc,product_price,product_urll);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
}
    private void updateproduct(DatabaseReference db, String product_id, String product_name,String product_price,String product_desc,String product_picurl) {

            TomatoesModel tomatoesModel=new TomatoesModel(product_name,product_desc,product_id,product_price,product_picurl);
        Map<String, Object> postValues = tomatoesModel.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/PostedProducts/" + product_id, postValues);
        db.updateChildren(childUpdates);
    }
    public void deleteAproduct(){
        Query dbRef= FirebaseDatabase.getInstance().getReference("PostedProducts").orderByChild("productId").equalTo(selectedproduct);
        dbRef .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot deletesnap:dataSnapshot.getChildren()){
                        deletesnap.getRef().removeValue();
                        new MyTomatoesFragment().myAdapter.notifyDataSetChanged();
                        startActivity(new Intent(getActivity(), HomeActivity.class));

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        })      ;
    }
}

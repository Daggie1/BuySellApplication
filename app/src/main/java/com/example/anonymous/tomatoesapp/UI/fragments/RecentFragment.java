package com.example.anonymous.tomatoesapp.UI.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.UI.activities.RecentMasterDetails;
import com.example.anonymous.tomatoesapp.mpesa.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.example.anonymous.tomatoesapp.model.TomatoesModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment {

    ArrayList<TomatoesModel> tomatoeslistitems = new ArrayList<>();
    RecyclerView mRecyclerView;
    TomatoesModel mTomatoesModel;
    RecyclerView.LayoutManager layoutManager;
    MyAdapter myAdapter=new MyAdapter(tomatoeslistitems);
    public RecentFragment() {
        // Required empty public constructor
    }



    @Override
    public void onResume() {
        super.onResume();

        updateRecyclerView();
    }

    @Override
    public void setRetainInstance(boolean retain) {
        super.setRetainInstance(retain);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

             View view=   inflater.inflate(R.layout.fragment_recent, container, false);
             mRecyclerView=(RecyclerView) view.findViewById(R.id.recentRecyclerview);
             layoutManager=new LinearLayoutManager(getActivity());
        LinearLayoutManager MyLayoutManager = new GridLayoutManager(getActivity(),2);
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
             mRecyclerView.setHasFixedSize(true);

             if(tomatoeslistitems.size()<=0){
                 mRecyclerView.setAdapter(myAdapter);
             }
        mRecyclerView.setLayoutManager(MyLayoutManager);
             return    view;
    }
    public void updateRecyclerView(){
        DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference("PostedProducts");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    tomatoeslistitems.clear();
                    for (DataSnapshot tomatoesSnapShot:dataSnapshot.getChildren()){

                        TomatoesModel tomatoesModel=tomatoesSnapShot.getValue(TomatoesModel.class);
                        if(tomatoesModel.getSellerId()!= FirebaseAuth.getInstance().getCurrentUser().getUid()){
                        tomatoeslistitems.add(tomatoesModel);}
                    }
                   myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<TomatoesModel> list;

        public MyAdapter(ArrayList<TomatoesModel> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
            LayoutInflater layoutInflater=LayoutInflater.from(getActivity());
            // create a new view

            return new MyViewHolder(layoutInflater,parent);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
           TomatoesModel tomatoe=tomatoeslistitems.get(position);
           holder.bind(tomatoe);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {

        public TextView productName,productPrice;
        Button buy_btn;
        public ImageView productImage;
TomatoesModel mtomatoesModel;

        public MyViewHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.tomatoes_recycler_item,parent,false));
buy_btn=(Button) itemView.findViewById(R.id.add_to_cartBtn);
            productName=(TextView) itemView.findViewById(R.id.productName);
            productPrice=(TextView) itemView.findViewById(R.id.productPrice);
          productImage=(ImageView) itemView.findViewById(R.id.productsImageview);
          itemView.setOnClickListener(this);
          buy_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Intent newintent=new Intent(getActivity(), MainActivity.class);
                  newintent.putExtra("selectedproduct_name",mtomatoesModel.getProductName());
                  newintent.putExtra("selectedproduct_price",mtomatoesModel.getPrice());
                  newintent.putExtra("selectedproduct_desc",mtomatoesModel.getDescription());
                  newintent.putExtra("selectedproduct_sellersloc",mtomatoesModel.getMapmarker_name());
                  newintent.putExtra("selectedproduct_picurl",mtomatoesModel.getProduct_pictureUrl());
                  newintent.putExtra("selectedproduct_qty",mtomatoesModel.getQuantity());
                  startActivity(newintent);
              }
          });

        }
        public void bind(TomatoesModel tomatoeModel){
            mtomatoesModel=tomatoeModel;
            productName.setText(mtomatoesModel.getProductName());
            productPrice.setText(mtomatoesModel.getPrice());
            Glide.with(getActivity()).load(mtomatoesModel.getProduct_pictureUrl()).into(productImage);

        }

        @Override
        public void onClick(View view) {

            Intent newintent=new Intent(getActivity(),RecentMasterDetails.class);
            newintent.putExtra("selectedproduct_id",mtomatoesModel.getProductId());
            startActivity(newintent);
        }
    }
    }


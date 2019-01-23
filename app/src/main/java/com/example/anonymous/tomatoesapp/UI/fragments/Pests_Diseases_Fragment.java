package com.example.anonymous.tomatoesapp.UI.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.UI.activities.ServiesActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Pests_Diseases_Fragment extends Fragment implements View.OnClickListener {
ImageView img_about_us,img_contact_us,img_advice,img_goodframing,img_our_team,img_pest_diseases;

    public Pests_Diseases_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
   View view= inflater.inflate(R.layout.fragment_pests_diseases, container, false);
        img_about_us=(ImageView)view.findViewById(R.id.about_us_image);
        img_contact_us=(ImageView)view.findViewById(R.id.contact_us_image);
        img_advice=(ImageView)view.findViewById(R.id.advice_image);
        img_goodframing=(ImageView)view.findViewById(R.id.good_farming_image);
        img_our_team=(ImageView)view.findViewById(R.id.our_team_image);
        img_pest_diseases=(ImageView)view.findViewById(R.id.pest_desiese_image);

        img_pest_diseases.setOnClickListener(this);
        img_about_us.setOnClickListener(this);
        img_advice.setOnClickListener(this);
        img_goodframing.setOnClickListener(this);
        img_contact_us.setOnClickListener(this);
        img_our_team.setOnClickListener(this);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        //mrecyclerview.setLayoutManager(gridLayoutManager);

   return  view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.advice_image:
                startActivity(new Intent(getActivity(),AdviceFrament.class));
                break;
            case R.id.contact_us_image:
                startActivity(new Intent(getActivity(),MapsActivity2.class));
                break;
            case R.id.our_team_image:
                startActivity(new Intent(getActivity(),OurTeamFragment.class));
                break;
            case R.id.about_us_image:
                startActivity(new Intent(getActivity(),AboutUsFragment.class));
                break;
            case R.id.good_farming_image:
                startActivity(new Intent(getActivity(),GoodFarmingFragment.class));
                break;
            case R.id.pest_desiese_image:
                startActivity(new Intent(getActivity(),PestAndDiseasesFragment.class));
                break;
                default:
                    startActivityHere("default");
        }

    }

    private void startActivityHere(String text) {
        Intent newintent=new Intent(getActivity(),ServiesActivity.class);
        newintent.putExtra("text",text);
        startActivity(newintent);
    }
}

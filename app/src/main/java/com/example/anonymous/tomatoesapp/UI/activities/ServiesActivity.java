package com.example.anonymous.tomatoesapp.UI.activities;

import android.os.Bundle;

import com.example.anonymous.tomatoesapp.UI.fragments.GoodFarmingFragment;
import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.UI.fragments.AboutUsFragment;
import com.example.anonymous.tomatoesapp.UI.fragments.AdviceFrament;
import com.example.anonymous.tomatoesapp.UI.fragments.ContactUsFragment;
import com.example.anonymous.tomatoesapp.UI.fragments.OurTeamFragment;
import com.example.anonymous.tomatoesapp.UI.fragments.Pests_Diseases_Fragment;
import com.example.anonymous.tomatoesapp.adapters.fragmentActivityLinker;

public class ServiesActivity extends fragmentActivityLinker {
String selectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selectedFragment=getIntent().getStringExtra("text");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servies);

}}

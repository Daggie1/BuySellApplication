package com.example.anonymous.tomatoesapp.UI.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.UI.fragments.UpdateProductActivity;
import com.example.anonymous.tomatoesapp.adapters.fragmentActivityLinker;

public class UpdateActivity extends fragmentActivityLinker {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
addFragment(new UpdateProductActivity(),R.id.update_fragment_Container);
    }
}

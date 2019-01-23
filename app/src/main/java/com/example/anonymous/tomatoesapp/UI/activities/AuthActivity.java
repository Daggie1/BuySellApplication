package com.example.anonymous.tomatoesapp.UI.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.anonymous.tomatoesapp.UI.fragments.Login;
import com.example.anonymous.tomatoesapp.adapters.fragmentActivityLinker;
import com.example.anonymous.tomatoesapp.R;

public class AuthActivity extends fragmentActivityLinker {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        addFragment(new Login(),R.id.auth_containerfragment);
    }
}

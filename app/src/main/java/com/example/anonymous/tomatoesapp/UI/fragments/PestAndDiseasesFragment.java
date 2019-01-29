package com.example.anonymous.tomatoesapp.UI.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.anonymous.tomatoesapp.R;
import com.example.anonymous.tomatoesapp.UI.activities.DiagnosisActivity;


public class PestAndDiseasesFragment extends AppCompatActivity {

    private Button diagnosis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pest_and_diseases);
        diagnosis=(Button) findViewById(R.id.diagnosis);
diagnosis.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(PestAndDiseasesFragment.this, DiagnosisActivity.class));
    }
});
    }
}

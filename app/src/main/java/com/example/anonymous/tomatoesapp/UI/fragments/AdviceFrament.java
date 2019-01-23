package com.example.anonymous.tomatoesapp.UI.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.anonymous.tomatoesapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AdviceFrament extends AppCompatActivity {
Button process_btn;
EditText sizeOfLandtxt;
TextView resultstxt;
String fertilizerRequired="100kg";




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_advice_frament);
        process_btn=(Button) findViewById(R.id.processbtn);
        resultstxt=(TextView) findViewById(R.id.result_txt);
        sizeOfLandtxt=(EditText) findViewById(R.id.size_of_land);
        process_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sizeOfLandtxt.getText().toString()!=""){
                    calCulateAdAdvice(Integer.parseInt(sizeOfLandtxt.getText().toString()));
                    resultstxt.setText("The recommended amount of fertilizer for your farm is "+fertilizerRequired);
                }

            }
        });

    }
public void calCulateAdAdvice(int size_Of_land){
    if(size_Of_land<=0.5){
        fertilizerRequired="between30kg-38kg";
        return;
    }
    else if(size_Of_land<=1){
        fertilizerRequired="between40kg-45300kg";
        return;
    }
    else if(size_Of_land<=5){
        fertilizerRequired="between40kg-50kg";
        return;
    }
    else if(size_Of_land<=10){
        fertilizerRequired="between50kg-80kg";
        return;
    }
    else if(size_Of_land<=20){
        fertilizerRequired="between100kg-300kg";
        return;
    }
   else if(size_Of_land<=40){
        fertilizerRequired="between300kg-600kg";
        return;
    }
    else if(size_Of_land<=55){
        fertilizerRequired="between500kg-700kg";
        return;
    }
    else if(size_Of_land<=75){
        fertilizerRequired="between700kg-1100kg";
        return;
    }
    else if(size_Of_land<=85){
        fertilizerRequired="between1000kg-1300kg";
        return;
    }
    else if(size_Of_land<=100){
        fertilizerRequired="between1300kg-1700kg";
        return;
    }
    else if(size_Of_land<=150){
        fertilizerRequired="between1800kg-2300kg";
        return;
    }
    else if(size_Of_land<=200){
        fertilizerRequired="between2900kg-3300kg";
        return;
    }
    else if(size_Of_land<=500){
        fertilizerRequired="between4000kg-4800kg";
        return;
    }
    else if(size_Of_land>500){
        fertilizerRequired="between5000kg---10000kg";
        return;
    }

}
}

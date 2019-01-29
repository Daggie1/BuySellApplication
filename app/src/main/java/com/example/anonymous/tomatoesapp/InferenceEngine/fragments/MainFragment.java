package com.example.anonymous.tomatoesapp.InferenceEngine.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.anonymous.tomatoesapp.InferenceEngine.util.EngineBayes;
import com.example.anonymous.tomatoesapp.R;

import java.util.ArrayList;


/**
 * Created by lirfu on 24.06.17..
 */

public class MainFragment extends Fragment {
    private ArrayList<String> selectedSymptopmps;
    String result;
    TextView detected,treatment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        selectedSymptopmps=getActivity().getIntent().getStringArrayListExtra("selectedSymptopmps");
        View v = inflater.inflate(R.layout.fragment_main, null);

       detected=(TextView) v.findViewById(R.id.detectedtxt);
        treatment=(TextView) v.findViewById(R.id.treatment);


        String[] stockArr = new String[selectedSymptopmps.size()];
                stockArr = selectedSymptopmps.toArray(stockArr);


              result=   EngineBayes.getInstance().testData(stockArr);
             if(result==null){
                 result=EngineBayes.getInstance().testData(stockArr[0]);
             }
             detected.setText(result);

treatment();
        return v;
    }

    private void treatment(){
        switch (result){

            case "Aphids":
                treatment.setText(R.string.for_aphids);
                break;
            case "Cutworms":
                treatment.setText(R.string.for_cutworms);
                break;
            case "Flea Bettle":
                treatment.setText(R.string.for_flea_bettle);
                break;
            case "Dumping Off":
                treatment.setText(R.string.for_dumoing_off);
                break;
            case "Blossom End Rot":
                treatment.setText(R.string.for_end_rot);
                break;
            case "Mossaic Virus":
                treatment.setText(R.string.for_mossaic);
                break;
                default:
                    treatment.setText("");

        }
    }
}

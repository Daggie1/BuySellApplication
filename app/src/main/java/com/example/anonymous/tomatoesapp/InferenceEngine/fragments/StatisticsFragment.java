package com.example.anonymous.tomatoesapp.InferenceEngine.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.anonymous.tomatoesapp.InferenceEngine.DataListAdapter;
import com.example.anonymous.tomatoesapp.InferenceEngine.util.EngineBayes;
import com.example.anonymous.tomatoesapp.InferenceEngine.util.Pair;
import com.example.anonymous.tomatoesapp.R;

import java.util.ArrayList;
import java.util.Iterator;



/**
 * Created by lirfu on 24.06.17..
 */

public class StatisticsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistics, null);

        Iterator<Pair> it = EngineBayes.getInstance().getAprioryIterator();
        ArrayList<Pair> apriories = new ArrayList<>();
        while(it.hasNext())
            apriories.add(it.next());

        it = EngineBayes.getInstance().getAposterioryIterator();
        ArrayList<Pair> aposteriories = new ArrayList<>();
        while(it.hasNext())
            aposteriories.add(it.next());

        GridView apriory = (GridView) v.findViewById(R.id.apriory_list);
        apriory.setAdapter(new DataListAdapter(apriories, true));

        GridView aposteriory = (GridView) v.findViewById(R.id.aposteriory_list);
        aposteriory.setAdapter(new DataListAdapter(aposteriories, true));

        return v;
    }
}

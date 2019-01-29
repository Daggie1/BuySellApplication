package com.example.anonymous.tomatoesapp.InferenceEngine;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.anonymous.tomatoesapp.InferenceEngine.util.Pair;
import com.example.anonymous.tomatoesapp.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;




public class DataListAdapter implements ListAdapter {

    private ArrayList<Pair> data;
    private ArrayList<DataSetObserver> observers;
    private ArrayList<View> views;

    public DataListAdapter(ArrayList<Pair> data, boolean sort) {
        observers = new ArrayList<>();
        this.data = data;
        if (sort)
            Collections.sort(this.data, new Comparator<Pair>() {
                @Override
                public int compare(Pair pair, Pair t1) {
                    return pair.getName().compareTo(t1.getName());
                }
            });
        views = new ArrayList<>();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        observers.add(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        observers.remove(dataSetObserver);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return data.get(i) != null ? i : -1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            Context c = viewGroup.getContext();
            LayoutInflater inflater = LayoutInflater.from(c);
            view = inflater.inflate(R.layout.list_entry, viewGroup, false);
        }

        ((TextView) view.findViewById(R.id.entry_result)).setText(data.get(i).getName());
        ((TextView) view.findViewById(R.id.entry_title)).setText(data.get(i).getValue().toString());

        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }
}

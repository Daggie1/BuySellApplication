package com.example.anonymous.tomatoesapp.InferenceEngine.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;



public class DemoSource implements Source {
    private ArrayList<DataEntry> entries;

    public DemoSource() {
        entries = new ArrayList<>();
        entries.add(new DataEntry("Aphids", "Misshapen, curling, stunted, or yellowing leaves","Flowers or fruit become distorted or deformed","Galls  form on roots or leaves"));
        entries.add(new DataEntry("Cutworms", "cut off the plant from underneath the soil", "the bottom of the plant is destroyed"));
        entries.add(new DataEntry("Flea Bettle", "Shotholes in leaves, especially on young seedlings", "A lacy appearance"));
        entries.add(new DataEntry("Mossaic Virus", "The leaves are mottled with yellow", "the leaves have a blister-like appearance","Plants are often stunted, or they grow poorly"));
        entries.add(new DataEntry("Blossom End Rot", "blossom-end rot occurs to the fruit "));
        entries.add(new DataEntry("Dumping Off", "the rotting of stem and root tissues at and below "));
    }

    @Override
    public List<DataEntry> loadEntries(Context context) {
        return entries;
    }

    @Override
    public void storeEntries(Context context, List<DataEntry> entries) {
        this.entries = new ArrayList<>(entries);
    }
}

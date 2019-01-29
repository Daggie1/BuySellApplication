package com.example.anonymous.tomatoesapp.InferenceEngine.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lirfu on 24.06.17..
 */

public class EngineBayes {
    private static EngineBayes instance;

    private ArrayList<DataEntry> entries;
    private Map<String, Double> resultsApriory;
    private Map<String, Double> resultsAposteriory;

    private EngineBayes() {
        entries = new ArrayList<>();
        resultsApriory = new TreeMap<>();
        resultsAposteriory = new TreeMap<>();

        calculate();
    }

    public void loadEntriesFrom(Context context, Source source) {
        this.entries = new ArrayList<>(source.loadEntries(context));
        calculate();
    }

    public void saveEntriesTo(Context context, Source source) {
        source.storeEntries(context, entries);
    }

    public static EngineBayes getInstance() {
        if (instance == null)
            instance = new EngineBayes();

        return instance;
    }

    private void calculate() {
        if (entries == null || entries.size() == 0) return;

        resultsApriory.clear();
        resultsAposteriory.clear();

        // Sum occurrences of features.
        for (DataEntry e : entries) {

            if (resultsApriory.containsKey(e.getResult())) // Increment existing.
                resultsApriory.put(e.getResult(), resultsApriory.get(e.getResult()) + 1);

            else // No such result exists.
                resultsApriory.put(e.getResult(), 1.);

            for (String feature : e.getFeatures())

                if (resultsAposteriory.containsKey(toKey(feature, e.getResult()))) // Increment existing.
                    resultsAposteriory.put(
                            toKey(feature, e.getResult()),
                            resultsAposteriory.get(toKey(feature, e.getResult())) + 1
                    );

                else
                    resultsAposteriory.put(toKey(feature, e.getResult()), 1.);
        }

        // Divide values by number of features to get probabilities.
        for (String key : resultsApriory.keySet())
            resultsApriory.put(
                    key,
                    resultsApriory.get(key) / entries.size()
            );
        for (String key : resultsAposteriory.keySet())
            resultsAposteriory.put(
                    key,
                    resultsAposteriory.get(key) / entries.size()
            );
    }

    private String toKey(String feature, String result) {
        return feature + "_" + result;
    }

    public String testData(String... keys) {
        String result = null;

        double prob;
        double max = 0;

        // Calculate probabilities of each result.
        for (String res : resultsApriory.keySet()) {
            // A priory.
            prob = resultsApriory.get(res);

            // A posteriors.
            for (String key : keys)
                if (resultsAposteriory.containsKey(toKey(key, res)))
                    prob *= resultsAposteriory.get(toKey(key, res));
                else {
                    prob = 0;
                    break;
                }

            if (prob > max) {
                max = prob;
                result = res;
            } // TODO ne racuna dobro.
        }

        return result;
    }

    public int size() {
        return entries.size();
    }

    public DataEntry get(int i) {
        return entries.get(i);
    }

    public Iterator<DataEntry> getEntriesIterator() {
        return entries.iterator();
    }

    public Iterator<Pair> getAprioryIterator() {
        return new Iterator<Pair>() {
            Object[] keys = resultsApriory.keySet().toArray();
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < keys.length;
            }

            @Override
            public Pair next() {
                String key = keys[index++].toString();
                return new Pair(key, Tools.round(resultsApriory.get(key), 4));
            }
        };
    }

    public Iterator<Pair> getAposterioryIterator() {
        return new Iterator<Pair>() {
            Object[] keys = resultsAposteriory.keySet().toArray();
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < keys.length;
            }

            @Override
            public Pair next() {
                String key = keys[index++].toString();
                return new Pair(key, Tools.round(resultsAposteriory.get(key), 4));
            }
        };
    }
}

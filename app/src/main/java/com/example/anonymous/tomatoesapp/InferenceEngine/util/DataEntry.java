package com.example.anonymous.tomatoesapp.InferenceEngine.util;

/**
 * Created by lirfu on 18.06.17..
 */

public class DataEntry {
    private String[] features;
    private String result;

    DataEntry(String result, String... features) {
        this.features = features;
        this.result = result;
    }

    public String[] getFeatures() {
        return features;
    }

    public String getResult() {
        return result;
    }

    public String getfeatureInitials() {
        String str = "";

        for (String feature : features)
            str += feature.charAt(0);

        return str;
    }

    @Override
    public String toString() {
        String s = "";

        for (String d : features)
            s += d + ",";

        s += result;

        return s;
    }

    /**
     * Interprets the CSV format.<br>
     * First n-1 entries become features, last entry becomes the result.
     */
    public static DataEntry parseFromString(String string) {
        String[] parts = string.split(",");

        String[] keys = new String[parts.length - 1];
        String result = parts[parts.length - 1];


        System.arraycopy(parts, 0, keys, 0, parts.length - 1);
        return new DataEntry(result, keys);
    }

    @Override
    public boolean equals(Object obj) {
        if (features.length != ((DataEntry) obj).features.length) return false;

        for (String s : features)
            if (!s.equals(obj))
                return false;

        return result.equals(obj);
    }
}

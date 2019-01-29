package com.example.anonymous.tomatoesapp.InferenceEngine.util;

import android.content.Context;

import java.util.List;

/**
 * Created by lirfu on 25.06.17..
 */

public interface Source {
    /**
     * Loads the entries from the source.
     * @return A List containing entries.
     */
    public List<DataEntry> loadEntries(Context context);

    /**
     * Stores the given entries to the source.
     * @param entries A List containing entries to be saved.
     */
    public void storeEntries(Context context, List<DataEntry> entries);
}

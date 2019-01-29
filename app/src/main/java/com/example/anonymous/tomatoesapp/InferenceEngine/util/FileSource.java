package com.example.anonymous.tomatoesapp.InferenceEngine.util;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lirfu on 25.06.17..
 */

public class FileSource implements Source {
    private String filepath;

    /**
     * Load from the default internal file.
     */
    public FileSource() {
        filepath = Globals.DATA_FILENAME;
    }

    /**
     * Load from the given file.
     *
     * @param filepath Path to the file.
     * @throws FileNotFoundException if the file doesn't exist.
     */
    public FileSource(String filepath) throws FileNotFoundException {
        if (!new File(filepath).exists())
            throw new FileNotFoundException("File " + filepath + " doesn't exist!");

        this.filepath = filepath;
    }

    @Override
    public List<DataEntry> loadEntries(Context context) {
        ArrayList<DataEntry> entries = new ArrayList<>();

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput(filepath)));

            String tmp;
            while ((tmp = reader.readLine()) != null)
                entries.add(DataEntry.parseFromString(tmp));

            reader.close();
        } catch (FileNotFoundException e) {
            Log.e(Globals.TAG, "Data file not found: " + Globals.DATA_FILENAME);
        } catch (IOException e) {
            Log.e(Globals.TAG, "Error reading data file: " + e.getMessage());
        }

        return entries;
    }

    @Override
    public void storeEntries(Context context, List<DataEntry> entries) {
//TODO
    }
}

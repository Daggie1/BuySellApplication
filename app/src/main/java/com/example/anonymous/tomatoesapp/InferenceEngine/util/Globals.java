package com.example.anonymous.tomatoesapp.InferenceEngine.util;

import android.util.Log;

/**
 * Created by lirfu on 18.06.17..
 */

public class Globals {
    static final String TAG = "lirfu.bayesianapp";
    final static String DATA_FILENAME="bayesian_data.csv";

    public static void logd(String message){
        Log.d(TAG, message);
    }
    public static void loge(String message){
        Log.e(TAG, message);
    }
}

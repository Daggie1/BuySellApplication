package com.example.anonymous.tomatoesapp.InferenceEngine.util;

/**
 * Created by lirfu on 25.06.17..
 */

public class Tools {
    public static double round(double value, int precision) {
        return Math.round(value * Math.pow(10, precision)) / Math.pow(10, precision);
    }
}

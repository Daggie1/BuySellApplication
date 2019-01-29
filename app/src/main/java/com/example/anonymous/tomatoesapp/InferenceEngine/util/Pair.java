package com.example.anonymous.tomatoesapp.InferenceEngine.util;

/**
 * Created by lirfu on 25.06.17..
 */

public class Pair{
    private final String name;
    private final Object value;

    public Pair(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}

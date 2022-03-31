package com.example.myapplication;

import java.util.Map;

public class Criteria {
    private Map<String, Double> val;
    private Map<String, Double> vector;
    private Map<String, Double> weight;

    public Criteria(Map<String, Double> val) {
        this.val = val;
    }

    public Map<String, Double> getVector() {
        return vector;
    }

    public void setVector(Map<String, Double> vector) {
        this.vector = vector;
    }

    public Map<String, Double> getWeight() {
        return weight;
    }

    public void setWeight(Map<String, Double> weight) {
        this.weight = weight;
    }

    public Map<String, Double> getVal() {
        return val;
    }

    public void setVal(Map<String, Double> val) {
        this.val = val;
    }
}

package com.example.myapplication;

import java.util.Map;

public class CompanyModel {

    private String company_name;
    private Map<String, Double> val;
    private Map<String, Double> vector;
    private Map<String, Double> weight;

    public CompanyModel(String company_name, Map<String, Double> val) {
        this.company_name = company_name;
        this.val = val;

    }

    public Map<String, Double> getWeight() {
        return weight;
    }

    public void setWeight(Map<String, Double> weight) {
        this.weight = weight;
    }

    public Map<String, Double> getVector() {
        return vector;
    }

    public void setVector(Map<String, Double> vector) {
        this.vector = vector;
    }

    public Map<String, Double> getVal() {
        return val;
    }

    public void setVal(Map<String, Double> val) {
        this.val = val;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }


}

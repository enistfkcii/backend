package com.testcase.TestCase.model;

import java.util.List;

public class WeatherResponse {
    private List<Weather> result;
    private String success;
    private String city;
    public List<Weather> getResult() {
        return result;
    }

    public void setResult(List<Weather> result) {
        this.result = result;
    }
    public String getSuccess() {
        return success;
    }

    public String getCity() {
        return city;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    public void setCity(String city) {
        this.city = city;
    }
}


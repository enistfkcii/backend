package com.testcase.TestCase.model;

import java.util.List;

public class CityResponse {
    private List<String> cities;

    public CityResponse(List<String> cities) {
        this.cities = cities;
    }

    public List<String> getCities() {
        return cities;
    }




}
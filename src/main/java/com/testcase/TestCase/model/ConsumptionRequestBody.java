package com.testcase.TestCase.model;

public class ConsumptionRequestBody {

    private int cityId;
    private String temperature;
    private String averageConsumption;

    public ConsumptionRequestBody() {
    }

    public ConsumptionRequestBody(int cityId, String temperature, String averageConsumption) {
        this.cityId = cityId;
        this.temperature = temperature;
        this.averageConsumption = averageConsumption;
    }


    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getAverageConsumption() {
        return averageConsumption;
    }

    public void setAverageConsumption(String averageConsumption) {
        this.averageConsumption = averageConsumption;
    }

    @Override
    public String toString() {
        return "ConsumptionBody{" +
                "cityId=" + cityId +
                ", temperature=" + temperature +
                ", averageConsumption=" + averageConsumption +
                '}';
    }
}

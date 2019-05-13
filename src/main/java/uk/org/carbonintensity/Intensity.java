package uk.org.carbonintensity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Intensity {

    @JsonProperty
    private int forecast;

    @JsonProperty
    private String index;

    public int getForecast() {
        return forecast;
    }

    public void setForecast(int forecast) {
        this.forecast = forecast;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}

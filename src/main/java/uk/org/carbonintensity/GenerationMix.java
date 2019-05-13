package uk.org.carbonintensity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerationMix {

    @JsonProperty
    private String fuel;

    @JsonProperty
    private int perc;

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getPerc() {
        return perc;
    }

    public void setPerc(int perc) {
        this.perc = perc;
    }
}

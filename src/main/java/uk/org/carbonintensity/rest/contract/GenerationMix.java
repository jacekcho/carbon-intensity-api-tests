package uk.org.carbonintensity.rest.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class GenerationMix {

    @JsonProperty
    private String fuel;

    @JsonProperty
    private double perc;

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public double getPerc() {
        return perc;
    }

    public void setPerc(double perc) {
        this.perc = perc;
    }
}

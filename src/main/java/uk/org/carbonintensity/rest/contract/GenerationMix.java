package uk.org.carbonintensity.rest.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class GenerationMix {

    @JsonProperty
    private String fuel;

    @JsonProperty
    private BigDecimal perc;

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public BigDecimal getPerc() {
        return perc;
    }

    public void setPerc(BigDecimal perc) {
        this.perc = perc;
    }
}

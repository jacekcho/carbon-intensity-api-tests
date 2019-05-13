package uk.org.carbonintensity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Regional {

    @JsonProperty("data")
    private List<Regions> regions;

    public void setRegions(List<Regions> regions) {
        this.regions = regions;
    }

    public List<Regions> getRegions() {
        return regions;
    }
}

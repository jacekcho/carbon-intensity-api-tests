package uk.org.carbonintensity.rest.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Regions {

    @JsonProperty("regionid")
    private int regionId;

    @JsonProperty("dnoregion")
    private String dnoRegion;

    @JsonProperty("shortname")
    private String shortName;

    @JsonProperty("data")
    private List<Data> data;

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getDnoRegion() {
        return dnoRegion;
    }

    public void setDnoRegion(String dnoRegion) {
        this.dnoRegion = dnoRegion;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}

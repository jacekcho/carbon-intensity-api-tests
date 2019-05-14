package uk.org.carbonintensity.rest.contract;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    @JsonProperty
    private String from;

    @JsonProperty
    private String to;

    @JsonProperty
    private Intensity intensity;

    @JsonProperty("generationmix")
    private List<GenerationMix> generationMix;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Intensity getIntensity() {
        return intensity;
    }

    public void setIntensity(Intensity intensity) {
        this.intensity = intensity;
    }

    public List<GenerationMix> getGenerationMix() {
        return generationMix;
    }

    public void setGenerationMix(List<GenerationMix> generationMix) {
        this.generationMix = generationMix;
    }
}

package uk.org.carbonintensity.rest;

import uk.org.carbonintensity.rest.contract.GenerationMix;
import uk.org.carbonintensity.rest.contract.Regional;
import uk.org.carbonintensity.rest.contract.Regions;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;

public class CarbonIntensityApi {

    public List<Regions> getAllRegions() {
        List<Regions> allRegions = new ArrayList<>();

        for (int regionId : getAllRegionsIds()) {
            Regional regional = get("/regional/regionid/" + regionId).as(Regional.class);
            allRegions.add(regional.getRegions().get(0));
        }
        return allRegions;
    }

    public List<Regions> getSortedListByCarbonIntensity(List<Regions> allRegions) {
        return allRegions
                .stream()
                .sorted(Comparator.comparing(r -> r.getData().get(0).getIntensity().getForecast()))
                .collect(Collectors.toList());
    }

    public Map<String, Double> getCarbonIntensityPercentageForRegion(List<Regions> allRegions) {
        Map<String, Double> generationMixSums = new HashMap<>();

        for (Regions region : allRegions) {
            Double generationMixSum = getGenerationMixSum(region);
            generationMixSums.put(region.getShortName(), generationMixSum);
        }

        return generationMixSums;
    }

    private double getGenerationMixSum(Regions region) {
        return region.getData().get(0)
                .getGenerationMix()
                .stream()
                .mapToDouble(GenerationMix::getPerc)
                .sum();
    }

    private List<Integer> getAllRegionsIds() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
    }

}

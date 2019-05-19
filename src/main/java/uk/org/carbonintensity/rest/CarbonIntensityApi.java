package uk.org.carbonintensity.rest;

import uk.org.carbonintensity.rest.contract.GenerationMix;
import uk.org.carbonintensity.rest.contract.Regional;
import uk.org.carbonintensity.rest.contract.Regions;

import java.math.BigDecimal;
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
        List<Regions> sortedList = allRegions.stream()
                .sorted(Comparator.comparing(r -> r.getData().get(0).getIntensity().getForecast()))
                .collect(Collectors.toList());

        Collections.reverse(sortedList);

        return sortedList;
    }

    public boolean isGenerationMixSumsTo100ForAllRegions(List<Regions> allRegions) {
        return allRegions
                .stream()
                .map(this::getGenerationMixSum)
                .allMatch(c -> c.toString().equals("100.0"));
    }

    private BigDecimal getGenerationMixSum(Regions region) {
        List<GenerationMix> generationMix = region
                .getData().get(0)
                .getGenerationMix();

        BigDecimal sum = new BigDecimal(0.0);

        for (GenerationMix gm : generationMix) {
            sum = sum.add(new BigDecimal(Double.toString(gm.getPerc())));
        }
        return sum;
    }

    private List<Integer> getAllRegionsIds() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
    }

}

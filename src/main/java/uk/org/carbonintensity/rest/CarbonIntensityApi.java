package uk.org.carbonintensity.rest;

import uk.org.carbonintensity.rest.contract.GenerationMix;
import uk.org.carbonintensity.rest.contract.Regional;
import uk.org.carbonintensity.rest.contract.Regions;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static java.util.stream.Collectors.toMap;

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
        List<Regions> sortedList = allRegions
                .stream()
                .sorted(Comparator.comparing(r -> r.getData().get(0).getIntensity().getForecast()))
                .collect(Collectors.toList());

        Collections.reverse(sortedList);

        return sortedList;
    }

    public Map<String, Double> getGenerationMixSumByRegion(List<Regions> allRegions) {
        HashMap<String, Double> generationMixSumByRegion = new HashMap<>();

        for (Regions region : allRegions) {
            generationMixSumByRegion.put(region.getShortName(), getGenerationMixSum(region));
        }

        return generationMixSumByRegion;
    }

    public Set<String> getAllFuelTypes(List<Regions> allRegions) {
        return allRegions
                .stream()
                .flatMap(r -> r.getData().stream())
                .flatMap(d -> d.getGenerationMix().stream())
                .map(GenerationMix::getFuel)
                .collect(Collectors.toSet());
    }

    private double getGenerationMixSum(Regions region) {
        List<GenerationMix> generationMix = region
                .getData().get(0)
                .getGenerationMix();

        BigDecimal sum = new BigDecimal(0.0);

        for (GenerationMix gm : generationMix) {
            sum = sum.add(new BigDecimal(Double.toString(gm.getPerc())));
        }
        return sum.doubleValue();
    }

    public Map<String, Map<String, Double>> getRegionsHighestGenerationPercByFuel(Set<String> allFuelTypes, List<Regions> allRegions) {
        Map<String, Map<String, Double>> regionsHighestGenerationPercByFuel = new HashMap<>();

        for (String fuelType : allFuelTypes) {
            Map<String, Double> generationPercByRegion = getGenerationPercByRegion(allRegions, fuelType);
            Map<String, Double> sortedGenerationPercByRegion = getSortedGenerationPercByRegion(generationPercByRegion);
            regionsHighestGenerationPercByFuel.put(fuelType, sortedGenerationPercByRegion);
        }

        return regionsHighestGenerationPercByFuel;
    }

    private Map<String, Double> getGenerationPercByRegion(List<Regions> allRegions, String fuelType) {
        Map<String, Double> maps = new HashMap<>();
        for (String regionName : getAllRegionNames(allRegions)) {
            Double perc = getGenerationPercForRegionByFuelType(allRegions, regionName, fuelType);
            maps.put(regionName, perc);
        }
        return maps;
    }

    private List<String> getAllRegionNames(List<Regions> allRegions) {
        return allRegions
                .stream()
                .map(Regions::getShortName)
                .collect(Collectors.toList());
    }

    private double getGenerationPercForRegionByFuelType(List<Regions> allRegions, String regionName, String fuelType) {
        return allRegions.stream()
                .filter(r -> r.getShortName().equals(regionName))
                .map(r -> r.getData().get(0))
                .flatMap(d -> d.getGenerationMix().stream())
                .filter(gm -> gm.getFuel().equals(fuelType))
                .map(GenerationMix::getPerc)
                .findFirst()
                .get();
    }

    private Map<String, Double> getSortedGenerationPercByRegion(Map<String, Double> fuelTypeByGenerationPercMap) {
        return fuelTypeByGenerationPercMap
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

    private List<Integer> getAllRegionsIds() {
        return Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);
    }

}

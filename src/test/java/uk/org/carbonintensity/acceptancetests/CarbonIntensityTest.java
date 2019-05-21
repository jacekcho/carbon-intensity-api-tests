package uk.org.carbonintensity.acceptancetests;

import org.testng.annotations.Test;
import uk.org.carbonintensity.rest.CarbonIntensityApi;
import uk.org.carbonintensity.rest.contract.GenerationMix;
import uk.org.carbonintensity.rest.contract.Regions;
import uk.org.carbonintensity.utils.TestBase;

import java.util.*;

import static java.util.stream.Collectors.toMap;
import static org.fest.assertions.Assertions.assertThat;
import static uk.org.carbonintensity.helpers.TestHelper.printForecastByRegionName;
import static uk.org.carbonintensity.helpers.TestHelper.printRegionsWithHighestGenerationPercentageByFuelType;

public class CarbonIntensityTest extends TestBase {

    private CarbonIntensityApi carbonIntensityApi = new CarbonIntensityApi();

    @Test
    public void shouldPrintSortedListOfCarbonIntensityForecast() {
        // given
        List<Regions> allRegions = carbonIntensityApi.getAllRegions();

        // when
        List<Regions> sortedRegions = carbonIntensityApi.getSortedListByCarbonIntensity(allRegions);

        // then
        printForecastByRegionName(sortedRegions);
    }

    @Test
    public void shouldCheckIsGenerationMixSumsTo100() {
        // when
        List<Regions> allRegions = carbonIntensityApi.getAllRegions();

        // then
        assertThat(carbonIntensityApi.isGenerationMixSumsTo100ForAllRegions(allRegions)).isTrue();
    }

    @Test
    public void shouldListRegionsForEachFuel() { // TODO refactoring needed !
        // given
        List<Regions> allRegions = carbonIntensityApi.getAllRegions();
        Set<String> allFuelTypes = carbonIntensityApi.getAllFuelTypes(allRegions);

        // when
        Map<String, Map<String, Double>> fuelHighestPercentageByRegionMap = getFuelHighestPercentageByRegionMap(allFuelTypes, allRegions);

        // then
        printRegionsWithHighestGenerationPercentageByFuelType(allFuelTypes, fuelHighestPercentageByRegionMap);
    }

    private Map<String, Map<String, Double>> getFuelHighestPercentageByRegionMap(Set<String> allFuelTypes, List<Regions> allRegions) {
        Map<String, Map<String, Double>> fuelHighestPercentageByRegionMap = new HashMap<>();

        for (String fuelType : allFuelTypes) {
            Map<String, Double> regionByPerc = getRegionNameByPercMap(allRegions, fuelType);
            Map<String, Double> sorted = getSortedMap(regionByPerc);
            fuelHighestPercentageByRegionMap.put(fuelType, sorted);
        }

        return fuelHighestPercentageByRegionMap;
    }

    private Map<String, Double> getRegionNameByPercMap(List<Regions> allRegions, String fuelType) {
        Map<String, Double> maps = new HashMap<>();
        for (String regionName : carbonIntensityApi.getAllRegionNames(allRegions)) {
            Double perc = getFuelTypePercForRegion(allRegions, regionName, fuelType);
            maps.put(regionName, perc);
        }

        return maps;
    }

    private double getFuelTypePercForRegion(List<Regions> allRegions, String regionName, String fuelType) {
        return allRegions.stream()
                .filter(r -> r.getShortName().equals(regionName))
                .map(r -> r.getData().get(0))
                .flatMap(d -> d.getGenerationMix().stream())
                .filter(gm -> gm.getFuel().equals(fuelType))
                .map(GenerationMix::getPerc)
                .findFirst()
                .get();
    }

    private Map<String, Double> getSortedMap(Map<String, Double> maps) {
        return maps
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

}
package uk.org.carbonintensity.helpers;

import uk.org.carbonintensity.rest.contract.Regions;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestHelper {

    public static void printForecastByRegionName(List<Regions> allRegions) {
        allRegions.forEach(r -> System.out.println(r.getShortName() + " " + getRegionForecast(r)));
    }

    private static int getRegionForecast(Regions region) {
        return region.getData().get(0).getIntensity().getForecast();
    }

    public static void printRegionsWithHighestGenerationPercentageByFuelType(Set<String> fuelTypes, Map<String, Map<String, Double>> regionsHighestGenerationPercByFuel) {
        for (String fuelType : fuelTypes) {
            System.out.println(String.format("\n%s - five regions where the generation percentage is the highest:", fuelType));

            regionsHighestGenerationPercByFuel.get(fuelType)
                    .entrySet().stream()
                    .limit(5)
                    .forEach(s -> System.out.println(s.getKey() + " " + s.getValue()));
        }
    }
}

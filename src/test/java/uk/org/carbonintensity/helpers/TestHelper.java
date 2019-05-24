package uk.org.carbonintensity.helpers;

import org.apache.log4j.Logger;
import uk.org.carbonintensity.rest.contract.Regions;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestHelper {

    private final static Logger LOGGER = Logger.getLogger(TestHelper.class);

    public static void printForecastByRegionName(List<Regions> allRegions) {
        LOGGER.info("Sorted list by carbon intensity:");
        allRegions.forEach(r -> LOGGER.info(r.getShortName() + " " + getRegionForecast(r)));
    }

    private static int getRegionForecast(Regions region) {
        return region.getData().get(0).getIntensity().getForecast();
    }

    public static boolean areAllRegionsGenerationMixSumTo100(Map<String, Double> generationMixSumByRegion) {
        boolean generationMixSumTo100 = true;

        for (String region : generationMixSumByRegion.keySet()) {
            if (!generationMixSumByRegion.get(region).toString().equals("100.0")) {
                generationMixSumTo100 = false;
                LOGGER.info(region + " Generation mix sum to: " + generationMixSumByRegion.get(region));
            }
        }
        return generationMixSumTo100;
    }

    public static void printRegionsWithHighestGenerationPercentageByFuelType(Set<String> fuelTypes, Map<String, Map<String, Double>> regionsHighestGenerationPercByFuel) {
        for (String fuelType : fuelTypes) {
            LOGGER.info(String.format("%s - five regions where the generation percentage is the highest:", fuelType));

            regionsHighestGenerationPercByFuel.get(fuelType)
                    .entrySet().stream()
                    .limit(5)
                    .forEach(s -> LOGGER.info(s.getKey() + " " + s.getValue()));
        }
    }
}

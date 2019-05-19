package uk.org.carbonintensity.helpers;

import uk.org.carbonintensity.rest.contract.Regions;

import java.util.List;

public class TestHelper {

    public static void printForecastByRegionName(List<Regions> regions) {
        regions.forEach(r -> System.out.println(r.getShortName() + " " + getRegionForecast(r)));
    }

    private static int getRegionForecast(Regions region) {
        return region.getData().get(0).getIntensity().getForecast();
    }
}

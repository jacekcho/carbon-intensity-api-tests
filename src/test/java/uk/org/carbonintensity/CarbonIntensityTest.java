package uk.org.carbonintensity;

import org.testng.Assert;
import org.testng.annotations.Test;
import uk.org.carbonintensity.rest.CarbonIntensityApi;
import uk.org.carbonintensity.rest.contract.Regions;
import uk.org.carbonintensity.utils.TestBase;

import java.util.List;
import java.util.Map;

public class CarbonIntensityTest extends TestBase {

    private CarbonIntensityApi carbonIntensityApi = new CarbonIntensityApi();

    @Test
    public void shouldPrintASortedListOfCarbonIntensityForecast() {
        // given
        List<Regions> allRegions = carbonIntensityApi.getAllRegions();

        // when
        List<Regions> sortedRegions = carbonIntensityApi.getSortedListByCarbonIntensity(allRegions);

        // then
        sortedRegions.forEach(r -> System.out.println(r.getShortName() + " " + r.getData().get(0).getIntensity().getForecast()));
    }

    @Test
    public void shouldCheckIsGenerationMixSumsTo100() {
        // given
        List<Regions> allRegions = carbonIntensityApi.getAllRegions();
        Map<String, Double> carbonIntensityPercentageForRegion = carbonIntensityApi.getCarbonIntensityPercentageForRegion(allRegions);

        // when
        boolean isGenerationMixSumTo100 = carbonIntensityPercentageForRegion.keySet()
                .stream()
                .allMatch(k -> carbonIntensityPercentageForRegion.get(k).equals(100.0));

        // then
        Assert.assertTrue(isGenerationMixSumTo100);
    }

// TODO
//    Scenario 3 (optional):
//1. For each region get carbon intensity
//2. For each fuel type list five regions where the generation percentage is the highest

}
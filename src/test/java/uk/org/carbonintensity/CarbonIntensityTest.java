package uk.org.carbonintensity;

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
    public void shouldCheckIsGenerationMixSumsTo100() { //TODO refactoring
        // given
        List<Regions> allRegions = carbonIntensityApi.getAllRegions();

        // when
        Map<String, Double> generationMixSums = carbonIntensityApi.getGenerationMixSums(allRegions);

        // then //TODO assertion
        for (String region : generationMixSums.keySet()) {
            System.out.println(region + " - " + generationMixSums.get(region));
        }
    }

}
package uk.org.carbonintensity.acceptancetests;

import org.testng.annotations.Test;
import uk.org.carbonintensity.rest.CarbonIntensityApi;
import uk.org.carbonintensity.rest.contract.Regions;
import uk.org.carbonintensity.utils.TestBase;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static uk.org.carbonintensity.helpers.TestHelper.printForecastByRegionName;

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
        // given
        List<Regions> allRegions = carbonIntensityApi.getAllRegions();

        // when
        boolean isGenerationMixSumTo100 = carbonIntensityApi.isGenerationMixSumsTo100ForAllRegions(allRegions);

        // then
        assertThat(isGenerationMixSumTo100).isTrue();
    }

// TODO
//    Scenario 3 (optional):
//1. For each region get carbon intensity
//2. For each fuel type list five regions where the generation percentage is the highest

}
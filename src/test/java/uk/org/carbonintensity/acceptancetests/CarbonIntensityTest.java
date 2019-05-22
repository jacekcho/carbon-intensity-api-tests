package uk.org.carbonintensity.acceptancetests;

import org.testng.annotations.Test;
import uk.org.carbonintensity.rest.CarbonIntensityApi;
import uk.org.carbonintensity.rest.contract.Regions;
import uk.org.carbonintensity.utils.TestBase;

import java.util.*;

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
    public void shouldListRegionsForEachFuel() {
        // given
        List<Regions> allRegions = carbonIntensityApi.getAllRegions();
        Set<String> allFuelTypes = carbonIntensityApi.getAllFuelTypes(allRegions);

        // when
        Map<String, Map<String, Double>> regionsHighestGenerationPercByFuel = carbonIntensityApi.getRegionsHighestGenerationPercByFuel(allFuelTypes, allRegions);

        // then
        printRegionsWithHighestGenerationPercentageByFuelType(allFuelTypes, regionsHighestGenerationPercByFuel);
    }

}
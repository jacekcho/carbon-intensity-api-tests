package uk.org.carbonintensity;

import org.testng.annotations.Test;
import uk.org.carbonintensity.rest.CarbonIntensityApi;
import uk.org.carbonintensity.rest.contract.Regions;
import uk.org.carbonintensity.utils.TestBase;

import java.util.List;

public class CarbonIntensityTest extends TestBase {

    @Test
    public void shouldPrintASortedListOfCarbonIntensityForecast() {
        // given
        CarbonIntensityApi carbonIntensityApi = new CarbonIntensityApi();
        List<Regions> allRegions = carbonIntensityApi.getAllRegions();

        // when
        List<Regions> sortedRegions = carbonIntensityApi.getSortedListByCarbonIntensity(allRegions);

        // then
        sortedRegions.forEach(r -> System.out.println(r.getShortName() + " " + r.getData().get(0).getIntensity().getForecast()));
    }

}
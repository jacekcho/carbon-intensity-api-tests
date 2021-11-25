package uk.org.carbonintensity.acceptancetests;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import org.testng.annotations.Test;
import uk.org.carbonintensity.rest.contract.Regional;
import uk.org.carbonintensity.rest.contract.Regions;

import static io.restassured.config.EncoderConfig.encoderConfig;

public class RestAssuredExampleTest {

    @Test
    public void baseRestAssuredTest() {
        // given (base configuration that can be moved to the other place)
        RestAssured.config = new RestAssuredConfig().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.useRelaxedHTTPSValidation();

        String requestUrl = "https://api.carbonintensity.org.uk/regional/regionid/1";

        // when (we can map the response to the object)
        Regional regional = RestAssured.given()
                .log().all()  // To log Request
                .get(requestUrl)
                .peek()       // To log Response
                .as(Regional.class);  // To map response to the Regional

        //then (we can use mapped response)
        System.out.println("All Regions data using loop: \n");
        for (Regions region : regional.getRegions()) {
            System.out.println("region.getRegionId: " + region.getRegionId());
            System.out.println("region.getDnoRegion: " + region.getDnoRegion());
            System.out.println("region.getShortName: " + region.getShortName());

            // using stream
            region.getData().forEach(d -> {
                System.out.println("    region.getData().getFrom(): " + d.getFrom());
                System.out.println("    region.getData().getTo()  : " + d.getTo());
            });

        }

    }

}

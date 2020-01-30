package uk.org.carbonintensity.utils;

import com.netflix.config.DynamicProperty;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;

import static io.restassured.config.EncoderConfig.encoderConfig;

public class TestBase {

    public TestBase() {
        setRestAssuredConfiguration();
    }

    private void setRestAssuredConfiguration() {
        RestAssured.baseURI = DynamicProperty.getInstance("api.url").getString();
        RestAssured.config = new RestAssuredConfig().encoderConfig(encoderConfig().defaultContentCharset("UTF-8"));
        RestAssured.useRelaxedHTTPSValidation();
    }
}
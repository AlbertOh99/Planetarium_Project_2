package com.revature.planetarium.controller.planet;

import com.revature.planetarium.controller.APIFixture;
import io.javalin.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class PlanetDeletionPositiveControllerTest extends APIFixture {


    private Map<String, String> loginInfo;
    private String planetName;
    private int ownerId;

    @Before
    public void setup() {
        this.loginInfo = new HashMap<>();
        this.loginInfo.put("username", "Batman");
        this.loginInfo.put("password", "Iamthenight1939");

        ownerId = 1;
        planetName = "Earth";
    }


    @Test
    public void planetDeletionPositiveAPITest() throws IOException {
        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(this.loginInfo)
                .when()
                .post("login");

        Map<String, String> cookies = loginResponse.getCookies();

        given()
                .cookies(cookies)
                .when()
                .delete("planetarium/owner/" + ownerId + "/planet/" + planetName)
                .then()
                .statusCode(204);
    }
}

package com.revature.planetarium.controller.planet;

import io.javalin.http.ContentType;
import io.javalin.http.HttpStatus;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;


public class PlanetOwnerIdRetrievalTest {

    private Map<String, String> loginInfo;
    private int ownerId;
    private String jsonBody;

    @Before
    public void setup() throws IOException {
        ownerId = 1;
        this.loginInfo = new HashMap<>();
        this.loginInfo.put("username", "Batman");
        this.loginInfo.put("password", "Iamthenight1939");

        jsonBody = new String(Files.readAllBytes(Paths.get("src/test/java/com/revature/planetarium/controller/planet/retrievalData.json")));
//        File test = new File(Paths.get("src/test/java/com/revature/planetarium/controller/planet/retrievalData.json"));
    }


    @Test
    public void planetRetrievalAPITest() throws IOException {
        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(this.loginInfo)
                .when()
                .post("login");

        Map<String, String> cookies = loginResponse.getCookies();

        given()
                .cookies(cookies)
                .when()
                .get("planetarium/user/" + ownerId + "/planet")
                .then()
                .statusCode(200).body(equalTo(jsonBody));

    }
}

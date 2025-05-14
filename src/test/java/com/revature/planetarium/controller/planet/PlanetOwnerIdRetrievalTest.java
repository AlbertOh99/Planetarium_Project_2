package com.revature.planetarium.controller.planet;

import io.javalin.http.ContentType;
import io.javalin.http.HttpStatus;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;


public class PlanetOwnerIdRetrievalTest {

    private Map<String, String> loginInfo;
    private int ownerId;



    @Before
    public void setup(){
        ownerId = 1;
        this.loginInfo = new HashMap<>();
        this.loginInfo.put("username", "Batman");
        this.loginInfo.put("password", "Iamthenight1939");
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
                .get("planetarium/user/"+ ownerId +"/planet")
                .then()
                .statusCode(200);
    }
}

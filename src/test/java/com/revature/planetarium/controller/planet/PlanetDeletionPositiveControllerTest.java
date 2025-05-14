package com.revature.planetarium.controller.planet;

import io.javalin.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PlanetDeletionPositiveControllerTest {
    private Map<String, Object> planetInfo;
    private Map<String, String> loginInfo;

    @Parameterized.Parameter(0)
    public String planetName;

    @Parameterized.Parameter(1)
    public int ownerId;

    @Parameterized.Parameter(2)
    public String imageData;


    @Parameterized.Parameters
    public static Object[][] inputs() throws IOException {
        return new Object[][]{
                {"E", 1, null},
                {"ThePlanetNameIs30CharactersNow", 1, null},
                {"E-arth _3", 1, null},
                {"Earth2", 1, null},
                {"E", 1, "compressed.jpg"},
                {"E", 1, "compressed.png"}
        };
    }


    // To prevent errors from happening, converting the image into a byte array as a method instead of in the new planet
    public static byte[] convertToByte(BufferedImage image){
        return ((DataBufferByte) image.getData().getDataBuffer()).getData();
    }

    @Before
    public void setup(){
        this.loginInfo = new HashMap<>();
        this.loginInfo.put("username", "Batman");
        this.loginInfo.put("password", "Iamthenight1939");

        this.planetInfo = new HashMap<>();
        this.planetInfo.put("planetName", planetName);
        this.planetInfo.put("ownerId", ownerId);
    }


    @Test
    public void planetDeletionPositiveAPITest() throws IOException {
        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(this.loginInfo)
                .when()
                .post("login");

        Map<String, String> cookies = loginResponse.getCookies();

        if(imageData == null){
            given().contentType(ContentType.JSON).body(planetInfo)
                    .cookies(cookies)
                    .when()
                    .post("planetarium/user/"+ownerId+"/planet")
                    .then()
                    .statusCode(201);
        }
        else {
            given()
                    .multiPart("planetName", planetName)
                    .multiPart("ownerId", String.valueOf(ownerId))
                    .multiPart("imageData", imageData)
                    .cookies(cookies)
                    .when()
                    .post("planetarium/user/" + ownerId + "/planet")
                    .then()
                    .statusCode(201);
        }
    }
}

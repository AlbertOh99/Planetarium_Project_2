package com.revature.planetarium.controller.planet;

import com.revature.planetarium.controller.APIFixture;
import io.javalin.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;


import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class PlanetCreationNegativeControllerTest extends APIFixture {
    private Map<String, Object> planetInfo;
    private Map<String, String> loginInfo;

    @Parameter(0)
    public String planetName;

    @Parameter(1)
    public int ownerId;

    @Parameter(2)
    public String imageData;

    @Parameter(3)
    public String message;


    @Parameters
    public static Object[][] inputs() throws IOException {
        return new Object[][]{
                {"", 1, null, "Invalid planet name"},
                {"ThePlanetNameIs31CharactersNowW", 1, null, "Invalid planet name"},
                {"E-arth_3%", 1, null, "Invalid planet name"},
                {"Earth", 1, null, "Invalid planet name"},
                {"E", 5, null, "Invalid owner identifier"},
                {"E", 1, "README.md", "Invalid file type"}
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
    public void planetCreationNegativeAPITest() throws IOException {
        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(this.loginInfo)
                .when()
                .post("login");

        Map<String, String> cookies = loginResponse.getCookies();

        if(imageData == null){
            // Without image
            given().contentType(ContentType.JSON).body(planetInfo)
                    .cookies(cookies)
                    .when()
                    .post("planetarium/user/"+ownerId+"/planet")
                    .then()
                    .statusCode(400)
                    .contentType("text/plain")
                    .body(equalTo(message));
        }
        else {
            // With images
            given()
                    .multiPart("planetName", planetName)
                    .multiPart("ownerId", String.valueOf(ownerId))
                    .multiPart("imageData", imageData)
                    .cookies(cookies)
                    .when()
                    .post("planetarium/user/" + ownerId + "/planet")
                    .then()
                    .statusCode(400)
                    .contentType("text/plain")
                    .body(equalTo(message));
        }
    }
}

package com.revature.planetarium.controller.moon;

import com.revature.planetarium.controller.APIFixture;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class CreateMoonAPITest extends APIFixture {

    private Map<String, String> jsonAsMap;

    private Map <String, Object> moonAsMap;

    @Parameterized.Parameter
    public String moonName;

    @Parameterized.Parameter(1)
    public int ownerId;

    @Parameterized.Parameter(2)
    public byte[] imageData;

    @Parameterized.Parameter(3)
    public int statusCode;

    @Parameterized.Parameter(4)
    public String message;

    @Parameterized.Parameters
    public static Object[][] inputs() {
        return new Object[][] {
                {"E", 1, null, 201, ""},
                {"TheMoonsNameIs30CharactersNows", 1, null, 201, ""},
                {"M-oon_3", 1, null, 201, ""},
                {"Moon2", 1, null, 201, ""},
                {"MoonWithImage", 1, loadImage("moon-1.jpg"), 201, ""},
                {"MoonWithImage2", 1, loadImage("moon-6.png"), 201, ""},
                {"TheMoonsNameIs31CharactersNowsW", 1, null, 400, "Invalid moon name"},
                {"Io", -1, null, 400, "Invalid planet identifier"},
                {"MoonWithImage3", 1, loadImage("moon-7.pdf"), 400, "Invalid file type"}
        };
    }

    private static byte[] loadImage(String file) {
        try {
            Path path = Paths.get("src/test/resources/Celestial-Images/" + file);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }

    @Before
    public void setup() {
        this.jsonAsMap = new HashMap<>();
        this.jsonAsMap.put("username", "Batman");
        this.jsonAsMap.put("password", "Iamthenight1939");

        this.moonAsMap = new HashMap<>();
        this.moonAsMap.put("moonName", moonName);
        this.moonAsMap.put("ownerId", ownerId);
        if (imageData != null) {
            String baseImage = Base64.getEncoder().encodeToString(imageData);
            this.moonAsMap.put("imageData", baseImage);
        }
    }

    @Test
    public void CreateMoonAPITests() {
        Response loginResponse = given()
                .contentType("application/json")
                .body(jsonAsMap)
                .when()
                .post("login");

        Map<String, String> cookies = loginResponse.getCookies();

        given().cookies(cookies).pathParam("planetId", ownerId).contentType("application/json").accept("application/json").body(moonAsMap)
                .when().post("/planetarium/planet/{planetId}/moon")
                .then().statusCode(statusCode);
    }

}

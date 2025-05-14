package com.revature.planetarium.controller.moon;

import io.javalin.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class DeleteMoonAPITest {

    private Map<String, String> jsonAsMap;

    @Parameterized.Parameter
    public String moonName;

    @Parameterized.Parameter(1)
    public int ownerId;

    @Parameterized.Parameter(2)
    public int statusCode;

    @Parameterized.Parameter(3)
    public String message;

    @Parameterized.Parameters
    public static Object[][] inputs() {
        return new Object[][] {
                {"Luna", 1, 204, ""},
                {"TheMoonsNameIs30CharactersNows", 1, 404, "Invalid moon name"},
                {"Luna", -1, 404, "Invalid planet identifier"}
        };
    }

    @Before
    public void setup() {
        io.restassured.RestAssured.registerParser("text/plain", Parser.TEXT);

        this.jsonAsMap = new HashMap<>();
        this.jsonAsMap.put("username", "Batman");
        this.jsonAsMap.put("password", "Iamthenight1939");
    }

    @Test
    public void deleteMoonAPITest() {
        Response loginResponse = given()
                .contentType("application/json")
                .body(jsonAsMap)
                .when()
                .post("login");

        Map<String, String> cookies = loginResponse.getCookies();

        String encodedMoonName = URLEncoder.encode(moonName, StandardCharsets.UTF_8);

        if (statusCode == 204) {
            if (moonName.length() <= 30 && ownerId > 0) {
                Map<String, Object> moonData = new HashMap<>();
                moonData.put("moonName", moonName);
                moonData.put("ownerId", ownerId);
                moonData.put("imageData", null);

                given()
                        .cookies(cookies)
                        .contentType("application/json")
                        .body(moonData)
                        .when()
                        .post("/planetarium/planet/" + ownerId + "/moon")
                        .then()
                        .statusCode(201);
            }
        }
        Response response = given()
                .cookies(cookies)
                .pathParam("planetId", ownerId)
                .pathParam("identifier", encodedMoonName)
                .when()
                .delete("/planetarium/planet/{planetId}/moon/{identifier}");

        response.then().statusCode(statusCode);

        if (statusCode == 404) {
            if (!message.isEmpty()) {
                response.then().body(equalTo("Endpoint DELETE /planetarium/planet/" + ownerId + "/moon/" + encodedMoonName + " not found"));
            }
        }
    }
}

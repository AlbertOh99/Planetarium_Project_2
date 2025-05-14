package com.revature.planetarium.controller.moon;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GetMoonsByUserIdAPIPositiveTest {
    private Map<String, String> jsonAsMap;

    @Before
    public void setup() {
        this.jsonAsMap = new HashMap<>();
        this.jsonAsMap.put("username", "Batman");
        this.jsonAsMap.put("password", "Iamthenight1939");
    }

    @Test
    public void getMoonsByUserIdPositiveTest() {
        Response loginResponse = given()
                .contentType("application/json")
                .body(jsonAsMap)
                .when()
                .post("login");

        Map<String, String> cookies = loginResponse.getCookies();

        given().cookies(cookies).pathParam("userId", 1)
                .when()
                .get("/planetarium/user/{userId}/moon")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$", not(empty()));
    }
}

package com.revature.planetarium.controller.moon;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class GetMoonsByUserIdAPINegativeTest {
    private Map<String, String> jsonAsMap;

    @Before
    public void setup() {
        this.jsonAsMap = new HashMap<>();
        this.jsonAsMap.put("username", "Batman");
        this.jsonAsMap.put("password", "Iamthenight1939");
    }

    @Test
    public void getMoonsByUserIdNegativeTest() {
        Response loginResponse = given()
                .contentType("application/json")
                .body(jsonAsMap)
                .when()
                .post("login");

        Map<String, String> cookies = loginResponse.getCookies();

        given().cookies(cookies).pathParam("userId", -111)
                .when()
                .get("/planetarium/user/{userId}/moon")
                .then()
                .statusCode(200);
    }
}


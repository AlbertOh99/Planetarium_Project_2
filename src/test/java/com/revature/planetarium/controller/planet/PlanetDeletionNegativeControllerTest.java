package com.revature.planetarium.controller.planet;

import com.revature.planetarium.controller.APIFixture;
import io.javalin.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(Parameterized.class)
public class PlanetDeletionNegativeControllerTest extends APIFixture {
    private static final Logger log = LoggerFactory.getLogger(PlanetDeletionPositiveControllerTest.class);
    private Map<String, String> loginInfo;

    @Parameterized.Parameter(0)
    public int ownerId;
    @Parameterized.Parameter(1)
    public String planetName;
    @Parameterized.Parameters
    public static Object[][] input(){
        return new Object[][]{
                {1, "Earth2"},
                {2, "Earth"}
        };
    }

    @Before
    public void setup() {
        this.loginInfo = new HashMap<>();
        this.loginInfo.put("username", "Batman");
        this.loginInfo.put("password", "Iamthenight1939");
    }


    @Test
    public void planetDeletionNegativeAPITest() throws IOException {
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
                .statusCode(400).contentType("text/plain")
                .body(equalTo("Invalid planet name"));
    }

}

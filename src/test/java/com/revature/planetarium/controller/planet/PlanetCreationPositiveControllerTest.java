package com.revature.planetarium.controller.planet;

import com.revature.planetarium.controller.APIFixture;
import io.javalin.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import static io.restassured.RestAssured.*;

@RunWith(Parameterized.class)
public class PlanetCreationPositiveControllerTest extends APIFixture {
    private Map<String, Object> planetInfo;
    private Map<String, String> loginInfo;

    @Parameterized.Parameter(0)
    public String planetName;

    @Parameterized.Parameter(1)
    public int ownerId;

    @Parameterized.Parameter(2)
    public byte[] imageData;

    @Parameterized.Parameters
    public static Object[][] inputs() throws IOException{
        return new Object[][]{
                {"E", 1, convertToByte(ImageIO.read(new File("src/test/resources/Celestial-Images/planet-1.jpg")))},
                {"E", 1, convertToByte(ImageIO.read(new File("src/test/resources/Celestial-Images/planet-5.png")))},
                {"ThePlanetNameIs30CharactersNow", 1, convertToByte(ImageIO.read(new File("src/test/resources/Celestial-Images/planet-1.jpg")))},
                {"E-arth_3", 1, convertToByte(ImageIO.read(new File("src/test/resources/Celestial-Images/planet-1.jpg")))},
                {"E", 1, null}
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
        this.planetInfo.put("imageData", Arrays.toString(imageData));
    }


    @Test
    public void planetCreationPositiveAPITest() throws IOException {
        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body(this.loginInfo)
                .when()
                .post("login");

        Map<String, String> cookies = loginResponse.getCookies();

        given().contentType(ContentType.JSON).body(planetInfo)
                .cookies(cookies)
                .when()
                .post("planetarium/user/"+ownerId+"/planet")
                .then()
                .statusCode(201);
    }
}

package com.revature.planetarium.repository.users;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RunWith(Parameterized.class)
public class PlanetCreationPositiveTest {

    private PlanetDao planetDao;
    private Planet positivePlanet;

    @Parameter(0)
    public String planetName;

    @Parameter(1)
    public int ownerId;

    @Parameter(2)
    public byte[] imageData;

    @Parameters
    public static Object[][] inputs() throws IOException {
        return new Object[][]{
                {"E", 1, convertToByte(ImageIO.read(new File("src/test/resources/Celestial-Images/moon-1.jpg")))},
                {"ThePlanetNameIs30CharactersNow", 1, convertToByte(ImageIO.read(new File("src/test/resources/Celestial-Images/moon-1.jpg")))},
                {"E-arth_3", 1, convertToByte(ImageIO.read(new File("src/test/resources/Celestial-Images/moon-1.jpg")))},
                {"E", 1, null}
        };
    }

    // To prevent errors from happening, converting the image into a byte array as a method instead of in the new planet
    public static byte[] convertToByte(BufferedImage image){
        return ((DataBufferByte) image.getData().getDataBuffer()).getData();
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = new PlanetDaoImp();
        positivePlanet = new Planet(0,planetName, ownerId, imageData);
    }

    @Test
    public void planetCreationPositiveTest() throws SQLException {
        Optional<Planet> result = planetDao.createPlanet(positivePlanet);
        Assert.assertTrue(result.isPresent());
        Planet returnedPlanet = result.get();
        Assert.assertTrue(returnedPlanet.getPlanetId() > 0);
    }
}

package com.revature.planetarium.service.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RunWith(Parameterized.class)
public class PlanetCreationPositiveServiceTest {

    private PlanetDao planetDao;
    private PlanetService planetService;
    private Planet positivePlanet;
    private Planet stubbedPlanet;

    @Parameter(0)
    public String planetName;

    @Parameter(1)
    public int ownerId;

    @Parameter(2)
    public byte[] imageData;

    @Parameters
    public static Object[][] inputs() throws IOException {
        return new Object[][]{
                {"E", 1, null},
                {"ThePlanetNameIs30CharactersNow", 1, null},
                {"E-arth_3", 1, null},
                {"E", 1, convertToByte(ImageIO.read(new File("src/test/resources/Celestial-Images/planet-2.jpg")))},
                {"E", 1, convertToByte(ImageIO.read(new File("src/test/resources/Celestial-Images/planet-5.png")))},
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
        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetService = new PlanetServiceImp(planetDao);
        positivePlanet = new Planet(0, planetName, ownerId, imageData);
        // Planet returned to simulate getting our new planet from the database
        stubbedPlanet = new Planet(3, planetName, ownerId, imageData);
    }

    @Test
    public void planetCreationPositiveServiceTest() throws SQLException {
        Mockito.when(planetDao.readPlanet(positivePlanet.getPlanetName())).thenReturn(Optional.empty());
        Mockito.when(planetDao.createPlanet(positivePlanet)).thenReturn(Optional.of(stubbedPlanet));
        boolean result = planetService.createPlanet(ownerId,positivePlanet);
        Assert.assertTrue(result);
    }
}

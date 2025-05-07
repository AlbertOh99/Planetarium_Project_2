package com.revature.planetarium.service.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RunWith(Parameterized.class)
public class PlanetCreationNegativeServiceTest {
    private PlanetDao planetDao;
    private PlanetService planetService;
    private Planet negativePlanet;

    @Parameterized.Parameter(0)
    public String planetName;

    @Parameterized.Parameter(1)
    public int ownerId;

    @Parameterized.Parameter(2)
    public byte[] imageData;

    @Parameterized.Parameter(3)
    public String errorMessage;

    @Parameterized.Parameters
    public static Object[][] inputs() throws IOException {
        return new Object[][]{
                // Invalid planet names
                {"", 1, null, "Invalid planet name"},
                {"ThePlanetNameIs31CharactersNowW", 1, null, "Invalid planet name"},
                {"E-arth_3%", 1, null, "Invalid planet name"},
                // Non-unique planet name
                {"Earth", 1, null, "Invalid planet name"},
                // ownerID invalid
                {"Earth2", 5, null, "Invalid owner identifier"},
                // Image Data invalid
                {"Earth2", 1, convertToByte(ImageIO.read(new File("src/test/resources/Fail-Images/No.gif"))), "Invalid file type"}
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
        negativePlanet = new Planet(1, planetName, 1, imageData);
    }

    @Test
    public void planetCreationNegativeServiceTest() throws SQLException, IOException {
        /*
            To prevent the DAO from calling the createUser method, we auto-fail our test since
            service method did not properly stop a faulty planet from being created.
         */
        Mockito.when(planetDao.createPlanet(Mockito.any())).thenThrow(new AssertionError("Failed to create Planet"));


        // For any string expected return should be an empty optional.
        Mockito.when(planetDao.readPlanet(Mockito.anyString())).thenReturn(Optional.empty());
        // Except for duplicate entries.
        Mockito.when(planetDao.readPlanet("Earth")).thenReturn(Optional.of(new Planet(1, "Earth", 1)));
        // Validate the expected exception is thrown
        PlanetFail exception = Assert.assertThrows(PlanetFail.class, ()-> planetService.createPlanet(ownerId, negativePlanet));
        // Expected message is the right reason.
        Assert.assertEquals(errorMessage, exception.getMessage());
    }
}

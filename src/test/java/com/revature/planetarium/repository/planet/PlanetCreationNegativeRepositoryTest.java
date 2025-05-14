package com.revature.planetarium.repository.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@RunWith(Parameterized.class)
public class PlanetCreationNegativeRepositoryTest {

    private PlanetDao planetDao;
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
                {"", 1, null, "name_length_check"},
                {"ThePlanetNameIs31CharactersNowW", 1, null, "name_length_check"},
                {"E-arth_3%", 1, null, "name_character_check"},
                // Non-unique planet name
                {"Earth", 1, null, "UNIQUE"},
                // ownerID invalid
                {"E", 5, null, "FOREIGN KEY"},
                // Image Data invalid
                {"E", 1, convertToByte(ImageIO.read(new File("src/test/resources/Fail-Images/No.gif"))), "SQLException"}
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
        negativePlanet = new Planet(0,planetName, ownerId, imageData);
    }

    @Test
    public void planetCreationNegativeTest() throws SQLException {
        SQLException result = Assert.assertThrows(SQLException.class, ()-> planetDao.createPlanet(negativePlanet));
        Assert.assertTrue(result.getMessage().contains(errorMessage));
        // Temp note - What should I do for testing images. I believe this is a defect
    }
}

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
import java.util.ArrayList;
import java.util.List;

@RunWith(Parameterized.class)
public class PlanetDeletionNegativeServiceTest {

    private PlanetDao planetDao;
    private PlanetService planetService;
    private Planet stubPlanet;
    private List<Planet> stubPlanets;


    @Parameterized.Parameter(0)
    public String planetDeleteName;

    @Parameterized.Parameter(1)
    public int userId;

    @Parameterized.Parameters
    public static Object[][] inputs(){
        return new Object[][]{
                {"Earth", 5},
                {"EarthGibberish", 1}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetService = new PlanetServiceImp(planetDao);
        stubPlanet = new Planet(1, "Earth", 1);
        stubPlanets = new ArrayList<>();
        stubPlanets.add(stubPlanet);
    }

    @Test
    public void planetDeletionNegativeServiceTest() throws SQLException {
        // Should never reach this
        Mockito.when(planetDao.createPlanet(Mockito.any())).thenThrow(new AssertionError("Failed to create Planet"));

        Mockito.when(planetDao.readPlanetsByOwner(userId)).thenReturn(stubPlanets);
        PlanetFail exception = Assert.assertThrows(PlanetFail.class, () -> planetService.deletePlanet(userId, planetDeleteName));
        Assert.assertEquals("Invalid planet name", exception.getMessage());
    }
}
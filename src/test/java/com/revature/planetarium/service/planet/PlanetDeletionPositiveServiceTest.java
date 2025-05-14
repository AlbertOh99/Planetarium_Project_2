package com.revature.planetarium.service.planet;


import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PlanetDeletionPositiveServiceTest {
    private PlanetDao planetDao;
    private PlanetService planetService;
    private Planet stubPlanet;
    private List<Planet> stubPlanets;

    public static String planetDeleteName ;
    public static int userId;

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDeleteName = "Earth";
        userId = 1;
        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetService = new PlanetServiceImp(planetDao);
        stubPlanet = new Planet(1, planetDeleteName, 1);
        stubPlanets = new ArrayList<>();
        stubPlanets.add(stubPlanet);
    }

    @Test
    public void planetDeletionPositiveTest() throws SQLException {
        Mockito.when(planetDao.readPlanetsByOwner(userId)).thenReturn(stubPlanets);

        Mockito.when(planetDao.deletePlanet(planetDeleteName)).thenReturn(true);
        boolean result = planetService.deletePlanet(userId, planetDeleteName);
        Assert.assertTrue(result);

    }
}

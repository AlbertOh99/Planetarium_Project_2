package com.revature.planetarium.service.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlanetRetrievalPositiveServiceTest {
    private PlanetDao planetDao;
    private PlanetService planetService;
    private Planet stubbedPlanet;
    private List<Planet> stubbedPlanets;



    private static int userId;

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetService = new PlanetServiceImp(planetDao);
        userId = 1;
        stubbedPlanet = new Planet(1, "Earth", 1);
        stubbedPlanets = new ArrayList<>();
        stubbedPlanets.add(stubbedPlanet);
    }

    @Test
    public void planetRetrievalPositiveTest() throws SQLException {
        Mockito.when(planetDao.readPlanetsByOwner(userId)).thenReturn(stubbedPlanets);
        List<Planet> result = planetService.selectByOwner(userId);
        Assert.assertEquals(stubbedPlanets, result);
    }
}

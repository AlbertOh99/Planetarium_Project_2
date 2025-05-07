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
import java.util.Collections;
import java.util.List;

public class PlanetRetrievalNegativeServiceTest {
    private PlanetDao planetDao;
    private PlanetService planetService;

    private static int userId;

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        userId = 5;
        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetService = new PlanetServiceImp(planetDao);
    }

    @Test
    public void planetRetrievalNegativeServiceTest() throws SQLException {
        Mockito.when(planetDao.readPlanetsByOwner(userId)).thenReturn(Collections.emptyList());
        List<Planet> result = planetService.selectByOwner(userId);
        Assert.assertEquals(Collections.emptyList(), result);
    }
}
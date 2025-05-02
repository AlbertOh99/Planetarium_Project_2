package com.revature.planetarium.repository.users;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PlanetRetrievalPositiveTest {
    private PlanetDao planetDao;
    private Planet negativePlanet;

    @Parameterized.Parameter
    public int userId = 1;

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = new PlanetDaoImp();
    }

    @Test
    public void planetRetrievalPositiveTest() throws SQLException {
        List<Planet> result = planetDao.readPlanetsByOwner(userId);
        Assert.assertFalse(result.isEmpty());
    }
}

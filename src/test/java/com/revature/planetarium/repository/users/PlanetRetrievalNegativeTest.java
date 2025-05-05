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

public class PlanetRetrievalNegativeTest {
    private PlanetDao planetDao;
    private String planetDeleteName;


    private static int userId;

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = new PlanetDaoImp();
        userId = 5;
    }

    @Test
    public void planetRetrievalNegativeTest() throws SQLException {
        List<Planet> result = planetDao.readPlanetsByOwner(userId);
        Assert.assertTrue(result.isEmpty());
    }
}
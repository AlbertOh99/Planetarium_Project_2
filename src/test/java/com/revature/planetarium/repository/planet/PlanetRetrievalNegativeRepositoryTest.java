package com.revature.planetarium.repository.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class PlanetRetrievalNegativeRepositoryTest {
    private PlanetDao planetDao;


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
package com.revature.planetarium.repository.users;

import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.sql.SQLException;

public class PlanetDeletionNegativeTest {
    private PlanetDao planetDao;
    @Parameterized.Parameter
    public String planetDeleteName = "Earth";

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDeleteName = "EarthGibberish";
        planetDao = new PlanetDaoImp();
    }

    @Test
    public void planetDeletionNegativeTest() throws SQLException {
        Assert.assertFalse(planetDao.deletePlanet(planetDeleteName));
    }
}

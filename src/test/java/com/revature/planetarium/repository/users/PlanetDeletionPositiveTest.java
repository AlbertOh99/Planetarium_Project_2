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


public class PlanetDeletionPositiveTest {
    private PlanetDao planetDao;

    @Parameterized.Parameter
    public String planetDeleteName = "Earth";

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = new PlanetDaoImp();
    }

    @Test
    public void planetDeletionPositiveTest() throws SQLException {
        Assert.assertTrue(planetDao.deletePlanet(planetDeleteName));
    }
}

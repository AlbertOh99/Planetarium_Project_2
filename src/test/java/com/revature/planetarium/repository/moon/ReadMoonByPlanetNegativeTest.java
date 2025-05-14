package com.revature.planetarium.repository.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ReadMoonByPlanetNegativeTest {

    private MoonDao moonDao;

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = new MoonDaoImp();
    }

    @Test
    public void readMoonByPlanetNegativeTest() throws SQLException {
        List<Moon> moons = moonDao.readMoonsByPlanet(-1);
        assertTrue(moons.isEmpty());
    }
}

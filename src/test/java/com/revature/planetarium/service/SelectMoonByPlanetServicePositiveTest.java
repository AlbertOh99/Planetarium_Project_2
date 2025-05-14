package com.revature.planetarium.service;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import com.revature.planetarium.service.moon.MoonService;
import com.revature.planetarium.service.moon.MoonServiceImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SelectMoonByPlanetServicePositiveTest {

    private MoonDao moonDao;
    private MoonService moonService;

    List<Moon> stubbedMoon = Arrays.asList(
            new Moon(1, "Luna", 1, null)
    );


    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = Mockito.mock(MoonDaoImp.class);
        moonService = new MoonServiceImp(moonDao);
    }

    @Test
    public void selectMoonByPlanetServicePositiveTest() throws SQLException {
        Mockito.when(moonDao.readMoonsByPlanet(1)).thenReturn(stubbedMoon);
        List<Moon> result = moonService.selectByPlanet(1);
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("Luna", result.get(0).getMoonName());
    }
}

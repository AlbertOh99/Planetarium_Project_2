package com.revature.planetarium.service;

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

public class DeleteMoonServicePositiveTest {

    private MoonDao moonDao;
    private MoonService moonService;

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = Mockito.mock(MoonDaoImp.class);
        moonService = new MoonServiceImp(moonDao);
    }

    @Test
    public void deleteMoonServicePositiveTest() throws SQLException {
        String moonName = "Luna";
        Mockito.when(moonDao.deleteMoon(moonName)).thenReturn(true);
        boolean result = moonService.deleteMoon(moonName);
        Assert.assertTrue(result);
    }
}

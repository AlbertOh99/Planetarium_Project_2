package com.revature.planetarium.service;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.exceptions.MoonFail;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import com.revature.planetarium.service.moon.MoonService;
import com.revature.planetarium.service.moon.MoonServiceImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

//@RunWith(Parameterized.class)
public class DeleteMoonServiceNegativeTest {

    private MoonDao moonDao;
    private MoonService moonService;

    /*@Parameterized.Parameter
    public String moonName;

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][]{
                {"ThisMoonDoesNotExist"},
                {"Luna"}
        };
    }*/

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = Mockito.mock(MoonDaoImp.class);
        moonService = new MoonServiceImp(moonDao);
    }

    @Test
    public void deleteMoonServiceNegativeTest() throws SQLException {
        //Mockito.when(moonDao.deleteMoon(Mockito.anyString())).thenThrow(new AssertionError("Moon delete failed, please try again"));
        Mockito.when(moonDao.deleteMoon("ThisMoonDoesNotExist")).thenReturn(false);
        MoonFail exception = Assert.assertThrows(MoonFail.class, () -> {
            moonService.deleteMoon("ThisMoonDoesNotExist");
        });
        Assert.assertEquals("Could not delete the moon", exception.getMessage());
    }
}

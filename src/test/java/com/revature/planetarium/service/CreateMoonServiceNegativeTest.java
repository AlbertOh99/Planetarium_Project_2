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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Optional;

@RunWith(Parameterized.class)
public class CreateMoonServiceNegativeTest {

    private MoonDao moonDao;
    private MoonService moonService;

    private Moon negativeMoon;

    @Parameterized.Parameter
    public int moonId;

    @Parameterized.Parameter(1)
    public String moonName;

    @Parameterized.Parameter(2)
    public int ownerId;

    @Parameterized.Parameter(3)
    public byte[] imageData;

    @Parameterized.Parameter(4)
    public String constraint;

    @Parameterized.Parameters
    public static Object[][] inputs() {
        return new Object[][]{
                {0, "TheMoonsNameIs31CharactersNowsW", 1, null, "Invalid moon name"},
                {0, "M-oon_3%", 1, null, "Invalid moon name"},
                {0, "Luna", 1, null, "Invalid moon name"},
                {0, "M-oon_5", -1, null, "Invalid planet ID"},
                {0, "MoonWithImage", 1, loadImage("moon-7.pdf"), "Invalid file type"}
        };
    }

    private static byte[] loadImage(String file) {
        try {
            Path path = Paths.get("src/test/resources/Celestial-Images/" + file);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            return null;
        }
    }


    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = Mockito.mock(MoonDaoImp.class);
        moonService = new MoonServiceImp(moonDao);
        negativeMoon = new Moon(0, moonName, ownerId, imageData);
    }

    @Test
    public void createMoonServiceNegativeTest() throws SQLException {
        Mockito.when(moonDao.createMoon(Mockito.any())).thenThrow(new AssertionError("createMoon should not have been reached"));
        Mockito.when(moonDao.readMoon(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(moonDao.readMoon("Luna")).thenReturn(Optional.of(new Moon(1, "Luna", 1, null)));
        MoonFail exception = Assert.assertThrows(MoonFail.class, () -> moonService.createMoon(negativeMoon));
        Assert.assertEquals(constraint, exception.getMessage());
    }
}

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
public class CreateMoonServicePositiveTest {

    private MoonDao moonDao;
    private MoonService moonService;

    private Moon positiveMoon;
    private Moon stubbedMoon;

    @Parameterized.Parameter
    public int moonId;

    @Parameterized.Parameter(1)
    public String moonName;

    @Parameterized.Parameter(2)
    public int ownerId;

    @Parameterized.Parameter(3)
    public byte[] imageData;

    @Parameterized.Parameters
    public static Object[][] inputs() {
        return new Object[][] {
                {0, "E", 1, null},
                {0, "TheMoonsNameIs30CharactersNows", 1, null},
                {0, "M-oon_3", 1, null},
                {0, "Moon2", 1, null},
                {0, "MoonWithImage", 1, loadImage("moon-1.jpg")},
                {0, "MoonWithImage2", 1, loadImage("moon-6.png")}
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
        positiveMoon = new Moon(0, moonName, ownerId, imageData);
        stubbedMoon = new Moon(1, moonName, ownerId, imageData);
    }

    @Test
    public void createMoonServicePositiveTest() throws SQLException {
        Mockito.when(moonDao.readMoon(positiveMoon.getMoonName())).thenReturn(Optional.empty());
        Mockito.when(moonDao.createMoon(positiveMoon)).thenReturn(Optional.of(stubbedMoon));
        Boolean result = moonService.createMoon(positiveMoon);
        Assert.assertEquals(true, result);
    }
}

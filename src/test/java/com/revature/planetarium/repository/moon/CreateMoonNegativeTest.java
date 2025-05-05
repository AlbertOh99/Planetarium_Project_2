package com.revature.planetarium.repository.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

@RunWith(Parameterized.class)
public class CreateMoonNegativeTest {

    private MoonDao moonDao;

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
                {0, null, 1, null, "NOT NULL"},
                {0, "TheMoonsNameIs31CharactersNowsW", 1, null, "name_length_check"},
                {0, "M-oon_3%", 1, null, "name_character_check"},
                {0, "Luna", 1, null, "UNIQUE"},
                {0, "MoonWithImage", 1, loadImage("moon-7.pdf"), ""}
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
        moonDao = new MoonDaoImp();
        negativeMoon = new Moon(0, moonName, ownerId, imageData);
    }

    @Test
    public void createMoonNegativeTest() {
        SQLException result = Assert.assertThrows(SQLException.class, () -> moonDao.createMoon(negativeMoon));
        Assert.assertTrue(result.getMessage().contains(constraint));
    }


}

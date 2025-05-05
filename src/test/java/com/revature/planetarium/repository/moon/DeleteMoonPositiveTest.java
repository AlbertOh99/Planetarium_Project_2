package com.revature.planetarium.repository.moon;

import com.revature.planetarium.util.TestUtilities;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class DeleteMoonPositiveTest {

    private MoonDao moonDao;

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = new MoonDaoImp();
    }

    @Test
    public void deleteMoonPositiveTest() throws SQLException {
        boolean isDeleted = moonDao.deleteMoon("Luna");
        assertTrue(isDeleted);
    }
}

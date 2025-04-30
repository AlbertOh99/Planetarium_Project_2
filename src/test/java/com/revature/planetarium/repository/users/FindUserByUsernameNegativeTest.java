package com.revature.planetarium.repository.users;

import com.revature.planetarium.entities.User;
import com.revature.planetarium.repository.user.UserDao;
import com.revature.planetarium.repository.user.UserDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
@RunWith(Parameterized.class)
public class FindUserByUsernameNegativeTest {
    private UserDao userDao;


    @Parameterized.Parameter
    public String username;

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][]{
                {"Batman1"},
                {"Superman1"},
                {"Robin1"}
        };
    }
    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        userDao = new UserDaoImp();
    }
    @Test
    public void findUserPositiveTest() throws SQLException {
        Optional<User> result = userDao.findUserByUsername(username);
        Assert.assertTrue("Expected no user to be found for username: "+username, result.isEmpty());

    }
}

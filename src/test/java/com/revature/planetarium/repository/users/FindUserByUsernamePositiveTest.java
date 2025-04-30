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
public class FindUserByUsernamePositiveTest {
    private UserDao userDao;


    @Parameterized.Parameter
    public String username;

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][]{
                {"Batman"},
                {"Superman"},
                {"Robin"}
        };
    }
    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        userDao = new UserDaoImp();
    }
    @Test
    public void findUserPositiveTest() throws SQLException {
        User userToInsert = new User(0, "Robin", "Bobb1");
        userDao.createUser(userToInsert);
        Optional<User> result = userDao.findUserByUsername(username);
        Assert.assertTrue(result.isPresent());
        User user = result.get();
        Assert.assertEquals(username, user.getUsername());
    }


}

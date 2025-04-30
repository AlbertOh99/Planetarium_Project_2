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
public class CreatUsersPositiveTest {
    private UserDao userDao;

    private User positiveUser;

    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public String password;

    @Parameterized.Parameters
    public static String[][] inputs(){
        return new String[][]{
                {"Robin","Bobb1"},
                {"bobby","Bobb1"},
                {"Robin_1-5","Bobb1"},
                {"Robin","Bob_b-1"}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        // make sure to reset your test database between tests
        // current reset method requires planetarium to be up and running to ping the reset route
        TestUtilities.resetDatabase();
        userDao = new UserDaoImp();
        positiveUser = new User(0,username,password);
    }

    @Test
    public void createUserPositiveTest() throws SQLException {
        Optional<User> result = userDao.createUser(positiveUser);
        // here we check that the user was actually returned
        Assert.assertTrue(result.isPresent());
        User returnedUser = result.get();
        // here we check that the user object was assigned a new Id
        Assert.assertTrue(returnedUser.getId() > 0);

    }


}

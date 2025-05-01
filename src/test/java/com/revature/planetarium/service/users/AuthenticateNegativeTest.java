package com.revature.planetarium.service.users;

import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.repository.user.UserDao;
import com.revature.planetarium.service.user.UserService;
import com.revature.planetarium.service.user.UserServiceImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@RunWith(Parameterized.class)
public class AuthenticateNegativeTest {

    private UserDao userDao;
    private UserService userService;

    private User inputUser;

    @Parameterized.Parameter(0)
    public String inputUsername;

    @Parameterized.Parameter(1)
    public String inputPassword;

    @Parameterized.Parameter(2)
    public Optional<User> stubbedUser;



    @Parameterized.Parameters(name = "{index}: username={0}, password={1}, stubbed={2}")
    public static Collection<Object[]> inputs() {
        return Arrays.asList(new Object[][]{
                {"unknown", "anyPass", Optional.empty()},  // User not found
                {"bobby", "wrongPass", Optional.of(new User(1, "bobby", "correctPass"))}, // Password mismatch
                {"Robin", "badPassword", Optional.of(new User(1, "Robin", "GoodPass1"))}  // Password mismatch
        });
    }

    @Before
    public void setup() throws IOException, InterruptedException,SQLException {
        TestUtilities.resetDatabase();

        userDao = Mockito.mock(UserDao.class);
        userService = new UserServiceImp(userDao);

        inputUser = new User(0, inputUsername, inputPassword);

        // Mock DAO to return configured stubbed user (or empty)
        Mockito.when(userDao.findUserByUsername(inputUsername)).thenReturn(stubbedUser);
    }
    @Test
    public void testAuthenticateFailure() throws SQLException {
        try {
            userService.authenticate(inputUser);
            // If no exception is thrown, the test should fail
            org.junit.Assert.fail("Expected UserFail to be thrown");
        } catch (UserFail ex) {
            org.junit.Assert.assertEquals("Invalid credentials", ex.getMessage());
        }
    }

}

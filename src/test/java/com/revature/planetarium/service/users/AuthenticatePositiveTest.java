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

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class AuthenticatePositiveTest {

    private UserDao userDao;
    private UserService userService;

    private User inputUser;
    private User stubbedUser;

    @Parameterized.Parameter(0)
    public String username;

    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][]{
                {"Robin", "Bobb1"},
                {"bobby", "Bobb1"},
                {"Robin_1-5", "Bobb1"},
                {"Robin", "Bob_b-1"}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException ,SQLException{
        TestUtilities.resetDatabase();

        userDao = Mockito.mock(UserDao.class);
        userService = new UserServiceImp(userDao);

        inputUser = new User(0, username, password);
        stubbedUser = new User(1, username, password); // simulate found user from DB

        Mockito.when(userDao.findUserByUsername(username)).thenReturn(Optional.of(stubbedUser));
    }

    @Test
    public void testAuthenticateSuccess() throws SQLException {
        User result = userService.authenticate(inputUser);
        assertEquals(username, result.getUsername());
        assertNull(result.getPassword()); // password should be nulled after auth
    }
}

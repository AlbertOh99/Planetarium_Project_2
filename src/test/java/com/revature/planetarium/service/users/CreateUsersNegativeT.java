package com.revature.planetarium.service.users;

import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.repository.user.UserDao;
import com.revature.planetarium.repository.user.UserDaoImp;
import com.revature.planetarium.service.user.UserService;
import com.revature.planetarium.service.user.UserServiceImp;
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

@RunWith(Parameterized.class)
public class CreateUsersNegativeT {
    private UserDao userDao;
    private UserService userService;

    private User NegativeUser;


    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameter(2)
    public String message;
    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][]{
                {"Batman","Bobb1","Invalid username"},
                {"bobb","Bobb1", "Invalid username"},
                {"Robin!%?","Bobb1", "Invalid username"},
                {"3obin","Bobb1", "Invalid username"},
                // then check the password requirements are enforced
                {"Robin","Bob3", "Invalid password"},
                {"Robin", "bobb3", "Invalid password"},
                {"Robin", "Bobby", "Invalid password"},
                {"Robin", "BOBB3", "Invalid password"},
                {"Robin", "Bobb3!?", "Invalid password"},
                {"Robin", "3obbY", "Invalid password"}
        };
    }

    @Before
    public  void setup() throws IOException,InterruptedException {

        TestUtilities.resetDatabase();
        userDao = Mockito.mock(UserDaoImp.class);
        userService = new UserServiceImp(userDao);
        NegativeUser = new User(0,username,password);

    }
    @Test
    public void CreateUserNegativeServiceT() throws SQLException {
        Mockito.when(userDao.createUser(Mockito.any())).thenThrow(new AssertionError("createUser should not have been reached"));
        Mockito.when(userDao.findUserByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userDao.findUserByUsername("Batman")).thenReturn(Optional.of(new User(1,"Batman","Iamthenight1939")));
        UserFail exception = Assert.assertThrows(UserFail.class,()->userService.createUser(NegativeUser));
        Assert.assertEquals(message,exception.getMessage());

    }
}

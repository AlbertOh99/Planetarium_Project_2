package com.revature.planetarium.service.users;

import com.revature.planetarium.entities.User;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import org.mockito.Mockito;

@RunWith(Parameterized.class)
public class CreateUsersPostiveServiceT {

    private UserDao userDao;
    private UserService userService;

    private User positiveUser;
    private User stubbedUser;

    @Parameterized.Parameter
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
    public  void setup() throws IOException,InterruptedException {

        TestUtilities.resetDatabase();
        userDao = Mockito.mock(UserDaoImp.class);
        userService = new UserServiceImp(userDao);
        positiveUser = new User(0,username,password);
        stubbedUser =new User(3,username,password);
    }
    @Test
    public void createPositiveServiceTest() throws SQLException{
        Mockito.when(userDao.findUserByUsername(positiveUser.getUsername())).thenReturn(Optional.empty());
        Mockito.when(userDao.createUser(positiveUser)).thenReturn(Optional.of(stubbedUser));
        String result = userService.createUser(positiveUser);
        Assert.assertEquals("User created successfully",result);



    }

}

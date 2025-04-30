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
public class CreateUsersNegativeTest {

    private UserDao userDao;
    private User negativeUser;

    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameter(2)
    public String constraint;

    @Parameterized.Parameters
    public static String[][] inputs(){
        return new String[][]{
                {"Batman","Bobb1","UNIQUE"},
                {"bobb","Bobb1", "username_length_check"},
                {"Robin!%?","Bobb1", "username_character_check"},
                {"3obin","Bobb1", "username_character_check"},
                // then check the password requirements are enforced
                {"Robin","Bob3", "password_length_check"},
                {"Robin", "bobb3", "password_character_check"},
                {"Robin", "Bobby", "password_character_check"},
                {"Robin", "BOBB3", "password_character_check"},
                {"Robin", "Bobb3!?", "password_character_check"},
                {"Robin", "3obbY", "password_character_check"}
        };
    }
    @Before
    public void setup() throws IOException, InterruptedException{
        TestUtilities.resetDatabase();
        userDao = new UserDaoImp();
        negativeUser = new User(0,username,password);
    }

    @Test
    public  void createUserNegativeTest(){
        SQLException result = Assert.assertThrows(SQLException.class,()-> userDao.createUser(negativeUser));
        Assert.assertTrue(result.getMessage().contains(constraint));


    }


}

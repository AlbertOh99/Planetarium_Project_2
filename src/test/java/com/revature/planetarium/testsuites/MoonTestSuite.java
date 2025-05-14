package com.revature.planetarium.testsuites;

import com.revature.planetarium.repository.moon.*;
import com.revature.planetarium.service.*;
import com.revature.planetarium.controller.moon.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //Repository Layer Tests
        CreateMoonPositiveTest.class,
        CreateMoonNegativeTest.class,
        ReadMoonByPlanetPositiveTest.class,
        ReadMoonByPlanetNegativeTest.class,
        DeleteMoonPositiveTest.class,
        DeleteMoonNegativeTest.class,

        //Service Layer Tests
        CreateMoonServicePositiveTest.class,
        CreateMoonServiceNegativeTest.class,
        SelectMoonByPlanetServicePositiveTest.class,
        SelectMoonByPlanetServiceNegativeTest.class,
        DeleteMoonServicePositiveTest.class,
        DeleteMoonServiceNegativeTest.class,

        //API Testing
        CreateMoonAPITest.class,
        GetMoonsByUserIdAPIPositiveTest.class,
        GetMoonsByUserIdAPINegativeTest.class,
        DeleteMoonAPITest.class
})
public class MoonTestSuite {
}

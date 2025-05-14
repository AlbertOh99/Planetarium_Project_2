package com.revature.planetarium.testsuits;

import com.revature.planetarium.repository.planet.*;
import com.revature.planetarium.service.planet.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        // Repository Layer Tests
        PlanetCreationNegativeRepositoryTest.class,
        PlanetCreationPositiveRepositoryTest.class,
        PlanetRetrievalNegativeRepositoryTest.class,
        PlanetRetrievalPositiveRepositoryTest.class,
        PlanetDeletionNegativeRepositoryTest.class,
        PlanetDeletionPositiveRepositoryTest.class,
        // Service Layer Tests
        PlanetCreationNegativeServiceTest.class,
        PlanetCreationPositiveServiceTest.class,
        PlanetRetrievalNegativeServiceTest.class,
        PlanetRetrievalPositiveServiceTest.class,
        PlanetDeletionNegativeServiceTest.class,
        PlanetDeletionPositiveServiceTest.class,
        // API Layer Tests
})
public class PlanetTestSuit {
}

package eu.planets_project.ifr.core.services.comparison;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import eu.planets_project.ifr.core.services.comparison.comparator.config.ComparatorConfigCreatorTests;
import eu.planets_project.ifr.core.services.comparison.comparator.config.ComparatorConfigParserTests;
import eu.planets_project.ifr.core.services.comparison.comparator.impl.ComparatorServiceTests;
import eu.planets_project.ifr.core.services.comparison.comparator.impl.ComparatorWrapperTests;
import eu.planets_project.ifr.core.services.comparison.comparator.impl.ResultPropertiesReaderTests;

/**
 * Suite to run all tests in the comparator component.
 * @author Fabian Steeg (fabian.steeg@uni-koeln.de)
 */

@RunWith(Suite.class)
@Suite.SuiteClasses( { ComparatorWrapperTests.class,
        ComparatorServiceTests.class, ResultPropertiesReaderTests.class,
        ComparatorConfigCreatorTests.class, ComparatorConfigParserTests.class })
public class AllComparatorSuite {
    
    /**
     * set the props for testing.
     */
    @BeforeClass
    public static void setupProperties() {
        System.setProperty("pserv.test.context", "server");
        System.setProperty("pserv.test.host", "localhost");
        System.setProperty("pserv.test.port", "8080");
    }
}

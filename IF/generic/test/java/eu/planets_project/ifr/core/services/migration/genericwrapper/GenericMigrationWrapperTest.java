package eu.planets_project.ifr.core.services.migration.genericwrapper;

import eu.planets_project.ifr.core.services.migration.genericwrapper.utils.DocumentLocator;
import eu.planets_project.services.datatypes.*;
import eu.planets_project.services.migrate.MigrateResult;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Thomas Skou Hansen &lt;tsh@statsbiblioteket.dk&gt;
 */
public class GenericMigrationWrapperTest {

    /**
     * File path to the test files used by this test class.
     */
    private static final File TEST_FILE_PATH = new File(
            "IF/generic/test/resources/");

    private static final String TEST_FILE_NAME = "Arrows_doublestraight_arrow2.dia";

    private final URI sourceFormatURI;
    private final URI destinationFormatURI;
    private GenericMigrationWrapper genericWrapper;
final List<Parameter> testParameters = new ArrayList<Parameter>();
    /**
     */
    public GenericMigrationWrapperTest() throws Exception {
        sourceFormatURI = new URI("info:test/lowercase");
        destinationFormatURI = new URI("info:test/uppercase");
    }

    @Before
    public void setUp() throws Exception {

        testParameters.add(new Parameter("mode", "complete"));

        DocumentLocator documentLocator = new DocumentLocator("exampleConfigfile.xml");
         genericWrapper = new GenericMigrationWrapper(
                documentLocator.getDocument(), this.getClass().getCanonicalName());

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDescribe(){
        ServiceDescription sb = genericWrapper.describe();
        System.out.println(sb);
    }

    @Test
    public void testMigrateUsingTempFiles() throws Exception {


        MigrateResult migrationResult = genericWrapper.migrate(
                getDigitalTestObject(), sourceFormatURI, destinationFormatURI,
                testParameters);

        Assert.assertEquals(ServiceReport.Status.SUCCESS, migrationResult
                .getReport().getStatus());
    }

    private DigitalObject getDigitalTestObject() {
        final File diaTestFile = new File(TEST_FILE_PATH, TEST_FILE_NAME);

        DigitalObject.Builder digitalObjectBuilder = new DigitalObject.Builder(
                Content.byValue(diaTestFile));
        digitalObjectBuilder.format(sourceFormatURI);
        digitalObjectBuilder.title(TEST_FILE_NAME);
        return digitalObjectBuilder.build();

    }
}
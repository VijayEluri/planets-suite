package eu.planets_project.ifr.core.services.comparison.comparator.impl;

import java.io.File;
import java.util.List;

import org.junit.Test;

import eu.planets_project.services.compare.Compare;
import eu.planets_project.services.datatypes.Content;
import eu.planets_project.services.datatypes.DigitalObject;
import eu.planets_project.services.datatypes.Prop;
import eu.planets_project.services.utils.ByteArrayHelper;
import eu.planets_project.services.utils.test.ServiceCreator;

/**
 * Local and client tests of the comparator service.
 * @author Fabian Steeg
 */
public final class ComparatorServiceTests {

    private static final String WSDL = "/pserv-pp-comparator/XcdlCompare?wsdl";

    /**
     * Tests PP comparator comparison using the XCDL comparator.
     */
    @Test
    public void testService() {
        byte[] data1 = ByteArrayHelper.read(new File(
                ComparatorWrapperTests.XCDL1));
        byte[] data2 = ByteArrayHelper.read(new File(
                ComparatorWrapperTests.XCDL2));
        byte[] configData = ByteArrayHelper.read(new File(
                ComparatorWrapperTests.PCR_SINGLE));
        testServices(data1, data2, configData);
    }

    /**
     * Tests the services that use the actual value strings.
     * @param data1 The XCDL1 data
     * @param data2 The XCDL2 data
     * @param configData The config data
     */
    protected void testServices(final byte[] data1, final byte[] data2,
            final byte[] configData) {
        Compare c = ServiceCreator.createTestService(XcdlCompare.QNAME,
                XcdlCompare.class, WSDL);
        DigitalObject[] objects = new DigitalObject[] {
                new DigitalObject.Builder(Content.byValue(data1)).build(),
                new DigitalObject.Builder(Content.byValue(data2)).build() };
        DigitalObject configFile = new DigitalObject.Builder(Content
                .byValue(configData)).build();
        List<Prop> properties = c.compare(objects,
                c.convert(configFile)).getProperties();
        ComparatorWrapperTests.check(properties);
    }
}

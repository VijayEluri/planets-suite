package eu.planets_project.ifr.core.services.validation.impl;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.MalformedURLException;

import org.junit.Test;

import eu.planets_project.services.PlanetsException;
import eu.planets_project.services.datatypes.Content;
import eu.planets_project.services.datatypes.DigitalObject;
import eu.planets_project.services.validate.Validate;
import eu.planets_project.services.validate.ValidateResult;
import eu.planets_project.services.validate.ValidateResult.Validity;
import eu.planets_project.services.utils.test.ServiceCreator;
import eu.planets_project.services.utils.test.ServiceCreator.Mode;

/**
 * Local and client tests of the LibTiffCheck functionality.
 * @author Klaus Rechert
 */
public final class TiffCheckTests 
{
	/**
	 * Tests TiffValidation identification using a local TiffValidation instance.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void localTests() {
		test(new TiffValidation());
	}

	/**
	 * Tests TiffCheck identification using a TiffCheck instance retrieved via the
	 * web service (running on localhost).
	 */
	@Test(expected = Exception.class)
	/*
	 * Depending on the setting, the IllegalArgumentException might be wrapped
	 * in a SOAPFaultExcpetion, so we expect an Exception
	 */
	public void clientTests() 
	{
		Validate tiffCheck = ServiceCreator.createTestService(
			Validate.QNAME, TiffValidation.class,
			"/pserv-pc-libtiff/TiffValidation?wsdl", Mode.SERVER);
		test(tiffCheck);
	}

	/**
	 * Test a TiffCheck instance by trying to validate a valid TIFF file and by
	 * trying to invalidate a PNG file.
	 * @param tiffCheck The tiffCheck instance to test
	 */
	private void test(final Validate tiffCheck) 
	{
		File pngFile = new File("PC/libtiff/src/resources/image01.png");
		File tifFile = new File("PC/libtiff/src/resources/image01.tif");
		Content pngContent = null;
		Content tifContent = null;
		try {
			pngContent = Content.byReference(pngFile.toURL());
			tifContent = Content.byReference(tifFile.toURL());
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}

		DigitalObject inPng = new DigitalObject.Builder(pngContent).build();
		DigitalObject inTiff = new DigitalObject.Builder(pngContent).build();

		ValidateResult result;
		/* Check with null PRONOM URI, both with PNG and TIFF */
		try {
			result = tiffCheck.validate(inTiff, null);
			assertTrue("Valid TIFF was not validated;", 
				result.getValidity().equals(Validity.VALID));

			result = tiffCheck.validate(inPng, null);
			assertTrue("Invalid TIFF was not invalidated;", 
				result.getValidity().equals(Validity.INVALID));

			/* Check with valid and invalid PRONOM URI */
			URI uri = new URI("info:pronom/fmt/7");
			result = tiffCheck.validate(inTiff, uri);
			assertTrue("Valid TIFF with URI was not validated;", 
				result.getValidity().equals(Validity.VALID));

			/* This should throw an IllegalArgumentException: */
			uri = new URI("info:pronom/fmt/11");
			result = tiffCheck.validate(inTiff, uri);
			assertTrue("Valid TIFF with invalid URI not invalidated;", 
				result.getValidity().equals(Validity.INVALID));

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

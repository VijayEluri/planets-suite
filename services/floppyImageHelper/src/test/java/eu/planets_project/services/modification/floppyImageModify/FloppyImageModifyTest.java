/**
 * 
 */
package eu.planets_project.services.modification.floppyImageModify;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.planets_project.ifr.core.techreg.formats.FormatRegistry;
import eu.planets_project.ifr.core.techreg.formats.FormatRegistryFactory;
import eu.planets_project.services.datatypes.Content;
import eu.planets_project.services.datatypes.DigitalObject;
import eu.planets_project.services.datatypes.ServiceDescription;
import eu.planets_project.services.migration.floppyImageHelper.impl.utils.Fat_Imgen;
import eu.planets_project.services.modification.floppyImageModify.impl.FloppyImageModifyWin;
import eu.planets_project.services.modify.Modify;
import eu.planets_project.services.modify.ModifyResult;
import eu.planets_project.services.utils.DigitalObjectUtils;
import eu.planets_project.services.utils.test.ServiceCreator;

/**
 * @author melmsp
 *
 */
public class FloppyImageModifyTest {
	
	static Modify FLOPPY_IMAGE_MODIFY;
	
	static String WSDL = "/pserv-pa-floppyimagehelper/FloppyImageModifyWin?wsdl";
	
	static String OUT_DIR_NAME = "FLOPPY_IMAGE_MODIFY_TEST_OUT";
	
	static File OUT_DIR = null;
	
	static File FILES_TO_INJECT = new File("PA/floppyImageHelper/src/test/resources/input_files/for_injection");
	
	static File FILES_FOR_MODIFICATION = new File("PA/floppyImageHelper/src/test/resources/input_files/for_modification");
	
	static File FILES_FOR_MODIFICATION_WITH_ERROR = new File("PA/floppyImageHelper/src/test/resources/input_files/for_modification_with_error");
	
	static File FLOPPY_IMAGE = new File("PA/floppyImageHelper/src/test/resources/input_files/for_modification/floppy144.IMA");
//	static File FLOPPY_IMAGE = new File("tests/test-files/images/bitmap/test_tiff/2277759451_2e4cd93544.tif");

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
//		System.setProperty("pserv.test.context", "server");
//        System.setProperty("pserv.test.host", "localhost");
//        System.setProperty("pserv.test.port", "8080");
        
		// Config the logger:
        Logger.getLogger("").setLevel( Level.FINE );
        OUT_DIR = new File(Fat_Imgen.SYSTEM_TEMP_FOLDER, OUT_DIR_NAME);
        FileUtils.forceMkdir(OUT_DIR);
        FLOPPY_IMAGE_MODIFY = ServiceCreator.createTestService(Modify.QNAME, FloppyImageModifyWin.class, WSDL);
        
	}

	/**
	 * Test method for {@link eu.planets_project.services.migration.floppyImageHelper.impl.utils.FloppyImageHelperWin#describe()}.
	 */
	@Test
	public void testDescribe() {
		ServiceDescription sd = FLOPPY_IMAGE_MODIFY.describe();
		assertTrue("The ServiceDescription should not be NULL.", sd != null );
    	System.out.println("test: describe()");
    	System.out.println("--------------------------------------------------------------------");
    	System.out.println();
    	System.out.println("Received ServiceDescription from: " + FLOPPY_IMAGE_MODIFY.getClass().getName());
    	System.out.println(sd.toXmlFormatted());
    	System.out.println("--------------------------------------------------------------------");
	}
	
	
	
	@Test
	public void testModify() throws URISyntaxException {
		List<File> fileList = listAllFilesAndFolders(FILES_FOR_MODIFICATION, new ArrayList<File>());
		fileList.remove(FLOPPY_IMAGE);
//		fileList.remove(new File("PA/floppyImageHelper/src/test/resources/input_files/for_modification/FLOPPY144.IMA"));
		FormatRegistry format = FormatRegistryFactory.getFormatRegistry();
        DigitalObject inputDigObj = new DigitalObject.Builder(Content.byReference(FLOPPY_IMAGE))
									.title(FLOPPY_IMAGE.getName())
									.format(format.createExtensionUri(FilenameUtils.getExtension(FLOPPY_IMAGE.getName())))
									.build();
		ModifyResult result = FLOPPY_IMAGE_MODIFY.modify(inputDigObj, format.createExtensionUri(FilenameUtils.getExtension(FLOPPY_IMAGE.getName())), null);
		
		System.out.println("Got report: " + result.getReport());
		DigitalObject digObjres = result.getDigitalObject();
		assertTrue("Resulting DigitalObject should not be NULL", digObjres!=null);
		System.out.println("DigitalObject: " + digObjres);
		File out = new File(OUT_DIR, digObjres.getTitle());
		DigitalObjectUtils.toFile(digObjres, out);
		System.out.println("Please find the floppy image here: " + out.getAbsolutePath());
	}
	
	private List<File> listAllFilesAndFolders(final File dir,
            final List<File> list) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File currentFile = files[i];
                boolean currentFileIsDir = currentFile.isDirectory();
                if (currentFileIsDir) {
                    // Ignore hidden folders
                    if (currentFile.isHidden()) {
                        continue;
                    }
                    if (currentFile.getName().equalsIgnoreCase("CVS")) {
                        continue;
                    }
                    list.add(currentFile);
                    listAllFilesAndFolders(currentFile, list);
                } else {
                    list.add(currentFile);
                }
            }
        }
        return list;
    }
}
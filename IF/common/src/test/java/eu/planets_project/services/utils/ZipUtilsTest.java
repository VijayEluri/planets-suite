/**
 * 
 */
package eu.planets_project.services.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author melmsp
 *
 */
public class ZipUtilsTest {
	
	private static final File TEST_FILE_FOLDER = new File("IF/common/src/test/resources/test_zip");
//	private static final File TEST_FILE_FOLDER = new File("C:/Dokumente und Einstellungen/melmsp/Desktop/hartwig/test_zip");
	
	private static final File OUTPUT_FOLDER = 
		FileUtils.createWorkFolderInSysTemp("ZipUtils_Test_Tmp".toUpperCase());
	
	private static final File EXTRACT_RESULT_OUT = 
		FileUtils.createFolderInWorkFolder(OUTPUT_FOLDER, "EXTRACTED");

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		FileUtils.deleteAllFilesInFolder(OUTPUT_FOLDER);
	}
	

	/**
	 * Test method for {@link eu.planets_project.services.utils.ZipUtils#createZip(java.io.File, java.io.File, java.lang.String, boolean)}.
	 */
	@Test
	public void testCreateZipAndUnzipTo() {
		FileUtils.deleteAllFilesInFolder(OUTPUT_FOLDER);
		int inputFileCount = FileUtils.listAllFilesAndFolders(TEST_FILE_FOLDER, new ArrayList<File>()).size();
		File zip = ZipUtils.createZip(TEST_FILE_FOLDER, OUTPUT_FOLDER, "zipUtilsTest.zip", true);
		System.out.println("Zip created. Please find it here: " + zip.getAbsolutePath());
		String folderName = zip.getName().substring(0, zip.getName().lastIndexOf("."));
		File extract = FileUtils.createFolderInWorkFolder(OUTPUT_FOLDER, folderName);
		List<File> extracted = ZipUtils.unzipTo(zip, extract);
		System.out.println("Extracted files:" + System.getProperty("line.separator"));
		for (File file : extracted) {
			System.out.println(file.getAbsolutePath());
		}
		System.out.println("input file-count:  " + inputFileCount);
		System.out.println("output file-count: " + extracted.size());
	}

	/**
	 * Test method for {@link eu.planets_project.services.utils.ZipUtils#createZipAndCheck(java.io.File, java.io.File, java.lang.String, boolean)}.
	 */
	@Test
	public void testCreateZipAndCheckAndCheckAndUnzip() {
		int inputFileCount = FileUtils.listAllFilesAndFolders(TEST_FILE_FOLDER, new ArrayList<File>()).size();
		ZipResult zip = ZipUtils.createZipAndCheck(TEST_FILE_FOLDER, OUTPUT_FOLDER, "zipUtilsTestCheck.zip", true);
		System.out.println("[Checksum]: Algorith=" + zip.getChecksum().getAlgorithm() + " | checksum=" + zip.getChecksum().getValue());
		System.out.println("Zip created. Please find it here: " + zip.getZipFile().getAbsolutePath());
		String folderName = zip.getZipFile().getName().substring(0, zip.getZipFile().getName().lastIndexOf("."));
		File extract = FileUtils.createFolderInWorkFolder(OUTPUT_FOLDER, folderName);
		List<File> extracted = ZipUtils.checkAndUnzipTo(zip.getZipFile(), EXTRACT_RESULT_OUT, zip.getChecksum());
		System.out.println("Extracted files:" + System.getProperty("line.separator"));
		for (File file : extracted) {
			System.out.println(file.getAbsolutePath());
		}
		System.out.println("input file-count:  " + inputFileCount);
		System.out.println("output file-count: " + extracted.size());
	}
	
	/**
	 * Test method for {@link eu.planets_project.services.utils.ZipUtils#removeFileFrom(java.io.File, java.lang.String)}.
	 */
	@Test
	public void testRemoveFile() {
		FileUtils.deleteAllFilesInFolder(OUTPUT_FOLDER);
		int inputFileCount = FileUtils.listAllFilesAndFolders(TEST_FILE_FOLDER, new ArrayList<File>()).size();
		File zip = ZipUtils.createZip(TEST_FILE_FOLDER, OUTPUT_FOLDER, "zipUtilsTestRemove.zip", true);
		System.out.println("Zip created. Please find it here: " + zip.getAbsolutePath());
		String folderName = zip.getName().substring(0, zip.getName().lastIndexOf("."));
		File extract = FileUtils.createFolderInWorkFolder(OUTPUT_FOLDER, folderName);
		List<File> extracted = ZipUtils.unzipTo(zip, extract);
		File deleteSingleFile = new File("IF/common/src/test/resources/test_zip/images/test_jp2/canon-ixus.jpg.jp2");
		File modifiedZip = ZipUtils.removeFileFrom(zip, "images\\test_jp2\\canon-ixus.jpg.jp2");
		modifiedZip = ZipUtils.removeFileFrom(zip, "images\\test_gif");
		System.out.println("Zip modified. Please find it here: " + zip.getAbsolutePath());
	}

	/**
	 * Test method for {@link eu.planets_project.services.utils.ZipUtils#insertFileInto(java.io.File, java.io.File, java.lang.String)}.
	 */
	@Test
	public void testInsertFile() {
		FileUtils.deleteAllFilesInFolder(OUTPUT_FOLDER);
		File zip = ZipUtils.createZip(TEST_FILE_FOLDER, OUTPUT_FOLDER, "zipUtilsTestInsert.zip", true);
		System.out.println("Zip created. Please find it here: " + zip.getAbsolutePath());
		File toInsert = new File("IF/common/src/test/resources/test_zip/images/Kopie von test_gif");
		File modifiedZip = ZipUtils.insertFileInto(zip, toInsert, "images\\test_gif");
//		File insertMore = new File("tests/test-files/documents/test_pdf/");
//		modifiedZip = ZipUtils.insertFileInto(zip, insertMore, "documents\\test_pdf");
		System.out.println("Zip modified. Please find it here: " + zip.getAbsolutePath());
	}
	
	
	/**
	 * Test method for {@link eu.planets_project.services.utils.ZipUtils#insertFileInto(java.io.File, java.io.File, java.lang.String)}.
	 */
	@Test
	public void testGetFileFrom() {
		FileUtils.deleteAllFilesInFolder(OUTPUT_FOLDER);
		File zip = ZipUtils.createZip(TEST_FILE_FOLDER, OUTPUT_FOLDER, "zipUtilsTestGetFile.zip", true);
		System.out.println("Zip created. Please find it here: " + zip.getAbsolutePath());
		
		File fromZip = ZipUtils.getFileFrom(zip, "images\\test_gif", OUTPUT_FOLDER);
		System.out.println("File extracted. Please find it here: " + fromZip.getAbsolutePath());
	}

	

	

}

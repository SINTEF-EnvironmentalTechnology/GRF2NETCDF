/**
 * 
 */
package no.sintef.fates.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;

import junit.framework.TestCase;
import no.sintef.NetCDF.NetCDFException;
import no.sintef.fates.io.files.GRFException;
import no.sintef.fates.io.files.HDR;
import no.sintef.model.OilSpillSimulation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ubr
 * 
 */
public class GRFFileDAOTest extends TestCase {

	private File grfFile, hdrFile;
	private Calendar cal = Calendar.getInstance();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		String filename = "";
		filename = "./testfiles/OSCAR1.grf";
		grfFile = new File(filename);

		String absolutePath = grfFile.getAbsolutePath();

		String filenameWOending = absolutePath.substring(0,
				absolutePath.lastIndexOf("."));

		hdrFile = new File(filenameWOending + HDR.FILENAME);
		HDRFileDAO hdrReader = new HDRFileDAO(hdrFile);
		HDR headerFileCOntent = hdrReader.readHeaderFile();

		OilSpillSimulation spillInfo = headerFileCOntent.getSpillInfo();

		cal.set(spillInfo.getStartYearOfSpill(),
				spillInfo.getStartMonthOfSpill() - 1,
				spillInfo.getStartDayOfSpill(),
				spillInfo.getStartHourOfSpill(), 0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link no.sintef.fates.io.GRFFileDAO#GRFFileReader(java.io.File, java.util.Date)}
	 * .
	 */
	@Test
	public void testGRFFileDAO() {

		GRFFileDAO tester;
		try {
			tester = new GRF2NetCDFDAO(grfFile, cal.getTime());

			assertNotNull(tester);
			assertEquals(grfFile.getAbsolutePath(), tester.filename);
			assertEquals(cal.getTime(), tester.simulationStart);

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (NetCDFException e) {
			fail(e.getMessage());
		}
	}

	

	/**
	 * Test method for {@link no.sintef.fates.io.GRFFileDAO#readFile()}.
	 */
	@Test
	public void testReadFile() {
		GRFFileDAO tester;
		try {
			tester = new GRF2NetCDFDAO(grfFile, cal.getTime());
//			tester.subSurfaceGridConcentrationsFile = true;
			tester.massBalanceFile = true;
			tester.surfaceGridOilFile = true;
			tester.surfaceGridGasFile = true;
			tester.readFile();

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (GRFException e) {
		    e.printStackTrace();
			fail(e.getMessage());
		} catch (NetCDFException e) {
			fail(e.getMessage());
		}

	}

}

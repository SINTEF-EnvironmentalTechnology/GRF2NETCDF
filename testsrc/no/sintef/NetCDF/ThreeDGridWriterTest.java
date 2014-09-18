/**
 * 
 */
package no.sintef.NetCDF;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ucar.nc2.NetcdfFile;

/**
 * @author ubr
 * 
 */
public class ThreeDGridWriterTest {
	private ThreeDGridWriter tester = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tester = new ConcentrationGridWriter();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateFile() {
		String fileLocation = "./testfiles/generated/ThreeDGridWriter-CreateTest.nc";

		try {
			tester.createFile(3, 2, 1, Calendar.getInstance().getTime(),

			fileLocation);
			// file created
			File testfile = new File(fileLocation);
			assertTrue(testfile.exists());
			assertTrue(testfile.isFile());

			// file can be opened
			assertTrue(NetcdfFile.canOpen(fileLocation));

			// CDL correct
			NetcdfFile ncfile = NetcdfFile.open(fileLocation);
			assertNotNull(ncfile.getUnlimitedDimension());
			assertNotNull(ncfile.findVariable(ConcentrationGridWriter.LATITUDE));
			assertNotNull(ncfile
					.findVariable(ConcentrationGridWriter.LONGITUDE));
			assertNotNull(ncfile.findVariable(ConcentrationGridWriter.DEPTH));

			assertNotNull(ncfile.findVariable(ConcentrationGridWriter.TIME));
		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}

/**
 * 
 */
package no.sintef.fates.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

import junit.framework.TestCase;
import no.sintef.fates.io.files.BinaryHeader;
import no.sintef.fates.io.files.BinaryHeader.BinaryHeaderDAO;
import no.sintef.fates.io.files.HDR;
import no.sintef.model.ComponentData;
import no.sintef.model.ComponentData.ComponentDataDAO;
import no.sintef.model.OilSpillSimulation;
import no.sintef.model.ReleaseData;
import no.sintef.model.ReleaseData.ReleaseDataDAO;
import no.sintef.model.ReleaseSiteData;
import no.sintef.model.ResponseData;
import no.sintef.model.ResponseData.ResponseDataDAO;
import no.sintef.model.SpecieData;
import no.sintef.model.ReleaseSiteData.ReleaseSiteDataDAO;
import no.sintef.model.SpecieData.SpecieDataDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class TimeStepIndependentDataTest extends TestCase {

	private File grfFile, hdrFile;
	private Calendar cal = Calendar.getInstance();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		String filename = "";
		// TODO create test GRF file
		filename = "./testfiles/Ichthys20x20surface.grf";
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

	@Test
	public void testReadBinaryHeader() {
		// Time step-independent data consists of:
		// Binary Header
		// other data
		try {
			FileInputStream fileInputStream = new FileInputStream(
					grfFile.getAbsolutePath());
			LittleEndianDataInputStream datastream = new LittleEndianDataInputStream(
					fileInputStream);
			BinaryHeader testedheader = BinaryHeaderDAO.readData(datastream);
			datastream.close();

			byte[] myheader = new byte[96];
			fileInputStream = new FileInputStream(grfFile.getAbsolutePath());
			int readNumberBytes = fileInputStream.read(myheader);
			fileInputStream.close();

			assertEquals(96, readNumberBytes);
			byte[] binaryHeader = testedheader.getBinaryHeader();
			for (int i = 0; i < myheader.length; i++) {
				assertEquals(myheader[i], binaryHeader[i]);
			}

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testReadComponents() {
		// Time step-independent data consists of:
		// Binary Header
		// Component info
		// other stuff
		// to test if the Component data is read correctly we'll assume that
		// everything before is read correctly and just skip this
		FileInputStream fileInputStream;
		ComponentData readdata = null;
		try {
			fileInputStream = new FileInputStream(grfFile.getAbsolutePath());
			LittleEndianDataInputStream datastream = new LittleEndianDataInputStream(
					fileInputStream);
			datastream.skip(96); // Binary Header
			readdata = ComponentDataDAO.readData(datastream, (short) 23);
			datastream.close();

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(76, readdata.getLengthOfComponentName());
		assertEquals(26, readdata.getTotalNumberOfComponents());
		assertEquals(25, readdata.getNumberOfOilComponents());
		assertEquals(0, readdata.getNumberOfChemComponents());
		assertEquals(0, readdata.getNumberOfParticleMaterialComponents());
		assertEquals(1, readdata.getNumberOfGasComponents());
		assertEquals("C1-C4 gasses (dissolved in oil)", readdata
				.getComponents().get(0).getName().trim());
		assertEquals(0.615, readdata.getComponents().get(0).getDensity(),
				0.00001);
		assertEquals(0.0, readdata.getComponents().get(0)
				.getBackgroundConcentration(), 0.00001);
		assertEquals((short) 1, readdata.getComponents().get(0)
				.getIsDegradable());

	}

	@Test
	public void testSpecies() {
		// Time step-independent data consists of:
		// Binary Header
		// Component info
		// Species data
		// other data
		// to test if the Species data is read correctly we'll assume that
		// everything before is read correctly and just skip this
		FileInputStream fileInputStream;
		SpecieData specData = null;
		try {
			fileInputStream = new FileInputStream(grfFile.getAbsolutePath());
			LittleEndianDataInputStream datastream = new LittleEndianDataInputStream(
					fileInputStream);

			// skip data irrelevant for this test and tested elsewhere
			BinaryHeader header = BinaryHeaderDAO.readData(datastream);
			@SuppressWarnings("unused")
			ComponentData compData = ComponentDataDAO.readData(datastream,
					header.getFileversion());

			specData = SpecieDataDAO.readData(datastream);
			datastream.close();

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNull(specData.getSpecies());
	}

	@Test
	public void testReleaseSites() {
		// Time step-independent data consists of:
		// Binary Header
		// Component info
		// Species data
		// some other data that was stored here
		// Release site data
		// more stuff
		// to test if the Release site data is read correctly we'll assume that
		// everything before is read correctly and just skip this
		FileInputStream fileInputStream;
		ReleaseSiteData releaseData = null;
		try {
			fileInputStream = new FileInputStream(grfFile.getAbsolutePath());
			LittleEndianDataInputStream datastream = new LittleEndianDataInputStream(
					fileInputStream);
			// skip data irrelevant for this test and tested elsewhere
			BinaryHeader header = BinaryHeaderDAO.readData(datastream);
			@SuppressWarnings("unused")
			ComponentData compData = ComponentDataDAO.readData(datastream,
					header.getFileversion());
			@SuppressWarnings("unused")
			SpecieData specData = SpecieDataDAO.readData(datastream);
			datastream.skipBytes(24); // other stuff

			releaseData = ReleaseSiteDataDAO.readData(datastream);
			datastream.close();

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		assertTrue(releaseData.getNumberSites() > 0);
		assertEquals(releaseData.getNumberSites(), releaseData.getSites()
				.size());
		assertEquals(1, releaseData.getSites().size());
		assertEquals(-13.9674, releaseData.getSites().get(0).getLatitude(),
				0.00001);
		assertEquals(123.14237, releaseData.getSites().get(0).getLongitude(),
				0.00001);
		assertEquals(245.00000, releaseData.getSites().get(0).getDepth(),
				0.00001);
		assertEquals(247.10000, releaseData.getSites().get(0).getBathymetry(),
				0.00001);
		assertEquals(0.2454,
				releaseData.getSites().get(0).getReleaseDiameter(), 0.000001);
		assertEquals(0, releaseData.getSites().get(0).getStartTime(), 0.000001);
		assertEquals(1.0, releaseData.getSites().get(0).getDuration(), 0.000001);
		assertEquals(0, releaseData.getSites().get(0).getMoving());
	}

	@Test
	public void testReleaseData() {
		// Time step-independent data consists of:
		// Binary Header
		// Component info
		// Species data
		// some other data that was stored here
		// Release site data
		// Release data
		// more stuff
		// to test if the Release data is read correctly we'll assume that
		// everything before is read correctly and just skip this
		FileInputStream fileInputStream;
		ReleaseSiteData releaseSiteData = null;
		ReleaseData releaseData = null;
		try {
			fileInputStream = new FileInputStream(grfFile.getAbsolutePath());
			LittleEndianDataInputStream datastream = new LittleEndianDataInputStream(
					fileInputStream);
			// skip data irrelevant for this test and tested elsewhere
			BinaryHeader header = BinaryHeaderDAO.readData(datastream);
			ComponentData compData = ComponentDataDAO.readData(datastream,
					header.getFileversion());
			@SuppressWarnings("unused")
			SpecieData specData = SpecieDataDAO.readData(datastream);
			datastream.skip(24); // other stuff
			releaseSiteData = ReleaseSiteDataDAO.readData(datastream);

			releaseData = ReleaseDataDAO.readData(datastream,
					releaseSiteData.getNumberSites(),
					compData.getTotalNumberOfComponents());

			datastream.close();

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

		assertEquals(releaseSiteData.getNumberSites(), releaseData
				.getReleases().size());
		assertEquals(1, releaseData.getReleases().size());
		assertEquals("ICHTYS (TRYM CONDENSATE))", releaseData.getReleases()
				.get(0).getProfile().trim());
		assertEquals(2654.39453, releaseData.getReleases().get(0)
				.getReleaseRate(), 0.00001);
		assertEquals(26, releaseData.getReleases().get(0)
				.getComponentConcentrations().size());

	}

	@Test
	public void testResponseUnits() {
		// Time step-independent data consists of:
		// Binary Header
		// Component info
		// Species data
		// some other data that was stored here
		// Release site data
		// Release data
		// more stuff that got stored here
		// Response units
		// to test if the Response data is read correctly we'll assume that
		// everything before is read correctly and just skip this
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(grfFile.getAbsolutePath());
			LittleEndianDataInputStream datastream = new LittleEndianDataInputStream(
					fileInputStream);
			// skip data irrelevant for this test and tested elsewhere
			BinaryHeader header = BinaryHeaderDAO.readData(datastream);
			ComponentData compData = ComponentDataDAO.readData(datastream,
					header.getFileversion());
			@SuppressWarnings("unused")
			SpecieData specData = SpecieDataDAO.readData(datastream);
			datastream.skip(24); // other stuff
			ReleaseSiteData releaseSiteData = ReleaseSiteDataDAO.readData(datastream);
			@SuppressWarnings("unused")
			ReleaseData releaseData = ReleaseDataDAO.readData(datastream,
					releaseSiteData.getNumberSites(),
					compData.getTotalNumberOfComponents());
			datastream.skip(8); // more other stuff

			/* Response info */
			ResponseData responseVessels = ResponseDataDAO.readData(datastream);
			// implement asserts!
			assertNull(responseVessels.getUnits());

			datastream.close();

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testSomeStuffThatNeedsToBeReadInbetween() {
		// Time step-independent data consists of:
		// Binary Header
		// Component info
		// Species data
		// some other data that was stored here
		// Release site data
		// Release data
		// more stuff that got stored here
		// Response units
		// to test if the other data is read correctly we'll assume that
		// everything before and in-between is read correctly and just skip this
		FileInputStream fileInputStream;
		float simulationDuration = -5f;
		float sedpor = -5f;
		float sedtox = -5f;
		float natGrainSize = -5f;
		short hasSedRiskData = 5;
		short isIntFracStorage = 5;
		short hasSedPoreData = 5;
		short hasPlume3DOutput = 5;

		short hasExpandingGrid = 5;
		short hasVertExpGrid = 5;
		float seaOxygenAvg = -5f;

		try {
			fileInputStream = new FileInputStream(grfFile.getAbsolutePath());
			LittleEndianDataInputStream datastream = new LittleEndianDataInputStream(
					fileInputStream);

			// skip data irrelevant for this test and tested elsewhere
			BinaryHeader header = BinaryHeaderDAO.readData(datastream);
			ComponentData compData = ComponentDataDAO.readData(datastream,
					header.getFileversion());
			@SuppressWarnings("unused")
			SpecieData specData = SpecieDataDAO.readData(datastream);

			simulationDuration = datastream.readFloat();
			sedpor = datastream.readFloat();
			sedtox = datastream.readFloat();
			natGrainSize = datastream.readFloat();
			hasSedRiskData = datastream.readShort();
			hasSedPoreData = datastream.readShort();
			isIntFracStorage = datastream.readShort();
			hasPlume3DOutput = datastream.readShort();

			ReleaseSiteData releaseSiteData = ReleaseSiteDataDAO.readData(datastream);
			@SuppressWarnings("unused")
			ReleaseData releaseData = ReleaseDataDAO.readData(datastream,
					releaseSiteData.getNumberSites(),
					compData.getTotalNumberOfComponents());
			
			hasExpandingGrid = datastream.readShort();
			hasVertExpGrid = datastream.readShort();
			seaOxygenAvg = datastream.readFloat();

			datastream.close();

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals(1.0, simulationDuration, 0.00001);
		assertEquals(0.6, sedpor, 0.00001);
		assertEquals(3.0, sedtox, 0.00001);
		assertEquals(0.150, natGrainSize, 0.00001);
		assertEquals(0, hasSedRiskData);
		assertEquals(0, isIntFracStorage);
		assertEquals(0, hasSedPoreData);
		assertEquals(1, hasPlume3DOutput);
		assertEquals(1, hasExpandingGrid);
		assertEquals(1, hasVertExpGrid);
		assertEquals(10, seaOxygenAvg, 0.00001);

	}

}

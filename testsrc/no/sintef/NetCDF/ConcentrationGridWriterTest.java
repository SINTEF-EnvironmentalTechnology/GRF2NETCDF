/**
 * 
 */
package no.sintef.NetCDF;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

import no.sintef.NetCDF.ConcentrationGridWriter.ConcentrationGrid;
import no.sintef.model.SubsurfaceGridDimensions;
import no.sintef.model.Summary;
import no.sintef.model.WaterConcentration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;

/**
 * @author ubr
 * 
 */
public class ConcentrationGridWriterTest {

	private ConcentrationGrid grid;
	private ConcentrationGridWriter tester = new ConcentrationGridWriter();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		WaterConcentration conc = new WaterConcentration();
		int iCell = 2;
		float concentration = 2f;
		float totalConcentration = 2f;
		byte percentEvaporated = (byte) 2;

		conc.setConcentration(concentration);
		conc.setiCell(iCell);
		conc.setPercentEvap(percentEvaporated);
		conc.setTotalconcentration(totalConcentration);

		Vector<WaterConcentration> concVector = new Vector<WaterConcentration>();
		concVector.add(conc);

		iCell = 4;
		concentration = 4f;
		totalConcentration = 4f;
		percentEvaporated = (byte) 4;
		conc = new WaterConcentration();
		conc.setConcentration(concentration);
		conc.setiCell(iCell);
		conc.setPercentEvap(percentEvaporated);
		conc.setTotalconcentration(totalConcentration);
		concVector.add(conc);

		Summary summary = new Summary();
		summary.setnX_conc((short) 3);
		summary.setnY_conc((short) 2);
		summary.setnZ_conc((short) 1);
		summary.setXmin_conc(2.5f);
		summary.setYmin_conc(2.5f);
		summary.setZmin(2.5f);
		summary.setDx_conc(5f);
		summary.setDy_conc(5f);

		SubsurfaceGridDimensions gridDims = new SubsurfaceGridDimensions();
		Vector<Float> height = new Vector<Float>();
		height.add(5f);
		gridDims.setCellHeightPerLayer(height);

		Vector<Integer> cells = new Vector<Integer>();
		cells.add(2);
		gridDims.setNumberWatConcCellsPerLayer(cells);

		grid = tester.new ConcentrationGrid(concVector, summary, gridDims);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreate() {
		// file created

		String fileLocation = "./testfiles/generated/ConcentrationGridWriter-CreateTest.nc";
		try {
			tester.createFile(3, 2, 1, Calendar.getInstance().getTime(),
					fileLocation);
			// file created
			File testfile = new File(fileLocation);
			assertTrue(testfile.exists());
			assertTrue(testfile.isFile());
			
			// file can be opened
			assertTrue(NetcdfFile.canOpen(fileLocation));
			
			// file closed

			// CDL correct
			NetcdfFile ncfile = NetcdfFile.open(fileLocation);
			
			assertEquals(ncfile.getDimensions().size(), 4);
			Variable dcVariable = ncfile.findVariable(ConcentrationGridWriter.DISSOLVED_CONCENTRATION);
			assertNotNull(dcVariable);
			dcVariable.findAttributeIgnoreCase("long_name");
			dcVariable.findAttributeIgnoreCase("units");
			dcVariable.findAttributeIgnoreCase("_Fill_Value");
			dcVariable.findAttributeIgnoreCase("grid_mapping");
			dcVariable.findAttributeIgnoreCase("coordinates");

			Variable tcVariable = ncfile.findVariable(ConcentrationGridWriter.TOTAL_CONCENTRATION);
			assertNotNull(tcVariable);
			tcVariable.findAttributeIgnoreCase("long_name");
			tcVariable.findAttributeIgnoreCase("units");
			tcVariable.findAttributeIgnoreCase("_Fill_Value");
			tcVariable.findAttributeIgnoreCase("grid_mapping");
			tcVariable.findAttributeIgnoreCase("coordinates");

		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testWrite() {
		String fileLocation = "./testfiles/generated/ConcentrationGridWriter-WriteTest.nc";
		// String referenceFileLocation =
		// "./testfiles/generated/ConcentrationGridWriter-ReferenceTest.nc";

		try {
			tester.createFile(3, 2, 1, Calendar.getInstance().getTime(),
					fileLocation);
			tester.writeToFile(fileLocation, 5, grid);
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (NetCDFException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		// compare file to reference file from setup TODO

	}
}

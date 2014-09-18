package no.sintef.NetCDF;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import no.sintef.NetCDF.SurfaceGridOilWriter.SurfaceOilGrid;
import no.sintef.model.Summary;
import no.sintef.model.SurfaceCellOil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ucar.ma2.Array;
import ucar.ma2.Index;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.NetcdfDataset;

public class SurfaceGridOilWriterTest {
	private static final String CREATE_TESTFILE = "./testfiles/generated/SurfaceGridOilWriterTest_Create.nc";
	private static final String WRITE_TESTFILE = "./testfiles/generated/SurfaceGridOilWriterTest_Write.nc";

	private SurfaceGridOilWriter tester = new SurfaceGridOilWriter();
	private SurfaceOilGrid grid = null;
	Summary summary = null;
	

	@Before
	public void setUp() throws Exception {
		Vector<SurfaceCellOil> surfacegridcells = new Vector<SurfaceCellOil>();
		
		SurfaceCellOil cell = new SurfaceCellOil();
		cell.setCellIndex(6);
		cell.setCoverage(25.5f);
		cell.setEmulsionViscosity(255f);
		cell.setThickness(0.000005f);
		cell.setWaterConcentration(25f);
		surfacegridcells.add(cell);
		
		cell = new SurfaceCellOil();
		cell.setCellIndex(8);
		cell.setCoverage(5.25f);
		cell.setEmulsionViscosity(525f);
		cell.setThickness(0.000025f);
		cell.setWaterConcentration(5f);
		surfacegridcells.add(cell);
		
		cell = new SurfaceCellOil();
		cell.setCellIndex(11);
		cell.setCoverage(2.55f);
		cell.setEmulsionViscosity(2.55f);
		cell.setThickness(0.000025f);
		cell.setWaterConcentration(2.5f);
		surfacegridcells.add(cell);
		
		cell = new SurfaceCellOil();
		cell.setCellIndex(12);
		cell.setCoverage(52.5f);
		cell.setEmulsionViscosity(525f);
		cell.setThickness(0.000052f);
		cell.setWaterConcentration(52.5f);
		surfacegridcells.add(cell);
		
		summary = new Summary();
		summary.setnX_surf((short) 5);
		summary.setnY_surf((short) 2);
		summary.setXmin_surf(2.5f);
		summary.setYmin_surf(2.5f);
		summary.setDx_surf(5f);
		summary.setDy_surf(5f);
		summary.setDaysSinceStart(5f);
		
		grid = tester.new SurfaceOilGrid(surfacegridcells, summary);
		
				
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testWriteToFile() {
		try {
			tester.createFile(5, 2, Calendar.getInstance().getTime(), WRITE_TESTFILE);
			tester.writeToFile(WRITE_TESTFILE, 1, grid);
			
			//TODO implement test criteria:
			// 4 cells, one timesteps, correct values
			NetcdfFile data = NetcdfDataset.open(WRITE_TESTFILE);
			
			Variable timeVar = data.findVariable(SurfaceGridOilWriter.TIME);
			// size = 2 since we wrote with offset 1
			assertTrue(timeVar.getSize() == 2);
			
			Variable latVar = data.findVariable(SurfaceGridOilWriter.LATITUDE);
			assertTrue(latVar.getSize() == 2);
			
			Variable lonVar = data.findVariable(SurfaceGridOilWriter.LONGITUDE);
			assertTrue(lonVar.getSize() == 5);
			
			Variable coverageVar = data.findVariable(SurfaceGridOilWriter.COVERAGE);
            // size = 5 x 2 x 2 = 20
			assertTrue(coverageVar.getSize() == 20);
			
			Variable emulViscVar = data.findVariable(SurfaceGridOilWriter.EMULSIONVISCOSITY);
			assertTrue(emulViscVar.getSize() == 20);
			
			Variable emulVar = data.findVariable(SurfaceGridOilWriter.EMULSIONWATCONT);
			assertTrue(emulVar.getSize() == 20);
			
			Variable thickVar = data.findVariable(SurfaceGridOilWriter.THICKNESS);
			assertTrue(thickVar.getSize() == 20);
			
			Array mydata = timeVar.read();
			assertTrue(mydata.getFloat(1)== 5f);
			
			mydata = latVar.read();
			assertTrue(mydata.getFloat(0) == 5f);
			assertTrue(mydata.getFloat(1) == 10f);

			mydata = lonVar.read();
			assertTrue(mydata.getFloat(0) == 5f);
			assertTrue(mydata.getFloat(1) == 10f);
			assertTrue(mydata.getFloat(2) == 15f);
			assertTrue(mydata.getFloat(3) == 20f);
			assertTrue(mydata.getFloat(4) == 25f);

			Array my2Ddata = coverageVar.read();
			Index myIndex = my2Ddata.getIndex();
			myIndex.set(1, 1, 0); // cell index = 6
			assertEquals(my2Ddata.getFloat(myIndex), 25.5f, 0.001);
			myIndex.set(1, 1, 2); // cell index = 8
			assertEquals(my2Ddata.getFloat(myIndex), 5.25f, 0.001);
			
			// and so forth ...

		} catch (NetCDFException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateFile() {
		try {
			tester.createFile(5, 2, Calendar.getInstance().getTime(), CREATE_TESTFILE);
			
			//TODO implement test criteria:
			// all variables
			// correct dimensions
			// correct attributes
			NetcdfFile data = NetcdfDataset.open(CREATE_TESTFILE);
			List<Variable> vars = data.getVariables();
			assertTrue(vars.size() == 8);
			List<Dimension> dims = data.getDimensions();
			assertTrue(dims.size() == 3);
			
			assertNotNull(data.findVariable(SurfaceGridOilWriter.TIME));
			assertNotNull(data.findVariable(SurfaceGridOilWriter.LATITUDE));
			assertNotNull(data.findVariable(SurfaceGridOilWriter.LONGITUDE));
			assertNotNull(data.findVariable(SurfaceGridOilWriter.COVERAGE));
			assertNotNull(data.findVariable(SurfaceGridOilWriter.EMULSIONVISCOSITY));
			assertNotNull(data.findVariable(SurfaceGridOilWriter.EMULSIONWATCONT));
			assertNotNull(data.findVariable(SurfaceGridOilWriter.THICKNESS));
			assertNotNull(data.findVariable(SurfaceGridOilWriter.GRIDMAPPING));
			
		} catch (IOException e) {
			
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}

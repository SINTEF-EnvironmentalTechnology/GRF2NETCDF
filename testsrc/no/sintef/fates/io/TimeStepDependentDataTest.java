/**
 * 
 */
package no.sintef.fates.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Vector;

import junit.framework.TestCase;
import no.sintef.fates.io.files.BinaryHeader;
import no.sintef.fates.io.files.BinaryHeader.BinaryHeaderDAO;
import no.sintef.fates.io.files.GRF;
import no.sintef.fates.io.files.HDR;
import no.sintef.model.ComponentData;
import no.sintef.model.ComponentData.ComponentDataDAO;
import no.sintef.model.OilSpillSimulation;
import no.sintef.model.Plume3D;
import no.sintef.model.ReleaseData.ReleaseDataDAO;
import no.sintef.model.ReleaseSite;
import no.sintef.model.ReleaseSiteData;
import no.sintef.model.ReleaseSiteData.ReleaseSiteDataDAO;
import no.sintef.model.ResponseData.ResponseDataDAO;
import no.sintef.model.ResponseUnit;
import no.sintef.model.SpecieData.SpecieDataDAO;
import no.sintef.model.SubsurfaceGridDimensions;
import no.sintef.model.SubsurfaceGridDimensions.SubsurfaceGridDimensionDAO;
import no.sintef.model.Summary;
import no.sintef.model.Summary.SummaryDAO;
import no.sintef.model.SurfaceCellGas.SurfaceCellGasDAO;
import no.sintef.model.SurfaceCellOil.SurfaceCellOilDAO;
import no.sintef.model.WaterConcentration.WaterConcentrationDAO;
import no.sintef.model.SurfaceCellGas;
import no.sintef.model.SurfaceCellOil;
import no.sintef.model.WaterConcentration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class TimeStepDependentDataTest extends TestCase {

	private File grfFile, hdrFile;
	private Calendar cal = Calendar.getInstance();
	private ReleaseSiteData releaseSiteData;
	private BinaryHeader header;
	private boolean expandingGrid;

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
	public void testSummary() {
		// Time step-dependent data consists of: (*) test!
		// Summary *
		// Site Data
		// sub-surface grid dimensions
		// spillet data
		// particle data
		// organism data
		// gas bubble data
		// sub surface grid data
		// surface grid (oil) data
		// surface grid (gas) data
		// shoreline data
		// sediment data
		// response data
		// Plume3D data
		// to test if the Summary data is read correctly we'll assume that
		// everything before and after is read correctly and just skip this
		try {
			LittleEndianDataInputStream dataStream = new LittleEndianDataInputStream(
					new FileInputStream(grfFile));

			// time step independent data
			skipTimeIndependentData(dataStream);

			Summary summary;
			float previousTimeStep = 0f;
			float currentTimeStep = 0f;
			float delta = currentTimeStep - previousTimeStep;

			while (dataStream.available() > 0) {
				// -----------------------------------------------------------------
				// this is what is tested
				summary = SummaryDAO.readData(dataStream,
						header.getFileversion());

				currentTimeStep = summary.getDaysSinceStart();

				if (previousTimeStep == 0f) {
					delta = currentTimeStep - previousTimeStep;
				}

				if (currentTimeStep != 0f) {

					// make sure the constant stuff is read correctly each time
					// step
					// derive that rest is read correctly as well :-/
					// not perfect but sufficient
					assertEquals(10, summary.getnZ_conc());
					assertEquals(100, summary.getnY_conc());
					assertEquals(100, summary.getnX_conc());

					assertEquals(20, summary.getnX_surf());
					assertEquals(20, summary.getnY_surf());

					assertEquals(20, summary.getnX_gas());
					assertEquals(20, summary.getnY_gas());
					// check time steps are equidistant
					assertEquals(previousTimeStep, currentTimeStep - delta,
							0.0001);
					previousTimeStep = currentTimeStep;

				}
				// -----------------------------------------------------------------

				// skip the rest
				/* Site Data */
				int numberSites = releaseSiteData.getNumberSites();
				dataStream.skip(numberSites * ReleaseSite.SIZE);

				short numberLayers = summary.getnZ_conc();
				/* sub-surface grid dimensions */
				SubsurfaceGridDimensions sSGDims = SubsurfaceGridDimensionDAO
						.readData(dataStream, numberLayers);

				/* Spillet data */
				dataStream.skip(dataStream.readInt());

				/* Particle data */
				dataStream.skip(dataStream.readInt());

				/* Organism data */
				dataStream.skip(dataStream.readInt());

				/* Gasbubble data */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					dataStream.skip(dataStream.readInt());
				}

				/* Sub-surface water concentration grid data */
				dataStream.skip(sSGDims.getTotalNumberWatConcCells()
						* WaterConcentration.SIZE);

				/* Surface grid (oil) data */
				dataStream.skip(summary.getNcellssurf() * SurfaceCellOil.SIZE);

				/* Surface grid (gas) data from version 23 */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					dataStream.skip(summary.getNcellsgas()
							* SurfaceCellGas.SIZE);
				}

				/* Shoreline data */
				if (summary.getNcellsshore() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Sediment data */
				if (summary.getNumberCellsSediment() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Response data */
				int numberActiveVessels = summary.getNvess_active();
				if (numberActiveVessels > 0) {
					dataStream.skip(numberActiveVessels * ResponseUnit.SIZE);
				}

				/* Plume3D data */
				if (summary.hasPlume()) {
					for (int site = 0; site < numberSites; site++) {
						dataStream.skip(dataStream.readShort() * Plume3D.SIZE);
					}
				}
			}
			dataStream.close();

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testGridDimensionData() {
		// Time step-dependent data consists of: (*) test!
		// Summary
		// Site Data
		// sub-surface grid dimensions *
		// spillet data
		// particle data
		// organism data
		// gas bubble data
		// sub surface grid data
		// surface grid (oil) data
		// surface grid (gas) data
		// shoreline data
		// sediment data
		// response data
		// Plume3D data

		// to test if the sub-surface grid dimension data is read correctly
		// we'll assume that
		// everything before and after is read correctly and just skip this

		try {

			LittleEndianDataInputStream dataStream = new LittleEndianDataInputStream(
					new FileInputStream(grfFile));

			// time step independent data
			skipTimeIndependentData(dataStream);
			Summary summary;
			SubsurfaceGridDimensions sSGDimsPreviousTimestep = null;

			while (dataStream.available() > 0) {
				summary = SummaryDAO.readData(dataStream,
						header.getFileversion());

				/* Site Data */
				int numberSites = releaseSiteData.getNumberSites();
				dataStream.skip(numberSites * ReleaseSite.SIZE);
				// -----------------------------------------------------------------
				// this is what is tested
				short numberLayers = summary.getnZ_conc();
				/* sub-surface grid dimensions */
				SubsurfaceGridDimensions sSGDims = SubsurfaceGridDimensionDAO
						.readData(dataStream, numberLayers);

				// cell heights stay constant if non-expanding grid but are 0.0
				// in time step 0.0
				if (sSGDimsPreviousTimestep != null && !expandingGrid) {
					for (int i = 0; i < sSGDims.getCellHeightPerLayer().size(); i++) {
						assertEquals(sSGDims.getCellHeightPerLayer().get(i),
								sSGDimsPreviousTimestep.getCellHeightPerLayer()
										.get(i), 0.0001);
					}
				}
				if (summary.getDaysSinceStart() > 0) {
					sSGDimsPreviousTimestep = sSGDims;
				}

				if (sSGDims.getTotalNumberWatConcCells() > 0) {
					for (int i = 0; i < sSGDims.getNumberWatConcCellsPerLayer()
							.size(); i++) {
						// value is an integer
						assertEquals((int) sSGDims
								.getNumberWatConcCellsPerLayer().get(i)
								.intValue(), sSGDims
								.getNumberWatConcCellsPerLayer().get(i)
								.intValue());
					}
				}

				// -----------------------------------------------------------------

				// skip the rest
				/* Spillet data */
				dataStream.skip(dataStream.readInt());

				/* Particle data */
				dataStream.skip(dataStream.readInt());

				/* Organism data */
				dataStream.skip(dataStream.readInt());

				/* Gasbubble data */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					dataStream.skip(dataStream.readInt());
				}

				/* Sub-surface water concentration grid data */
				dataStream.skip(sSGDims.getTotalNumberWatConcCells()
						* WaterConcentration.SIZE);

				/* Surface grid (oil) data */
				dataStream.skip(summary.getNcellssurf() * SurfaceCellOil.SIZE);

				/* Surface grid (gas) data from version 23 */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					dataStream.skip(summary.getNcellsgas()
							* SurfaceCellGas.SIZE);
				}

				/* Shoreline data */
				if (summary.getNcellsshore() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Sediment data */
				if (summary.getNumberCellsSediment() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Response data */
				int numberActiveVessels = summary.getNvess_active();
				if (numberActiveVessels > 0) {
					dataStream.skip(numberActiveVessels * ResponseUnit.SIZE);
				}

				/* Plume3D data */
				if (summary.hasPlume()) {
					for (int site = 0; site < numberSites; site++) {
						dataStream.skip(dataStream.readShort() * Plume3D.SIZE);
					}
				}
			}
			dataStream.close();
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSubSurfaceGridData() {
		// Time step-dependent data consists of: (*) test!
		// Summary
		// Site Data
		// sub-surface grid dimensions
		// spillet data
		// particle data
		// organism data
		// gas bubble data
		// sub surface grid data *
		// surface grid (oil) data
		// surface grid (gas) data
		// shoreline data
		// sediment data
		// response data
		// Plume3D data

		// to test if the sub-surface grid dimension data is read correctly
		// we'll assume that
		// everything before and after is read correctly and just skip this

		try {

			LittleEndianDataInputStream dataStream = new LittleEndianDataInputStream(
					new FileInputStream(grfFile));

			// time step independent data
			skipTimeIndependentData(dataStream);
			Summary summary;

			while (dataStream.available() > 0) {
				summary = SummaryDAO.readData(dataStream,
						header.getFileversion());

				/* Site Data */
				int numberSites = releaseSiteData.getNumberSites();
				dataStream.skip(numberSites * ReleaseSite.SIZE);

				short numberLayers = summary.getnZ_conc();
				/* sub-surface grid dimensions */
				SubsurfaceGridDimensions sSGDims = SubsurfaceGridDimensionDAO
						.readData(dataStream, numberLayers);

				/* Spillet data */
				dataStream.skip(dataStream.readInt());

				/* Particle data */
				dataStream.skip(dataStream.readInt());

				/* Organism data */
				dataStream.skip(dataStream.readInt());

				/* Gasbubble data */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					dataStream.skip(dataStream.readInt());
				}

				// -----------------------------------------------------------------
				// this is what is tested
				/* Sub-surface water concentration grid data */
				Vector<WaterConcentration> waterconcentrations = new Vector<WaterConcentration>();
				for (int i = 0; i < numberLayers + 2; i++) {
					for (int j = 0; j < sSGDims.getNumberWatConcCellsPerLayer()
							.get(i); j++) {
						waterconcentrations.add(WaterConcentrationDAO
								.readData(dataStream));
					}
				}

				for (int i = 0; i < waterconcentrations.size(); i++) {
					// iCell is an integer
					assertTrue((int) waterconcentrations.get(i).getCellIndex() == waterconcentrations
							.get(i).getCellIndex());
					// concentration is smaller than or equal to total
					// concentration
					assertTrue(waterconcentrations.get(i).getConcentration() <= waterconcentrations
							.get(i).getTotalconcentration());
					assertTrue(Integer.parseInt(Byte
							.toString(waterconcentrations.get(i)
									.getPercentEvap())) < 100);
				}

				// -----------------------------------------------------------------

				/* Surface grid (oil) data */
				dataStream.skip(summary.getNcellssurf() * SurfaceCellOil.SIZE);

				/* Surface grid (gas) data from version 23 */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					dataStream.skip(summary.getNcellsgas()
							* SurfaceCellGas.SIZE);
				}

				/* Shoreline data */
				if (summary.getNcellsshore() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Sediment data */
				if (summary.getNumberCellsSediment() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Response data */
				int numberActiveVessels = summary.getNvess_active();
				if (numberActiveVessels > 0) {
					dataStream.skip(numberActiveVessels * ResponseUnit.SIZE);
				}

				/* Plume3D data */
				if (summary.hasPlume()) {
					for (int site = 0; site < numberSites; site++) {
						dataStream.skip(dataStream.readShort() * Plume3D.SIZE);
					}
				}
			}
			dataStream.close();
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * @param dataStream
	 * @throws IOException
	 */
	private void skipTimeIndependentData(LittleEndianDataInputStream dataStream)
			throws IOException {
		header = BinaryHeaderDAO.readData(dataStream);
//		System.out.println(header.getModelAndVersion());
//		System.out.println("Fileversion: " + header.getFileversion());
		ComponentData compData = ComponentDataDAO.readData(dataStream,
				header.getFileversion());
		SpecieDataDAO.readData(dataStream);
		dataStream.skip(24); // other stuff
		releaseSiteData = ReleaseSiteDataDAO.readData(dataStream);
		ReleaseDataDAO.readData(dataStream, releaseSiteData.getNumberSites(),
				compData.getTotalNumberOfComponents());
		expandingGrid = (dataStream.readShort() == 1);
		dataStream.skip(6); // more other stuff
		ResponseDataDAO.readData(dataStream);
	}

	@Test
	public void testSkipData() {
		try {
			LittleEndianDataInputStream dataStream = new LittleEndianDataInputStream(
					new FileInputStream(grfFile));

			// skip BinaryHeader
			long skipped = dataStream.skip(96);
			assertEquals(96, skipped);
			assertEquals(76, dataStream.readShort());
			long referenceReadBytes = dataStream.skip(76);
			dataStream.close();

			dataStream = new LittleEndianDataInputStream(new FileInputStream(
					grfFile));
			dataStream.skip(96);
			long readBytes = dataStream.skip(dataStream.readShort());
			dataStream.close();

			assertEquals(referenceReadBytes, readBytes);

		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSurfaceGridOilData() {
		// Time step-dependent data consists of: (*) test!
		// Summary
		// Site Data
		// sub-surface grid dimensions
		// spillet data
		// particle data
		// organism data
		// gas bubble data
		// sub surface grid data
		// surface grid (oil) data *
		// surface grid (gas) data
		// shoreline data
		// sediment data
		// response data
		// Plume3D data

		// to test if the sub-surface grid dimension data is read correctly
		// we'll assume that
		// everything before and after is read correctly and just skip this

		try {

			LittleEndianDataInputStream dataStream = new LittleEndianDataInputStream(
					new FileInputStream(grfFile));

			// time step independent data
			skipTimeIndependentData(dataStream);
			Summary summary;

			while (dataStream.available() > 0) {
				summary = SummaryDAO.readData(dataStream,
						header.getFileversion());

				/* Site Data */
				int numberSites = releaseSiteData.getNumberSites();
				dataStream.skip(numberSites * ReleaseSite.SIZE);

				short numberLayers = summary.getnZ_conc();
				/* sub-surface grid dimensions */
				SubsurfaceGridDimensions sSGDims = SubsurfaceGridDimensionDAO
						.readData(dataStream, numberLayers);

				/* Spillet data */
				dataStream.skip(dataStream.readInt());

				/* Particle data */
				dataStream.skip(dataStream.readInt());

				/* Organism data */
				dataStream.skip(dataStream.readInt());

				/* Gasbubble data */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					dataStream.skip(dataStream.readInt());
				}

				/* Sub-surface water concentration grid data */
				for (int i = 0; i < numberLayers + 2; i++) {
					dataStream.skip(sSGDims.getNumberWatConcCellsPerLayer()
							.get(i) * WaterConcentration.SIZE);

				}

				// -----------------------------------------------------------------
				// this is what is tested
				/* Surface grid (oil) data */
				Vector<SurfaceCellOil> surfaceCellsOil = new Vector<SurfaceCellOil>();
				for (int i = 0; i < summary.getNcellssurf(); i++) {
					surfaceCellsOil.add(SurfaceCellOilDAO.readData(dataStream));
				}
				for (int i = 0; i < summary.getNcellssurf(); i++) {
					// cell index is an int
					SurfaceCellOil surfaceCellOil = surfaceCellsOil.get(i);
					assertTrue((int) surfaceCellOil.getCellIndex() == surfaceCellOil
							.getCellIndex());
					// water content is a percentage
					assertTrue(surfaceCellOil.getWaterContent() >= 0.0
							&& surfaceCellOil.getWaterContent() < 100.0);
					// coverage is a fraction
					assertTrue(surfaceCellOil.getCoverage() >= 0.0
							&& surfaceCellOil.getCoverage() < 1.0);

				}

				// -----------------------------------------------------------------

				/* Surface grid (gas) data from version 23 */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					dataStream.skip(summary.getNcellsgas()
							* SurfaceCellGas.SIZE);
				}

				/* Shoreline data */
				if (summary.getNcellsshore() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Sediment data */
				if (summary.getNumberCellsSediment() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Response data */
				int numberActiveVessels = summary.getNvess_active();
				if (numberActiveVessels > 0) {
					dataStream.skip(numberActiveVessels * ResponseUnit.SIZE);
				}

				/* Plume3D data */
				if (summary.hasPlume()) {
					for (int site = 0; site < numberSites; site++) {
						dataStream.skip(dataStream.readShort() * Plume3D.SIZE);
					}
				}
			}
			dataStream.close();
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testSurfaceGridGasData() {
		// Time step-dependent data consists of: (*) test!
		// Summary
		// Site Data
		// sub-surface grid dimensions
		// spillet data
		// particle data
		// organism data
		// gas bubble data
		// sub surface grid data
		// surface grid (oil) data
		// surface grid (gas) data *
		// shoreline data
		// sediment data
		// response data
		// Plume3D data

		// to test if the sub-surface grid dimension data is read correctly
		// we'll assume that
		// everything before and after is read correctly and just skip this

		try {

			LittleEndianDataInputStream dataStream = new LittleEndianDataInputStream(
					new FileInputStream(grfFile));

			// time step independent data
			skipTimeIndependentData(dataStream);
			Summary summary;

			while (dataStream.available() > 0) {
				summary = SummaryDAO.readData(dataStream,
						header.getFileversion());

				/* Site Data */
				int numberSites = releaseSiteData.getNumberSites();
				dataStream.skip(numberSites * ReleaseSite.SIZE);

				short numberLayers = summary.getnZ_conc();
				/* sub-surface grid dimensions */
				SubsurfaceGridDimensions sSGDims = SubsurfaceGridDimensionDAO
						.readData(dataStream, numberLayers);

				/* Spillet data */
				dataStream.skip(dataStream.readInt());

				/* Particle data */
				dataStream.skip(dataStream.readInt());

				/* Organism data */
				dataStream.skip(dataStream.readInt());

				/* Gasbubble data */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					dataStream.skip(dataStream.readInt());
				}

				/* Sub-surface water concentration grid data */
				for (int i = 0; i < numberLayers + 2; i++) {
					dataStream.skip(sSGDims.getNumberWatConcCellsPerLayer()
							.get(i) * WaterConcentration.SIZE);

				}

				/* Surface grid (oil) data */
				dataStream.skip(summary.getNcellssurf() * SurfaceCellOil.SIZE);

				// -----------------------------------------------------------------
				// this is what is tested
				/* Surface grid (gas) data from version 23 */
				if (header.getFileversion() >= GRF.GRFFILEGASVERSION) {
					Vector<SurfaceCellGas> surfaceCellsGas = new Vector<SurfaceCellGas>();
					for (int i = 0; i < summary.getNcellsgas(); i++) {
						surfaceCellsGas.add(SurfaceCellGasDAO
								.readData(dataStream));
					}

					for (int i = 0; i < summary.getNcellsgas(); i++) {
						SurfaceCellGas surfaceCellGas = surfaceCellsGas.get(i);
						// cell index is an int
						assertTrue((int) surfaceCellGas.getCellIndex() == surfaceCellGas
								.getCellIndex());
						// water content is a percentage
						assertTrue(surfaceCellGas.getMassFlux() > 0.0);
						// coverage is a fraction
						assertTrue(surfaceCellGas.getVoidFraction() > 0.0);
					}
				}

				// -----------------------------------------------------------------

				/* Shoreline data */
				if (summary.getNcellsshore() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Sediment data */
				if (summary.getNumberCellsSediment() > 0) {
					dataStream.skip(dataStream.readInt());
				}

				/* Response data */
				int numberActiveVessels = summary.getNvess_active();
				if (numberActiveVessels > 0) {
					dataStream.skip(numberActiveVessels * ResponseUnit.SIZE);
				}

				/* Plume3D data */
				if (summary.hasPlume()) {
					for (int site = 0; site < numberSites; site++) {
						dataStream.skip(dataStream.readShort() * Plume3D.SIZE);
					}
				}
			}
			dataStream.close();
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

}

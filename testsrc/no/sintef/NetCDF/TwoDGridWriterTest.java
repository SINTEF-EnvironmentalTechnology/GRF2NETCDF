/**
 * 
 */
package no.sintef.NetCDF;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ucar.ma2.Array;
import ucar.ma2.ArrayDouble;
import ucar.ma2.ArrayFloat;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.NetcdfFileWriter;
import ucar.nc2.Variable;

/**
 * @author ubr
 * 
 */
public class TwoDGridWriterTest {
	private TwoDGridWriter tester = null;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		tester = new SurfaceGridOilWriter();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link no.sintef.NetCDF.TwoDGridWriter#createFile(int, int, java.util.Date, java.lang.String)}
	 * . Test the create method. Expected result: 2D grid with dimensions time,
	 * lat, lon and the respective coordinate system, i.e. 3 dimensions, their
	 * respective coodinate variables, and grid_mapping and coordinates
	 * according to CF convention
	 */
	@Test
	public void testCreate() {
		NetcdfFileWriter writer;
		try {
			writer = NetcdfFileWriter.createNew(
					NetcdfFileWriter.Version.netcdf3, "./testfiles/generated/TwoDGridWriter-CreateTest.nc");

			// add dimensions
			Dimension latDim = writer.addDimension(null, "lat", 64);
			Dimension lonDim = writer.addDimension(null, "lon", 128);

			// add Variable double temperature(lat,lon)
			List<Dimension> dims = new ArrayList<Dimension>();
			dims.add(latDim);
			dims.add(lonDim);
			writer.addVariable(null, "temperature", DataType.DOUBLE, dims);

			writer.create();

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testCreateFile() {
		String fileLocation = "./testfiles/generated/TwoDGridWriter-CreateFileTest.nc";

		try {
			tester.createFile(3, 2, Calendar.getInstance().getTime(),

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
			assertNotNull(ncfile.findVariable(ConcentrationGridWriter.TIME));

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link no.sintef.NetCDF.TwoDGridWriter#writeToFile(java.lang.String, int, no.sintef.NetCDF.TwoDGridWriter.TwoDGrid)}
	 * .
	 */
	@Test
	public void testWriteToFile() {
		SurfaceGridOilWriter myWriter;
		try {
			String fileLocation = "./testfiles/generated/TwoDGridWriter-WriteToFileTest.nc";

			myWriter = new SurfaceGridOilWriter();
			myWriter.createFile(3, 2, Calendar.getInstance().getTime(),
					fileLocation);

			// write data to variable
			NetcdfFileWriter writer = NetcdfFileWriter
					.openExisting(fileLocation);
			writer.setFill(true);

			Variable et = writer.findVariable("emulsion_thickness");
			Variable t = writer.findVariable("time");

			ArrayFloat.D3 etValues = new ArrayFloat.D3(1, 2, 3);
			int i, j;
			for (i = 0; i < 2; i++) {
				for (j = 0; j < 3; j++) {
					etValues.set(0, i, j, (float) (i * 1000000 + j * 1000));
				}
			}
			ArrayFloat timevalues = new ArrayFloat(new int[] { 1 });
			timevalues.setFloat(0, 25f);

			// for testing purposes we will assume time offset 2 (3rd record to
			// write)
			int offset = 2;
			int[] origin = new int[] { 0, 0, 0 };
			int[] time_offset = new int[] { 0 };

			origin[0] = offset;
			time_offset[0] = offset;

			writer.write(et, origin, etValues);
			writer.write(t, time_offset, timevalues);

			writer.close();

		} catch (IOException e) {

			e.printStackTrace();
			fail(e.getMessage());
		} catch (InvalidRangeException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void testUnidataCode() {

		String location = "testWrite.nc";
		NetcdfFileWriter writer;
		try {
			writer = NetcdfFileWriter.createNew(
					NetcdfFileWriter.Version.netcdf3, location, null);

			// define dimensions, including unlimited
			Dimension latDim = writer.addDimension(null, "lat", 3);
			Dimension lonDim = writer.addDimension(null, "lon", 4);
			@SuppressWarnings("unused")
			Dimension timeDim = writer.addUnlimitedDimension("time");

			// define Variables
			Variable lat = writer.addVariable(null, "lat", DataType.FLOAT,
					"lat");
			lat.addAttribute(new Attribute("units", "degrees_north"));

			Variable lon = writer.addVariable(null, "lon", DataType.FLOAT,
					"lon");
			lon.addAttribute(new Attribute("units", "degrees_east"));

			Variable t = writer.addVariable(null, "T", DataType.DOUBLE,
					"time lat lon");
			t.addAttribute(new Attribute("long_name", "surface temperature"));
			t.addAttribute(new Attribute("units", "degC"));

			Variable time = writer.addVariable(null, "time", DataType.INT,
					"time");
			time.addAttribute(new Attribute("units", "hours since 1990-01-01"));

			// create the file
			writer.create();
			writer.close();

			writer = NetcdfFileWriter.openExisting(location);
			lat = writer.findVariable("lat");
			lon = writer.findVariable("lon");
			// write out the non-record variables
			writer.write(lat, Array.factory(new float[] { 41, 40, 39 }));

			writer.write(lon,
					Array.factory(new float[] { -109, -107, -105, -103 }));

			// // heres where we write the record variables
			// different ways to create the data arrays.
			// Note the outer dimension has shape 1, since we will write one
			// record at a time

			ArrayDouble.D3 tempData = new ArrayDouble.D3(1, latDim.getLength(),
					lonDim.getLength());
			Array timeData = Array.factory(DataType.INT, new int[] { 1 });

			int[] origin = new int[] { 0, 0, 0 };
			int[] time_origin = new int[] { 0 };

			// loop over each record
			for (int timeIdx = 0; timeIdx < 10; timeIdx++) {
				// make up some data for this record, using different ways to
				// fill the data arrays.
				timeData.setInt(timeData.getIndex(), timeIdx * 12);

				for (int latIdx = 0; latIdx < latDim.getLength(); latIdx++) {
					for (int lonIdx = 0; lonIdx < lonDim.getLength(); lonIdx++) {

						tempData.set(0, latIdx, lonIdx, timeIdx * latIdx
								* lonIdx / 3.14159);
					}
				}
				// write the data out for one record
				// set the origin here
				time_origin[0] = timeIdx;
				origin[0] = timeIdx;

				t = writer.findVariable("T");
				writer.write(t, origin, tempData);
				time = writer.findVariable("time");
				writer.write(time, time_origin, timeData);
			} // loop over record

			// all done
			writer.close();
		} catch (IOException | InvalidRangeException e) {
			e.printStackTrace();
			fail();
		}
	}

}

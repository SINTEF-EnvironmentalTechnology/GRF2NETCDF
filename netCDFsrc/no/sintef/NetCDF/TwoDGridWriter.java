/**
 * Construct CDM objects for 2D surface grids
 */
package no.sintef.NetCDF;

import java.io.IOException;
import java.util.Date;

import no.sintef.model.Summary;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Variable;

/**
 * @author ubr
 * 
 */
public abstract class TwoDGridWriter extends NetCDFWriter {

	public static final String TIME = "time";
	public static final String LONGITUDE = "longitude";
	public static final String LATITUDE = "latitude";
	public static final String GRIDMAPPING = "gridmapping";

	protected Attribute grid_mapping = new Attribute("grid_mapping",
			GRIDMAPPING);

	protected Attribute coordinates = new Attribute("coordinates", TIME + " "
			+ LATITUDE + " " + LONGITUDE);

	public abstract class TwoDGrid {
		protected Array time;
		protected Array latitudes;
		protected Array longitudes;
		protected Index index;
		protected int[] dimensions;
		protected int nXDim;
		protected int nYDim;
		protected float xMin;
		protected float dX;
		protected float yMin;
		protected float dY;

		protected TwoDGrid(Summary summary) {

			time = Array.factory(DataType.FLOAT, new int[] { 1 });
			time.setFloat(0, summary.getDaysSinceStart());

			setDimensions(summary);

			// CF 1.5: The values of a coordinate variable or auxiliary
			// coordinate
			// variable indicate the locations of the grid points. The locations
			// of the boundaries between cells are indicated by bounds variables
			// (see Section 7.1, “Cell Boundaries”). If bounds are not provided,
			// an application might reasonably assume the grid points to be at
			// the centers of the cells, but we do not require that in this
			// standard.
			latitudes = Array.factory(DataType.FLOAT, new int[] { nYDim });
			for (int i = 0; i < nYDim; i++) {
				latitudes.setFloat(i, yMin + (i + 0.5f) * dY);
			}

			longitudes = Array.factory(DataType.FLOAT, new int[] { nXDim });
			for (int i = 0; i < nXDim; i++) {
				longitudes.setFloat(i, xMin + (i + 0.5f) * dX);
			}

			dimensions = new int[] { 1, nYDim, nXDim };
			index = Index.factory(dimensions);
		}

		abstract void setDimensions(Summary summary);
	}

	/**
	 * 
	 * @param xDim
	 * @param yDim
	 * @param simulationStart
	 * @param fileLocation
	 * @throws IOException
	 */
	public void createFile(int xDim, int yDim, Date simulationStart,
			String fileLocation) throws IOException {
		prepareFile(xDim, yDim, simulationStart, fileLocation);
		addVariables();

		writer.create();
		
		// write time = 0, as there will be an undefined value elsewise
		Variable time = writer.findVariable(TIME);
		int[] from_start = new int[] { 0 };
		Array mytime = Array.factory(DataType.FLOAT, new int[] { 1 });
		mytime.setFloat(0, 0f);
		try {
			writer.write(time, from_start, mytime);
		} catch (InvalidRangeException e) {
			throw new IOException(e.getMessage());
		}
		
		writer.close();

	}

	/**
	 * @param xDim
	 * @param yDim
	 * @param simulationStart
	 * @param fileLocation
	 * @throws IOException
	 */
	protected void prepareFile(int xDim, int yDim, Date simulationStart,
			String fileLocation) throws IOException {
		// create a new file for writing
		getWriterForCreateNew(fileLocation);

		// dimensions
		dims.add(writer.addUnlimitedDimension(TIME));
		dims.add(writer.addDimension(null, LATITUDE, yDim));
		dims.add(writer.addDimension(null, LONGITUDE, xDim));

		// coordinate system "grid_mapping"
		// Latitude and longitude on the WGS 1984 datum, CF1.5, grid-mappings-and-projections
		// short latitude_longitude
		Variable myVar = writer.addVariable(null, GRIDMAPPING, DataType.SHORT,
				"");
		writer.addVariableAttribute(myVar, new Attribute("grid_mapping_name",
				"latitude_longitude"));
		writer.addVariableAttribute(myVar, new Attribute(
				"longitude_of_prime_meridian", 0.0));
		writer.addVariableAttribute(myVar, new Attribute("semi_major_axis",
				6378137.0));
		writer.addVariableAttribute(myVar, new Attribute("inverse_flattening",
				298.257223563));

		// coordinate variables
		// float Time(time)
		myVar = writer.addVariable(null, TIME, DataType.FLOAT, TIME);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"forecast time after simulation start"));
		writer.addVariableAttribute(myVar, new Attribute("standard_name",
				"time"));
		writer.addVariableAttribute(myVar, new Attribute("units", "days since "
				+ makeNetCDFDate(simulationStart)));
		writer.addVariableAttribute(myVar, new Attribute("calendar",
				"gregorian"));
		writer.addVariableAttribute(myVar, fillValue);
		writer.addVariableAttribute(myVar, new Attribute("axis", "T"));

		// float latitude (latitude)
		myVar = writer.addVariable(null, LATITUDE, DataType.FLOAT, LATITUDE);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"latitude of grid cell center"));
		writer.addVariableAttribute(myVar, new Attribute("standard_name",
				"latitude"));
		writer.addVariableAttribute(myVar, new Attribute("units",
				"degrees_north"));
		writer.addVariableAttribute(myVar, new Attribute("axis", "Y"));

		// float longitude(longitude)
		myVar = writer.addVariable(null, LONGITUDE, DataType.FLOAT, LONGITUDE);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"longitude of grid cell center"));
		writer.addVariableAttribute(myVar, new Attribute("standard_name",
				"longitude"));
		writer.addVariableAttribute(myVar, new Attribute("units",
				"degrees_east"));
		writer.addVariableAttribute(myVar, new Attribute("axis", "X"));

	}

	abstract void addVariables();
	
	/**
	 * @param myVar
	 */
	protected void addStandardAttributes(Variable myVar) {

		writer.addVariableAttribute(myVar, fillValue);
		writer.addVariableAttribute(myVar, grid_mapping);
		writer.addVariableAttribute(myVar, coordinates);
	}

	abstract void writeToFile(String fileLocation, int offset, TwoDGrid grid)
			throws NetCDFException;

}

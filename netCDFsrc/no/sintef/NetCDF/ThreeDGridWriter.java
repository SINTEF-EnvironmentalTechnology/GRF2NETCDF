/**
 * Construct CDM objects for 3D water column grids
 */
package no.sintef.NetCDF;

import java.io.IOException;
import java.util.Date;

import no.sintef.model.SubsurfaceGridDimensions;
import no.sintef.model.Summary;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.nc2.Attribute;
import ucar.nc2.Variable;

/**
 * @author ubr
 * 
 */

public abstract class ThreeDGridWriter extends TwoDGridWriter {

	public static final String DEPTH = "depth";

	/**
	 * add dimension depth for variable(time, depth, latitude, longitude)
	 * 
	 * @throws IOException
	 */
	public ThreeDGridWriter() {
		super();
		coordinates = new Attribute("coordinates", TIME + " " + DEPTH + " "
				+ LATITUDE + " " + LONGITUDE);
	}

	/**
	 * 
	 * @param xDim
	 * @param yDim
	 * @param simulationStart
	 * @param fileLocation
	 * @throws IOException
	 */
	public void createFile(int xDim, int yDim, int zDim, Date simulationStart,
			String fileLocation) throws IOException {
		// create a new file for writing - from 2D Grid
		prepareFile(xDim, yDim, simulationStart, fileLocation);

		// insert dimension depth at position 1 => (time, depth, lat, lon)
		dims.add(1, writer.addDimension(null, DEPTH, zDim));

		// add coordinate variable for depth
		// float depth(depth)
		Variable myVar = writer.addVariable(null, DEPTH, DataType.FLOAT, DEPTH);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"depth of grid cell center"));
		writer.addVariableAttribute(myVar, new Attribute("standard_name",
				"depth"));
		writer.addVariableAttribute(myVar, new Attribute("positive", "down"));
		writer.addVariableAttribute(myVar, new Attribute("axis", "Z"));

		addVariables();

		writer.create();
		writer.close();

	}

	public abstract class ThreeDGrid {
		protected Array time;
		protected Array depths;
		protected Array latitudes;
		protected Array longitudes;
		protected Index index;
		protected int[] dimensions;
		protected int nXDim;
		protected int nYDim;
		protected int nZDim;

		protected ThreeDGrid(Summary summary,
				SubsurfaceGridDimensions gridDimensions) {

			time = Array.factory(DataType.FLOAT, new int[] { 1 });
			time.setFloat(0, summary.getDaysSinceStart());

			// grid dimension from summary
			// number cells in X, Y and Z direction
			nXDim = summary.getnX_conc();
			nYDim = summary.getnY_conc();
			nZDim = summary.getnZ_conc();

			// longitude of lower left corner in degrees E, width in [m]
			float xMin = summary.getXmin_conc();
			float dX = summary.getDx_conc();

			// latitude of lower left corner in degrees N, height in [m]
			float yMin = summary.getYmin_conc();
			float dY = summary.getDy_conc();

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

			depths = Array.factory(DataType.FLOAT, new int[] { nZDim });
			float depth = summary.getZmin(); // depth of most left most bottom
												// cell in [m]
			for (int i = 0; i < nZDim; i++) {
				float dZ = gridDimensions.getCellHeightPerLayer().get(i);
				float halfdZ = 0.5f * dZ;
				
				depths.setFloat(i, depth - halfdZ);
				// new depth
				depth = depth - dZ;
					
				
			}
			dimensions = new int[] { 1, nZDim, nYDim, nXDim };
			index = Index.factory(dimensions);
		}
	}

	abstract void writeToFile(String fileLocation, int offset, ThreeDGrid grid)
			throws NetCDFException;
}

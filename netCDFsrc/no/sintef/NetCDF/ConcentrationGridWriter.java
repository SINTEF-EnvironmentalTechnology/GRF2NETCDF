/**
 * Construct CDM objects for 3D water column grids
 */
package no.sintef.NetCDF;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import no.sintef.model.SubsurfaceGridDimensions;
import no.sintef.model.Summary;
import no.sintef.model.WaterConcentration;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Variable;

/**
 * @author ubr
 * 
 */

public class ConcentrationGridWriter extends ThreeDGridWriter {

	public static final String DISSOLVED_CONCENTRATION = "dissolved_concentration";
	public static final String TOTAL_CONCENTRATION = "total_concentration";

	public ConcentrationGridWriter() {
		super();
		FILENAME_EXTENSION = "-ConcentrationGridOil.nc";
	}

	public class ConcentrationGrid extends ThreeDGrid {

		private Array dissolved_concentration;
		private Array total_concentration;

		public ConcentrationGrid(Vector<WaterConcentration> concentrations,
				Summary summary, SubsurfaceGridDimensions gridDimensions) {
			super(summary, gridDimensions);

			dissolved_concentration = Array.factory(DataType.FLOAT, dimensions);
			total_concentration = Array.factory(DataType.FLOAT, dimensions);

			Iterator<WaterConcentration> concentrationCellsIterator = concentrations
					.iterator();
			for (int i = 0; i < summary.getnZ_conc(); i++) { // for each layer

				for (int j = 0; j < gridDimensions
						.getNumberWatConcCellsPerLayer().get(i); j++) {
					// for each cell in this layer

					WaterConcentration cell = concentrationCellsIterator.next();

					// Calculate x and y from cell index:
					// index=x + nXDim*(y-1)+ nXDim*nYDim*i
					// --> x + A*y = B with A = nXDim and B =
					// index + nXDim(1-nYDim*i)
					// --> x = B%A and y = B/A
					int xIndex = (cell.getCellIndex() - 1) % nXDim;
					int yIndex = (cell.getCellIndex() - 1) % (nXDim * nYDim)
							/ nXDim;

					index.set(0, i, yIndex, xIndex);
					dissolved_concentration.setFloat(index,
							cell.getConcentration());
					total_concentration.setFloat(index,
							cell.getTotalconcentration());
				} // end for each cell in layer i
			} // end for each layer
		}
	} // end class

	@Override
	void addVariables() {

		// concentration of dissolved matter in grid cell
		// float dissolved_concentration (time, depth, latitude, longitude)
		Variable myVar = writer.addVariable(null, DISSOLVED_CONCENTRATION,
				DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"concentration of dissolved matter"));
		// writer.addVariableAttribute(myVar, new Attribute("standard_name",
		// "concentration_of_dissolved_suspended_matter_in_sea_water"));
		writer.addVariableAttribute(myVar, new Attribute("units", "ppb"));
		addStandardAttributes(myVar);

		// total hydrocarbon concentration in grid cell
		// float total_concentration (time, depth, latitude, longitude)
		myVar = writer.addVariable(null, TOTAL_CONCENTRATION, DataType.FLOAT,
				dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"total hydrocarbon concentration"));
		// writer.addVariableAttribute(myVar, new Attribute("standard_name",
		// "concentration_of_suspended_matter_in_sea_water"));
		writer.addVariableAttribute(myVar, new Attribute("units", "ppb"));
		addStandardAttributes(myVar);
	}

	/**
	 * 
	 * @param fileLocation
	 * @param offset
	 * @param timestep
	 * @param surfaceCellsOil
	 * @param summary
	 * @throws IOException
	 */
	@Override
	public void writeToFile(String fileLocation, int offset, ThreeDGrid grid)
			throws NetCDFException {

		try {
			// open file for writing, initialize writer with this
			getWriterForWriteExisting(fileLocation);
		} catch (IOException e) {
			throw new NetCDFException(e.getMessage());
		}

		// origin = where to put the record, set with offset, we need one 4D and one 1D
		int[] origin = new int[] { offset, 0, 0, 0 };
		int[] time_origin = new int[] { offset };
		int[] from_start = new int[] { 0 };

		Variable concentration = writer.findVariable(DISSOLVED_CONCENTRATION);
		Variable thconcentration = writer.findVariable(TOTAL_CONCENTRATION);
		Variable timeV = writer.findVariable(TIME);

		Variable depthV = writer.findVariable(DEPTH);
		Variable latitudeV = writer.findVariable(LATITUDE);
		Variable longitudeV = writer.findVariable(LONGITUDE);

		try {
			//write the non record data first
			writer.write(depthV, from_start, grid.depths);
			writer.write(latitudeV, from_start, grid.latitudes);
			writer.write(longitudeV, from_start, grid.longitudes);
			
			
			writer.write(timeV, time_origin, grid.time);
			
			writer.write(concentration, origin,
					((ConcentrationGrid) grid).dissolved_concentration);
			writer.write(thconcentration, origin,
					((ConcentrationGrid) grid).total_concentration);
			
			writer.close();

		} catch (InvalidRangeException e) {
			throw new NetCDFException(e.getMessage());
		} catch (IOException e) {
			throw new NetCDFException(e.getMessage());
		}

	}

	@Override
	void writeToFile(String fileLocation, int offset, TwoDGrid grid)
			throws NetCDFException {
		// use writeToFile(string, int, ThreeDGrid)!!

	}
}

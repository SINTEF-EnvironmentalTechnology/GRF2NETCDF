/**
 * Construct CDM objects for 2D surface grids for oil
 */
package no.sintef.NetCDF;

import java.io.IOException;
import java.util.Vector;

import no.sintef.model.Summary;
import no.sintef.model.SurfaceCellOil;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Variable;

/**
 * @author ubr
 * 
 */

public class SurfaceGridOilWriter extends TwoDGridWriter {

	public static final String THICKNESS = "emulsion_thickness";
	public static final String EMULSIONVISCOSITY = "emulsion_viscosity";
	public static final String EMULSIONWATCONT = "emulsion_water_content";
	public static final String COVERAGE = "coverage";
	
	public SurfaceGridOilWriter() {
		super();
		FILENAME_EXTENSION = "-SeaSurfaceGrid-Oil.nc";
	}

	public class SurfaceOilGrid extends TwoDGrid {

		private Array thickness;
		private Array viscosity;
		private Array waterContent;
		private Array coverage;

		public SurfaceOilGrid(Vector<SurfaceCellOil> surfacegridcells,
				Summary summary) {

			super(summary);
			

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

			

			thickness = Array.factory(DataType.FLOAT, dimensions);
			viscosity = Array.factory(DataType.FLOAT, dimensions);
			waterContent = Array.factory(DataType.FLOAT, dimensions);
			coverage = Array.factory(DataType.FLOAT, dimensions);

			for (int i = 0; i < surfacegridcells.size(); i++) {
				SurfaceCellOil surfaceCell = surfacegridcells.get(i);
				// it happened that there was 0 data in a GRF file record, throw
				// away that one
				if (surfaceCell.getCellIndex() == 0) {
					continue;
				}

				// Calculate x and y from cell index:
				// index=x + nXDim*(y-1)+ nXDim*nYDim*i
				// --> x + A*y = B with A = nXDim and B =
				// index + nXDim(1-nYDim*i)
				// --> x = B%A and y = B/A

				int xIndex = (surfaceCell.getCellIndex() - 1) % nXDim;
				int yIndex = (surfaceCell.getCellIndex() - 1) % (nXDim * nYDim)
						/ nXDim;

				index.set(0, yIndex, xIndex);
				thickness.setFloat(index, surfacegridcells.get(i)
						.getThickness()); // µm!!
				viscosity.setFloat(index, surfacegridcells.get(i)
						.getEmulsionViscosity());
				waterContent.setFloat(index, surfacegridcells.get(i)
						.getWaterContent());
				coverage.setFloat(index, surfacegridcells.get(i).getCoverage());
			}
		}

		@Override
		void setDimensions(Summary summary) {// grid dimension from summary
			// number cells in X and Y direction
			nXDim = summary.getnX_surf();
			nYDim = summary.getnY_surf();

			// longitude of lower left corner in degrees E, width in [m]
			xMin = summary.getXmin_surf();
			dX = summary.getDx_surf();

			// latitude of lower left corner in degrees N, height in [m]
			yMin = summary.getYmin_surf();// TODO Auto-generated method stub
			dY = summary.getDy_surf();

		}
	}

	@Override
	void addVariables() {

		// thickness of film in grid cell
		// float thickness (time, latitude, longitude)
		Variable myVar = writer.addVariable(null, THICKNESS, DataType.FLOAT,
				dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Thickness of contaminant emulsion"));
		writer.addVariableAttribute(myVar, new Attribute("units", "µm"));
		addStandardAttributes(myVar);

		// water content of emulsion in grid cell
		// float concentration (time, latitude, longitude)
		myVar = writer.addVariable(null, EMULSIONWATCONT, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Water content of contaminant emulsion"));
		writer.addVariableAttribute(myVar, new Attribute("units", "%"));
		addStandardAttributes(myVar);

		// emulsion viscosity in grid cell
		// float emulsion_viscosity (time, latitude, longitude)
		myVar = writer.addVariable(null, EMULSIONVISCOSITY, DataType.FLOAT,
				dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Emulsion viscosity of contaminant"));
		writer.addVariableAttribute(myVar, new Attribute("units", "centipoise"));
		addStandardAttributes(myVar);

		// coverage of film in grid cell
		// float coverage (time, latitude, longitude)
		myVar = writer.addVariable(null, COVERAGE, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Fraction of grid cell covered with contaminant"));
		writer.addVariableAttribute(myVar, new Attribute("units", "%"));
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
	public void writeToFile(String fileLocation, int offset, TwoDGrid grid)
			throws NetCDFException {

		try {
			// open file for writing, initialize writer with this
			getWriterForWriteExisting(fileLocation);
		} catch (IOException e) {
			throw new NetCDFException(e.getMessage());
		}

		Variable thickness = writer.findVariable(THICKNESS);
		Variable viscosity = writer.findVariable(EMULSIONVISCOSITY);
		Variable waterContent = writer.findVariable(EMULSIONWATCONT);
		Variable coverage = writer.findVariable(COVERAGE);
		Variable latitude = writer.findVariable(LATITUDE);
		Variable longitude = writer.findVariable(LONGITUDE);
		Variable time = writer.findVariable(TIME);

		try {
			int[] origin = new int[] { offset, 0, 0 };
			int[] from_start = new int[] { 0 };
			int[] time_origin = new int[] { offset };
			//write the non record data first
			writer.write(latitude, from_start, grid.latitudes);
			writer.write(longitude, from_start, grid.longitudes);
			
			writer.write(time, time_origin, grid.time);
			
			writer.write(thickness, origin, ((SurfaceOilGrid) grid).thickness);
			writer.write(viscosity, origin, ((SurfaceOilGrid) grid).viscosity);
			writer.write(waterContent, origin,
					((SurfaceOilGrid) grid).waterContent);
			writer.write(coverage, origin, ((SurfaceOilGrid) grid).coverage);
			
			writer.close();
			
		} catch (InvalidRangeException e) {
			throw new NetCDFException(e.getMessage());
		} catch (IOException e) {
			throw new NetCDFException(e.getMessage());
		}

	}
}

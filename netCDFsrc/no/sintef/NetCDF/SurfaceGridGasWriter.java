/**
 * Construct CDM objects for 2D surface grids for gas
 */
package no.sintef.NetCDF;

import java.io.IOException;
import java.util.Vector;

import no.sintef.model.Summary;
import no.sintef.model.SurfaceCellGas;
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

public class SurfaceGridGasWriter extends TwoDGridWriter {

	public static final String MASS_FLUX = "mass_flux";
	public static final String VOID_FRACTION = "void_fraction";
	
	public SurfaceGridGasWriter() {
		super();
		FILENAME_EXTENSION = "-SeaSurfaceGrid-Gas.nc";
	}

	public class SurfaceGasGrid extends TwoDGrid {

		private Array mass_flux;
		private Array void_fraction;

		public SurfaceGasGrid(Vector<SurfaceCellGas> surfacegridcells,
				Summary summary) {

			super(summary);

			dimensions = new int[] { 1, nYDim, nXDim };
			index = Index.factory(dimensions);

			mass_flux = Array.factory(DataType.FLOAT, dimensions);
			void_fraction = Array.factory(DataType.FLOAT, dimensions);

			for (int i = 0; i < surfacegridcells.size(); i++) {
				SurfaceCellGas surfaceCell = surfacegridcells.get(i);
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
				mass_flux
						.setFloat(index, surfacegridcells.get(i).getMassFlux());
				void_fraction.setFloat(index, surfacegridcells.get(i)
						.getVoidFraction());
			}
		}

		@Override
		void setDimensions(Summary summary) {
			// number cells in X and Y direction
			nXDim = summary.getnX_gas();
			nYDim = summary.getnY_gas();

			// longitude of lower left corner in degrees E, width in [m]
			xMin = summary.getXmin_gas();
			dX = summary.getDx_gas();

			// latitude of lower left corner in degrees N, height in [m]
			yMin = summary.getYmin_gas();// TODO Auto-generated method stub
			dY = summary.getDy_gas();

		}
	}

	@Override
	void addVariables() {

		// mass_flux of surfaced gas in grid cell
		// float mass_flux (time, latitude, longitude)
		Variable myVar = writer.addVariable(null, MASS_FLUX, DataType.FLOAT,
				dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Flux of gas mass through sea surface"));
		writer.addVariableAttribute(myVar, new Attribute("units", "g/sqm/s"));
		addStandardAttributes(myVar);

		// mass_flux of surfaced gas in grid cell
		// float void_fraction (time, latitude, longitude)
		myVar = writer.addVariable(null, VOID_FRACTION, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Void fraction at sea surface"));
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

		Variable thickness = writer.findVariable(MASS_FLUX);
		Variable viscosity = writer.findVariable(VOID_FRACTION);
		Variable latitude = writer.findVariable(LATITUDE);
		Variable longitude = writer.findVariable(LONGITUDE);
		Variable time = writer.findVariable(TIME);

		try {
			int[] from_start = new int[] { 0 };
			int[] time_origin = new int[] { offset };
			int[] origin = new int[] {offset, 0, 0};
			
			writer.write(time, time_origin, grid.time);
			writer.write(latitude, from_start, grid.latitudes);
			writer.write(longitude, from_start, grid.longitudes);
			writer.write(thickness, origin, ((SurfaceGasGrid) grid).mass_flux);
			writer.write(viscosity, origin,
					((SurfaceGasGrid) grid).void_fraction);
			
			writer.close();

		} catch (InvalidRangeException e) {
			throw new NetCDFException(e.getMessage());
		} catch (IOException e) {
			throw new NetCDFException(e.getMessage());
		}

	}
}

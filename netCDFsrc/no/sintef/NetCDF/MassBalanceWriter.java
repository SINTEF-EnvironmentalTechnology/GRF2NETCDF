/**
 * 
 */
package no.sintef.NetCDF;

import java.io.IOException;
import java.util.Date;

import no.sintef.model.Summary;
import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.InvalidRangeException;
import ucar.nc2.Attribute;
import ucar.nc2.Variable;

/**
 * @author ubr
 * 
 */
public class MassBalanceWriter extends NetCDFWriter {
	private static final String TIME = "time";
	private static String SURFACE = "mass_fraction_of_released_substances_on_sea_surface";
	private static String ATMOSPHERE = "mass_fraction_of_released_substances_in_atmosphere";
	private static String SUBMERGED = "mass_fraction_of_released_substances_submerged";
	private static String SEDIMENT = "mass_fraction_of_released_substances_in_sediment";
	private static String CLEANED = "mass_fraction_of_released_substances_cleaned";
	private static String STRANDED = "mass_fraction_of_released_substances_stranded";
	private static String BIODEGRADED = "mass_fraction_of_released_substances_biodegraded";
	private static String OUTSIDE_GRID = "mass_fraction_of_released_substances_outside_grid";
	private static String TOTAL_MASS = "total_mass_of_released_substances";
	private static String SURFACE_AREA = "surface_area_covered_by_released_substances";

	private Attribute units = new Attribute("units", "tonnes");
	private Attribute coordinates = new Attribute("coordinates", TIME);

	public MassBalanceWriter() {
		super();
		FILENAME_EXTENSION = "-MassBalance.nc";
	}
	/**
	 * 
	 * @param filename
	 * @param simulationStart
	 * @throws NetCDFException
	 * @throws IOException
	 */
	public void createFile(String filename, Date simulationStart)
			throws IOException {
		// create a new file for writing
		getWriterForCreateNew(filename);
		// dimensions
		dims.add(writer.addUnlimitedDimension(TIME));

		// coordinate variables
		// float Time(time)
		Variable myVar = writer.addVariable(null, TIME, DataType.FLOAT, TIME);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"forecast time after simulation start"));
		writer.addVariableAttribute(myVar, new Attribute("standard_name",
				"time"));
		writer.addVariableAttribute(myVar, new Attribute("units", "days since "
				+ makeNetCDFDate(simulationStart)));
		writer.addVariableAttribute(myVar, new Attribute("calendar",
				"gregorian"));
		writer.addVariableAttribute(myVar, new Attribute("axis", "T"));

		// variables for mass balance
		myVar = writer.addVariable(null, SURFACE, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Surface fraction of the release"));
		addStandardAttributes(myVar);

		myVar = writer.addVariable(null, ATMOSPHERE, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Evaporated fraction of the release"));
		addStandardAttributes(myVar);

		myVar = writer.addVariable(null, SUBMERGED, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Submerged fraction of the release"));
		addStandardAttributes(myVar);

		myVar = writer.addVariable(null, SEDIMENT, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Fraction of the release in sediment"));
		addStandardAttributes(myVar);

		myVar = writer.addVariable(null, CLEANED, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Cleaned fraction of the release"));
		addStandardAttributes(myVar);

		myVar = writer.addVariable(null, STRANDED, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Stranded fraction of the release"));
		addStandardAttributes(myVar);

		myVar = writer.addVariable(null, BIODEGRADED, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Decayed fraction of the release"));
		addStandardAttributes(myVar);

		myVar = writer.addVariable(null, OUTSIDE_GRID, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Fraction of the release outside grid"));
		addStandardAttributes(myVar);

		myVar = writer.addVariable(null, TOTAL_MASS, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Total mass of the release"));
		addStandardAttributes(myVar);

		myVar = writer.addVariable(null, SURFACE_AREA, DataType.FLOAT, dims);
		writer.addVariableAttribute(myVar, new Attribute("long_name",
				"Surface area covered by the release"));
		addStandardAttributes(myVar);
		writer.deleteVariableAttribute(myVar, "units");
		writer.addVariableAttribute(myVar, new Attribute("units", "km-2"));

		// create the file
		writer.create();
		writer.close();

	}

	/**
	 * @param myVar
	 */
	private void addStandardAttributes(Variable myVar) {

		writer.addVariableAttribute(myVar, units);
		writer.addVariableAttribute(myVar, fillValue);
		writer.addVariableAttribute(myVar, coordinates);
	}

	/**
	 * 
	 * @param fileLocation
	 * @param offset
	 * @param summary
	 * @throws NetCDFException
	 */
	public void writeToFile(String fileLocation, int offset, Summary summary)
			throws NetCDFException {
		try {
			// open file for writing, initialize writer with this
			getWriterForWriteExisting(fileLocation);
		} catch (IOException e) {
			throw new NetCDFException(e.getMessage());
		}

		Variable surface = writer.findVariable(SURFACE);
		Variable atmosphere = writer.findVariable(ATMOSPHERE);
		Variable submerged = writer.findVariable(SUBMERGED);
		Variable sediment = writer.findVariable(SEDIMENT);
		Variable cleaned = writer.findVariable(CLEANED);
		Variable stranded = writer.findVariable(STRANDED);
		Variable biodegraded = writer.findVariable(BIODEGRADED);
		Variable outside = writer.findVariable(OUTSIDE_GRID);
		Variable total = writer.findVariable(TOTAL_MASS);
		Variable surfaceArea = writer.findVariable(SURFACE_AREA);
		Variable time = writer.findVariable(TIME);

		Array timestamp = Array.factory(DataType.FLOAT, new int[] { 1 });
		timestamp.setFloat(0, summary.getDaysSinceStart());

		Array surfaceA = Array.factory(DataType.FLOAT, new int[] { 1 });
		Array atmosphereA = Array.factory(DataType.FLOAT, new int[] { 1 });
		Array submergedA = Array.factory(DataType.FLOAT, new int[] { 1 });
		Array sedimentA = Array.factory(DataType.FLOAT, new int[] { 1 });
		Array cleanedA = Array.factory(DataType.FLOAT, new int[] { 1 });
		Array strandedA = Array.factory(DataType.FLOAT, new int[] { 1 });
		Array biodegradedA = Array.factory(DataType.FLOAT, new int[] { 1 });
		Array outsideA = Array.factory(DataType.FLOAT, new int[] { 1 });
		Array totalA = Array.factory(DataType.FLOAT, new int[] { 1 });
		Array surfaceAreaA = Array.factory(DataType.FLOAT, new int[] { 1 });

		int[] origin = new int[] { offset };

		try {

			writer.write(time, origin, timestamp);
			writer.write(surface, origin, surfaceA);
			writer.write(atmosphere, origin, atmosphereA);
			writer.write(submerged, origin, submergedA);
			writer.write(sediment, origin, sedimentA);
			writer.write(cleaned, origin, cleanedA);
			writer.write(stranded, origin, strandedA);
			writer.write(biodegraded, origin, biodegradedA);
			writer.write(outside, origin, outsideA);
			writer.write(total, origin, totalA);
			writer.write(surfaceArea, origin, surfaceAreaA);
			
			writer.close();

		} catch (InvalidRangeException e) {
			throw new NetCDFException(e.getMessage());
		} catch (IOException e) {
			throw new NetCDFException(e.getMessage());
		}
	}

}

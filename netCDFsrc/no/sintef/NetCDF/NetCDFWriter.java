/**
 * 
 */
package no.sintef.NetCDF;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFileWriter;
import ucar.nc2.dataset.NetcdfDataset;

/**
 * @author ubr
 * 
 */
public abstract class NetCDFWriter {

	protected static NetcdfFileWriter writer;
	protected static String FILENAME_EXTENSION = "";
	protected static final NetcdfFileWriter.Version version = NetcdfFileWriter.Version.netcdf3;
	protected List<Dimension> dims = new ArrayList<Dimension>();
	protected Attribute fillValue = new Attribute("_FillValue", 0f);
	@Deprecated
	protected Attribute missingValue = new Attribute("missing_value", 0f);

	public NetCDFWriter() {
		ucar.nc2.jni.netcdf.Nc4Iosp.setLibraryAndPath(
				"C:/Program Files (x86)/netCDF 4.3.1.1/bin", "netCDF4");

	}

	/**
	 * Create a new Netcdf file, with fill mode true.
	 * 
	 * @param fileLocation
	 * @throws IOException
	 */
	protected static void getWriterForCreateNew(String fileLocation)
			throws IOException {
		writer = NetcdfFileWriter.createNew(version, fileLocation);
	}

	/**
	 * Open an existing Netcdf file for writing data. Fill mode is true. Cannot
	 * add new objects, you can only read/write data to existing Variables.
	 * 
	 * @param fileLocation
	 * @throws IOException
	 */
	protected static void getWriterForWriteExisting(String fileLocation)
			throws IOException {
		writer = NetcdfFileWriter.openExisting(fileLocation);
	}

	/**
	 * makes a date in the form "YYYY-MM-DD-hh:mm:00"
	 * 
	 * @param simulationStart
	 * @return
	 */
	static String makeNetCDFDate(Date simulationStart) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(simulationStart);
		// get correct month
		int month = cal.get(Calendar.MONTH) + 1;
		String monthString;
		// make String with always 2 characters
		if (month < 10) {
			monthString = "0" + month;
		} else {
			monthString = "" + month;
		}
		
		// get correct hour
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		String hourString;
		// make String with always 2 characters
		if (hour < 10) {
			hourString = "0" + hour;
		} else {
			hourString = "" + hour;
		}
		
		// get correct minute
		int minute = cal.get(Calendar.MINUTE);
		String minuteString;
		// make String with always 2 characters
		if (minute < 10) {
			minuteString = "0" + minute;
		} else {
			minuteString = "" + minute;
		}
		return cal.get(Calendar.YEAR) + "-" + monthString + "-"
				+ cal.get(Calendar.DAY_OF_MONTH) + " "
				+ hourString + ":" + minuteString + ":00";
	}

	/**
	 * 
	 * @param ncfile
	 * @param title
	 * @param comment
	 * @param version
	 * @param largeFile
	 */
	protected void addGlobalAttributes(NetcdfDataset ncfile, String title,
			String comment, String version, boolean largeFile) {

		// @TODO
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public static String getNetCDFFilename(String filename) {

		String filenameWOending = filename.substring(0,
				filename.lastIndexOf("."));

		return filenameWOending + FILENAME_EXTENSION;

	}

}

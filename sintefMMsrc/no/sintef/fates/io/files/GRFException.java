/**
 * 
 */
package no.sintef.fates.io.files;

/**
 * @author ubr
 *
 */
@SuppressWarnings("serial")
public class GRFException extends Exception {

	public GRFException(String message) {
		super(message);
	}

	public class GRFError {
		public static final String ERROR_READING_GRFFILE_WRONG_VERSION = "The result file version is not supported! ";
		public static final String TRANSFORMATION_STOPPED = "Transformation will stop now. Sorry for the inconvenience! ";
		public static final String OUTPUT_TRANSFORMATION_FAILED = "Error in output data transformation. Can't produce netCDF!";
		public static final String ERROR_READING_GRFFILE_EMPTY_FILE = "GRF file is empty, can't read results!";
		public static final String ERROR_IN_SIMULATION = "No or too may result file(s)!";
	}
}

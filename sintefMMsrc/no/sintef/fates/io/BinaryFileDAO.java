/**
 * 
 */
package no.sintef.fates.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;



import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */

public class BinaryFileDAO extends BinaryDataDAO {
	
	public static class Sizes {
	    public static final int LONG_FIELD_SIZE     = 8;
	    public static final int INT_FIELD_SIZE      = 4;
	    public static final int SHORT_FIELD_SIZE    = 2;
	    public static final int CHAR_FIELD_SIZE     = 2;
	    public static final int BYTE_FIELD_SIZE     = 1;
	    public static final int BOOLEAN_FIELD_SIZE  = 1;
	    public static final int DOUBLE_FIELD_SIZE   = 8;
	    public static final int FLOAT_FIELD_SIZE    = 4;

	}

	public static long bytesRead = 0;
	protected String filename = "Scenario.file";
	/**
	 * 
	 */
	protected LittleEndianDataInputStream dataStream;
	protected boolean largeFile = false;
	protected int totalAvailable = 0;
	protected int currentlyAvailable = 0;

	/**
	 * @throws FileNotFoundException
	 * @param file the file to read from
	 * Provides a datastream im LitteEndian byteorder
	 */
	public BinaryFileDAO(File file) throws FileNotFoundException {
		super();
		
		// Wrap the FileInputStream with a DataInputStream
		dataStream = new LittleEndianDataInputStream(new FileInputStream(file));
	}

	protected boolean reachedEndOfFile() throws IOException {
		return dataStream.available() <= 0;
	}

	
	
}

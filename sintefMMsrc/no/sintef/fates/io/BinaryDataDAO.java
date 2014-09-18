/**
 * 
 */
package no.sintef.fates.io;

import java.io.IOException;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 *
 */
public class BinaryDataDAO {
	/**
	 * @param myStream
	 * @param length
	 * @return String with length length read from myStream
	 * @throws IOException 
	 */
	public static String getString(LittleEndianDataInputStream myStream, int length) throws IOException {
		byte[] b = new byte[length];
		myStream.read(b);

		return new String(b);

	}

}

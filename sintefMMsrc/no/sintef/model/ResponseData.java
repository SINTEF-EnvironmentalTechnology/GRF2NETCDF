/**
 * 
 */
package no.sintef.model;

import java.io.IOException;
import java.util.Vector;

import no.sintef.fates.io.BinaryDataDAO;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class ResponseData extends BinaryDataDAO {

	private Vector<String> units;

	public static class ResponseDataDAO {
		/**
		 * 
		 * @param dataStream
		 * @throws IOException
		 */
		public static ResponseData readData(
				LittleEndianDataInputStream dataStream) throws IOException {
			ResponseData data = new ResponseData();

			int lengthOfResponseVesselName = dataStream.readInt();
			int numberResponseVessels = dataStream.readInt();

			if (numberResponseVessels > 0) {
				Vector<String> units = new Vector<String>();
				for (int i = 0; i < numberResponseVessels; i++) {
					units.add(getString(dataStream, lengthOfResponseVesselName));
				}
				data.units = units;
			}

			return data;
		}
	}

	/***************************************************************************************/

	/**
	 * @return the vessels
	 */
	public Vector<String> getUnits() {
		return units;
	}

	/**
	 * @param units
	 *            the units to set
	 */
	public void setUnits(Vector<String> units) {
		this.units = units;
	}

}

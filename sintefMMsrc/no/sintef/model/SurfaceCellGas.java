/**
 * 
 */
package no.sintef.model;

import java.io.IOException;

import com.google.common.io.LittleEndianDataInputStream;

import no.sintef.fates.io.BinaryFileDAO.Sizes;

/**
 * @author ubr
 * 
 */
public class SurfaceCellGas {

	/*********************************************************************************/

	public static int SIZE = 2 * Sizes.FLOAT_FIELD_SIZE + Sizes.INT_FIELD_SIZE;

	private int cellIndex;
	private float massFlux;
	private float voidFraction;

	/**
	 * 
	 * @author ubr
	 * 
	 */
	public static class SurfaceCellGasDAO {
		/**
		 * 
		 * @param datastream
		 * @return
		 * @throws IOException
		 */
		public static SurfaceCellGas readData(
				LittleEndianDataInputStream datastream) throws IOException {
			SurfaceCellGas data = new SurfaceCellGas();

			data.cellIndex = datastream.readInt();
			data.massFlux = datastream.readFloat();
			data.voidFraction = datastream.readFloat();

			return data;
		}
	}

	/*********************************************************************************/
	/**
	 * @return the iCell
	 */
	public int getCellIndex() {
		return cellIndex;
	}

	/**
	 * @param iCell
	 *            the iCell to set
	 */
	public void setCellIndex(int iCell) {
		this.cellIndex = iCell;
	}

	public float getMassFlux() {
		return massFlux;
	}

	public void setMassFlux(float massFlux) {
		this.massFlux = massFlux;
	}

	public float getVoidFraction() {
		return voidFraction;
	}

	public void setVoidFraction(float voidFraction) {
		this.voidFraction = voidFraction;
	}

	/*********************************************************************************/

}

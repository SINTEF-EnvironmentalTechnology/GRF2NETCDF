/**
 * The GRF file contains water concentrations per time step per cell and in addition the mean concentration per 
 * time step and the maximum concentration per time step in the order Cell, concentration, total concentration, percentEvaporated
 */
package no.sintef.model;

import java.io.IOException;

import com.google.common.io.LittleEndianDataInputStream;

import no.sintef.fates.io.BinaryFileDAO.Sizes;

/**
 * @author ubr
 * 
 */

public class WaterConcentration {

	/********************************************************************************/
	private int iCell;
	private float concentration;
	private float totalConcentration;
	private byte percentEvaporated;

	/********************************************************************************/

	public static int SIZE = Sizes.INT_FIELD_SIZE + 2 * Sizes.FLOAT_FIELD_SIZE
			+ Sizes.BYTE_FIELD_SIZE;

	/**
	 * 
	 * @author ubr
	 * 
	 */
	public static class WaterConcentrationDAO {
		/**
		 * 
		 * @param datastream
		 * @return
		 * @throws IOException
		 */
		public static WaterConcentration readData(
				LittleEndianDataInputStream datastream) throws IOException {
			WaterConcentration data = new WaterConcentration();

			data.setiCell(datastream.readInt());
			data.setConcentration(datastream.readFloat());
			data.setTotalconcentration(datastream.readFloat());
			data.setPercentEvap(datastream.readByte());

			return data;
		}
	}

	/**
	 * @return the i
	 */
	public int getCellIndex() {
		return iCell;
	}

	/**
	 * @param i
	 *            the i to set
	 */
	public void setiCell(int iCell) {
		this.iCell = iCell;
	}

	/**
	 * @return the conc
	 */
	public float getConcentration() {
		return concentration;
	}

	/**
	 * @param conc
	 *            the conc to set
	 */
	public void setConcentration(float conc) {
		this.concentration = conc;
	}

	/**
	 * @return the totc
	 */
	public float getTotalconcentration() {
		return totalConcentration;
	}

	/**
	 * @param totc
	 *            the totc to set
	 */
	public void setTotalconcentration(float totc) {
		this.totalConcentration = totc;
	}

	/**
	 * @return the percent_evap
	 */
	public byte getPercentEvap() {
		return percentEvaporated;
	}

	/**
	 * @param percentEvap
	 *            the percent_evap to set
	 */
	public void setPercentEvap(byte percentEvap) {
		percentEvaporated = percentEvap;
	}
}

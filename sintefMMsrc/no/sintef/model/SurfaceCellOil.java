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
public class SurfaceCellOil {

	/*********************************************************************************/

	public static int SIZE = 4 * Sizes.FLOAT_FIELD_SIZE + Sizes.INT_FIELD_SIZE;

	private int cellIndex;
	private float thickness;
	private float emulsionWaterContent;
	private float emulsionViscosity;
	private float coverage;
	
	/**
	 * 
	 * @author ubr
	 *
	 */
	public static class SurfaceCellOilDAO {
		/**
		 * 
		 * @param datastream
		 * @return
		 * @throws IOException
		 */
		public static SurfaceCellOil readData(LittleEndianDataInputStream datastream) throws IOException {
			SurfaceCellOil data = new SurfaceCellOil();
			
			data.cellIndex = datastream.readInt();
			data.thickness = datastream.readFloat();
			data.emulsionWaterContent = datastream.readFloat();
			data.emulsionViscosity = datastream.readFloat();
			data.coverage = datastream.readFloat();
			
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

	/**
	 * @return the thickness
	 */
	public float getThickness() {
		return thickness;
	}

	/**
	 * @param thickness
	 *            the thickness to set
	 */
	public void setThickness(float thickness) {
		this.thickness = thickness;
	}

	/**
	 * @return the waterConcentration
	 */
	public float getWaterContent() {
		return emulsionWaterContent;
	}

	/**
	 * @param waterConcentration
	 *            the waterConcentration to set
	 */
	public void setWaterConcentration(float waterConcentration) {
		this.emulsionWaterContent = waterConcentration;
	}

	/**
	 * @return the emulsionViscosity
	 */
	public float getEmulsionViscosity() {
		return emulsionViscosity;
	}

	/**
	 * @param emulsionViscosity
	 *            the emulsionViscosity to set
	 */
	public void setEmulsionViscosity(float emulsionViscosity) {
		this.emulsionViscosity = emulsionViscosity;
	}

	/**
	 * @return the coverage
	 */
	public float getCoverage() {
		return coverage;
	}

	/**
	 * @param coverage
	 *            the coverage to set
	 */
	public void setCoverage(float coverage) {
		this.coverage = coverage;
	}

	/*********************************************************************************/

}

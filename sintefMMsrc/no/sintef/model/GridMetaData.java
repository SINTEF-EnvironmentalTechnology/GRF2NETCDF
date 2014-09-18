/**
 * 
 */
package no.sintef.model;

import no.sintef.fates.io.BinaryFileDAO.Sizes;

/**
 * @author ubr
 * 
 */
public class GridMetaData {

	/**
	 * 
	 */
	protected int numberBytesForCells;
	/**
	 * 
	 */
	protected short province;
	/**
	 * 
	 */
	protected short frame;
	/**
	 * 
	 */
	protected short gridDimensionX;
	/**
	 * 
	 */
	protected short gridDimensionY;

	/**
	 * 
	 */
	protected int numberCells;

	/**
	 * 
	 */
	public GridMetaData() {
		super();
	}

	/**
	 * @return
	 */
	public static int getSize() {
		return 4 * Sizes.SHORT_FIELD_SIZE + 2 * Sizes.INT_FIELD_SIZE;
	}

	/**
	 * @return the numberBytesForShoreCells
	 */
	public int getNumberBytesForCells() {
		return numberBytesForCells;
	}

	/**
	 * @param numberBytesForShoreCells
	 *            the numberBytesForShoreCells to set
	 */
	public void setNumberBytesForCells(int numberBytesForShoreCells) {
		this.numberBytesForCells = numberBytesForShoreCells;
	}

	/**
	 * @return the province
	 */
	public short getProvince() {
		return province;
	}

	/**
	 * @param province
	 *            the province to set
	 */
	public void setProvince(short province) {
		this.province = province;
	}

	/**
	 * @return the frame
	 */
	public short getFrame() {
		return frame;
	}

	/**
	 * @param frame
	 *            the frame to set
	 */
	public void setFrame(short frame) {
		this.frame = frame;
	}

	/**
	 * @return the gridDimensionX
	 */
	public short getGridDimensionX() {
		return gridDimensionX;
	}

	/**
	 * @param gridDimensionX
	 *            the gridDimensionX to set
	 */
	public void setGridDimensionX(short gridDimensionX) {
		this.gridDimensionX = gridDimensionX;
	}

	/**
	 * @return the gridDimensionY
	 */
	public short getGridDimensionY() {
		return gridDimensionY;
	}

	/**
	 * @param gridDimensionY
	 *            the gridDimensionY to set
	 */
	public void setGridDimensionY(short gridDimensionY) {
		this.gridDimensionY = gridDimensionY;
	}

	/**
	 * @return the numberShoreCells
	 */
	public int getNumberCells() {
		return numberCells;
	}

	/**
	 * @param numberShoreCells
	 *            the numberShoreCells to set
	 */
	public void setNumberCells(int numberCells) {
		this.numberCells = numberCells;
	}

}
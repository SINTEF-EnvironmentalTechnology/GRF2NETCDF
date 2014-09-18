/**
 * 
 */
package no.sintef.model;

import no.sintef.fates.io.BinaryFileDAO.Sizes;

/**
 * @author ubr
 * 
 */
public class Cell {
	private short indexX, indexY;
	private float value;

	public static int getSize() {
		return 2 * Sizes.SHORT_FIELD_SIZE + Sizes.FLOAT_FIELD_SIZE;
	}

	/**
	 * @return the i
	 */
	public short getIndexX() {
		return indexX;
	}

	/**
	 * @param i
	 *            the i to set
	 */
	public void setIndexX(short i) {
		this.indexX = i;
	}

	/**
	 * @return the j
	 */
	public short getIndexY() {
		return indexY;
	}

	/**
	 * @param j
	 *            the j to set
	 */
	public void setIndexY(short j) {
		this.indexY = j;
	}

	/**
	 * @return the concentration
	 */
	public float getValue() {
		return value;
	}

	/**
	 * @param concentration
	 *            the concentration to set
	 */
	public void setValue(float value) {
		this.value = value;
	}

}

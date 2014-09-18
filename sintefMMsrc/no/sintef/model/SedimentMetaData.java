/**
 * 
 */
package no.sintef.model;

import java.util.Vector;

import no.sintef.fates.io.BinaryFileDAO.Sizes;

/**
 * @author ubr
 * 
 */
public class SedimentMetaData extends GridMetaData {
	private short method;
	private int sizeOfSedimentBitmap;
	private Vector<Integer> bitmap;

	public static int getSedSize() {
		return GridMetaData.getSize() + Sizes.SHORT_FIELD_SIZE;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(short method) {
		this.method = method;
	}

	/**
	 * @return the method
	 */
	public short getMethod() {
		return method;
	}

	/**
	 * @param sizeOfSedimentBitmap
	 *            the sizeOfSedimentBitmap to set
	 */
	public void setSizeOfSedimentBitmap(int sizeOfSedimentBitmap) {
		this.sizeOfSedimentBitmap = sizeOfSedimentBitmap;
	}

	/**
	 * @return the sizeOfSedimentBitmap
	 */
	public int getSizeOfSedimentBitmap() {
		return sizeOfSedimentBitmap;
	}

	/**
	 * @param bitmap
	 *            the bitmap to set
	 */
	public void setBitmap(Vector<Integer> bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * @return the bitmap
	 */
	public Vector<Integer> getBitmap() {
		return bitmap;
	}
}

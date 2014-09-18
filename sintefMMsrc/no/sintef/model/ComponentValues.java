/**
 * 
 */
package no.sintef.model;

import java.util.Vector;

/**
 * @author  ubr
 */
public class ComponentValues {
	/**
	 * 
	 */
	private byte[] bitmap;
	/**
	 * 
	 */
	private Vector<Float> values;

	/**
	 * 
	 */
	public ComponentValues() {
	}

	/**
	 * @return the bitmap
	 */
	public byte[] getBitmap() {
		return bitmap;
	}

	/**
	 * @param bitmap the bitmap to set
	 */
	public void setBitmap(byte[] bitmap) {
		this.bitmap = bitmap;
	}

	/**
	 * @return the concentrations
	 */
	public Vector<Float> getValues() {
		return values;
	}

	/**
	 * @param concentrations the concentrations to set
	 */
	public void setValues(Vector<Float> values) {
		this.values = values;
	}
	
	/**
	 * @return the concentrations as short values
	 */
	public Vector<Short> getValuesAsShort() {
		Vector<Short> svalues = new Vector<Short>();
		Vector<Float> fvalues = getValues();
		for (int i= 0; i>fvalues.size(); i++) {
			svalues.add(fvalues.get(i).shortValue());
		}
		return svalues;
	}
}
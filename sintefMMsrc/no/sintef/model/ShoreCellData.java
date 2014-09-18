/**
 * 
 */
package no.sintef.model;

import java.util.Vector;


/**
 * @author ubr
 *
 */
public class ShoreCellData {
	/**********************************************************************/
	private ShoreCell shoreCell;
	private ComponentValues componentConcentrations = new ComponentValues();
 
	/**********************************************************************/
	
	
	
	/**
	 * @param shoreCell the shoreCell to set
	 */
	public void setShoreCell(ShoreCell shoreCell) {
		this.shoreCell = shoreCell;
	}

	/**
	 * @return the shoreCell
	 */
	public ShoreCell getShoreCell() {
		return shoreCell;
	}

	/**
	 * @param bitmap the bitmap to set
	 */
	public void setBitmap(byte[] bitmap) {
		this.componentConcentrations.setBitmap(bitmap);
	}

	/**
	 * @return the bitmap
	 */
	public byte[] getBitmap() {
		return componentConcentrations.getBitmap();
	}

	/**
	 * @param concentrations the concentrations to set
	 */
	public void setConcentrations(Vector<Float> concentrations) {
		this.componentConcentrations.setValues(concentrations);
	}

	/**
	 * @return the concentrations
	 */
	public Vector<Float> getConcentrations() {
		return componentConcentrations.getValues();
	}
	
	/**
	 * @return the concentrations
	 */
	public Vector<Short> getConcentrationsAsShort() {
		
		return componentConcentrations.getValuesAsShort();
	}

	
	
}

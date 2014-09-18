/**
 * 
 */
package no.sintef.model;

import java.util.Vector;


/**
 * @author ubr
 *
 */
public class DropletData {
	private Droplet droplet;
	private ComponentValues componentConcentrations = new ComponentValues();

	/**
	 * @param _droplet the spillet to set
	 */
	public void setDroplet(Droplet _droplet) {
		this.droplet = _droplet;
	}

	/**
	 * @return the spillet
	 */
	public Droplet getDroplet() {
		return droplet;
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
		Vector<Short> sconcs = new Vector<Short>();
		Vector<Float> fconcs = getConcentrations();
		for (int i= 0; i>fconcs.size(); i++) {
			sconcs.add(fconcs.get(i).shortValue());
		}
		return sconcs;
	}


}

/**
 * 
 */
package no.sintef.model;

import java.util.Vector;


/**
 * @author ubr
 *
 */
public class OrganismData {
	private Organism organism;
	private ComponentValues componentConcentrations = new ComponentValues();
	
	/**
	 * @param organism the organism to set
	 */
	public void setOrganism(Organism organism) {
		this.organism = organism;
	}

	/**
	 * @return the organism
	 */
	public Organism getOrganism() {
		return organism;
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

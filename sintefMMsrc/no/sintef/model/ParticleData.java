/**
 * 
 */
package no.sintef.model;

import java.util.Vector;


/**
 * @author ubr
 *
 */
public class ParticleData {
	private Particle particle;
	private ComponentValues componentConcentrations = new ComponentValues();

	

	/**
	 * @param particle the particle to set
	 */
	public void setParticle(Particle particle) {
		this.particle = particle;
	}

	/**
	 * @return the particle
	 */
	public Particle getParticle() {
		return particle;
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

/**
 * class for model parcels representing dissolved matter, which in Fates can be oil components in the water column
 * . They are also referred to as particles (deprecated).
 */
package no.sintef.model;

/**
 * @author ubr
 * 
 */
public class Particle extends ParticleCloud {

	private float fractionChemicallyDispersed;

	public void setFractionChemicallyDispersed(float fraction) {
		this.fractionChemicallyDispersed = fraction;

	}

	public float getFractionChemicallyDispersed() {
		return fractionChemicallyDispersed;
	}

}

/**
 * 
 */
package no.sintef.model;

import no.sintef.fates.io.BinaryFileDAO.Sizes;


public class Component {
	private String name; // name of component
	private float density; // density in tons/m3
	private float backgroundConcentration; // background concentrations in ppb
	private short isDegradable; // whether the component is degradable

	public static int getSizeWOName() {
		return 2 * Sizes.FLOAT_FIELD_SIZE + Sizes.SHORT_FIELD_SIZE;
	}

	

	/**
	 * @param name
	 * @param density
	 * @param backgroundConcentration
	 * @param isDegradable
	 */
	public Component() {
		super();
		this.name = "";
		this.density = 0;
		this.backgroundConcentration = 0;
		this.isDegradable = 0;
	}

	public Component(String name, float density, float backgroundConcentration,
			short isDegradable) {
		super();
		this.name = name;
		this.density = density;
		this.backgroundConcentration = backgroundConcentration;
		this.isDegradable = isDegradable;
	}



	public String toString() {
		return (name + ", " + density + ", " + backgroundConcentration + ", " + isDegradable());
	}

	/**
	 * @return
	 */
	private boolean isDegradable() {
		return isDegradable == 0;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the density
	 */
	public float getDensity() {
		return density;
	}

	/**
	 * @param density
	 *            the density to set
	 */
	public void setDensity(float density) {
		this.density = density;
	}

	/**
	 * @return the backgroundConcentration
	 */
	public float getBackgroundConcentration() {
		return backgroundConcentration;
	}

	/**
	 * @param backgroundConcentration
	 *            the backgroundConcentration to set
	 */
	public void setBackgroundConcentration(float backgroundConcentration) {
		this.backgroundConcentration = backgroundConcentration;
	}

	/**
	 * @return the isDegradable
	 */
	public short getIsDegradable() {
		return isDegradable;
	}

	/**
	 * @param isDegradable
	 *            the isDegradable to set
	 */
	public void setIsDegradable(short isDegradable) {
		this.isDegradable = isDegradable;
	}

}
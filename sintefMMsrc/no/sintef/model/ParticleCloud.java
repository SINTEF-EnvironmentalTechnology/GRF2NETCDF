package no.sintef.model;

import no.sintef.fates.io.BinaryFileDAO.Sizes;
import no.sintef.fates.io.files.GRF;


public class ParticleCloud {

	/********************************************************************************/
	private float xPosition; // longitude [degrees East] 
	private float yPosition; // latitude [degrees North]
	private float zPosition; // depth [m], positive = down
	private float mass; // [tonnes]
	private float sigmax; // spreading in x direction
	private float sigmaz; // spreading in z direction

	/*********************************************************************************/
	public static int getSize(short fileversion) {
		if (fileversion > GRF.GRFFILEMINVERSION) {
			// chemically dispersed fraction since version 22
			return 7 * Sizes.FLOAT_FIELD_SIZE;
		} else {
			return 6 * Sizes.FLOAT_FIELD_SIZE;
		}
	}

	public ParticleCloud() {
		super();
	}

	/**
	 * @return the xPosition
	 */
	public float getLongitude() {
		return xPosition;
	}

	/**
	 * @return the yPosition
	 */
	public float getLatitude() {
		return yPosition;
	}

	/**
	 * @return the zPosition
	 */
	public float getDepth() {
		return zPosition;
	}

	/**
	 * @return the xPosition
	 */
	public float getxPosition() {
		return xPosition;
	}

	/**
	 * @param xPosition the xPosition to set
	 */
	public void setxPosition(float xPosition) {
		this.xPosition = xPosition;
	}

	/**
	 * @return the yPosition
	 */
	public float getyPosition() {
		return yPosition;
	}

	/**
	 * @param yPosition the yPosition to set
	 */
	public void setyPosition(float yPosition) {
		this.yPosition = yPosition;
	}

	/**
	 * @return the zPosition
	 */
	public float getzPosition() {
		return zPosition;
	}

	/**
	 * @param zPosition the zPosition to set
	 */
	public void setzPosition(float zPosition) {
		this.zPosition = zPosition;
	}

	/**
	 * @return the mass
	 */
	public float getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(float mass) {
		this.mass = mass;
	}

	/**
	 * @return the sigmax
	 */
	public float getSigmax() {
		return sigmax;
	}

	/**
	 * @param sigmax the sigmax to set
	 */
	public void setSigmax(float sigmax) {
		this.sigmax = sigmax;
	}

	/**
	 * @return the sigmaz
	 */
	public float getSigmaz() {
		return sigmaz;
	}

	/**
	 * @param sigmaz the sigmaz to set
	 */
	public void setSigmaz(float sigmaz) {
		this.sigmaz = sigmaz;
	}

}
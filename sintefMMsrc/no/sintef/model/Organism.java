/**
 * 
 */
package no.sintef.model;

import no.sintef.fates.io.BinaryFileDAO.Sizes;

/**
 * @author ubr
 * 
 */
public class Organism {

	/********************************************************************************/

	private short species, stage;
	private float lon, lat, depth;
	private float bodyresidue;
	private float killedFraction;

	public static int getSize() {
		return 2 * Sizes.SHORT_FIELD_SIZE + 5 * Sizes.FLOAT_FIELD_SIZE;
	}

	/********************************************************************************/
	/**
	 * @return the species
	 */
	public int getSpecies() {
		return species;
	}

	/**
	 * @param species
	 *            the species to set
	 */
	public void setSpecies(short species) {
		this.species = species;
	}

	/**
	 * @return the stage
	 */
	public short getStage() {
		return stage;
	}

	/**
	 * @param stage
	 *            the stage to set
	 */
	public void setStage(short stage) {
		this.stage = stage;
	}

	/**
	 * @return the lon
	 */
	public float getLon() {
		return lon;
	}

	/**
	 * @param lon
	 *            the lon to set
	 */
	public void setLon(float lon) {
		this.lon = lon;
	}

	/**
	 * @return the lat
	 */
	public float getLat() {
		return lat;
	}

	/**
	 * @param lat
	 *            the lat to set
	 */
	public void setLat(float lat) {
		this.lat = lat;
	}

	/**
	 * @return the depth
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}

	/**
	 * @return the bodyresidue
	 */
	public float getBodyresidue() {
		return bodyresidue;
	}

	/**
	 * @param bodyresidue
	 *            the bodyresidue to set
	 */
	public void setBodyresidue(float bodyresidue) {
		this.bodyresidue = bodyresidue;
	}

	/**
	 * @return the fractionKilled
	 */
	public float getKilledFraction() {
		return killedFraction;
	}

	/**
	 * @param fractionKilled
	 *            the fractionKilled to set
	 */
	public void setKilledFraction(float killedFraction) {
		this.killedFraction = killedFraction;
	}

}

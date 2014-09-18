/**
 * class for model parcels representing non-dissolved matter, which in Fates can be oil on the sea surface, 
 * dispersed droplets in the water column
 * or even fish / zoo-plankton. They are also referred to as spillets (deprecated).
 */
package no.sintef.model;

import no.sintef.fates.io.BinaryFileDAO.Sizes;
import no.sintef.fates.io.files.GRF;

/**
 * @author ubr
 * 
 */
public class Droplet {

	/********************************************************************************/

	/**
	 * From FATES code: category, sigmaz, & sfxloc(i), sfyloc(i), & sfxold(i),
	 * sfyold(i), !xd 042299 & radnew(i), radold(i), srfmas(i), emulmass,
	 * srfden(i), fwc(i)*100.0, viscm(i), & spdep(i) , drop_diam,
	 * CompBitMap(1:nINT2Comp)
	 */

	/**
	 * category: 0,1 = submerged, >=2: surface: 1 submerged, 2 thin, 3 medium, 4
	 * thick
	 */
	private int category = 0;

	/**
	 * [m] film thickness (usually in the µm range, but the unit here is always
	 * meters!) for surface model parcels, or "sigma-z" value of a submerged
	 * model parcels. A negative value means "unknown".
	 */
	private float sigmaz = 0.0f;

	/**
	 * location (current and former) and radius of the parcel
	 * 
	 */
	private float sfxloc = 0.0f, sfyloc = 0.0f;
	private float sfxold = 0.0f, sfyold = 0.0f;
	private float radnew = 0.0f, radold = 0.0f;

	/**
	 * mass, emulsion mass [tonnes] and emulsion density
	 */
	private float srfmas = 0.0f;
	private float emulmass = 0.0f;
	private float emuldens = 0.0f;

	/**
	 * water content [%], emulsion viscosity [cP]
	 */
	private float watercont = 0.0f;
	private float emulvisc = 0.0f;

	/**
	 * depth of the model parcel's center [m]
	 */
	private float depth = 0.0f;

	/**
	 * droplet diameter for submerged droplets in this model parcel [µm]
	 */
	private int dropletdiameter = 0;

	/**
	 * fraction of chemically disperded oil in this parcel [0..1]
	 */
	private float fractionChemicallyDispersed = 0.0f;

	/**
	 * Determine required size to store (read) droplet information
	 * 
	 * @param fileversion
	 * @return size [byte]
	 */
	public static int getSize(short fileversion) {
		if (fileversion > GRF.GRFFILEMINVERSION) {
			// chemically dispersed fraction since version 22
			return 14 * Sizes.FLOAT_FIELD_SIZE + 2 * Sizes.INT_FIELD_SIZE;
		} else {
			return 13 * Sizes.FLOAT_FIELD_SIZE + 2 * Sizes.INT_FIELD_SIZE;
		}
	}

	/*
	 * category: 0,1 = submerged, >1 = surface: 1 submerged, 2 thin, 3 medium, 4
	 * thick
	 */
	public boolean IsSubmerged() {
		return this.category == 1 || this.category == 0;
	}

	public boolean IsSurface() {
		return this.category > 1;
	}

	public float getLatitude() {
		return sfyloc;
	}

	public float getLongitude() {
		return sfxloc;
	}

	/**
	 * @return the depth
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * @return the radnew
	 */
	public float getRadius() {
		return radnew;
	}

	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return the srfmas
	 */
	public float getMass() {
		return srfmas;
	}

	public float getFilmThickness() {
		return sigmaz;
	}

	/**
	 * @return the sigmaz
	 */
	public float getSigmaz() {
		return sigmaz;
	}

	/**
	 * @param sigmaz
	 *            the sigmaz to set
	 */
	public void setSigmaz(float sigmaz) {
		this.sigmaz = sigmaz;
	}

	/**
	 * @return the sfxloc
	 */
	public float getSfxloc() {
		return sfxloc;
	}

	/**
	 * @param sfxloc
	 *            the sfxloc to set
	 */
	public void setSfxloc(float sfxloc) {
		this.sfxloc = sfxloc;
	}

	/**
	 * @return the sfyloc
	 */
	public float getSfyloc() {
		return sfyloc;
	}

	/**
	 * @param sfyloc
	 *            the sfyloc to set
	 */
	public void setSfyloc(float sfyloc) {
		this.sfyloc = sfyloc;
	}

	/**
	 * @return the sfxold
	 */
	public float getSfxold() {
		return sfxold;
	}

	/**
	 * @param sfxold
	 *            the sfxold to set
	 */
	public void setSfxold(float sfxold) {
		this.sfxold = sfxold;
	}

	/**
	 * @return the sfyold
	 */
	public float getSfyold() {
		return sfyold;
	}

	/**
	 * @param sfyold
	 *            the sfyold to set
	 */
	public void setSfyold(float sfyold) {
		this.sfyold = sfyold;
	}

	/**
	 * @return the radnew
	 */
	public float getRadnew() {
		return radnew;
	}

	/**
	 * @param radnew
	 *            the radnew to set
	 */
	public void setRadnew(float radnew) {
		this.radnew = radnew;
	}

	/**
	 * @return the radold
	 */
	public float getRadold() {
		return radold;
	}

	/**
	 * @param radold
	 *            the radold to set
	 */
	public void setRadold(float radold) {
		this.radold = radold;
	}

	/**
	 * @return the srfmas
	 */
	public float getSrfmas() {
		return srfmas;
	}

	/**
	 * @param srfmas
	 *            the srfmas to set
	 */
	public void setSrfmas(float srfmas) {
		this.srfmas = srfmas;
	}

	/**
	 * @return the emulmass
	 */
	public float getEmulmass() {
		return emulmass;
	}

	/**
	 * @param emulmass
	 *            the emulmass to set
	 */
	public void setEmulmass(float emulmass) {
		this.emulmass = emulmass;
	}

	/**
	 * @return the emuldens
	 */
	public float getEmuldens() {
		return emuldens;
	}

	/**
	 * @param emuldens
	 *            the emuldens to set
	 */
	public void setEmuldens(float emuldens) {
		this.emuldens = emuldens;
	}

	/**
	 * @return the watercont
	 */
	public float getWatercont() {
		return watercont;
	}

	/**
	 * @param watercont
	 *            the watercont to set
	 */
	public void setWatercont(float watercont) {
		this.watercont = watercont;
	}

	/**
	 * @return the emulvisc
	 */
	public float getEmulvisc() {
		return emulvisc;
	}

	/**
	 * @param emulvisc
	 *            the emulvisc to set
	 */
	public void setEmulvisc(float emulvisc) {
		this.emulvisc = emulvisc;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}

	/**
	 * @return the dropletdiameter
	 */
	public int getDropletdiameter() {
		return dropletdiameter;
	}

	/**
	 * @param dropletdiameter
	 *            the dropletdiameter to set
	 */
	public void setDropletdiameter(int dropletdiameter) {
		this.dropletdiameter = dropletdiameter;
	}

	/**
	 * 
	 * @return
	 */
	public float getFractionChemicallyDispersed() {
		return fractionChemicallyDispersed;
	}

	/**
	 * 
	 * @param fractionChemicallyDispersed
	 */
	public void setFractionChemicallyDispersed(float fractionChemicallyDispersed) {
		this.fractionChemicallyDispersed = fractionChemicallyDispersed;

	}

}

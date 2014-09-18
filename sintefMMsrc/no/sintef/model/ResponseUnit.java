/**
 * 
 */
package no.sintef.model;

import no.sintef.fates.io.BinaryFileDAO.Sizes;

/**
 * @author ubr
 */
public class ResponseUnit {

	private int responseType;
	private int xref;
	private float shiplat;
	private float shiplon;
	private int responseStatus;
	private float shiplat2;
	private float shiplon2;
	private float acumulatedMass;
	
	public static int SIZE = 3 * Sizes.INT_FIELD_SIZE + 5
			* Sizes.FLOAT_FIELD_SIZE;

	/*********************************************************************************************/
	/**
	 * @return the responseType
	 */
	public int getResponseType() {
		return responseType;
	}

	/**
	 * @param responseType
	 *            the responseType to set
	 */
	public void setResponseType(int responseType) {
		this.responseType = responseType;
	}

	/**
	 * @return the xref
	 */
	public int getXref() {
		return xref;
	}

	/**
	 * @param xref
	 *            the xref to set
	 */
	public void setXref(int xref) {
		this.xref = xref;
	}

	/**
	 * @return the shiplat
	 */
	public float getShiplat() {
		return shiplat;
	}

	/**
	 * @param shiplat
	 *            the shiplat to set
	 */
	public void setShiplat(float shiplat) {
		this.shiplat = shiplat;
	}

	/**
	 * @return the shiplon
	 */
	public float getShiplon() {
		return shiplon;
	}

	/**
	 * @param shiplon
	 *            the shiplon to set
	 */
	public void setShiplon(float shiplon) {
		this.shiplon = shiplon;
	}

	/**
	 * @return the responseStatus
	 */
	public int getResponseStatus() {
		return responseStatus;
	}

	/**
	 * @param responseStatus
	 *            the responseStatus to set
	 */
	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	/**
	 * @return the shiplat2
	 */
	public float getShiplat2() {
		return shiplat2;
	}

	/**
	 * @param shiplat2
	 *            the shiplat2 to set
	 */
	public void setShiplat2(float shiplat2) {
		this.shiplat2 = shiplat2;
	}

	/**
	 * @return the shiplon2
	 */
	public float getShiplon2() {
		return shiplon2;
	}

	/**
	 * @param shiplon2
	 *            the shiplon2 to set
	 */
	public void setShiplon2(float shiplon2) {
		this.shiplon2 = shiplon2;
	}

	/**
	 * @return the acumulatedMass
	 */
	public float getAcumulatedMass() {
		return acumulatedMass;
	}

	/**
	 * @param acumulatedMass
	 *            the acumulatedMass to set
	 */
	public void setAcumulatedMass(float acumulatedMass) {
		this.acumulatedMass = acumulatedMass;
	}

}
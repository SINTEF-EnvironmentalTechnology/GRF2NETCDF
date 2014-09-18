/**
 * 
 */
package no.sintef.model;

import java.util.Vector;


/**
 * @author ubr
 *
 */
public class Plume3DData {
	private short numberNearfiedRecords;
	private Vector<Plume3D> nearfieldRecords = new Vector<Plume3D>();
	/**
	 * @return the numberNearfiedRecords
	 */
	public short getNumberNearfiedRecords() {
		return numberNearfiedRecords;
	}
	/**
	 * @param numberNearfiedRecords the numberNearfiedRecords to set
	 */
	public void setNumberNearfiedRecords(short numberNearfiedRecords) {
		this.numberNearfiedRecords = numberNearfiedRecords;
	}
	/**
	 * @return the nearfieldRecords
	 */
	public Vector<Plume3D> getNearfieldRecords() {
		return nearfieldRecords;
	}
	/**
	 * @param nearfieldRecords the nearfieldRecords to set
	 */
	public void setNearfieldRecords(Vector<Plume3D> nearfieldRecords) {
		this.nearfieldRecords = nearfieldRecords;
	}
}

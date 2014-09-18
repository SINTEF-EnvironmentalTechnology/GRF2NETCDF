/**
 * 
 */
package no.sintef.model;

import no.sintef.fates.io.BinaryFileDAO.Sizes;

/**
 * @author ubr
 * 
 */
public class Plume3D {

	private float tid;
	private float plx;
	private float ply;
	private float dep;
	private float rad;
	private float tpd;
	private float exf;

	public static int SIZE = 7 * Sizes.FLOAT_FIELD_SIZE;
	

	/**
	 * @return the tid
	 */
	public float getTid() {
		return tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(float tid) {
		this.tid = tid;
	}

	/**
	 * @return the plx
	 */
	public float getPlx() {
		return plx;
	}

	/**
	 * @param plx
	 *            the plx to set
	 */
	public void setPlx(float plx) {
		this.plx = plx;
	}

	/**
	 * @return the ply
	 */
	public float getPly() {
		return ply;
	}

	/**
	 * @param ply
	 *            the ply to set
	 */
	public void setPly(float ply) {
		this.ply = ply;
	}

	/**
	 * @return the dep
	 */
	public float getDep() {
		return dep;
	}

	/**
	 * @param dep
	 *            the dep to set
	 */
	public void setDep(float dep) {
		this.dep = dep;
	}

	/**
	 * @return the rad
	 */
	public float getRad() {
		return rad;
	}

	/**
	 * @param rad
	 *            the rad to set
	 */
	public void setRad(float rad) {
		this.rad = rad;
	}

	/**
	 * @return the tpd
	 */
	public float getTpd() {
		return tpd;
	}

	/**
	 * @param tpd
	 *            the tpd to set
	 */
	public void setTpd(float tpd) {
		this.tpd = tpd;
	}

	/**
	 * @return the exf
	 */
	public float getExf() {
		return exf;
	}

	/**
	 * @param exf
	 *            the exf to set
	 */
	public void setExf(float exf) {
		this.exf = exf;
	}

}

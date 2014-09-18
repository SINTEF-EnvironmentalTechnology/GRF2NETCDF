/**
 * 
 */
package no.sintef.model;

import no.sintef.fates.io.BinaryFileDAO.Sizes;

/**
 * @author ubr
 * 
 */
public class GridCell extends Cell {

	public static int getSize() {
		return 2 * Sizes.SHORT_FIELD_SIZE + Cell.getSize();
	}

	/**********************************************************************/
	private short province;
	private short frame;

	/*********************************************************************/

	public short getProvince() {
		return province;
	}

	public void setProvince(short p) {
		this.province = p;
	}

	public short getFrame() {
		return frame;
	}

	public void setFrame(short f) {
		this.frame = f;
	}
}

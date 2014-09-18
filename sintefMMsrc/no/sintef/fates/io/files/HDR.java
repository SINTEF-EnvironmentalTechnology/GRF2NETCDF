/**
 * 
 */
package no.sintef.fates.io.files;

import no.sintef.model.Grid;
import no.sintef.model.HDRSummary;
import no.sintef.model.OilSpillSimulation;

public class HDR {
	/**
	 * 
	 * @author ubr
	 */

	// constants
	public static final int HDRFILENUMBER = 9;
	public static final int HDRFILEVERSION = 2;
	public static final String FILENAME = ".hdr";

	public static final int HDRFILE_GRIDDIM_X = 1000;
	public static final int HDRFILE_GRIDDIM_Y = 1000;
	public static final int HDRFILE_ARRAYDIM = 12;

	// fields
	private OilSpillSimulation spillInfo;
	private Grid gridData;
	private HDRSummary summary;

	// getters & setters
	/**
	 * @return the spillInfo
	 */
	public OilSpillSimulation getSpillInfo() {
		return spillInfo;
	}

	/**
	 * @param spillInfo
	 *            the spillInfo to set
	 */
	public void setSpillInfo(OilSpillSimulation spillInfo) {
		this.spillInfo = spillInfo;
	}

	/**
	 * @return the gridData
	 */
	public Grid getGridData() {
		return gridData;
	}

	/**
	 * @param gridData
	 *            the gridData to set
	 */
	public void setGridData(Grid gridData) {
		this.gridData = gridData;
	}

	/**
	 * @return the summary
	 */
	public HDRSummary getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(HDRSummary summary) {
		this.summary = summary;
	}
}

/**
 * 
 */
package no.sintef.fates.io.files;

import java.util.Vector;

import no.sintef.model.TimeStepDependentData;
import no.sintef.model.TimeStepIndependentData;

/**
 * @author ubr
 * 
 */
public class GRF {

	/**********************************************************************************************/
	private BinaryHeader binaryHeader;
	private TimeStepIndependentData tsindepData;
	private Vector <TimeStepDependentData> tsdepData;
	
	public static final int GRFFILENUMBER = 1;
	/**
	 * GRFFILEVERSION = earliest version supported
	 */
	public static final int GRFFILEMINVERSION = 21;
	public static final int GRFFILEMAXVERSION = 23;
	public static final int GRFFILEGASVERSION = 23;
	public static final String FILENAME = ".grf";
	
	/**********************************************************************************************/
	
	/**
	 * @return the binaryHeader
	 */
	public BinaryHeader getBinaryHeader() {
		return binaryHeader;
	}
	/**
	 * @param binaryHeader the binaryHeader to set
	 */
	public void setBinaryHeader(BinaryHeader binaryHeader) {
		this.binaryHeader = binaryHeader;
	}
	/**
	 * @return the tsindepData
	 */
	public TimeStepIndependentData getTsindepData() {
		return tsindepData;
	}
	/**
	 * @param tsindepData the tsindepData to set
	 */
	public void setTsindepData(TimeStepIndependentData tsindepData) {
		this.tsindepData = tsindepData;
	}
	/**
	 * @return the tsdepData
	 */
	public Vector<TimeStepDependentData> getTsdepData() {
		return tsdepData;
	}
	/**
	 * @param tsdepData the tsdepData to set
	 */
	public void setTsdepData(Vector<TimeStepDependentData> tsdepData) {
		this.tsdepData = tsdepData;
	}
	
	
}

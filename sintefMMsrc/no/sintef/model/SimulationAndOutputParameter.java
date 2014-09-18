/**
 * 
 */
package no.sintef.model;


/**
 * @author ubr
 * 
 */
public class SimulationAndOutputParameter {

	/**
	 * 
	 */
	public SimulationAndOutputParameter() {
		// TODO Auto-generated constructor stub
	}

	/***************************************************************************************/
	private float simulationDuration;
	private float sedimentPorosity;
	private float sedimentToxicity;
	private float naturalGridSize;
	private short sedimentRiskData; // 1 = true else false
	private short intFractionStrg; // 1 = true else false
	private short sedimentPoreData; // 1 = true else false
	private short plume3DOutput; // 1 = true else false
	private short expandingGrid; // 1= true else false
	private short vertExpnConcentrationGrid; // 1 = true else false
	private float seaOxygenAverage;

	/***************************************************************************************/
	/**
	 * @return the simulationDuration
	 */
	public float getSimulationDuration() {
		return simulationDuration;
	}

	/**
	 * @param simulationDuration
	 *            the simulationDuration to set
	 */
	public void setSimulationDuration(float simulationDuration) {
		this.simulationDuration = simulationDuration;
	}

	/**
	 * @return the sedimentPorosity
	 */
	public float getSedimentPorosity() {
		return sedimentPorosity;
	}

	/**
	 * @param sedimentPorosity
	 *            the sedimentPorosity to set
	 */
	public void setSedimentPorosity(float sedimentPorosity) {
		this.sedimentPorosity = sedimentPorosity;
	}

	/**
	 * @return the sedimentToxicity
	 */
	public float getSedimentToxicity() {
		return sedimentToxicity;
	}

	/**
	 * @param sedimentToxicity
	 *            the sedimentToxicity to set
	 */
	public void setSedimentToxicity(float sedimentToxicity) {
		this.sedimentToxicity = sedimentToxicity;
	}

	/**
	 * @return the naturalGridSize
	 */
	public float getNaturalGridSize() {
		return naturalGridSize;
	}

	/**
	 * @param naturalGridSize
	 *            the naturalGridSize to set
	 */
	public void setNaturalGridSize(float naturalGridSize) {
		this.naturalGridSize = naturalGridSize;
	}

	/**
	 * @return the sedimentRiskData
	 */
	public short getSedimentRiskData() {
		return sedimentRiskData;
	}

	public boolean hasSedimentRiskData() {
		return (sedimentRiskData == 1);
	}

	/**
	 * @param sedimentRiskData
	 *            the sedimentRiskData to set
	 */
	public void setSedimentRiskData(short sedimentRiskData) {
		this.sedimentRiskData = sedimentRiskData;
	}

	/**
	 * @return the intFractionStrg
	 */
	public short getIntFractionStrg() {
		return intFractionStrg;
	}

	/**
	 * @return the intFractionStrg
	 */
	public boolean isIntFractionStrg() {
		return intFractionStrg == 1;
	}

	/**
	 * @param intFractionStrg
	 *            the intFractionStrg to set
	 */
	public void setIntFractionStrg(short intFractionStrg) {
		this.intFractionStrg = intFractionStrg;
	}

	/**
	 * @return the sedimentPoreData
	 */
	public short getSedimentPoreData() {
		return sedimentPoreData;
	}

	/**
	 * @return the sedimentPoreData
	 */
	public boolean hasSedimentPoreData() {
		return sedimentPoreData == 1;
	}

	/**
	 * @param sedimentPoreData
	 *            the sedimentPoreData to set
	 */
	public void setSedimentPoreData(short sedimentPoreData) {
		this.sedimentPoreData = sedimentPoreData;
	}

	/**
	 * @return the plume3DOutput
	 */
	public short getPlume3DOutput() {
		return plume3DOutput;
	}

	/**
	 * @return the plume3DOutput
	 */
	public boolean hasPlume3DOutput() {
		return plume3DOutput == 1;
	}

	/**
	 * @param plume3dOutput
	 *            the plume3DOutput to set
	 */
	public void setPlume3DOutput(short plume3dOutput) {
		plume3DOutput = plume3dOutput;
	}

	/**
	 * @return the expandingGrid
	 */
	public short getExpandingGrid() {
		return expandingGrid;
	}

	/**
	 * @return the expandingGrid
	 */
	public boolean hasExpandingGrid() {
		return expandingGrid == 1;
	}

	/**
	 * @param expandingGrid
	 *            the expandingGrid to set
	 */
	public void setExpandingGrid(short expandingGrid) {
		this.expandingGrid = expandingGrid;
	}

	/**
	 * @return the vertExpnConcentrationGrid
	 */
	public short getVertExpnConcentrationGrid() {
		return vertExpnConcentrationGrid;
	}

	/**
	 * @return the vertExpnConcentrationGrid
	 */
	public boolean hasVertExpnConcentrationGrid() {
		return vertExpnConcentrationGrid == 1;
	}

	/**
	 * @param vertExpnConcentrationGrid
	 *            the vertExpnConcentrationGrid to set
	 */
	public void setVertExpnConcentrationGrid(short vertExpnConcentrationGrid) {
		this.vertExpnConcentrationGrid = vertExpnConcentrationGrid;
	}

	/**
	 * @return the seaOxygenAverage
	 */
	public float getSeaOxygenAverage() {
		return seaOxygenAverage;
	}

	/**
	 * @param seaOxygenAverage
	 *            the seaOxygenAverage to set
	 */
	public void setSeaOxygenAverage(float seaOxygenAverage) {
		this.seaOxygenAverage = seaOxygenAverage;
	}

}

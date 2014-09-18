/**
 * 
 */
package no.sintef.model;

import java.util.Vector;

/**
 * @author ubr
 *
 */
/***********************************************************************/
public class TimeStepDependentData {
	private Summary summary;
	private Vector<ReleaseSite> sites;
	private SubsurfaceGridDimensions subsurfGridData;

	private int numberBytesForSpillets;
	private Vector<DropletData> spilletData = new Vector<DropletData>();

	private int numberBytesForParticles;
	private Vector<ParticleData> particleData = new Vector<ParticleData>();

	private int numberBytesForOrganisms;
	private Vector<OrganismData> organismData = new Vector<OrganismData>();

	private Vector<WaterConcentration> waterConcentrations = new Vector<WaterConcentration>();
	private Vector<SurfaceCellOil> surfacegrid = new Vector<SurfaceCellOil>();

	private Vector<ShoreGrid> shoreData = new Vector<ShoreGrid>();

	private SedimentMetaData sedimentmetadata = new SedimentMetaData();
	private Vector<SedimentData> sedimentData = new Vector<SedimentData>();

	private Vector<ResponseUnit> responseData = new Vector<ResponseUnit>();
	private Vector<Plume3DData> plumeData = new Vector<Plume3DData>();
	private int numberBytesForGasParticles;
	private Vector<GasParticle> gasParticleData= new Vector<GasParticle>();;

	/***********************************************************************/
	/**
	 * @return the summary
	 */
	public Summary getSummary() {
		return summary;
	}

	/**
	 * @param summary
	 *            the summary to set
	 */
	public void setSummary(Summary summary) {
		this.summary = summary;
	}

	/**
	 * @return the siteData
	 */
	public Vector<ReleaseSite> getSites() {
		return sites;
	}

	/**
	 * @param siteData
	 *            the siteData to set
	 */
	public void setSites(Vector<ReleaseSite> siteData) {
		this.sites = siteData;
	}

	/**
	 * @return the subsurfGridData
	 */
	public SubsurfaceGridDimensions getSubsurfGridData() {
		return subsurfGridData;
	}

	/**
	 * @param subsurfGridData
	 *            the subsurfGridData to set
	 */
	public void setSubsurfGridData(SubsurfaceGridDimensions subsurfGridData) {
		this.subsurfGridData = subsurfGridData;
	}

	/**
	 * @param numberBytesForSpillets
	 *            the numberBytesForSpillets to set
	 */
	public void setNumberBytesForSpillets(int numberBytesForSpillets) {
		this.numberBytesForSpillets = numberBytesForSpillets;
	}

	/**
	 * @return the numberBytesForSpillets
	 */
	public int getNumberBytesForSpillets() {
		return numberBytesForSpillets;
	}

	/**
	 * @param spilletData
	 *            the spilletData to set
	 */
	public void setSpilletData(Vector<DropletData> spilletData) {
		this.spilletData = spilletData;
	}

	/**
	 * @return the spilletData
	 */
	public Vector<DropletData> getSpilletData() {
		return spilletData;
	}

	/**
	 * @param numberBytesForParticles
	 *            the numberBytesForParticles to set
	 */
	public void setNumberBytesForParticles(int numberBytesForParticles) {
		this.numberBytesForParticles = numberBytesForParticles;
	}

	/**
	 * @return the numberBytesForParticles
	 */
	public int getNumberBytesForParticles() {
		return numberBytesForParticles;
	}

	/**
	 * @param particleData
	 *            the particleData to set
	 */
	public void setParticleData(Vector<ParticleData> particleData) {
		this.particleData = particleData;
	}

	/**
	 * @return the particleData
	 */
	public Vector<ParticleData> getParticleData() {
		return particleData;
	}

	/**
	 * @param numberOfBytesForOrganisms
	 *            the numberOfBytesForOrganisms to set
	 */
	public void setNumberBytesForOrganisms(int numberOfBytesForOrganisms) {
		this.numberBytesForOrganisms = numberOfBytesForOrganisms;
	}

	/**
	 * @return the numberOfBytesForOrganisms
	 */
	public int getNumberBytesForOrganisms() {
		return numberBytesForOrganisms;
	}

	/**
	 * @param organismData
	 *            the organismData to set
	 */
	public void setOrganismData(Vector<OrganismData> organismData) {
		this.organismData = organismData;
	}

	/**
	 * @return the organismData
	 */
	public Vector<OrganismData> getOrganismData() {
		return organismData;
	}

	/**
	 * @param waterConcentrations
	 *            the waterConcentrations to set
	 */
	public void setWaterConcentrations(
			Vector<WaterConcentration> waterConcentrations) {
		this.waterConcentrations = waterConcentrations;
	}

	/**
	 * @return the waterConcentrations
	 */
	public Vector<WaterConcentration> getWaterConcentrations() {
		return waterConcentrations;
	}

	/**
	 * @param surfacegrid
	 *            the surfacegrid to set
	 */
	public void setSurfacegrid(Vector<SurfaceCellOil> surfacegrid) {
		this.surfacegrid = surfacegrid;
	}

	/**
	 * @return the surfacegrid
	 */
	public Vector<SurfaceCellOil> getSurfacegrid() {
		return surfacegrid;
	}

	/**
	 * @param shoreData
	 *            the shoreData to set
	 */
	public void setShoreData(Vector<ShoreGrid> shoreData) {
		this.shoreData = shoreData;
	}

	/**
	 * @return the shoreData
	 */
	public Vector<ShoreGrid> getShoreData() {
		return shoreData;
	}

	/**
	 * @param sedimentmetadata
	 *            the sedimentmetadata to set
	 */
	public void setSedimentmetadata(SedimentMetaData sedimentmetadata) {
		this.sedimentmetadata = sedimentmetadata;
	}

	/**
	 * @return the sedimentmetadata
	 */
	public SedimentMetaData getSedimentmetadata() {
		return sedimentmetadata;
	}

	/**
	 * @param vector
	 *            the sedimentData to set
	 */
	public void setSedimentData(Vector<SedimentData> vector) {
		this.sedimentData = vector;
	}

	/**
	 * @return the sedimentData
	 */
	public Vector<SedimentData> getSedimentData() {
		return sedimentData;
	}

	/**
	 * @return the responseData
	 */
	public Vector<ResponseUnit> getResponseData() {
		return responseData;
	}

	/**
	 * @param responseData
	 *            the responseData to set
	 */
	public void setResponseData(Vector<ResponseUnit> responseData) {
		this.responseData = responseData;
	}

	/**
	 * @return the plumeData
	 */
	public Vector<Plume3DData> getPlumeData() {
		return plumeData;
	}

	/**
	 * @param plumeData
	 *            the plumeData to set
	 */
	public void setPlumeData(Vector<Plume3DData> plumeData) {
		this.plumeData = plumeData;
	}

	public void setNumberBytesForGasParticles(int numberBytesForGasParticles) {
		this.numberBytesForGasParticles = numberBytesForGasParticles;
	}

	public int getNumberBytesForGasParticles() {
		return numberBytesForGasParticles;
	}

	public Vector<GasParticle> getGasParticleData() {
		return gasParticleData;
	}

	public void setGasParticleData(Vector<GasParticle> gasParticleData) {
		this.gasParticleData = gasParticleData;
	}

}

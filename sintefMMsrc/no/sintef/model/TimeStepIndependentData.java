/**
 * 
 */
package no.sintef.model;

import java.util.Vector;


/**
 * @author ubr
 * 
 */
public class TimeStepIndependentData {
	
	/**********************************************************************************************/
	private ComponentData componentdata;
	private SpecieData speciedata;
	private SimulationAndOutputParameter simAOutParams;
	private ReleaseSiteData siteData;
	private Vector<ReleaseData> releaseData;
	private ResponseData responseData;
	/**********************************************************************************************/
	

	
	/**
	 * @param speciedata the speciedata to set
	 */
	public void setSpeciedata(SpecieData speciedata) {
		this.speciedata = speciedata;
	}

	/**
	 * @return the speciedata
	 */
	public SpecieData getSpeciedata() {
		return speciedata;
	}

	/**
	 * @return the relatedSimulationParams
	 */
	public SimulationAndOutputParameter getSimulationParams() {
		return simAOutParams;
	}

	/**
	 * @param relatedSimulationParams
	 *            the relatedSimulationParams to set
	 */
	public void setSimulationParams(SimulationAndOutputParameter relatedSimulationParams) {
		this.simAOutParams = relatedSimulationParams;
	}

	/**
	 * @param componentdata the componentdata to set
	 */
	public void setComponentdata(ComponentData componentdata) {
		this.componentdata = componentdata;
	}

	/**
	 * @return the componentdata
	 */
	public ComponentData getComponentdata() {
		return componentdata;
	}

	/**
	 * @param siteData the siteData to set
	 */
	public void setSiteData(ReleaseSiteData siteData) {
		this.siteData = siteData;
	}

	/**
	 * @return the siteData
	 */
	public ReleaseSiteData getSiteData() {
		return siteData;
	}

	/**
	 * @param releaseDataVector the releaseData to set
	 */
	public void setReleaseData(Vector<ReleaseData> releaseDataVector) {
		this.releaseData = releaseDataVector;
	}

	/**
	 * @return the releaseData
	 */
	public Vector<ReleaseData> getReleaseData() {
		return releaseData;
	}

	/**
	 * @param responseData the responseData to set
	 */
	public void setResponseData(ResponseData responseData) {
		this.responseData = responseData;
	}

	/**
	 * @return the responseData
	 */
	public ResponseData getResponseData() {
		return responseData;
	}

	
}

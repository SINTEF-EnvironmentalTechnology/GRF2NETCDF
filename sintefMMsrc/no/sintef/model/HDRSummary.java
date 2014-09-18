/**
 * 
 */
package no.sintef.model;


/**
 * @author ubr
 *
 */
public class HDRSummary {
	private double averageDensity;
	private double koc;
	private boolean chronicTf;
	private double chronicFactor;
	private int numberComponents;

	/**
	 * @return the averageDensity
	 */
	public double getAverageDensity() {
		return averageDensity;
	}

	/**
	 * @param averageDensity
	 *            the averageDensity to set
	 */
	public void setAverageDensity(double averageDensity) {
		this.averageDensity = averageDensity;
	}

	/**
	 * @return the koc
	 */
	public double getKoc() {
		return koc;
	}

	/**
	 * @param koc
	 *            the koc to set
	 */
	public void setKoc(double koc) {
		this.koc = koc;
	}

	/**
	 * @return the chronicTf
	 */
	public boolean getChronicTf() {
		return chronicTf;
	}

	/**
	 * @param chronicTf
	 *            the chronicTf to set
	 */
	public void setChronicTf(boolean chronicTf) {
		this.chronicTf = chronicTf;
	}

	/**
	 * @return the chronicFactor
	 */
	public double getChronicFactor() {
		return chronicFactor;
	}

	/**
	 * @param chronicFactor
	 *            the chronicFactor to set
	 */
	public void setChronicFactor(double chronicFactor) {
		this.chronicFactor = chronicFactor;
	}

	/**
	 * @return the numberComponents
	 */
	public int getNumberComponents() {
		return numberComponents;
	}

	/**
	 * @param numberComponents
	 *            the numberComponents to set
	 */
	public void setNumberComponents(int numberComponents) {
		this.numberComponents = numberComponents;
	}

}

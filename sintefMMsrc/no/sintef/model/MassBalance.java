/**
 * 
 */
package no.sintef.model;

/**
 * @author ubr
 * 
 */
public class MassBalance {
	private Summary summary;

	public MassBalance(Summary summary) {
		super();
		this.summary = summary;
	}

	/**
	 * @return
	 */
	public double SURFACEOIL() {
		return summary.getWatsrf();
	}

	/**
	 * @return
	 */
	public double TIME() {
		return summary.getDaysSinceStart();
	}

	/**
	 * @return
	 */
	public double EVAPORATED() {
		return summary.getAtmos();
	}

	/**
	 * @return
	 */
	public double SUBMERGED() {

		return summary.getWatclmn();
	}

	/**
	 * @return
	 */
	public double SEDIMENT() {
		return summary.getSumsed();
	}

	/**
	 * @return
	 */
	public double CLEANED() {
		return summary.getSumcln();
	}

	/**
	 * @return
	 */
	public double STRANDED() {
		return summary.getAshor();
	}

	/**
	 * @return
	 */
	public double DECAYED() {
		return summary.getDecayed();
	}

	/**
	 * @return
	 */
	public double OUTSIDE_GRID() {
		return summary.getOutside();
	}

	/**
	 * @return
	 */
	public double TOTAL_MASS() {
		return SURFACEOIL() + EVAPORATED() + SUBMERGED() + SEDIMENT()
				+ CLEANED() + STRANDED() + DECAYED() + OUTSIDE_GRID();
	}

	/**
	 * @return
	 */
	public double SURFACE_AREA() {
		return summary.getSfarea();
	}

}

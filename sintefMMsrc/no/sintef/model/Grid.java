/**
 * 
 */
package no.sintef.model;

/**
 * @author ubr
 * 
 */
public class Grid {
	/* *********************************************************************** */
	private int provinceCode;

	private double orgLatitude; // lat N of the lower left corner (origin) of
	// the physical Fates grid system
	private double orgLongitude; // long E of the lower left corner (origin) of
	// the physical Fates grid system

	private int nx;
	private int ny;

	private int maxSpl;

	private double dtLon; // dtlon(provinceCode, ifrmem), ifrmem = frame number
	// whose gis data is in memory
	private double dtLat; // dtlat(provinceCode, ifrmem)

	private double horizontalDispersionCoefficient; // m**2/day
	private double verticalDispersionCoefficient; // m**2/day

	private int nXTrans; // x dimension of the floating grid
	private int nYTrans; // y dimension of the floating grid
	private int nZTrans; // z dimension of the floating grid

	private double rotatedDegress;
	private double meanTidalHeight; // in m
	private double widthOfSwahZone; // in m

	private double[] beachslope = new double[19]; // array: 19 * float (4byte)
	private int[][] landWaterArray = new int[nx][ny];
	private int[][] habitatArray = new int[nx][ny];
	private int[][] bathymetryArray = new int[nx][ny];

	private int[] thermoclineArray = new int[12];

	private int[] surfaceTemperatureArray = new int[12];

	private int[] subseaTemperatureArray = new int[12];

	/* *********************************************************************** */

	/**
	 * @return the provinceCode
	 */
	public int getProvinceCode() {
		return provinceCode;
	}

	/**
	 * @param provinceCode
	 *            the provinceCode to set
	 */
	public void setProvinceCode(int provinceCode) {
		this.provinceCode = provinceCode;
	}

	/**
	 * @return the orgLatitude
	 */
	public double getOrgLatitude() {
		return orgLatitude;
	}

	/**
	 * @param orgLatitude
	 *            the orgLatitude to set
	 */
	public void setOrgLatitude(double orgLatitude) {
		this.orgLatitude = orgLatitude;
	}

	/**
	 * @return the orgLongitude
	 */
	public double getOrgLongitude() {
		return orgLongitude;
	}

	/**
	 * @param orgLongitude
	 *            the orgLongitude to set
	 */
	public void setOrgLongitude(double orgLongitude) {
		this.orgLongitude = orgLongitude;
	}

	/**
	 * @return the nx
	 */
	public int getNx() {
		return nx;
	}

	/**
	 * @param nx
	 *            the nx to set
	 */
	public void setNx(int nx) {
		this.nx = nx;
	}

	/**
	 * @return the ny
	 */
	public int getNy() {
		return ny;
	}

	/**
	 * @param ny
	 *            the ny to set
	 */
	public void setNy(int ny) {
		this.ny = ny;
	}

	/**
	 * @return the maxSpl
	 */
	public int getMaxSpl() {
		return maxSpl;
	}

	/**
	 * @param maxSpl
	 *            the maxSpl to set
	 */
	public void setMaxSpl(int maxSpl) {
		this.maxSpl = maxSpl;
	}

	/**
	 * @return the dtLon
	 */
	public double getDtLon() {
		return dtLon;
	}

	/**
	 * @param dtLon
	 *            the dtLon to set
	 */
	public void setDtLon(double dtLon) {
		this.dtLon = dtLon;
	}

	/**
	 * @return the dtLat
	 */
	public double getDtLat() {
		return dtLat;
	}

	/**
	 * @param dtLat
	 *            the dtLat to set
	 */
	public void setDtLat(double dtLat) {
		this.dtLat = dtLat;
	}

	/**
	 * @return the horizontalDispersionCoefficient
	 */
	public double getHorizontalDispersionCoefficient() {
		return horizontalDispersionCoefficient;
	}

	/**
	 * @param horizontalDispersionCoefficient
	 *            the horizontalDispersionCoefficient to set
	 */
	public void setHorizontalDispersionCoefficient(
			double horizontalDispersionCoefficient) {
		this.horizontalDispersionCoefficient = horizontalDispersionCoefficient;
	}

	/**
	 * @return the verticalDispersionCoefficient
	 */
	public double getVerticalDispersionCoefficient() {
		return verticalDispersionCoefficient;
	}

	/**
	 * @param verticalDispersionCoefficient
	 *            the verticalDispersionCoefficient to set
	 */
	public void setVerticalDispersionCoefficient(
			double verticalDispersionCoefficient) {
		this.verticalDispersionCoefficient = verticalDispersionCoefficient;
	}

	/**
	 * @return the nXTrans
	 */
	public int getnXTrans() {
		return nXTrans;
	}

	/**
	 * @param nXTrans
	 *            the nXTrans to set
	 */
	public void setnXTrans(int nXTrans) {
		this.nXTrans = nXTrans;
	}

	/**
	 * @return the nYTrans
	 */
	public int getnYTrans() {
		return nYTrans;
	}

	/**
	 * @param nYTrans
	 *            the nYTrans to set
	 */
	public void setnYTrans(int nYTrans) {
		this.nYTrans = nYTrans;
	}

	/**
	 * @return the nZTrans
	 */
	public int getnZTrans() {
		return nZTrans;
	}

	/**
	 * @param nZTrans
	 *            the nZTrans to set
	 */
	public void setnZTrans(int nZTrans) {
		this.nZTrans = nZTrans;
	}

	/**
	 * @return the rotatedDegress
	 */
	public double getRotatedDegress() {
		return rotatedDegress;
	}

	/**
	 * @param rotatedDegress
	 *            the rotatedDegress to set
	 */
	public void setRotatedDegress(double rotatedDegress) {
		this.rotatedDegress = rotatedDegress;
	}

	/**
	 * @return the meanTidalHeight
	 */
	public double getMeanTidalHeight() {
		return meanTidalHeight;
	}

	/**
	 * @param meanTidalHeight
	 *            the meanTidalHeight to set
	 */
	public void setMeanTidalHeight(double meanTidalHeight) {
		this.meanTidalHeight = meanTidalHeight;
	}

	/**
	 * @return the widthOfSwahZone
	 */
	public double getWidthOfSwahZone() {
		return widthOfSwahZone;
	}

	/**
	 * @param widthOfSwahZone
	 *            the widthOfSwahZone to set
	 */
	public void setWidthOfSwahZone(double widthOfSwahZone) {
		this.widthOfSwahZone = widthOfSwahZone;
	}

	/**
	 * @return the beachslope
	 */
	public double[] getBeachslope() {
		return beachslope;
	}

	/**
	 * @param beachslope
	 *            the beachslope to set
	 */
	public void setBeachslope(double[] beachslope) {
		this.beachslope = beachslope;
	}

	/**
	 * @return the landWaterArray
	 */
	public int[][] getLandWaterArray() {
		return landWaterArray;
	}

	/**
	 * @param landWaterArray
	 *            the landWaterArray to set
	 */
	public void setLandWaterArray(int[][] landWaterArray) {
		this.landWaterArray = landWaterArray;
	}

	/**
	 * @return the habitatArray
	 */
	public int[][] getHabitatArray() {
		return habitatArray;
	}

	/**
	 * @param habitatArray
	 *            the habitatArray to set
	 */
	public void setHabitatArray(int[][] habitatArray) {
		this.habitatArray = habitatArray;
	}

	/**
	 * @return the bathymetryArray
	 */
	public int[][] getBathymetryArray() {
		return bathymetryArray;
	}

	/**
	 * @param bathymetryArray
	 *            the bathymetryArray to set
	 */
	public void setBathymetryArray(int[][] bathymetryArray) {
		this.bathymetryArray = bathymetryArray;
	}

	/**
	 * @return the thermoclineArray
	 */
	public int[] getThermoclineArray() {
		return thermoclineArray;
	}

	/**
	 * @param thermoclineArray
	 *            the thermoclineArray to set
	 */
	public void setThermoclineArray(int[] thermoclineArray) {
		this.thermoclineArray = thermoclineArray;
	}

	/**
	 * @return the surfaceTemperatureArray
	 */
	public int[] getSurfaceTemperatureArray() {
		return surfaceTemperatureArray;
	}

	/**
	 * @param surfaceTemperatureArray
	 *            the surfaceTemperatureArray to set
	 */
	public void setSurfaceTemperatureArray(int[] surfaceTemperatureArray) {
		this.surfaceTemperatureArray = surfaceTemperatureArray;
	}

	/**
	 * @return the subseaTemperatureArray
	 */
	public int[] getSubseaTemperatureArray() {
		return subseaTemperatureArray;
	}

	/**
	 * @param subseaTemperatureArray
	 *            the subseaTemperatureArray to set
	 */
	public void setSubseaTemperatureArray(int[] subseaTemperatureArray) {
		this.subseaTemperatureArray = subseaTemperatureArray;
	}

}

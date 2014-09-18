/**
 * 
 */
package no.sintef.model;

import java.io.IOException;

import no.sintef.fates.io.BinaryFileDAO;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class ReleaseSite {

	private double longitude;
	private double latitude;
	private double depth;
	private double bathymetry;
	private double releaseDiameter;
	private double startTime;
	private double duration;
	private short moving;

	public static int SIZE = 7 * BinaryFileDAO.Sizes.DOUBLE_FIELD_SIZE
			+ BinaryFileDAO.Sizes.SHORT_FIELD_SIZE;

	public static class ReleaseSiteDAO {
		/**
		 * Create releaseSite from data read from dataStream
		 * 
		 * @param dataStream
		 * @throws IOException
		 */
		public static ReleaseSite readData(
				LittleEndianDataInputStream dataStream) throws IOException {
			ReleaseSite data = new ReleaseSite();

			data.longitude = dataStream.readDouble();
			data.latitude = dataStream.readDouble();
			data.depth = dataStream.readDouble();
			data.bathymetry = dataStream.readDouble();
			data.releaseDiameter = dataStream.readDouble();
			data.startTime = dataStream.readDouble();
			data.duration = dataStream.readDouble();
			data.moving = dataStream.readShort();

			return data;
		}
	}

	/********************************************************************************/

	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the depth
	 */
	public double getDepth() {
		return depth;
	}

	/**
	 * @param depth
	 *            the depth to set
	 */
	public void setDepth(double depth) {
		this.depth = depth;
	}

	/**
	 * @return the bathymetry
	 */
	public double getBathymetry() {
		return bathymetry;
	}

	/**
	 * @param bathymetry
	 *            the bathymetry to set
	 */
	public void setBathymetry(double bathymetry) {
		this.bathymetry = bathymetry;
	}

	/**
	 * @return the releaseDiameter
	 */
	public double getReleaseDiameter() {
		return releaseDiameter;
	}

	/**
	 * @param releaseDiameter
	 *            the releaseDiameter to set
	 */
	public void setReleaseDiameter(double releaseDiameter) {
		this.releaseDiameter = releaseDiameter;
	}

	/**
	 * @return the startTime
	 */
	public double getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * @param duration
	 *            the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}

	/**
	 * @return the moving
	 */
	public short getMoving() {
		return moving;
	}

	/**
	 * @param moving
	 *            the moving to set
	 */
	public void setMoving(short moving) {
		this.moving = moving;
	}

}

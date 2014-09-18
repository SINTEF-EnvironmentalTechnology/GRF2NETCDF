/**
 * 
 */
package no.sintef.model;

import java.io.IOException;
import java.util.Vector;

import no.sintef.fates.io.BinaryDataDAO;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author  ubr
 */
public class Release extends BinaryDataDAO {
	/**
	 * 
	 */
	private String profile;
	private float releaseRate;
	private Vector<Float> componentConcentrations;

	/**
	 * 
	 * @param dataStream
	 * @param numberComponentsInProfile 
	 * @throws IOException 
	 */
	public Release(LittleEndianDataInputStream dataStream, int numberComponentsInProfile) throws IOException {
		short lengthOfProfileName;

		lengthOfProfileName = dataStream.readShort();
		this.profile = getString(dataStream, lengthOfProfileName);
		this.releaseRate = dataStream.readFloat();
		this.componentConcentrations = new Vector<Float>();
		for (int i = 0; i < numberComponentsInProfile; i++) {
			this.componentConcentrations.add(Float.valueOf(dataStream.readFloat()));
		}

	}

	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
	}

	/**
	 * @return the releaseRate
	 */
	public float getReleaseRate() {
		return releaseRate;
	}

	/**
	 * @param releaseRate the releaseRate to set
	 */
	public void setReleaseRate(float releaseRate) {
		this.releaseRate = releaseRate;
	}

	/**
	 * @return the componentConcentrations
	 */
	public Vector<Float> getComponentConcentrations() {
		return componentConcentrations;
	}

	/**
	 * @param componentConcentrations the componentConcentrations to set
	 */
	public void setComponentConcentrations(Vector<Float> componentConcentrations) {
		this.componentConcentrations = componentConcentrations;
	}
}
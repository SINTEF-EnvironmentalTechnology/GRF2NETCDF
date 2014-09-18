/**
 * 
 */
package no.sintef.model;

import java.io.IOException;
import java.util.Vector;

import no.sintef.fates.io.BinaryDataDAO;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class Specie {

	private String name;
	private Vector<String> stages = new Vector<String>();

	public static class SpecieDAO extends BinaryDataDAO {
	/**
	 * Create Specie with data from dataStream
	 * @param dataStream
	 * @throws IOException
	 */
	public static Specie readData(LittleEndianDataInputStream dataStream) throws IOException {
		int lengthOfSpecieName = dataStream.readShort();
		Specie data = new Specie();
		data.name = getString(dataStream, lengthOfSpecieName);

		int numberOfStages = dataStream.readShort();
		for (int j = 0; j < numberOfStages; j++) {
			String stageName = getString(dataStream, dataStream.readShort());
			data.stages.add(stageName);
		}
		return data;
	}}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the stages
	 */
	public Vector<String> getStages() {
		return stages;
	}

	/**
	 * @param stages
	 *            the stages to set
	 */
	public void setStages(Vector<String> stages) {
		this.stages = stages;
	}

}

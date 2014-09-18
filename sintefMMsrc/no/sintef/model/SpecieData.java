/**
 * 
 */
package no.sintef.model;

import java.io.IOException;
import java.util.Vector;

import no.sintef.model.Specie.SpecieDAO;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class SpecieData {

	private Vector<Specie> species;

	public static class SpecieDataDAO {
		/**
		 * Read SpecieData from dataStream
		 * 
		 * @param dataStream
		 * @throws IOException
		 */
		public static SpecieData readData(LittleEndianDataInputStream dataStream)
				throws IOException {
			SpecieData data = new SpecieData();
			short numberSpecies = dataStream.readShort();
			if (numberSpecies > 0) {
				Vector<Specie> species = new Vector<Specie>();

				for (int i = 0; i < numberSpecies; i++) {
					species.add(SpecieDAO.readData(dataStream));
				}
				data.species = species;
			}
			return data;

		}
	}

	/**********************************************************************************************/

	/**
	 * @return the species
	 */
	public Vector<Specie> getSpecies() {
		return species;
	}

	/**
	 * @param species
	 *            the species to set
	 */
	public void setSpecies(Vector<Specie> species) {
		this.species = species;
	}

}

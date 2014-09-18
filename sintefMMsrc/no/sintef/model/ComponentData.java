/**
 * 
 */
package no.sintef.model;

import java.io.IOException;
import java.util.Vector;

import com.google.common.io.LittleEndianDataInputStream;

import no.sintef.fates.io.BinaryFileDAO.Sizes;
import no.sintef.fates.io.files.GRF;

/**
 * @author ubr
 * 
 */
public class ComponentData {
	/**********************************************************************************************/
	private int lengthOfComponentName;
	private int totalNumberOfComponents;
	private int numberOfOilComponents;
	private int numberOfChemComponents;
	private int numberOfParticleMaterialComponents;
	private Vector<Component> components = new Vector<Component>();
	private int numberOfGasComponents;

	/**
	 * Determine size to store (read) component data
	 * 
	 * @param fileversion
	 * @return
	 */
	public static short getMetaDataSize(short fileversion) {
		if (fileversion >= GRF.GRFFILEGASVERSION) { // gas particles from
													// version 23
			return 6 * Sizes.INT_FIELD_SIZE;
		} else {
			return 5 * Sizes.INT_FIELD_SIZE;
		}
	}

	public static class ComponentDataDAO {
		public static ComponentData readData(
				LittleEndianDataInputStream dataStream, short fileversion)
				throws IOException {
			ComponentData data = new ComponentData();
			data.lengthOfComponentName = dataStream.readInt();
			data.totalNumberOfComponents = dataStream.readInt();
			data.numberOfOilComponents = dataStream.readInt();
			data.numberOfChemComponents = dataStream.readInt();
			data.numberOfParticleMaterialComponents = dataStream.readInt();
			// gas particles from version 23
			if (fileversion >= GRF.GRFFILEGASVERSION) {
				data.numberOfGasComponents = dataStream.readInt(); // number gas
																	// components
			}

			byte[] componentName = new byte[data.lengthOfComponentName];
			for (int i = 0; i < data.totalNumberOfComponents; i++) {
				dataStream.read(componentName);
				data.components.add(new Component(new String(componentName),
						dataStream.readFloat() /* density */, dataStream
								.readFloat() /* background concentration */,
						dataStream.readShort() /* is degradable */
				));
			}
			return data;
		}
	}

	/**********************************************************************************************/

	/**
	 * @return the components
	 */
	public Vector<Component> getComponents() {
		return components;
	}

	/**
	 * @param components
	 *            the components to set
	 */
	public void setComponents(Vector<Component> components) {
		this.components = components;
	}

	/**
	 * @return the lengthOfComponentName
	 */
	public int getLengthOfComponentName() {
		return lengthOfComponentName;
	}

	/**
	 * @param lengthOfComponentName
	 *            the lengthOfComponentName to set
	 */
	public void setLengthOfComponentName(int lengthOfComponentName) {
		this.lengthOfComponentName = lengthOfComponentName;
	}

	/**
	 * @return the totalNumberOfComponents
	 */
	public int getTotalNumberOfComponents() {
		return totalNumberOfComponents;
	}

	/**
	 * @param totalNumberOfComponents
	 *            the totalNumberOfComponents to set
	 */
	public void setTotalNumberOfComponents(int totalNumberOfComponents) {
		this.totalNumberOfComponents = totalNumberOfComponents;
	}

	/**
	 * @return the numberOfOilComponents
	 */
	public int getNumberOfOilComponents() {
		return numberOfOilComponents;
	}

	/**
	 * @param numberOfOilComponents
	 *            the numberOfOilComponents to set
	 */
	public void setNumberOfOilComponents(int numberOfOilComponents) {
		this.numberOfOilComponents = numberOfOilComponents;
	}

	/**
	 * @return the numberOfChemComponents
	 */
	public int getNumberOfChemComponents() {
		return numberOfChemComponents;
	}

	/**
	 * @param numberOfChemComponents
	 *            the numberOfChemComponents to set
	 */
	public void setNumberOfChemComponents(int numberOfChemComponents) {
		this.numberOfChemComponents = numberOfChemComponents;
	}

	/**
	 * @return the numberOfParticleMaterialComponents
	 */
	public int getNumberOfParticleMaterialComponents() {
		return numberOfParticleMaterialComponents;
	}

	/**
	 * @param numberOfParticleMaterialComponents
	 *            the numberOfParticleMaterialComponents to set
	 */
	public void setNumberOfParticleMaterialComponents(
			int numberOfParticleMaterialComponents) {
		this.numberOfParticleMaterialComponents = numberOfParticleMaterialComponents;
	}

	public void setNumberOfGasComponents(int numberGasComponents) {
		this.numberOfGasComponents = numberGasComponents;
	}

	public int getNumberOfGasComponents() {
		return numberOfGasComponents;
	}

}

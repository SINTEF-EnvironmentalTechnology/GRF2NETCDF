/**
 * 
 */
package no.sintef.model;

import java.io.IOException;
import java.util.Vector;

import no.sintef.fates.io.BinaryFileDAO;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class SubsurfaceGridDimensions {

	// Array of grid cell heights for each layer in [m],
	// cellHeightPerLayer.size() = numberOfLayers
	private Vector<Float> cellHeightPerLayer;

	// Array of WatConc record counts for each layer, including mean & max
	// layer, cellHeightPerLayer.size() = numberOfLayers + 2
	private Vector<Integer> numberWatConcCellsPerLayer;

	public static int getSize(short numberOfLayers) {
		return numberOfLayers * BinaryFileDAO.Sizes.FLOAT_FIELD_SIZE
				+ (numberOfLayers + 2) * BinaryFileDAO.Sizes.INT_FIELD_SIZE;
	}

	public int getTotalNumberWatConcCells() {
		int maxWaterConc = 0;
		if (numberWatConcCellsPerLayer != null) {
			for (int i = 0; i < numberWatConcCellsPerLayer.size(); i++) {

				maxWaterConc = maxWaterConc + numberWatConcCellsPerLayer.get(i);
			}
			return maxWaterConc;
		} else {
			return 0;
		}

	}

	/**
	 * 
	 * @author ubr
	 * 
	 */
	public static class SubsurfaceGridDimensionDAO {
		/**
		 * 
		 * @param datastream
		 * @param numberOfLayers
		 * @return
		 * @throws IOException
		 */
		public static SubsurfaceGridDimensions readData(
				LittleEndianDataInputStream datastream, short numberOfLayers)
				throws IOException {
			SubsurfaceGridDimensions data = new SubsurfaceGridDimensions();
			data.cellHeightPerLayer = new Vector<Float>();
			data.numberWatConcCellsPerLayer = new Vector<Integer>();

			for (short i = 0; i < numberOfLayers; i++) {
				data.cellHeightPerLayer.add(datastream.readFloat());
			}

			// additional mean and max layer
			for (short i = 0; i < numberOfLayers + 2; i++) {
				data.numberWatConcCellsPerLayer.add(datastream.readInt());
			}

			return data;

		}
	}

	@Deprecated
	public boolean isData() {
		int maxWaterConc = 0;
		Vector<Integer> numberWatConcCells = getNumberWatConcCellsPerLayer();
		for (int i = 0; i < numberWatConcCells.size(); i++) {

			maxWaterConc = Math.max(maxWaterConc, numberWatConcCells.get(i));
		}
		return maxWaterConc > 0;
	}

	/**
	 * @return the cellHeightPerLayer
	 */
	public Vector<Float> getCellHeightPerLayer() {
		return cellHeightPerLayer;
	}

	/**
	 * @param cellHeightPerLayer
	 *            the cellHeightPerLayer to set
	 */
	public void setCellHeightPerLayer(Vector<Float> cellHeightPerLayer) {
		this.cellHeightPerLayer = cellHeightPerLayer;
	}

	/**
	 * @return the numberWatConcCellsPerLayer
	 */
	public Vector<Integer> getNumberWatConcCellsPerLayer() {
		return numberWatConcCellsPerLayer;
	}

	/**
	 * @param numberWatConcCellsPerLayer2
	 *            the numberWatConcCellsPerLayer to set
	 */
	public void setNumberWatConcCellsPerLayer(
			Vector<Integer> numberWatConcCellsPerLayer2) {
		this.numberWatConcCellsPerLayer = numberWatConcCellsPerLayer2;
	}

}

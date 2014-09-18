package no.sintef.model;

import java.util.Vector;
	
	public class ShoreGrid  {

		private short gridDimensionX;
		private short gridDimensionY;
		private int numberCells;
		private Vector<ShoreCellData> cells; 

		public short getGridDimensionX() {
			return gridDimensionX;
		}

		public short getGridDimensionY() {
			return gridDimensionY;
		}


		public void setGridDimensionX(short xDim) {
			this.gridDimensionX = xDim;
		}

		public void setGridDimensionY(short yDim) {
			this.gridDimensionY = yDim;
		}

		/**
		 * @param numberCells the numberCells to set
		 */
		public void setNumberCells(int numberCells) {
			this.numberCells = numberCells;
		}

		/**
		 * @return the numberCells
		 */
		public int getNumberCells() {
			return numberCells;
		}

		/**
		 * @param cells the cells to set
		 */
		public void setCells(Vector<ShoreCellData> cells) {
			this.cells = cells;
		}

		/**
		 * @return the cells
		 */
		public Vector<ShoreCellData> getCells() {
			return cells;
		}
	}


/**
 * 
 */
package no.sintef.model;

import java.util.Vector;


/**
 * @author ubr
 *
 */
public class SedimentData {
	
	private SedimentCell cell;
	/** Oil components */
	private ComponentValues oilComponentMasses = new ComponentValues();
	
	/** Particle material components */
	private float particleMatValue;
	private ComponentValues partMatComponentMasses = new ComponentValues();
	
	/** Sediment risk stressors */
	private float thickness, oxygen, gridsize, oilCompFraction, partMatFraction;
	private float sedoxuse[]; // float sedoxuse[2];
	
	/** Sediment pore data */
	private float poreValue;
	private ComponentValues[] poreComponentValues =  new ComponentValues[2]; 


	
	/**
	 * @param cell the cell to set
	 */
	public void setCell(SedimentCell cell) {
		this.cell = cell;
	}
	/**
	 * @return the cell
	 */
	public SedimentCell getCell() {
		return cell;
	}
	/**
	 * @return the bitmap
	 */
	public byte[] getOilCompBitmap() {
		return this.oilComponentMasses.getBitmap();
	}

	/**
	 * @param bitmap the bitmap to set
	 */
	public void setOilCompBitmap(byte[] bitmap) {
		this.oilComponentMasses.setBitmap(bitmap);
	}

	/**
	 * @return the concentrations
	 */
	public Vector<Float> getOilCompMasses() {
		return this.oilComponentMasses.getValues();
	}

	/**
	 * @param masses the masses to set
	 */
	public void setOilCompMasses(Vector<Float> masses) {
		this.oilComponentMasses.setValues(masses);
	}
	
	/**
	 * @return the masses as short values
	 */
	public Vector<Short> getMassesAsShort() {
		return this.oilComponentMasses.getValuesAsShort();
	}
	
	/**
	 * @return the bitmap
	 */
	public byte[] getPartMatCompBitmap() {
		return this.partMatComponentMasses.getBitmap();
	}

	/**
	 * @param bitmap the bitmap to set
	 */
	public void setPartMatCompBitmap(byte[] bitmap) {
		this.partMatComponentMasses.setBitmap(bitmap);
	}

	/**
	 * @return the concentrations
	 */
	public Vector<Float> getPartMatCompMasses() {
		return this.partMatComponentMasses.getValues();
	}

	/**
	 * @param masses the masses to set
	 */
	public void setPartMatCompMasses(Vector<Float> masses) {
		this.partMatComponentMasses.setValues(masses);
	}
	
	/**
	 * @return the concentrations as short values
	 */
	public Vector<Short> getPartMatMassesAsShort() {
		return this.partMatComponentMasses.getValuesAsShort();
	}
	/**
	 * @return the oilComponentMasses
	 */
	public ComponentValues getOilComponentMasses() {
		return oilComponentMasses;
	}
	/**
	 * @param oilComponentMasses the oilComponentMasses to set
	 */
	public void setOilComponentMasses(ComponentValues oilComponentMasses) {
		this.oilComponentMasses = oilComponentMasses;
	}
	/**
	 * @param particleMatValue the particleMatValue to set
	 */
	public void setParticleMatValue(float particleMatValue) {
		this.particleMatValue = particleMatValue;
	}
	/**
	 * @return the particleMatValue
	 */
	public float getParticleMatValue() {
		return particleMatValue;
	}
	/**
	 * @return the partMatComponentMasses
	 */
	public ComponentValues getPartMatComponentMasses() {
		return partMatComponentMasses;
	}
	/**
	 * @param partMatComponentMasses the partMatComponentMasses to set
	 */
	public void setPartMatComponentMasses(ComponentValues partMatComponentMasses) {
		this.partMatComponentMasses = partMatComponentMasses;
	}
	/**
	 * @param thickness the thickness to set
	 */
	public void setThickness(float thickness) {
		this.thickness = thickness;
	}
	/**
	 * @return the thickness
	 */
	public float getThickness() {
		return thickness;
	}
	/**
	 * @return the oxygen
	 */
	public float getOxygen() {
		return oxygen;
	}
	/**
	 * @param oxygen the oxygen to set
	 */
	public void setOxygen(float oxygen) {
		this.oxygen = oxygen;
	}
	/**
	 * @return the gridsize
	 */
	public float getGridsize() {
		return gridsize;
	}
	/**
	 * @param gridsize the gridsize to set
	 */
	public void setGridsize(float gridsize) {
		this.gridsize = gridsize;
	}
	/**
	 * @return the oilCompFraction
	 */
	public float getOilCompFraction() {
		return oilCompFraction;
	}
	/**
	 * @param oilCompFraction the oilCompFraction to set
	 */
	public void setOilCompFraction(float oilCompFraction) {
		this.oilCompFraction = oilCompFraction;
	}
	/**
	 * @return the partMatFraction
	 */
	public float getPartMatFraction() {
		return partMatFraction;
	}
	/**
	 * @param partMatFraction the partMatFraction to set
	 */
	public void setPartMatFraction(float partMatFraction) {
		this.partMatFraction = partMatFraction;
	}
	/**
	 * @return the sedoxuse
	 */
	public float[] getSedoxuse() {
		return sedoxuse;
	}
	/**
	 * @param sedoxuse the sedoxuse to set
	 */
	public void setSedoxuse(float[] sedoxuse) {
		this.sedoxuse = sedoxuse;
	}
	
	/**
	 * @param poreValue the poreValue to set
	 */
	public void setPoreValue(float poreValue) {
		this.poreValue = poreValue;
	}
	/**
	 * @return the poreValue
	 */
	public float getPoreValue() {
		return poreValue;
	}
	
	/**
	 * @return the bitmap
	 */
	public byte[] getPoreCompBitmap(int index) {
		return this.poreComponentValues[index].getBitmap();
	}

	/**
	 * @param bitmap the bitmap to set
	 */
	public void setPoreCompBitmap(byte[] bitmap, int index) {
		this.poreComponentValues[index].setBitmap(bitmap);
	}

	/**
	 * @return the concentrations
	 */
	public Vector<Float> getPoreCompMasses(int index) {
		return this.poreComponentValues[index].getValues();
	}

	/**
	 * @param masses the masses to set
	 */
	public void setPoreCompValues(Vector<Float> masses, int index) {
		this.poreComponentValues[index].setValues(masses);
	}
	
	/**
	 * @return the concentrations as short values
	 */
	public Vector<Short> getPoreMassesAsShort(int index) {
		return this.poreComponentValues[index].getValuesAsShort();
	}
	/**
	 * @param poreComponentValues the poreComponentValues to set
	 */
	public void setPoreComponentValues(ComponentValues poreComponentValues, int index) {
		this.poreComponentValues[index] = poreComponentValues;
	}
	/**
	 * @return the poreComponentValues
	 */
	public ComponentValues getPoreComponentValues(int index) {
		return poreComponentValues[index];
	}
	
	
	
	
	
	
}

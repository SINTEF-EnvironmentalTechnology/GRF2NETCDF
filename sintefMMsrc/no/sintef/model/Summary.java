/**
 * Summary of all simulation results per time step. This data set contains important meta data information for the simulation results.
 */
package no.sintef.model;

import java.io.IOException;

import no.sintef.fates.io.BinaryFileDAO.Sizes;
import no.sintef.fates.io.files.GRF;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 */
public class Summary {

	/********************************************************************************/

	private int julday; // number of day in year
	private int numSpilletsSubmerged; // number particles in water column
	private int numSpilletsThin; // number particles on surface building a thin
									// slick
	private int numSpilletsMedium; // number of particles on surface building a
									// thin slick
	private int numSpilletsThick; // number of particles on surface building a
									// thin slick
	private int numSpilletsTotal; // number of non-dissolved particles in total
	private int numParticlesNow; // number of dissolved particles
	private int numOrganisms; // number organisms
	private int numGasBubbles; // number of gas bubble particles
	private int numCellsShore; // number cells with stranded oil
								// "ashore/beached/stranded"
	private int numCellsSediment; // number cells with oil in sediment
	private int numResponseItems; // number response units
	private int ncellssurf; // number of cells with oil on water surface

	private float time; // days since simulation start
	private float uw;
	private float vw;
	private float seasurface; // amount of oil on the surface [t]
	private float waterColumn; // amount of oil in the water column
								// (dissolved)[t]
	private float submerged; // amount of oil in the water column (droplets) [t]
	private float evaporated; // amount of oil evaproated from surface [t]
	private float decayed; // amount of oil biologically decayed [t]
	private float sumWatercolumn; // amount of oil in the water column
									// (dissolved + droplets)[t]
	private float q_skimmed; // amount of oil skimmed
	private float q_dispersed; // amount of oil chem. dipersed
	private float q_sburned; // amount of oil burned
	private float q_bburned; // amount of oil burned
	private float ashore; // amount of oil ashore
	private float totalMass; // amount of oil total
	private float outsideGrid; // amount of oil outside the simulation grid
	private float surfaceArea;
	private float thc; // not used anymore
	private float xmin_conc; // xMin in sub-surface concentration grid
	private float ymin_conc; // yMin in sub-surface concentration grid
	private float zmin;
	private float dx_conc;
	private float dy_conc;
	private float xmin_surf;
	private float ymin_surf;
	private float dx_surf;
	private float dy_surf;

	private short nX_conc;
	private short nY_conc;
	private short nZ_conc;
	private short nX_surf;
	private short nY_surf;

	private boolean hasPlume;
	private float Xmin_gas;
	private float Ymin_gas;
	private float Dx_gas;
	private float Dy_gas;
	private short nX_gas;
	private short nY_gas;
	private int Ncellsgas;

	public static int getSize(int fileversion) {
		if (fileversion >= GRF.GRFFILEGASVERSION) { // gas bubbles from
			// version 23
			return (31 * Sizes.FLOAT_FIELD_SIZE + 14 * Sizes.INT_FIELD_SIZE + 7
					* Sizes.SHORT_FIELD_SIZE + Sizes.BOOLEAN_FIELD_SIZE);
		} else {
			return (27 * Sizes.FLOAT_FIELD_SIZE + 12 * Sizes.INT_FIELD_SIZE + 5
					* Sizes.SHORT_FIELD_SIZE + Sizes.BOOLEAN_FIELD_SIZE);
		}
	}

	public static class SummaryDAO {
		public static Summary readData(LittleEndianDataInputStream datastream,
				int fileversion) throws IOException {
			Summary summary = new Summary();
			float daysSinceStart = datastream.readFloat();
			System.out.println("Reading data for day " + daysSinceStart);

			summary.setDaysSinceStart(daysSinceStart);
			summary.setJulday(datastream.readInt());
			summary.setUw(datastream.readFloat());
			summary.setVw(datastream.readFloat());
			summary.setWatsrf(datastream.readFloat());
			summary.setWatclmn(datastream.readFloat());
			summary.setSumsed(datastream.readFloat());
			summary.setAtmos(datastream.readFloat());
			summary.setDecay(datastream.readFloat());
			summary.setSumcln(datastream.readFloat());
			summary.setQ_skimmed(datastream.readFloat());
			summary.setQ_dispersed(datastream.readFloat());
			summary.setQ_sburned(datastream.readFloat());
			summary.setQ_bburned(datastream.readFloat());
			summary.setAshor(datastream.readFloat());
			summary.setTotmas(datastream.readFloat());
			summary.setOutsid(datastream.readFloat());
			summary.setSfarea(datastream.readFloat());
			summary.setNspilsubm(datastream.readInt());
			summary.setNspilthin(datastream.readInt());
			summary.setNspilmed(datastream.readInt());
			summary.setNspilthick(datastream.readInt());
			summary.setThc(datastream.readFloat());
			summary.setNspiltot(datastream.readInt());
			summary.setNptnow(datastream.readInt());
			summary.setNorgnsm(datastream.readInt());
			if (fileversion >= GRF.GRFFILEGASVERSION) { // gas bubbles from
														// version 23
				summary.setNumGasBubbles(datastream.readInt());
			}
			summary.setNcellsshore(datastream.readInt());
			summary.setNcellssed(datastream.readInt());
			summary.setNvess_active(datastream.readInt());
			summary.setXmin_conc(datastream.readFloat());
			summary.setYmin_conc(datastream.readFloat());
			summary.setZmin(datastream.readFloat());
			summary.setDx_conc(datastream.readFloat());
			summary.setDy_conc(datastream.readFloat());
			summary.setnX_conc(datastream.readShort());
			summary.setnY_conc(datastream.readShort());
			summary.setnZ_conc(datastream.readShort());
			summary.setXmin_surf(datastream.readFloat());
			summary.setYmin_surf(datastream.readFloat());
			summary.setDx_surf(datastream.readFloat());
			summary.setDy_surf(datastream.readFloat());
			summary.setnX_surf(datastream.readShort());
			summary.setnY_surf(datastream.readShort());
			summary.setNcellssurf(datastream.readInt());
			if (fileversion >= GRF.GRFFILEGASVERSION) { // gas surface grids from
														// version 23
				summary.setXmin_gas(datastream.readFloat());
				summary.setYmin_gas(datastream.readFloat());
				summary.setDx_gas(datastream.readFloat());
				summary.setDy_gas(datastream.readFloat());
				summary.setnX_gas(datastream.readShort());
				summary.setnY_gas(datastream.readShort());
				summary.setNcellsgas(datastream.readInt());
			}
			summary.setHasPlume(datastream.read() != 0);
			return summary;

		}
	}

	/********************************************************************************/

	/**
	 * @return the time
	 */
	public float getDaysSinceStart() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setDaysSinceStart(float time) {
		this.time = time;
	}

	/**
	 * @return the julday
	 */
	public int getJulday() {
		return julday;
	}

	/**
	 * @param julday
	 *            the julday to set
	 */
	public void setJulday(int julday) {
		this.julday = julday;
	}

	/**
	 * @return the uw
	 */
	public float getUw() {
		return uw;
	}

	/**
	 * @param uw
	 *            the uw to set
	 */
	public void setUw(float uw) {
		this.uw = uw;
	}

	/**
	 * @return the vw
	 */
	public float getVw() {
		return vw;
	}

	/**
	 * @param vw
	 *            the vw to set
	 */
	public void setVw(float vw) {
		this.vw = vw;
	}

	/**
	 * @return the watsrf
	 */
	public float getWatsrf() {
		return seasurface;
	}

	/**
	 * @param watsrf
	 *            the watsrf to set
	 */
	public void setWatsrf(float watsrf) {
		this.seasurface = watsrf;
	}

	/**
	 * @return the watclmn
	 */
	public float getWatclmn() {
		return waterColumn;
	}

	/**
	 * @param watclmn
	 *            the watclmn to set
	 */
	public void setWatclmn(float watclmn) {
		this.waterColumn = watclmn;
	}

	/**
	 * @return the sumsed
	 */
	public float getSumsed() {
		return submerged;
	}

	/**
	 * @param sumsed
	 *            the sumsed to set
	 */
	public void setSumsed(float sumsed) {
		this.submerged = sumsed;
	}

	/**
	 * @return the atmos
	 */
	public float getAtmos() {
		return evaporated;
	}

	/**
	 * @param atmos
	 *            the atmos to set
	 */
	public void setAtmos(float atmos) {
		this.evaporated = atmos;
	}

	/**
	 * @return the decay
	 */
	public float getDecayed() {
		return decayed;
	}

	/**
	 * @param decay
	 *            the decay to set
	 */
	public void setDecay(float decay) {
		this.decayed = decay;
	}

	/**
	 * @return the sumcln
	 */
	public float getSumcln() {
		return sumWatercolumn;
	}

	/**
	 * @param sumcln
	 *            the sumcln to set
	 */
	public void setSumcln(float sumcln) {
		this.sumWatercolumn = sumcln;
	}

	/**
	 * @return the q_skimmed
	 */
	public float getQ_skimmed() {
		return q_skimmed;
	}

	/**
	 * @param qSkimmed
	 *            the q_skimmed to set
	 */
	public void setQ_skimmed(float qSkimmed) {
		q_skimmed = qSkimmed;
	}

	/**
	 * @return the q_dispersed
	 */
	public float getQ_dispersed() {
		return q_dispersed;
	}

	/**
	 * @param qDispersed
	 *            the q_dispersed to set
	 */
	public void setQ_dispersed(float qDispersed) {
		q_dispersed = qDispersed;
	}

	/**
	 * @return the q_sburned
	 */
	public float getQ_sburned() {
		return q_sburned;
	}

	/**
	 * @param qSburned
	 *            the q_sburned to set
	 */
	public void setQ_sburned(float qSburned) {
		q_sburned = qSburned;
	}

	/**
	 * @return the q_bburned
	 */
	public float getQ_bburned() {
		return q_bburned;
	}

	/**
	 * @param qBburned
	 *            the q_bburned to set
	 */
	public void setQ_bburned(float qBburned) {
		q_bburned = qBburned;
	}

	/**
	 * @return the ashor
	 */
	public float getAshor() {
		return ashore;
	}

	/**
	 * @param ashor
	 *            the ashor to set
	 */
	public void setAshor(float ashor) {
		this.ashore = ashor;
	}

	/**
	 * @return the totmas
	 */
	public float getTotmas() {
		return totalMass;
	}

	/**
	 * @param totmas
	 *            the totmas to set
	 */
	public void setTotmas(float totmas) {
		this.totalMass = totmas;
	}

	/**
	 * @return the outsid
	 */
	public float getOutside() {
		return outsideGrid;
	}

	/**
	 * @param outsid
	 *            the outsid to set
	 */
	public void setOutsid(float outsid) {
		this.outsideGrid = outsid;
	}

	/**
	 * @return the sfarea
	 */
	public float getSfarea() {
		return surfaceArea;
	}

	/**
	 * @param sfarea
	 *            the sfarea to set
	 */
	public void setSfarea(float sfarea) {
		this.surfaceArea = sfarea;
	}

	/**
	 * @return the nspilsubm
	 */
	public int getNspilsubm() {
		return numSpilletsSubmerged;
	}

	/**
	 * @param nspilsubm
	 *            the nspilsubm to set
	 */
	public void setNspilsubm(int nspilsubm) {
		this.numSpilletsSubmerged = nspilsubm;
	}

	/**
	 * @return the nspilthin
	 */
	public int getNspilthin() {
		return numSpilletsThin;
	}

	/**
	 * @param nspilthin
	 *            the nspilthin to set
	 */
	public void setNspilthin(int nspilthin) {
		this.numSpilletsThin = nspilthin;
	}

	/**
	 * @return the nspilmed
	 */
	public int getNspilmed() {
		return numSpilletsMedium;
	}

	/**
	 * @param nspilmed
	 *            the nspilmed to set
	 */
	public void setNspilmed(int nspilmed) {
		this.numSpilletsMedium = nspilmed;
	}

	/**
	 * @return the nspilthick
	 */
	public int getNspilthick() {
		return numSpilletsThick;
	}

	/**
	 * @param nspilthick
	 *            the nspilthick to set
	 */
	public void setNspilthick(int nspilthick) {
		this.numSpilletsThick = nspilthick;
	}

	/**
	 * @return the thc
	 */
	public float getThc() {
		return thc;
	}

	/**
	 * @param thc
	 *            the thc to set
	 */
	public void setThc(float thc) {
		this.thc = thc;
	}

	/**
	 * @return the nspiltot
	 */
	public int getNspiltot() {
		return numSpilletsTotal;
	}

	/**
	 * @param nspiltot
	 *            the nspiltot to set
	 */
	public void setNspiltot(int nspiltot) {
		this.numSpilletsTotal = nspiltot;
	}

	/**
	 * @return the nptnow
	 */
	public int getNptnow() {
		return numParticlesNow;
	}

	/**
	 * @param nptnow
	 *            the nptnow to set
	 */
	public void setNptnow(int nptnow) {
		this.numParticlesNow = nptnow;
	}

	/**
	 * @return the norgnsm
	 */
	public int getNorgnsm() {
		return numOrganisms;
	}

	/**
	 * @param norgnsm
	 *            the norgnsm to set
	 */
	public void setNorgnsm(int norgnsm) {
		this.numOrganisms = norgnsm;
	}

	/**
	 * @return the ncellsshore
	 */
	public int getNcellsshore() {
		return numCellsShore;
	}

	/**
	 * @param ncellsshore
	 *            the ncellsshore to set
	 */
	public void setNcellsshore(int ncellsshore) {
		this.numCellsShore = ncellsshore;
	}

	/**
	 * @return the ncellssed
	 */
	public int getNumberCellsSediment() {
		return numCellsSediment;
	}

	/**
	 * @param ncellssed
	 *            the ncellssed to set
	 */
	public void setNcellssed(int ncellssed) {
		this.numCellsSediment = ncellssed;
	}

	/**
	 * @return the nvess_active
	 */
	public int getNvess_active() {
		return numResponseItems;
	}

	/**
	 * @param nvessActive
	 *            the nvess_active to set
	 */
	public void setNvess_active(int nvessActive) {
		numResponseItems = nvessActive;
	}

	/**
	 * @return the xmin_conc
	 */
	public float getXmin_conc() {
		return xmin_conc;
	}

	/**
	 * @param xminConc
	 *            the xmin_conc to set
	 */
	public void setXmin_conc(float xminConc) {
		xmin_conc = xminConc;
	}

	/**
	 * @return the ymin_conc
	 */
	public float getYmin_conc() {
		return ymin_conc;
	}

	/**
	 * @param yminConc
	 *            the ymin_conc to set
	 */
	public void setYmin_conc(float yminConc) {
		ymin_conc = yminConc;
	}

	/**
	 * @return the zmin
	 */
	public float getZmin() {
		return zmin;
	}

	/**
	 * @param zmin
	 *            the zmin to set
	 */
	public void setZmin(float zmin) {
		this.zmin = zmin;
	}

	/**
	 * @return the dx_conc
	 */
	public float getDx_conc() {
		return dx_conc;
	}

	/**
	 * @param dxConc
	 *            the dx_conc to set
	 */
	public void setDx_conc(float dxConc) {
		dx_conc = dxConc;
	}

	/**
	 * @return the dy_conc
	 */
	public float getDy_conc() {
		return dy_conc;
	}

	/**
	 * @param dyConc
	 *            the dy_conc to set
	 */
	public void setDy_conc(float dyConc) {
		dy_conc = dyConc;
	}

	/**
	 * @return the nX_conc
	 */
	public short getnX_conc() {
		return nX_conc;
	}

	/**
	 * @param nXConc
	 *            the nX_conc to set
	 */
	public void setnX_conc(short nXConc) {
		nX_conc = nXConc;
	}

	/**
	 * @return the nY_conc
	 */
	public short getnY_conc() {
		return nY_conc;
	}

	/**
	 * @param nYConc
	 *            the nY_conc to set
	 */
	public void setnY_conc(short nYConc) {
		nY_conc = nYConc;
	}

	/**
	 * @return the nZ_conc
	 */
	public short getnZ_conc() {
		return nZ_conc;
	}

	/**
	 * @param nZConc
	 *            the nZ_conc to set
	 */
	public void setnZ_conc(short nZConc) {
		nZ_conc = nZConc;
	}

	/**
	 * @return the xmin_surf
	 */
	public float getXmin_surf() {
		return xmin_surf;
	}

	/**
	 * @param xminSurf
	 *            the xmin_surf to set
	 */
	public void setXmin_surf(float xminSurf) {
		xmin_surf = xminSurf;
	}

	/**
	 * @return the ymin_surf
	 */
	public float getYmin_surf() {
		return ymin_surf;
	}

	/**
	 * @param yminSurf
	 *            the ymin_surf to set
	 */
	public void setYmin_surf(float yminSurf) {
		ymin_surf = yminSurf;
	}

	/**
	 * @return the dx_surf
	 */
	public float getDx_surf() {
		return dx_surf;
	}

	/**
	 * @param dxSurf
	 *            the dx_surf to set
	 */
	public void setDx_surf(float dxSurf) {
		dx_surf = dxSurf;
	}

	/**
	 * @return the dy_surf
	 */
	public float getDy_surf() {
		return dy_surf;
	}

	/**
	 * @param dySurf
	 *            the dy_surf to set
	 */
	public void setDy_surf(float dySurf) {
		dy_surf = dySurf;
	}

	/**
	 * @return the nX_surf
	 */
	public short getnX_surf() {
		return nX_surf;
	}

	/**
	 * @param nXSurf
	 *            the nX_surf to set
	 */
	public void setnX_surf(short nXSurf) {
		nX_surf = nXSurf;
	}

	/**
	 * @return the nY_surf
	 */
	public short getnY_surf() {
		return nY_surf;
	}

	/**
	 * @param nYSurf
	 *            the nY_surf to set
	 */
	public void setnY_surf(short nYSurf) {
		nY_surf = nYSurf;
	}

	/**
	 * @return the ncellssurf
	 */
	public int getNcellssurf() {
		return ncellssurf;
	}

	/**
	 * @param ncellssurf
	 *            the ncellssurf to set
	 */
	public void setNcellssurf(int ncellssurf) {
		this.ncellssurf = ncellssurf;
	}

	/**
	 * @return the hasPlume
	 */
	public boolean hasPlume() {
		return hasPlume;
	}

	/**
	 * @param hasPlume
	 *            the hasPlume to set
	 */
	public void setHasPlume(boolean hasPlume) {
		this.hasPlume = hasPlume;
	}

	/**
	 * @return the ngasbubbles
	 */
	public int getNumGasBubbles() {
		return numGasBubbles;
	}

	public void setNumGasBubbles(int numberGasBubbles) {
		this.numGasBubbles = numberGasBubbles;

	}

	public void setXmin_gas(float Xmin_gas) {
		this.Xmin_gas = Xmin_gas;
	}

	public void setYmin_gas(float Ymin_gas) {
		this.Ymin_gas = Ymin_gas;
	}

	public void setDx_gas(float Dx_gas) {
		this.Dx_gas = Dx_gas;
	}

	public void setDy_gas(float Dy_gas) {
		this.Dy_gas = Dy_gas;
	}

	public void setnX_gas(short nX_gas) {
		this.nX_gas = nX_gas;
	}

	public void setnY_gas(short nY_gas) {
		this.nY_gas = nY_gas;
	}

	public void setNcellsgas(int Ncellsgas) {
		this.Ncellsgas = Ncellsgas;
	}

	/**
	 * @return the xmin_gas
	 */
	public float getXmin_gas() {
		return Xmin_gas;
	}

	/**
	 * @return the ymin_gas
	 */
	public float getYmin_gas() {
		return Ymin_gas;
	}

	/**
	 * @return the dx_gas
	 */
	public float getDx_gas() {
		return Dx_gas;
	}

	/**
	 * @return the dy_gas
	 */
	public float getDy_gas() {
		return Dy_gas;
	}

	/**
	 * @return the nX_gas
	 */
	public short getnX_gas() {
		return nX_gas;
	}

	/**
	 * @return the nY_gas
	 */
	public short getnY_gas() {
		return nY_gas;
	}

	/**
	 * @return the ncellsgas
	 */
	public int getNcellsgas() {
		return Ncellsgas;
	}
}
/**
 * 
 */
package no.sintef.model;

import java.io.IOException;
import java.util.Vector;

import no.sintef.model.ReleaseSite.ReleaseSiteDAO;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class ReleaseSiteData {

	private Vector<ReleaseSite> sites;

	public static class ReleaseSiteDataDAO {
		/**
		 * Read ReleaseSiteData from dataStream
		 * 
		 * @param dataStream
		 * @throws IOException
		 */
		public static ReleaseSiteData readData(
				LittleEndianDataInputStream dataStream) throws IOException {
			ReleaseSiteData data = new ReleaseSiteData();
			int numberReleaseSites = dataStream.readShort();
			if (numberReleaseSites > 0) {
				Vector<ReleaseSite> sites = new Vector<ReleaseSite>();
				for (int i = 0; i < numberReleaseSites; i++) {
					sites.add(ReleaseSiteDAO.readData(dataStream));
				}
				data.sites = sites;
			}
			return data;

		}
	}

	/**********************************************************************************************/

	public int getNumberSites() {
		return sites.size();
	}

	/**
	 * @return the sites
	 */
	public Vector<ReleaseSite> getSites() {
		return sites;
	}

	/**
	 * @param sites
	 *            the sites to set
	 */
	public void setSites(Vector<ReleaseSite> sites) {
		this.sites = sites;
	}

}

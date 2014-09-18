/**
 * 
 */
package no.sintef.model;

import java.io.IOException;
import java.util.Vector;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class ReleaseData {

	private Vector<Release> releases;

	public static class ReleaseDataDAO {
		/**
		 * 
		 * @param dataStream
		 * @param numberSites
		 * @param numberComponentsInProfile
		 * @throws IOException
		 */
		public static ReleaseData readData(
				LittleEndianDataInputStream dataStream, int numberSites,
				int numberComponentsInProfile) throws IOException {
			ReleaseData data = new ReleaseData();

			if (numberSites > 0) {
				Vector<Release> releases = new Vector<Release>();
				for (int i = 0; i < numberSites; i++) {
					releases.add(new Release(dataStream,
							numberComponentsInProfile));
				}
				data.releases = releases;
			}

			return data;

		}
	}

	public Vector<Release> getReleases() {
		return releases;
	}

	public void setReleases(Vector<Release> releases) {
		this.releases = releases;
	}

}

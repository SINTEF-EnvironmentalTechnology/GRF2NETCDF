/**
 * 
 */
package no.sintef.fates.io;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import no.sintef.fates.io.files.HDR;
import no.sintef.model.Grid;
import no.sintef.model.HDRSummary;
import no.sintef.model.OilSpillSimulation;

/**
 * @author ubr
 */
public class HDRFileDAO {
	private DataInputStream dataStream;
	private ByteBuffer byteBuffer;
	// private File headerFileTXTdump = new File(Configuration.OUTPUTPATH
	// + "PleaseProvideAName.HDRTXT");
	FileOutputStream outputStream;
	FileWriter filewriter;
	BufferedWriter writer;

	/**
	 * 
	 */
	public HDRFileDAO(File file) {
		FileInputStream hdrFile;
		try {
			hdrFile = new FileInputStream(file);
			// Wrap the FileInputStream with a DataInputStream
			dataStream = new DataInputStream(hdrFile);
			long available = dataStream.available();
			byte[] b = new byte[(int) available];

			byteBuffer = ByteBuffer.allocate((int) available);
			byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

			dataStream.readFully(b);
			byteBuffer.put(b);
			byteBuffer.rewind();

			// create file if it doesn't exist, yet
			// headerFileTXTdump.createNewFile();
			// filewriter = new FileWriter(headerFileTXTdump);
			// writer = new BufferedWriter(filewriter);

		} catch (FileNotFoundException e) {

			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public HDR readHeaderFile() throws IOException {

		try {

			HDR headerFileContent = new HDR();

			readHeader();
			headerFileContent.setSpillInfo(readSpillInfo());
			headerFileContent.setGridData(readGridData());
			headerFileContent.setSummary(readSummary());

			dataStream.close();

			// writeSpillInfo(headerFileContent.getSpillInfo());
			// writeGridData(headerFileContent.getGridData());
			// writeSummary(headerFileContent.getSummary());

			// writer.close();

			return headerFileContent;
		} catch (IOException e) {
			throw new IOException("IOException in readHeaderFile: "
					+ e.getMessage());
		}
	}

	private void readHeader() throws IOException {
		byteBuffer.getInt();
		byteBuffer.getInt();
		byteBuffer.getShort();
		byteBuffer.getShort();
		byteBuffer.getShort();
		byteBuffer.getShort();
		byte[] s = new byte[40];
		byteBuffer.get(s);

		byteBuffer.get();
		byteBuffer.get();
		byteBuffer.get();
		byteBuffer.get();
		byte[] s2 = new byte[20];
		byteBuffer.get(s2);

		byteBuffer.get();
		byteBuffer.get();
		byteBuffer.get();

		byte[] s3 = new byte[9];
		byteBuffer.get(s3);
		byteBuffer.getInt();

	}

	@SuppressWarnings("unused")
	private void writeHeader() throws IOException {
		writer.write("HEADER ___________________________________________");
		writer.write(byteBuffer.getInt());
		writer.write(byteBuffer.getInt());
		writer.write(byteBuffer.getShort());
		writer.write(byteBuffer.getShort());
		writer.write(byteBuffer.getShort());
		writer.write(byteBuffer.getShort());
		byte[] s = new byte[40];
		byteBuffer.get(s);
		writer.write(new String(s));

		writer.write((char) (byteBuffer.get()));
		writer.write((int) byteBuffer.get());
		writer.write((int) byteBuffer.get());
		writer.write((int) byteBuffer.get());
		byte[] s2 = new byte[20];
		byteBuffer.get(s2);
		writer.write(new String(s2));

		writer.write((int) byteBuffer.get());
		writer.write((int) byteBuffer.get());
		writer.write((int) byteBuffer.get());

		byte[] s3 = new byte[9];
		byteBuffer.get(s3);
		writer.write(new String(s3));
		writer.write(byteBuffer.getInt());
		writer.write("HEADER ___________________________________________");
	}

	/**
	 * @param spillInfo
	 * @throws IOException
	 */
	private OilSpillSimulation readSpillInfo() throws IOException {
		OilSpillSimulation spillInfo = new OilSpillSimulation();
		int length;

		length = setLength(byteBuffer.getShort());
		spillInfo.setLengthOfDescription(length);
		byte[] b = new byte[length];
		byteBuffer.get(b);
		spillInfo.setDescription(new String(b));

		length = setLength(byteBuffer.getShort());
		spillInfo.setLengthOfScenarioName(length);

		b = new byte[length];
		byteBuffer.get(b);
		spillInfo.setScenarioName(new String(b));

		length = setLength(byteBuffer.getShort());
		spillInfo.setLengthOfGridFileName(length);

		b = new byte[length];
		byteBuffer.get(b);
		spillInfo.setGridFileName(new String(b));

		spillInfo.setStartHourOfSpill(byteBuffer.getShort());
		spillInfo.setStartMonthOfSpill(byteBuffer.getShort());
		spillInfo.setStartDayOfSpill(byteBuffer.getShort());
		spillInfo.setStartYearOfSpill(byteBuffer.getShort());

		spillInfo.setSpillStartLatitude(byteBuffer.getFloat());
		spillInfo.setSpillStartLongitude(byteBuffer.getFloat());
		spillInfo.setReleaseDuration(byteBuffer.getFloat()); // hours

		length = setLength(byteBuffer.getShort());
		spillInfo.setLengthOfWindFileName(length);

		b = new byte[length];
		byteBuffer.get(b);
		spillInfo.setWindFileName(new String(b));

		spillInfo.setTotalMassOfComponents(byteBuffer.getFloat()); // tons
		spillInfo.setCas1(byteBuffer.getShort());
		spillInfo.setCas2(byteBuffer.getShort());
		spillInfo.setCas3(byteBuffer.getShort());
		spillInfo.setCcode(byteBuffer.getShort());

		length = setLength(byteBuffer.getShort());
		spillInfo.setLengthOfProfileName(length);
		b = new byte[8];
		byteBuffer.get(b);
		spillInfo.setProfileName(new String(b));

		length = setLength(byteBuffer.getShort());
		spillInfo.setLengthOfCurrentFileName(length);

		b = new byte[length];
		byteBuffer.get(b);
		spillInfo.setCurrentFileName(new String(b));

		// number of current components into array
		int numberComponents = byteBuffer.getShort();
		spillInfo.setNumberOfCurrentComponents(numberComponents);
		if (numberComponents > 0) {
			double[] d = new double[numberComponents];

			for (int j = 0; j < numberComponents; j++) {
				d[j] = byteBuffer.getFloat();
			}
			spillInfo.setCurrentComponents(d);
		}

		spillInfo.setTidalHour(byteBuffer.getShort());
		spillInfo.setCleanUpIndex(byteBuffer.getShort());

		length = setLength(byteBuffer.getShort());
		spillInfo.setLengthOfResponseFileName(length);
		if (length > 0) {
			b = new byte[length];
			byteBuffer.get(b);
			spillInfo.setResponseFileName(new String(b));
		}

		spillInfo.setAirTemperature(byteBuffer.getShort());
		spillInfo.setWaterTemperature(byteBuffer.getShort());
		spillInfo.setSuspendedSedimentConcentration(byteBuffer.getFloat());
		spillInfo.setSettlingVelocity(byteBuffer.getFloat());
		spillInfo.setEffectiveConcentration(byteBuffer.getFloat());
		spillInfo.setLethalConcentration1(byteBuffer.getFloat());
		spillInfo.setLethalConcentration2(byteBuffer.getFloat());
		spillInfo.setLethalConcentration3(byteBuffer.getFloat());
		spillInfo.setLethalConcentration4(byteBuffer.getFloat());
		spillInfo.setUserConcentrationLimit(byteBuffer.getFloat());
		return spillInfo;
	}

	/**
	 * 
	 */
	private int setLength(int length) {

		if (length < 0) {
			return 0;
		} else {
			return length;
		}
	}

	/**
	 * @param summary
	 * @throws IOException
	 */
	private HDRSummary readSummary() throws IOException {

		HDRSummary summary = new HDRSummary();

		summary.setAverageDensity(byteBuffer.getFloat());
		summary.setKoc(byteBuffer.getFloat());
		summary.setChronicTf(byteBuffer.get() != 0);
		summary.setChronicFactor(byteBuffer.getFloat());
		summary.setNumberComponents(byteBuffer.getShort());

		return summary;

	}

	/**
	 * @param gridData
	 * @throws IOException
	 */
	private Grid readGridData() throws IOException {
		Grid gridData = new Grid();

		gridData.setProvinceCode(byteBuffer.getShort());

		gridData.setOrgLatitude(byteBuffer.getFloat());
		gridData.setOrgLongitude(byteBuffer.getFloat());
		gridData.setNx(byteBuffer.getShort());
		gridData.setNy(byteBuffer.getShort());
		gridData.setMaxSpl(byteBuffer.getShort());
		gridData.setDtLon(byteBuffer.getFloat());
		gridData.setDtLat(byteBuffer.getFloat());
		gridData.setHorizontalDispersionCoefficient(byteBuffer.getFloat());
		gridData.setVerticalDispersionCoefficient(byteBuffer.getFloat());
		gridData.setnXTrans(byteBuffer.getShort());
		gridData.setnYTrans(byteBuffer.getShort());
		gridData.setnZTrans(byteBuffer.getShort());
		gridData.setRotatedDegress(byteBuffer.getFloat());
		gridData.setMeanTidalHeight(byteBuffer.getFloat()); // in m
		gridData.setWidthOfSwahZone(byteBuffer.getFloat()); // in m

		double[] d = new double[19];
		for (int i = 0; i < 19; i++) {
			d[i] = byteBuffer.getFloat();
		}
		gridData.setBeachslope(d); // array: 19 * float (4byte)

		int x = gridData.getNx();
		int y = gridData.getNy();
		int[][] lwa = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				lwa[i][j] = byteBuffer.getShort();
			}
		}
		gridData.setLandWaterArray(lwa);

		int[][] ha = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				ha[i][j] = byteBuffer.getShort();
			}
		}
		gridData.setHabitatArray(ha);

		int[][] ba = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				ba[i][j] = byteBuffer.getShort();
			}
		}
		gridData.setBathymetryArray(ba);

		int[] tca = new int[12];
		for (int i = 0; i < 12; i++) {
			tca[i] = byteBuffer.getShort();
		}
		gridData.setThermoclineArray(tca);

		int[] sta = new int[12];
		for (int i = 0; i < 12; i++) {
			sta[i] = byteBuffer.getShort();
		}
		gridData.setSurfaceTemperatureArray(sta);

		int[] ssta = new int[12];
		for (int i = 0; i < 12; i++) {
			ssta[i] = byteBuffer.getShort();
		}
		gridData.setSubseaTemperatureArray(ssta);
		return gridData;
	}

	/**
	 * @param spillInfo
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void writeSpillInfo(OilSpillSimulation spillInfo)
			throws IOException {

		writer.write("SPILLINFO ___________________________________________");
		writer.write(spillInfo.getLengthOfDescription());
		System.out.println(spillInfo.getLengthOfDescription());
		writer.write(spillInfo.getDescription());
		System.out.println(spillInfo.getDescription());
		writer.write(spillInfo.getLengthOfScenarioName());
		System.out.println(spillInfo.getLengthOfScenarioName());
		writer.write(spillInfo.getScenarioName());
		writer.write(spillInfo.getLengthOfGridFileName());
		writer.write(spillInfo.getGridFileName());
		writer.write(spillInfo.getStartHourOfSpill());
		writer.write(spillInfo.getStartMonthOfSpill());
		writer.write(spillInfo.getStartDayOfSpill());
		writer.write(spillInfo.getStartYearOfSpill());
		writer.write(new Double(spillInfo.getSpillStartLatitude()).toString());
		writer.write(new Double(spillInfo.getSpillStartLongitude()).toString());
		writer.write(new Double(spillInfo.getReleaseDuration()).toString());
		writer.write(spillInfo.getLengthOfWindFileName());
		writer.write(spillInfo.getWindFileName());
		writer.write(new Double(spillInfo.getTotalMassOfComponents())
				.toString());
		writer.write(spillInfo.getCas1());
		writer.write(spillInfo.getCas2());
		writer.write(spillInfo.getCas3());
		writer.write(spillInfo.getCcode());
		writer.write(spillInfo.getLengthOfProfileName());
		writer.write(spillInfo.getProfileName());
		writer.write(spillInfo.getLengthOfCurrentFileName());
		writer.write(spillInfo.getCurrentFileName());
		int i = spillInfo.getNumberOfCurrentComponents();
		writer.write(i);
		if (i > 0) {
			double[] d = spillInfo.getCurrentComponents();
			for (int j = 0; j < i; j++) {
				writer.write(new Double(d[j]).toString());
			}

		}

		writer.write(spillInfo.getTidalHour());
		writer.write(spillInfo.getCleanUpIndex());
		writer.write(spillInfo.getLengthOfResponseFileName());
		writer.write(spillInfo.getResponseFileName());
		writer.write(spillInfo.getAirTemperature());
		writer.write(spillInfo.getWaterTemperature());
		writer.write(new Double(spillInfo.getSuspendedSedimentConcentration())
				.toString());
		writer.write(new Double(spillInfo.getSettlingVelocity()).toString());
		writer.write(new Double(spillInfo.getEffectiveConcentration())
				.toString());
		writer.write(new Double(spillInfo.getLethalConcentration1()).toString());
		writer.write(new Double(spillInfo.getLethalConcentration2()).toString());
		writer.write(new Double(spillInfo.getLethalConcentration3()).toString());
		writer.write(new Double(spillInfo.getLethalConcentration4()).toString());
		writer.write(new Double(spillInfo.getUserConcentrationLimit())
				.toString());
		writer.write("SPILLINFO ___________________________________________");
	}

	/**
	 * @param summary
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void writeSummary(HDRSummary summary) throws IOException {
		writer.write("SUMMARY ___________________________________________");
		writer.write(new Double(summary.getAverageDensity()).toString());
		writer.write(new Double(summary.getKoc()).toString());
		writer.write(new Boolean(summary.getChronicTf()).toString());
		writer.write(new Double(summary.getChronicFactor()).toString());
		writer.write(summary.getNumberComponents());
		writer.write("SUMMARY ___________________________________________");
	}

	/**
	 * @param gridData
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void writeGridData(Grid gridData) throws IOException {
		writer.write("GRIDDATA ___________________________________________");
		writer.write(gridData.getProvinceCode());

		writer.write(new Double(gridData.getOrgLatitude()).toString());
		writer.write(new Double(gridData.getOrgLongitude()).toString());
		writer.write(gridData.getNx());
		writer.write(gridData.getNy());
		writer.write(gridData.getMaxSpl());
		writer.write(new Double(gridData.getDtLon()).toString());
		writer.write(new Double(gridData.getDtLat()).toString());
		writer.write(new Double(gridData.getHorizontalDispersionCoefficient())
				.toString());
		writer.write(new Double(gridData.getVerticalDispersionCoefficient())
				.toString());
		writer.write(gridData.getnXTrans());
		writer.write(gridData.getnXTrans());
		writer.write(gridData.getnXTrans());
		writer.write(new Double(gridData.getRotatedDegress()).toString());
		writer.write(new Double(gridData.getMeanTidalHeight()).toString()); // in
		// m
		writer.write(new Double(gridData.getWidthOfSwahZone()).toString()); // in
		// m

		double[] d = gridData.getBeachslope();
		;
		for (int i = 0; i < 19; i++) {
			writer.write(new Double(d[i]).toString());
		}
		int x = gridData.getNx();
		int y = gridData.getNy();
		int[][] lwa = gridData.getLandWaterArray();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				writer.write(lwa[i][j]);
			}
		}

		int[][] ha = gridData.getHabitatArray();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				writer.write(ha[i][j]);
			}
		}

		int[][] ba = gridData.getBathymetryArray();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				writer.write(ba[i][j]);
			}
		}

		int[] tca = gridData.getThermoclineArray();
		for (int i = 0; i < 12; i++) {
			writer.write(tca[i]);
		}

		int[] sta = gridData.getSurfaceTemperatureArray();
		for (int i = 0; i < 12; i++) {
			writer.write(sta[i]);
		}

		int[] ssta = gridData.getSubseaTemperatureArray();
		for (int i = 0; i < 12; i++) {
			writer.write(ssta[i]);
		}
		writer.write("GRIDDATA ___________________________________________");
	}
}
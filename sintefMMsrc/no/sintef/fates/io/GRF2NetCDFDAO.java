package no.sintef.fates.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

import no.sintef.NetCDF.ConcentrationGridWriter;
import no.sintef.NetCDF.MassBalanceWriter;
import no.sintef.NetCDF.NetCDFException;
import no.sintef.NetCDF.SurfaceGridGasWriter;
import no.sintef.NetCDF.SurfaceGridOilWriter;
import no.sintef.fates.io.files.GRFException;

public class GRF2NetCDFDAO extends GRFFileDAO {

	private String outputFolder;

	/**
	 * Constructor: sets file name and simulation start date
	 * 
	 * @param _file
	 * @param _date
	 * @throws PredictOilDriftException
	 * @throws FileNotFoundException
	 */

	public GRF2NetCDFDAO(File _inFile, String _outFolder, Date _date)
			throws NetCDFException, FileNotFoundException {
		super(_inFile, _date);

		File folder = new File(_outFolder);
		if (folder.exists() && folder.isDirectory() && folder.canWrite()) {
			this.outputFolder = _outFolder;
		} else {
			throw new NetCDFException(this.getClass().getSimpleName()
					+ ": Can't write to" + _outFolder);
		}
	}

	public GRF2NetCDFDAO(File _inFile, Date _date) throws NetCDFException,
			FileNotFoundException {
		super(_inFile, _date);

		File folder = _inFile.getParentFile();
		if (folder.exists() && folder.isDirectory() && folder.canWrite()) {
			this.outputFolder = folder.getAbsolutePath();
		} else {
			throw new NetCDFException(this.getClass().getSimpleName()
					+ ": Can't write to" + folder.getAbsolutePath());
		}
	}

	/**
	 * @return if any netCDF is to create
	 */
	public boolean createNetCDF() {
		return (massBalanceFile || subSurfaceGridConcentrationsFile
				|| surfaceGridOilFile || surfaceGridGasFile);
	}

	/**
	 * 
	 * @param _massBalance
	 * @param subSurfaceGridConcentrations
	 * @param surfaceGridOil
	 * @param surfaceGridGas
	 * @return
	 * @throws GRFException
	 */
	public List<String> createResultFiles(boolean _massBalance,
			boolean subSurfaceGridConcentrations, boolean surfaceGridOil,
			boolean surfaceGridGas) throws GRFException {

		massBalanceFile = _massBalance;
		subSurfaceGridConcentrationsFile = subSurfaceGridConcentrations;
		surfaceGridOilFile = surfaceGridOil;
		surfaceGridGasFile = surfaceGridGas;

		return readFile();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see no.sintef.fates.io.GRFFileDAO#writeResult(int)
	 */
	@Override
	void writeResult(int recordCounter) throws GRFException {
		String modelAndVersion = "Fates, unknown version";
		writeResult(recordCounter, modelAndVersion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see no.sintef.fates.io.GRFFileDAO#writeResult(int, String)
	 */
	@Override
	protected void writeResult(int recordCounter, String modelVersion)
			throws GRFException {
		if (createNetCDF()) {

			if (!outputFolder.isEmpty()) {
				// replace folder by output folder and cut of file extension
				File file = new File(filename);
				String folder = file.getParent();
				filename = filename.replace(folder, outputFolder);
			}

			try {
				writeNetCDF(filename, recordCounter, modelVersion);
			} catch (NetCDFException e) {
				throw new GRFException(e.getMessage());
			}
		} else {
			throw new GRFException(this.getClass().getSimpleName()
					+ ": NetCDF: nothing to do.");
		}

	}

	/**
	 * 
	 * @param filenameBasis
	 * @param recordCounter
	 * @param modelAndVersion
	 * @throws NetCDFException
	 */
	private void writeNetCDF(String filename, int recordCounter,
			String modelAndVersion) throws NetCDFException {

		try {

			if (surfaceGridOilFile) {
				// create surface oil grid - netCDF file

				SurfaceGridOilWriter surfaceGridOilWriter = new SurfaceGridOilWriter();
				String netCDFFile = SurfaceGridOilWriter
						.getNetCDFFilename(filename);

				if (recordCounter == 0) {
					// create empty file with CDL part only
					surfaceGridOilWriter.createFile(summary.getnX_surf(),
							summary.getnY_surf(), simulationStart, netCDFFile);
					resultfiles.add(netCDFFile);

				} else {
					surfaceGridOilWriter.writeToFile(netCDFFile, recordCounter,
							surfaceGridOilWriter.new SurfaceOilGrid(
									surfaceCellsOil, summary));
				}
			}

			if (surfaceGridGasFile) {
				// create surface gas grid - netCDF file

				SurfaceGridGasWriter surfaceGridGasWriter = new SurfaceGridGasWriter();
				String fileLocation = SurfaceGridGasWriter
						.getNetCDFFilename(filename);

				if (recordCounter == 0) {
					// create empty file with CDL part only
					surfaceGridGasWriter
							.createFile(summary.getnX_surf(),
									summary.getnY_surf(), simulationStart,
									fileLocation);
					resultfiles.add(fileLocation);

				} else {
					surfaceGridGasWriter.writeToFile(fileLocation,
							recordCounter,
							surfaceGridGasWriter.new SurfaceGasGrid(
									surfaceCellsGas, summary));
				}
			}

			if (massBalanceFile) {
				// create mass-balance - netCDF file
				MassBalanceWriter writer = new MassBalanceWriter();
				String fileLocation = MassBalanceWriter
						.getNetCDFFilename(filename);

				if (recordCounter == 0) {
					// create empty file with CDL part only
					writer.createFile(fileLocation, simulationStart);
					resultfiles.add(fileLocation);

				} else {
					writer.writeToFile(fileLocation, recordCounter, summary);
				}
			}

			if (subSurfaceGridConcentrationsFile) {
				// create concentration grid - netCDF file
				ConcentrationGridWriter writer = new ConcentrationGridWriter();
				String fileLocation = ConcentrationGridWriter
						.getNetCDFFilename(filename);

				if (recordCounter == 0) {
					// create empty file with CDL part only
					writer.createFile(summary.getnX_conc(),
							summary.getnY_conc(), summary.getnZ_conc(),
							simulationStart, fileLocation);
					resultfiles.add(fileLocation);

				} else {
					writer.writeToFile(fileLocation, recordCounter,
							writer.new ConcentrationGrid(waterconcentrations,
									summary, sSGDims));
				}
			}

		} catch (Exception e) {
			if (recordCounter == 0) {
				throw new NetCDFException("Error while creating NetCDF file:\n"
						+ e.getMessage());
			} else {
				throw new NetCDFException("Error while writing NetCDF file:\n"
						+ e.getMessage());
			}
		}
	}
}

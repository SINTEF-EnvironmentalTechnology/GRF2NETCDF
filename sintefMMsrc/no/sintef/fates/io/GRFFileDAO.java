package no.sintef.fates.io;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import no.sintef.fates.io.files.BinaryHeader;
import no.sintef.fates.io.files.BinaryHeader.BinaryHeaderDAO;
import no.sintef.fates.io.files.GRF;
import no.sintef.fates.io.files.GRFException;
import no.sintef.fates.io.files.GRFException.GRFError;
import no.sintef.model.ComponentData;
import no.sintef.model.ComponentData.ComponentDataDAO;
import no.sintef.model.Plume3D;
import no.sintef.model.ReleaseData;
import no.sintef.model.ReleaseData.ReleaseDataDAO;
import no.sintef.model.ReleaseSite;
import no.sintef.model.ReleaseSiteData;
import no.sintef.model.ReleaseSiteData.ReleaseSiteDataDAO;
import no.sintef.model.ResponseData;
import no.sintef.model.ResponseData.ResponseDataDAO;
import no.sintef.model.ResponseUnit;
import no.sintef.model.SpecieData;
import no.sintef.model.SpecieData.SpecieDataDAO;
import no.sintef.model.SubsurfaceGridDimensions;
import no.sintef.model.SubsurfaceGridDimensions.SubsurfaceGridDimensionDAO;
import no.sintef.model.Summary;
import no.sintef.model.Summary.SummaryDAO;
import no.sintef.model.SurfaceCellGas;
import no.sintef.model.SurfaceCellGas.SurfaceCellGasDAO;
import no.sintef.model.SurfaceCellOil;
import no.sintef.model.SurfaceCellOil.SurfaceCellOilDAO;
import no.sintef.model.WaterConcentration;
import no.sintef.model.WaterConcentration.WaterConcentrationDAO;

public abstract class GRFFileDAO extends BinaryFileDAO {
	/**
	 * Data Access Object to read GRF file (from OSCAR or DREAM simulations). It
	 * contains time series of result data. GRF files are binary files in
	 * LittleEndian byte order which can be huge to due simulation duration and
	 * resolution. The file content is therefore read via methods from
	 * BinaryFileDAO.
	 */

	// protected TimeStepIndependentData tsiData = new
	// TimeStepIndependentData();
	// protected TimeStepDependentData tsdData = new TimeStepDependentData();

	
	protected List<String> resultfiles = new ArrayList<String>();

	protected short numberTimeSteps = 0;
	int read = 0;
	private static int available;

	// possible result files
	protected boolean massBalanceFile = false;
	protected boolean subSurfaceGridConcentrationsFile = false;
	protected boolean surfaceGridOilFile = false;
	protected boolean surfaceGridGasFile = false;
	protected boolean subSurfaceParticlesDissolvedFractionFile = false;
	protected boolean subSurfaceParticlesDropletFractionFile = false;
	protected boolean subSurfaceParticlesGasFile = false;
	
	protected short fileversion = 0;
	protected String modelversion = "unknown version";
	protected Date simulationStart;
	protected Summary summary;
	protected ReleaseSiteData releaseSiteData;
	protected SubsurfaceGridDimensions sSGDims;
	protected Vector<WaterConcentration> waterconcentrations;
	protected Vector<SurfaceCellOil> surfaceCellsOil;
	protected Vector<SurfaceCellGas> surfaceCellsGas;

	/**
	 * @param file
	 * @param date
	 * @throws PredictOilDriftException
	 * @throws FileNotFoundException
	 */
	public GRFFileDAO(File file, Date date) throws FileNotFoundException {
		super(file);

		this.filename = file.getAbsolutePath();
		this.simulationStart = date;
		// resultfiles.add(filename);
	}

	/**
	 * Reads grf file consisting of time step independent data and one time step
	 * dependent data set per time step. Read data maybe stored in other files,
	 * e.g. NetCDF.
	 * 
	 * @return list with generated files (paths)
	 * @throws GRFException
	 */
	protected List<String> readFile() throws GRFException {

		try {
			available = dataStream.available();
			if (available > 0) {
				try {
					// scenario setup & parameters, "meta data"
					readTimeIndependentData();
					int recordCounter = 0;
					while (!reachedEndOfFile()) {
						readTimeDependentData();
						writeResult(recordCounter, modelversion);
						recordCounter++;

					}

				} catch (EOFException eof) {
					dataStream.close();
					throw new GRFException(eof.getMessage());
				}

			} else {
				dataStream.close();
				throw new GRFException(
						GRFError.ERROR_READING_GRFFILE_EMPTY_FILE);
			}

			dataStream.close();
		} catch (IOException e) {
			throw new GRFException(e.getMessage());
		}
		return resultfiles;
	}

	/**
	 * writing method to be implemented by the respective writers
	 * 
	 * @param recordCounter
	 * @throws GRFException
	 */
	abstract void writeResult(int recordCounter) throws GRFException;

	abstract void writeResult(int recordCounter, String modelVersion)
			throws GRFException;

	/**
	 * Read the data sets from GRF file that are time step independent (Summary,
	 * results per time step)
	 * 
	 * @param skipData
	 *            read or just skip data
	 * @throws IOException
	 */
	private void readTimeDependentData() throws IOException {
		// possible result files are:
		// massBalanceFile
		// subSurfaceGridConcentrationsFile
		// surfaceGridOilFile
		// surfaceGridGasFile
		// subSurfaceParticlesDissolvedFractionFile TODO
		// subSurfaceParticlesDropletFractionFile TODO
		// subSurfaceParticlesGasFile TODO

		/* Summary */
		summary = SummaryDAO.readData(dataStream, fileversion);

		/* Site Data */
		int numberSites = releaseSiteData.getNumberSites();
		dataStream.skip(numberSites * ReleaseSite.SIZE);

		short numberLayers = summary.getnZ_conc();
		/* sub-surface grid dimensions */
		sSGDims = SubsurfaceGridDimensionDAO.readData(
				dataStream, numberLayers);

		/*
		 * Spillet data = model parcels for non-dissolved matter on surface or
		 * in water column; first Integer contains number Bytes for Spillet data
		 */
		dataStream.skip(dataStream.readInt());

		/*
		 * Particle data, model parcels for dissolved matter in water column;
		 * first Integer contains number Bytes for Particle data
		 */
		dataStream.skip(dataStream.readInt());

		/*
		 * Organism data; first Integer contains number Bytes for Organism data
		 */
		dataStream.skip(dataStream.readInt());

		/*
		 * Gasbubbles, model parcels representing gas bubbles gas bubbles from
		 * version 23; first Integer contains number Bytes for Gasbubble data
		 */
		if (fileversion >= GRF.GRFFILEGASVERSION) {
			dataStream.skip(dataStream.readInt());

		}

		/*
		 * Sub-surface grid data is read by cell. Number layers is
		 * summary.getnZ_conc(), we have two extra layers for mean and max
		 * concentration, respectively. for each layer we have read
		 * numberWatConcCellsPerLayer into SubsurfaceGridDimensions.
		 */
		waterconcentrations = new Vector<WaterConcentration>();
		for (int i = 0; i < numberLayers + 2; i++) {
			for (int j = 0; j < sSGDims.getNumberWatConcCellsPerLayer().get(i); j++) {
				waterconcentrations.add(WaterConcentrationDAO
						.readData(dataStream));
			}
		}

		/*
		 * Surface grid (oil) data; the summary has the information if there is
		 * data or not
		 */
		surfaceCellsOil = new Vector<SurfaceCellOil>();
		for (int i = 0; i < summary.getNcellssurf(); i++) {
			surfaceCellsOil.add(SurfaceCellOilDAO.readData(dataStream));
		}

		/* Surface grid (gas) data from version 23 */
		if (fileversion >= GRF.GRFFILEGASVERSION) {
			surfaceCellsGas = new Vector<SurfaceCellGas>();
			for (int i = 0; i < summary.getNcellsgas(); i++) {
				surfaceCellsGas.add(SurfaceCellGasDAO.readData(dataStream));
			}
		}

		/* Shoreline data */
		if (summary.getNcellsshore() > 0) {
			dataStream.skip(dataStream.readInt());
		}

		/* Sediment data */
		if (summary.getNumberCellsSediment() > 0) {
			dataStream.skip(dataStream.readInt());
		}

		/* Response data */
		int numberActiveVessels = summary.getNvess_active();
		if (numberActiveVessels > 0) {
			dataStream.skip(numberActiveVessels * ResponseUnit.SIZE);
		}

		/*
		 * Plume3D data Each site has its own data, first short determines how
		 * many records.
		 */
		if (summary.hasPlume()) {
			for (int site = 0; site < numberSites; site++) {
				dataStream.skip(dataStream.readShort() * Plume3D.SIZE);
			}
		}

	}

	/**
	 * Read the data sets from GRF file that are time step independent (meta
	 * data)
	 * 
	 * @throws IOException
	 * @throws GRFException
	 */
	private void readTimeIndependentData() throws IOException, GRFException {
		/* Header */
		BinaryHeader header = BinaryHeaderDAO.readData(dataStream);
		fileversion = header.getFileversion();
		modelversion = header.getModelAndVersion();
		/* Components */
		ComponentData componentData = ComponentDataDAO.readData(dataStream,
				fileversion);

		/* Species Data */
		@SuppressWarnings("unused")
		SpecieData specieData = SpecieDataDAO.readData(dataStream);

		/* other stuff: scenario related info */
		dataStream.readFloat(); // simulation duration
		dataStream.readFloat(); // sediment porosity
		dataStream.readFloat(); // sediment toxicity interval
		dataStream.readFloat(); // natural grain size
		dataStream.readShort(); // sediment risk data available? 1=true
		dataStream.readShort(); // integer fraction storage? 1 = true
		dataStream.readShort(); // sediment pore data ? 1=true
		dataStream.readShort(); // plume 3D output? 1= true

		/* Site Data */
		releaseSiteData = ReleaseSiteDataDAO.readData(dataStream);

		/* Release data */
		@SuppressWarnings("unused")
		ReleaseData releaseData = ReleaseDataDAO.readData(dataStream,
				releaseSiteData.getNumberSites(),
				componentData.getTotalNumberOfComponents());

		/* more other stuff */
		dataStream.readShort(); // expanding grid? 1 = true
		dataStream.readShort(); // vertically expanding concentration grid? 1=
								// true
		dataStream.readFloat(); // sea oxygen average

		/* Response Data */
		@SuppressWarnings("unused")
		ResponseData responseVessels = ResponseDataDAO.readData(dataStream);
	}

}

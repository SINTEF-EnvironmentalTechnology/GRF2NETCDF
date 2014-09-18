package no.sintef.NetCDF;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import no.sintef.fates.io.GRF2NetCDFDAO;
import no.sintef.fates.io.HDRFileDAO;
import no.sintef.fates.io.files.GRFException;
import no.sintef.fates.io.files.HDR;
import no.sintef.model.OilSpillSimulation;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class GRF2NetCDF {

	private static final String CONC_GRID = "concGrid";
	private static boolean massBalance = true;
	private static boolean subSurfaceGridConcentrations = false;
	private static boolean surfaceGridOil = true; 
	private static boolean surfaceGridGas = true;

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		/*
		 * GRF2NetCDF expects one argument, 1 option. java -jar
		 * GRF2NetCDF.jar <PathToGRFFile> It is assumed that the
		 * respective HDR is located file in the same folder.
		 * 
		 * The command line is parsed with help of Apache commons-cli
		 * framework
		 */
		Options options = new Options();
		options.addOption(CONC_GRID, false, "create sub-surface concentration grid (time consuming!)");

		CommandLineParser parser = new GnuParser();
		HelpFormatter formatter = new HelpFormatter();

		String usage = "java -jar GRF2NetCDF.jar [-concGrid] <PathToGRFFile> \n"
				+ "Example usage: java -jar GRF2NetCDF.jar -concGrid c:/myFolder/myScenario.grf \n\n"
				+ "It is assumed that myScenario.grf and myScenario.hdr are located in the same folder.";
        options.addOption(new Option("help", "this message"));
        
			try {
				CommandLine cmd = parser.parse(options, args);
				if(cmd.hasOption(CONC_GRID)) {
					subSurfaceGridConcentrations = true;
				}
				List cmdList = cmd.getArgList();
				if (cmdList.size() == 1) // 1 argument expected s.a.
				{
					// get argument
					File grfFile = new File((String) cmdList.get(0));
					String absolutePath = grfFile.getAbsolutePath();
					if (!(grfFile.exists() && grfFile.isFile())) {
						System.err
								.println(absolutePath
										+ " is not a valid file. "
										+ "Error in data transformation. Can't produce netCDF!");
						return;
					}

					String filenameWOending = absolutePath.substring(0,
							absolutePath.lastIndexOf("."));

					File hdrFile = new File(filenameWOending + HDR.FILENAME);
					Calendar cal = Calendar.getInstance();
					if (!hdrFile.exists()) {
						System.err
								.println(hdrFile.getAbsolutePath()
										+ " does not exist (should be located in same folder). "
										+ "Error in data transformation. Can't determine simulation start for netCDF!\nAssuming current date.");
					} else {

						HDRFileDAO hdrReader = new HDRFileDAO(hdrFile);

						HDR headerFileCOntent = hdrReader.readHeaderFile();
						OilSpillSimulation spillInfo = headerFileCOntent
								.getSpillInfo();

						cal.set(spillInfo.getStartYearOfSpill(),
								spillInfo.getStartMonthOfSpill() - 1,
								spillInfo.getStartDayOfSpill(),
								spillInfo.getStartHourOfSpill(), 0);
					} // end if HRDFile exists

					
					String grfFileFolder = new File(absolutePath).getParent();

					GRF2NetCDFDAO grfReader = new GRF2NetCDFDAO(grfFile,
							grfFileFolder, cal.getTime());
					System.out.println("Creating NetCDF from " + absolutePath
							+ "...\n");

					List<String> resultFiles = grfReader.createResultFiles(
							massBalance, subSurfaceGridConcentrations,
							surfaceGridOil, surfaceGridGas);
					if (resultFiles.size() < 1) {
						throw new GRFException(
								"Error in data transformation. No netCDF produced!");
					}

					System.out.println("\nNetCDF from " + absolutePath
							+ " successfully created:\n");

					for (int i = 0; i < resultFiles.size(); i++) {
						System.out.println(resultFiles.get(i));
					}
				
				} else {
					formatter.printHelp(usage, options);
				} // end if correct usage
			
			} catch (IOException e) {
				System.err.println(e.getClass().getName()
						+ "GF2NetCDF: Converting result failed.  Reason: "
						+ e.getMessage());

			} catch (NetCDFException e) {
				System.err
						.println("GRF2NetCDF: Converting result failed.  Reason: "
								+ e.getMessage());

			} catch (ParseException e) {
				System.err
						.println("GRF2NetCDF: Converting result failed.  Reason: "
								+ e.getMessage());

			} catch (GRFException e) {
				System.err
						.println("GRF2NetCDF: Converting result failed.  Reason: "
								+ e.getMessage());

			} // end try

	} // end main
}
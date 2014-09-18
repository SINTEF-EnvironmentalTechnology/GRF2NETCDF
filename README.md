# Description
This software contains a utility to convert the binary output of SINTEF's oil
spill, contingency and response model OSCAR to NetCDF 3 files.

It will read the produced file with the file ending .grf together with information
from the .hdr file from version 22 (MEMW6.2) of the file.

The two files are assumed to be located in the same folder.

The tool is started by running
java -jar GRF2NetCDF.jar [-concGrid] "absolute/path/to/grfFile.grf"
on the command line.
The file name should be put in "" if the path contains spaces (like Public Documents).

The tool will produce the mass balance and the two surface grids (oil and gas, if
OSCAR was run with GasTrack option) each time, the sub-surface concentration
grid only, when the option -concGrid is specified. Producing the sub-surface
concentration grid may take up to several seconds per output time step!

# Software structure
The Java project consists of three source folders:
1) the src folder, containing the GRF2NetCDF commandline tool
2) the sintefMMsrc folder, containing SINTEF specific code for reading files
   produced by the SINTEF models. 
3) the netCDFsrc folder, containing the code for producing netCDF files for the
   specific data.
   
The folder testsrc contains JUnit4 unit tests, using in the same packaging 
structure as the source folders.

The Java project requires
1.  Java 7 (Java SE 1.7)
2.  guava-16.0.1 (for com.google.common.io.LittleEndianDataInputStream)
3.  Apache commons-cli 1.2 (command line parsing)
4.  netcdfAll-4.3 (tha NetCDF Java library)
5.  JUnit4 (unit testing)

# The eclipse Java project
The project can be imported to eclipse as existing project by pointing to the 
folder containing the .project file.
It is configured as Java project with a LIB_HOME variable pointing to the folder 
containing all required Java libraries (2-4 from above). 

When the eclipse project is imported the programmer will have to adapt
-  the Java SE
-  the JUit library
-  the path in LIB_HOME 

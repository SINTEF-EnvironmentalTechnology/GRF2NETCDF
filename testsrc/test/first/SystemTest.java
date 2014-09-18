/**
 * 
 */
package test.first;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ubr
 *
 */
public class SystemTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println(System.getProperty("os.arch"));
		System.out.println(System.getProperty("sun.arch.data.model"));
		ucar.nc2.jni.netcdf.Nc4Iosp.setLibraryAndPath("C:/Program Files (x86)/netCDF 4.3.1.1/bin", "netCDF4");
	}

}

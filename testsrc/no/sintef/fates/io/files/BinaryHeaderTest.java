/**
 * 
 */
package no.sintef.fates.io.files;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import junit.framework.TestCase;
import no.sintef.fates.io.files.BinaryHeader.BinaryHeaderDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.io.LittleEndianDataInputStream;

/**
 * @author ubr
 * 
 */
public class BinaryHeaderTest extends TestCase {

	private File grfFile;
	private LittleEndianDataInputStream dataStream;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		String filename = "";
		
		filename = "./testfiles/Surface.grf";
		grfFile = new File(filename);

	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link no.sintef.fates.io.files.BinaryHeader#getFileversion()}.
	 */
	@Test
	public void testGetFileversion() {
		try {
			dataStream = new LittleEndianDataInputStream(new FileInputStream(
					grfFile));

			BinaryHeader header = BinaryHeaderDAO.readData(dataStream);
			dataStream.close();
			
			short fileversionToTest = header.getFileversion();

			DataInputStream mydataStream = new DataInputStream(new FileInputStream(
					grfFile));
			byte[] b = new byte[BinaryHeader.beginFileversion];
			mydataStream.read(b);
			
			byte[] c = new byte[2];
			mydataStream.read(c);
			mydataStream.close();
			
			ByteBuffer buffer = ByteBuffer.allocate(2);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			buffer.put(c);
			buffer.rewind();
			short myFileversion = buffer.getShort();
			
			assertEquals(23, fileversionToTest);
			assertEquals(myFileversion, fileversionToTest);
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link no.sintef.fates.io.files.BinaryHeader#getModelAndVersion()}.
	 */
	@Test
	public void testGetModelAndVersion() {
		try {
			dataStream = new LittleEndianDataInputStream(new FileInputStream(
					grfFile));

			BinaryHeader header = BinaryHeaderDAO.readData(dataStream);
			dataStream.close();

			String modelversionToTest = header.getModelAndVersion();

			DataInputStream mydataStream = new DataInputStream(new FileInputStream(
					grfFile));
			byte[] b = new byte[BinaryHeader.beginModelAndVersion];
			mydataStream.read(b);
			byte[] c = new byte[BinaryHeader.lengthModelAndVersion];
			mydataStream.read(c);
			mydataStream.close();
			
			String myModelVersion = new String(c);
			
			assertEquals(myModelVersion, modelversionToTest);
			
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}

	}

}

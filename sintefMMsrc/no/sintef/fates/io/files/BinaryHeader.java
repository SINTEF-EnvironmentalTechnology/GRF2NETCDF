/**
 * 


 */
package no.sintef.fates.io.files;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.google.common.io.LittleEndianDataInputStream;

public class BinaryHeader {

	private static int size = 96;
	public static final int beginModelAndVersion = 16;
	public static final int lengthModelAndVersion = 64;
	public static final int beginFileversion = 14;
	
	private byte[] binaryHeader; // 96 bytes
	private ByteBuffer buffer = ByteBuffer.allocate(size);

	public BinaryHeader(byte[] binaryHeader) {
		super();
		this.binaryHeader = binaryHeader;
		this.buffer.order(ByteOrder.LITTLE_ENDIAN);
		this.buffer.put(this.binaryHeader);
	}

	/**
	 * 
	 * @author ubr
	 * 
	 */
	public static class BinaryHeaderDAO {
		/**
		 * 
		 * @param dataStream
		 * @return
		 * @throws IOException
		 */
		public static BinaryHeader readData(
				LittleEndianDataInputStream dataStream) throws IOException {
			byte[] header = new byte[BinaryHeader.size];
			dataStream.read(header);
			return new BinaryHeader(header);
		}
	}

	/**
	 * Get the file version from the header
	 * 
	 * @return
	 */
	public short getFileversion() {
		return buffer.getShort(beginFileversion);
	}

	/**
	 * Get the model and modelversion from the header
	 * 
	 * @return
	 */
	public String getModelAndVersion() {
		byte[] modelAndVersion = new byte[lengthModelAndVersion];

		buffer.rewind().position(beginModelAndVersion);
		buffer.get(modelAndVersion);
		
		return new String(modelAndVersion);
	}

	public byte[] getBinaryHeader() {
		return this.binaryHeader;
	}

}

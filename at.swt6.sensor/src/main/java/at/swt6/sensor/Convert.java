package at.swt6.sensor;
import java.nio.ByteBuffer;

public class Convert {
	public static double bytestoDouble(byte[] bytes) {
	    return ByteBuffer.wrap(bytes).getDouble();
	}
	
	public static byte[] doubleToBytes(double value) {
	    byte[] bytes = new byte[8];
	    ByteBuffer.wrap(bytes).putDouble(value);
	    return bytes;
	}
	
	public static long bytesToLong(byte[] bytes) {
		ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	    buffer.put(bytes);
	    buffer.flip();
	    return buffer.getLong();	
	}
	
	public static byte[] longToBytes(long value) {
		byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(value).array();
	    return bytes;	
	}
}

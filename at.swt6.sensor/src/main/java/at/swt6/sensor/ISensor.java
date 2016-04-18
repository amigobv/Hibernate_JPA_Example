package at.swt6.sensor;

public interface ISensor {
	public enum SensorDataFormat {
		PERCENT, /*double*/
		ABSOLUTE, /*long*/
	}
	
	String getSensorId();
	byte[] getData();
	SensorDataFormat getDataFormat();
	void setDataFormat(SensorDataFormat format);
}

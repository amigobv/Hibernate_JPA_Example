package at.swt6.sensor;

public abstract class AbstractSensor implements ISensor {

	private ISensorFactory sensorFactory;
	
	public AbstractSensor(ISensorFactory sf) {
		this.sensorFactory = sf;
	}
	
	public String getSensorId() {
		return sensorFactory.getSensorType();
	}

	public abstract byte[] getData();
	public abstract SensorDataFormat getDataFormat();
	public abstract void setDataFormat(SensorDataFormat format);
}

package at.swt6.sensor.TemperatureSensor;

import at.swt6.sensor.AbstractSensor;
import at.swt6.sensor.ISensorFactory;
import at.swt6.sensor.Convert;

public class TemperatureSensor extends AbstractSensor {
	SensorDataFormat format;
	
	public TemperatureSensor(ISensorFactory sf) {
		super(sf);
		format = SensorDataFormat.ABSOLUTE;
	}
	
	@Override
	public byte[] getData() {
		double value = Math.random();
		if (format == SensorDataFormat.ABSOLUTE)
			return Convert.longToBytes((long)(value*100));
		
		return Convert.doubleToBytes(value);
	}

	@Override
	public SensorDataFormat getDataFormat() {
		return format;
	}

	@Override
	public void setDataFormat(SensorDataFormat format) {
		this.format = format;
	}

}

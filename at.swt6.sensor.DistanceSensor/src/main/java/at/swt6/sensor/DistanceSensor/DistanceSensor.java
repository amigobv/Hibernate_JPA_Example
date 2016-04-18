package at.swt6.sensor.DistanceSensor;

import at.swt6.sensor.AbstractSensor;
import at.swt6.sensor.ISensorFactory;
import at.swt6.sensor.Convert;

public class DistanceSensor extends AbstractSensor {
	SensorDataFormat format;
	
	public DistanceSensor(ISensorFactory sf) {
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

package at.swt6.sensor.DistanceSensor;

import at.swt6.sensor.ISensor;
import at.swt6.sensor.ISensor.SensorDataFormat;
import at.swt6.sensor.ISensorFactory;
import javafx.scene.image.Image;

public class DistanceSensorFactory implements ISensorFactory {
	private Image icon;	
	private static DistanceSensor sensor;
	
	
	public DistanceSensorFactory() {
		System.out.println("Distance Sensor Factory");
		icon = new Image(this.getClass().getResourceAsStream("distance.png"));
	}
	
	@Override
	public String getUnit() {
		return "cm";
	}
	
	@Override
	public Image getSensorIcon() {
		return icon;
	}
	
	@Override
	public ISensor getInstance() {
		if (sensor == null) {
			sensor = new DistanceSensor(this);
			sensor.setDataFormat(SensorDataFormat.ABSOLUTE);
		}
		
		return sensor;
	}

	@Override
	public String getSensorType() {
		return "Distance";
	}
}

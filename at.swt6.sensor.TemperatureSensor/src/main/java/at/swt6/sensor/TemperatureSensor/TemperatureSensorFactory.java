package at.swt6.sensor.TemperatureSensor;

import at.swt6.sensor.ISensor;
import at.swt6.sensor.ISensorFactory;
import javafx.scene.image.Image;


public class TemperatureSensorFactory implements ISensorFactory {
	private Image icon;
	
	private static TemperatureSensor sensor;
	
	public TemperatureSensorFactory() {
		System.out.println("Temperature Sensor Factory");
		icon = new Image(this.getClass().getResourceAsStream("temperature.png"));
	}
	
	@Override
	public String getUnit() {
		return "°C";
	}
	
	@Override
	public ISensor getInstance() {
		if (sensor == null)
			sensor = new TemperatureSensor(this);
		
		return sensor;
	}

	@Override
	public String getSensorType() {
		return "Temperature";
	}

	@Override
	public Image getSensorIcon() {
		return icon;
	}
}

package at.swt6.main;

import at.swt6.sensor.ISensor.SensorDataFormat;
import at.swt6.sensor.ISensorFactory;
import javafx.scene.layout.Pane;

public interface ISensorView {
	public ISensorFactory getFactory();
	public Pane getSensorPane(SensorDataFormat format);
	public void update(double value);
}

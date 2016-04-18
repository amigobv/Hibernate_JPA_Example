package at.swt6.sensor;

import javafx.scene.image.Image;

public interface ISensorFactory {
	public ISensor getInstance();
	public String getSensorType();
	public Image  getSensorIcon();
	public String getUnit();
}

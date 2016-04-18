package at.swt6.main;

import at.swt6.sensor.ISensor.SensorDataFormat;
import at.swt6.utils.JavaFxUtils;
import at.swt6.sensor.ISensorFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;

public class SensorView implements ISensorView {
	private ISensorFactory factory;
	private ProgressBar progress = new ProgressBar();
	private ProgressIndicator indicator = new ProgressIndicator();
	private Label lblUnit;
	private Label lblValue;
	
	public SensorView(ISensorFactory sf) {
		if (sf == null)
			throw new IllegalArgumentException("Sensor factory is null!");
		
		factory = sf;
		progress = new ProgressBar(0);
		progress.setPrefHeight(40);
		progress.setPrefWidth(120);
		indicator = new ProgressIndicator(0);
		indicator.setPadding(new Insets(5));
	}
	
	@Override
	public ISensorFactory getFactory() {
		return factory;
	}

	public void setFactory(ISensorFactory factory) {
		this.factory = factory;
	}

	@Override
	public Pane getSensorPane(SensorDataFormat format) {
		if (format == SensorDataFormat.ABSOLUTE)
			return CreateLabel();		
		
		return CreateProgressBar();
	}
	
	private Pane CreateLabel() {
		lblValue = new Label("0");
		lblValue.setId("DistanceValue");
		lblValue.setFont(new Font("Arial", 100));
		lblValue.setPadding(new Insets(5));
		
		lblUnit = new Label(factory.getUnit());
		lblUnit.setFont(new Font("Arial", 100));
		lblUnit.setPadding(new Insets(5));

		HBox pane = new HBox();
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(lblValue, lblUnit);
		pane.setPadding(new Insets(50, 10, 10, 10));	
		
		return pane;
	}
	
	private Pane CreateProgressBar() {
		HBox pane = new HBox();
		pane.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(progress, indicator);
		pane.setPadding(new Insets(80, 10, 10, 10));
		
		return pane;
	}
	
	@Override
	public void update(double value) {
		JavaFxUtils.runLater(() -> {
			lblValue.setText(String.valueOf((long)value));
			indicator.setProgress(value);
			progress.setProgress(value);	
		});
	}
}

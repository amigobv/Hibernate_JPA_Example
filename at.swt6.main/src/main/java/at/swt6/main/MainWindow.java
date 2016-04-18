package at.swt6.main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import at.swt6.sensor.Convert;
import at.swt6.sensor.ISensor;
import at.swt6.sensor.ISensor.SensorDataFormat;
import at.swt6.sensor.ISensorFactory;
import at.swt6.utils.Timer;
import at.swt6.utils.TimerEvent;
import at.swt6.utils.TimerListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainWindow {

	private Stage stage;
	private VBox rootPane;
	private ToolBar toolBar;
	private Button btnSwitch;
	private List<EventHandler<WindowEvent>> onCloseHandlers = new ArrayList<>();
	private Collection<ISensorView> sensorViews = new ArrayList<ISensorView>();
	private ISensorView currentSensorView;
	private Timer pollingTimer = new Timer();

	public MainWindow() {
		System.out.println("Initialize Main Window!");
		toolBar = new ToolBar();
		btnSwitch = new Button();
		btnSwitch.setTooltip(new Tooltip("Sensor format"));
		Image icon = new Image(this.getClass().getResourceAsStream("switch.png"));
		btnSwitch.setGraphic(new ImageView(icon));
		
		HBox btnPane = new HBox();
		btnPane.setAlignment(Pos.CENTER);
		btnPane.getChildren().add(btnSwitch);
		btnPane.setPadding(new Insets(10));
		
		rootPane = new VBox(toolBar, btnPane);
	}

	public void show() {
		System.out.println("Calling show method");
		if (stage == null) {
			stage = new Stage();
			stage.setScene(new Scene(rootPane, 500, 420));
			stage.setMinHeight(250);
			stage.setMinWidth(250);
			stage.setOnCloseRequest(evt -> {
				onCloseHandlers.forEach(h -> h.handle(evt));
			});

			stage.setTitle("Drive Analystics Dashboard");
			
			pollingTimer = new Timer();
			pollingTimer.setInterval(1000);
			pollingTimer.setNumOfTicks(500);
			pollingTimer.start();
		}

		stage.show();
	}

	public void close() {
		System.out.println("calling close method");
		if (stage != null)
			stage.close();
	}

	public void addOnCloseEventHandler(EventHandler<WindowEvent> evt) {
		onCloseHandlers.add(evt);
	}

	public void removeOnCloseEventHandler(EventHandler<WindowEvent> evt) {
		onCloseHandlers.remove(evt);
	}

	public void addSensorFactory(ISensorFactory sf) {
		if (getSensorViewByName(sf.getSensorType()) != null)
			return;

		SensorView view = new SensorView(sf);
		sensorViews.add(view);
		if (currentSensorView == null)
			setCurrentSensorView(view);

		Button button = new Button();
		button.setId("btn"+ sf.getSensorType());
		button.setTooltip(new Tooltip(sf.getSensorType()));
		button.setGraphic(new ImageView(sf.getSensorIcon()));
		button.setUserData(sf.getSensorType());
		button.setOnAction(evt -> toolbarButtonPressed(evt));
		toolBar.getItems().add(button);	
	}
	
	public void removeSensorFactory(ISensorFactory sf) {
		String name = sf.getSensorType();

		Iterator<ISensorView> sensorIterator = sensorViews.iterator();
		
		String sensorType = "";
		while (sensorIterator.hasNext()) {
			ISensorView view = sensorIterator.next();
			if (view.getFactory().getSensorType().equals(name)) {
				sensorIterator.remove();
				sensorType = view.getFactory().getSensorType();
			}
				
		}
		
		toolBar.getItems().remove(getToolBarButtonByName(sensorType));
		
	    // select a different shape factory.
	    if (sensorViews.size() > 0)
	    	setCurrentSensorView(sensorViews.iterator().next());
	    else
	    	setCurrentSensorView(null);
	}

	private void toolbarButtonPressed(ActionEvent evt) {
		String name = (String) ((Button) evt.getSource()).getUserData();
		setCurrentSensorView(getSensorViewByName(name));
	}

	private ISensorView getSensorViewByName(String name) {
		for (ISensorView sv : sensorViews) {
			if (sv.getFactory().getSensorType().equals(name))
				return sv;
		}

		return null;
	}

	private Button getToolBarButtonByName(String name) {
		for (Node n : toolBar.getItems())
			if (name.equals(n.getUserData()))
				return (Button) n;

		return null;
	}

	private void setCurrentSensorView(ISensorView sv) {
		currentSensorView = sv;
		
		if (sv == null) {
			return;
		}

		pollingTimer.addTimerListener(new TimerListener() {
			public void expired(TimerEvent event) {
				if (currentSensorView == null)
					return;
				
				ISensor sensor = currentSensorView.getFactory().getInstance();
				
				switch (sensor.getDataFormat()) {
					default:
					case ABSOLUTE: 
						long absolute = Convert.bytesToLong(sensor.getData());
						currentSensorView.update(absolute);
						break;
						
					case PERCENT:
						double percentage = Convert.bytestoDouble(sensor.getData());
						currentSensorView.update(percentage);
					break;	
				} 
			}
		});
		
		Button button = getToolBarButtonByName(sv.getFactory().getSensorType());
		if (button != null)
			button.requestFocus();
		
		btnSwitch.setUserData(sv.getFactory().getSensorType());
		btnSwitch.setOnAction(evt -> switchSensorFormat(evt));
		
		attachSensorView(sv.getSensorPane(SensorDataFormat.ABSOLUTE));
	}
	
	private void switchSensorFormat(ActionEvent evt) {
		String name = (String) ((Button) evt.getSource()).getUserData();
		ISensorView sv = getSensorViewByName(name);
		SensorDataFormat format = sv.getFactory().getInstance().getDataFormat();
		
		if (format != SensorDataFormat.ABSOLUTE)
			format = SensorDataFormat.ABSOLUTE;
		else
			format = SensorDataFormat.PERCENT;
		
		sv.getFactory().getInstance().setDataFormat(format);
		attachSensorView(sv.getSensorPane(format));
	}
	
	private void attachSensorView(Pane pane) {
		if (rootPane.getChildren().size() > 2)
			rootPane.getChildren().remove(2);
		
		rootPane.getChildren().add(pane);
	}
}

package at.swt6.main;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import at.swt6.sensor.ISensorFactory;
import at.swt6.utils.JavaFxUtils;

public class WindowActivator implements BundleActivator {
	private MainWindow	mainWindow;
	private ServiceTracker<ISensorFactory, ISensorFactory> sensorTracker;
	
	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Start Window Activator");
		JavaFxUtils.initJavaFx();
		JavaFxUtils.runAndWait(() -> startUI(context));
		
		sensorTracker = new ServiceTracker<>(context, ISensorFactory.class, new SensorTrackerCustomizer(context, mainWindow));
		sensorTracker.open();
	}
	
	private void startUI(BundleContext context) {
		System.out.println("Start UI");
		mainWindow = new MainWindow();
		mainWindow.show();
		
		mainWindow.addOnCloseEventHandler(evt -> {
			try {
				context.getBundle().stop();
				stopUI(context);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		//mainWindow.addSensorFactory(new DistanceSensor());
		//mainWindow.addSensorFactory(new TempratureSensor());
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stop Window Activator");
		JavaFxUtils.runAndWait(() -> stopUI(context));
		sensorTracker.close();
	}
	
	private void stopUI(BundleContext context) {
		mainWindow.close();
	}
}

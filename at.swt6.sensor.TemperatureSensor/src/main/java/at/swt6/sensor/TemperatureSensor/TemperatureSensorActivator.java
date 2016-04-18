package at.swt6.sensor.TemperatureSensor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import at.swt6.sensor.ISensorFactory;

public class TemperatureSensorActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Distance Sensor Activator");
		context.registerService(ISensorFactory.class, new TemperatureSensorFactory(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

}

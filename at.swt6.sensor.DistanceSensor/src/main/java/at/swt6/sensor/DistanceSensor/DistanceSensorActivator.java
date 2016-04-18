package at.swt6.sensor.DistanceSensor;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import at.swt6.sensor.ISensorFactory;

public class DistanceSensorActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		System.out.println("Distance Sensor Activator");
		context.registerService(ISensorFactory.class, new DistanceSensorFactory(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	}

}

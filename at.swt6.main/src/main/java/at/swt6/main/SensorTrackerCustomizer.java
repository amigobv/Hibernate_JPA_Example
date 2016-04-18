package at.swt6.main;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import at.swt6.sensor.ISensorFactory;
import at.swt6.utils.JavaFxUtils;

public class SensorTrackerCustomizer implements ServiceTrackerCustomizer<ISensorFactory, ISensorFactory> {

	private static enum Action {
		ADDED, MODIFIED, REMOVED
	}
	
	private BundleContext context;
	private MainWindow window;
	
	public SensorTrackerCustomizer(BundleContext context, MainWindow window) {
		this.context = context;
		this.window = window;
	}
	
	  @Override
	  public ISensorFactory addingService(ServiceReference<ISensorFactory> ref) {
		ISensorFactory sf = context.getService(ref);
	    processEventInUIThread(Action.ADDED, ref, sf);
	    return sf;
	  }

	  @Override
	  public void modifiedService(ServiceReference<ISensorFactory> ref, ISensorFactory sf) {
	    processEventInUIThread(Action.MODIFIED, ref, sf);
	  }

	  @Override
	  public void removedService(ServiceReference<ISensorFactory> ref, ISensorFactory sf) {
	    processEventInUIThread(Action.REMOVED, ref, sf);
	  }

	  private void processEvent(Action action, ServiceReference<ISensorFactory> ref, ISensorFactory sf) {
	    switch (action) {
	    case MODIFIED:
	      window.removeSensorFactory(sf);
	      window.addSensorFactory(sf);
	      break;
	      
	    case ADDED:
	      window.addSensorFactory(sf);
	      break;

	    case REMOVED:
	      window.removeSensorFactory(sf);
	      break;
	    }
	  }

	  private void processEventInUIThread(final Action action,
	      final ServiceReference<ISensorFactory> ref, final ISensorFactory sf) {
	    try {
	      JavaFxUtils.runAndWait(() -> processEvent(action, ref, sf));
	    }
	    catch (Exception ex) {
	      ex.printStackTrace();
	    }
	  }
}

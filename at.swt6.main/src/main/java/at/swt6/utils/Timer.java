package at.swt6.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Timer {
	private int numOfTicks = 1;
	
	private final AtomicInteger tickInterval = new AtomicInteger(1000);
	private final AtomicInteger tickCount = new AtomicInteger(0);
	private final AtomicBoolean stopTimer = new AtomicBoolean(false);
	private final AtomicReference<Thread> tickerThread = new AtomicReference<Thread>(null);
	
	private final Vector<TimerListener> listener = new Vector<>();
	private final PropertyChangeSupport changer = new PropertyChangeSupport(this);
	
	public Timer() {
		// nothing to do
	}
	
	public boolean isRunning() {
		return tickerThread.get() != null;
	}
	
	public void stop() {
		stopTimer.set(true);
	}
	
	public void reset() {
		if (isRunning()) {
			throw new IllegalStateException("Cannot reset while timer is running!");
		}
		
		tickCount.set(0);
	}
	
	public int getTickCount() {
		return tickCount.get();
	}
	
	public int getInterval() {
		return tickInterval.get();
	}
	
	public void setInterval(int interval) {
		int oldValue = tickInterval.get(); 
		if (interval != oldValue) {
			tickInterval.set(interval);
			firePropertyChange("interval", oldValue, interval);
		}
	}
	
	public int getNumOfTicks() {
		return numOfTicks;
	}
	
	public void setNumOfTicks(int ticks) {
		int oldValue = numOfTicks;
		numOfTicks = ticks;
		firePropertyChange("numOfTicks", oldValue, ticks);
	}
	
	public void addTimerListener(TimerListener listener) {
		this.listener.addElement(listener);
	}
	
	public void removeTimerListener(TimerListener listener) {
		this.listener.remove(listener);
	}
	
	protected void fireEvent(TimerEvent e) {
		for(TimerListener l : (Vector<TimerListener>)listener.clone()) {
			l.expired(e);
		}
	}
	
	public void start() {
		if (isRunning()) {
			throw new IllegalStateException("Cannot start: timer is already running!");
		}
		
		final int ticks = numOfTicks;
		tickerThread.set(new Thread(() -> {
			tickCount.set(0);
			
			while (!stopTimer.get() && tickCount.get() < ticks) {
				try {
					Thread.sleep(tickInterval.get());
				} catch (Exception e) {
					
				}
				
				if (!stopTimer.get())
					fireEvent(new TimerEvent(this, tickCount.incrementAndGet(), ticks));
			}
			
			stopTimer.set(false);
			tickerThread.set(null);
		}));
		
		tickerThread.get().start();
	}
	
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changer.addPropertyChangeListener(l);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l) {
		changer.removePropertyChangeListener(l);
	}
	
	private void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		changer.firePropertyChange(propertyName, oldValue, newValue);
	}
}

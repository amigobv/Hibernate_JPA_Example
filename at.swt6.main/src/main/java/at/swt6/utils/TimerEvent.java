package at.swt6.utils;

import java.util.EventObject;

public class TimerEvent extends EventObject {
	private final int numTicks;
	private final int tickCount;
	
	public TimerEvent(Object source, int ticks, int tickCount) {
		super(source);
		
		this.numTicks = ticks;
		this.tickCount = tickCount;
	}
	
	public int getNumTicks() {
		return numTicks;
	}
	
	public int getTickCount() {
		return tickCount;
	}
}

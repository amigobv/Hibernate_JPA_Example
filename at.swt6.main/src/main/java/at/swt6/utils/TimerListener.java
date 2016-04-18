package at.swt6.utils;

import java.util.EventListener;

public interface TimerListener extends EventListener {
	void expired(TimerEvent event);
}

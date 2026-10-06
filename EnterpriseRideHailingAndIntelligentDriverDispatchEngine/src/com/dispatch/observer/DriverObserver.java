package com.dispatch.observer;

import com.dispatch.model.Location;

public interface DriverObserver {
	void onRideRequested(String rideld,Location pickup);
	

}

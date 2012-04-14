package net.kaczmarzyk.moose.core.common;

import java.util.Date;


public class SystemClock implements Clock {

	@Override
	public Date currentTime() {
		return new Date();
	}

	@Override
	public long currentMillis() {
		return System.currentTimeMillis();
	}

}

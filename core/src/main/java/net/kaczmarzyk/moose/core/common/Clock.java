package net.kaczmarzyk.moose.core.common;

import java.util.Date;


public interface Clock {
	
	Date currentTime();
	long currentMillis();
}

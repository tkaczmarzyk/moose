package net.kaczmarzyk.moose.core.common;

import java.util.Date;


public class MockClock implements Clock {

	private Date date;
	
	
	private MockClock() {
	}
	
	@Override
	public Date currentTime() {
		return date;
	}

	@Override
	public long currentMillis() {
		return date.getTime();
	}

	public MockClock forward(long millis) {
		date = new Date(date.getTime() + millis);
		return this;
	}
	
	public MockClock time(Date time) {
		this.date = time;
		return this;
	}
	
	public static MockClock of(Date date) {
		MockClock clock = new MockClock();
		clock.date = date;
		return clock;
	}
}

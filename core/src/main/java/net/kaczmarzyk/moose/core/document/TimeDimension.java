package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.kaczmarzyk.moose.core.common.Clock;
import net.kaczmarzyk.moose.core.common.SystemClock;


public class TimeDimension implements Dimension<Date>{

	private Clock clock = new SystemClock();
	
	private List<Date> values = new ArrayList<>();
	
	
	public TimeDimension() {
	}
	
	TimeDimension(Clock clock) {
		this.clock = clock;
	}
	
	@Override
	public Coordinate extend() {
		int currTime = values.indexOf(clock.currentTime());
		if (currTime >= 0) {
			return Coordinate.abs(this, currTime);
		} else {
			values.add(clock.currentTime());
			return Coordinate.abs(this, values.size() -1);
		}
	}

	@Override
	public void extend(List<Date> values) {
		this.values.addAll(values);
	}

	@Override
	public Date get(int shift) {
		return values.get(shift);
	}

	@Override
	public List<Date> getValues() {
		return values;
	}

	@Override
	public int hashCode() {
		return TimeDimension.class.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.getClass() == TimeDimension.class;
	}
	
	@Override
	public String toString() {
		return "Time";
	}

	@Override
	public boolean isTransparent() {
		return true;
	}
}

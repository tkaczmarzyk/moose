package net.kaczmarzyk.moose.core.document;

import java.util.List;


public interface Dimension<T> {

	Coordinate extend();
	
//	void extend(int shift, T value);
	void extend(List<T> values);

	T get(int shift);

}

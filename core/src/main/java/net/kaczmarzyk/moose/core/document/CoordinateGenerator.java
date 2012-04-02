package net.kaczmarzyk.moose.core.document;

import java.util.Iterator;


public abstract class CoordinateGenerator<T> implements Iterator<Coordinate<T>> {

	@Override
	public final void remove() {
		throw new UnsupportedOperationException();
	}
}

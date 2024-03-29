package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Path {
	
	public static final Path IN_PLACE = new Path();
	
	private List<String> propertyChain;
	
	
	private Path() {
		propertyChain = Collections.emptyList();
	}
	
	private Path(String... properties) {
		this(new ArrayList<>(Arrays.asList(properties)));
	}
	
	public Path(List<String> propertyChain) {
		this.propertyChain = propertyChain;
	}

	public Path getObjectPath() {
		if (propertyChain.size() == 0) {
			return IN_PLACE;
		}
//		else if (propertyChain.size() == 1) {
//			return new Path(propertyChain.get(0));
//		}
//		else {
			return new Path(propertyChain.subList(0, propertyChain.size() - 1));
//		}
	}

	public Path getLastProperty() {
		if (propertyChain.size() == 0) {
			return IN_PLACE;
		}
		return new Path(propertyChain.get(propertyChain.size() - 1));
	}
	
	public boolean isInPlace() {
		return propertyChain.isEmpty();
	}

	public Path step() {
		return new Path(propertyChain.subList(1, propertyChain.size()));
	}

	private Path copy() {
		return new Path(new ArrayList<>(propertyChain));
	}

	public static Path of(String... properties) {
		return new Path(properties);
	}

	public Path with(String... properties) {
		Path copy = copy();
		copy.propertyChain.addAll(Arrays.asList(properties));
		return copy;
	}

	@Override
	public String toString() {
		return "Path" + propertyChain;
	}
	
	@Override
	public int hashCode() {
		return propertyChain.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Path) {
			return propertyChain.equals(((Path)obj).propertyChain);
		} else {
			return false;
		}
	}

	public String getFirstProperty() {
		return propertyChain.get(0);
	}

	public boolean isSingleProperty() {
		return propertyChain.size() == 1;
	}
	
	public DataObject getValue(DataObject start) {
		DataObject end = start;
		for (String property : propertyChain) {
			end = end.getProperty(property);
		}
		return end;
	}
}

package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Path {
	
	public static final Path IN_PLACE = new Path();
	
	private List<String> propertyChain;
	
	
	public Path(String... properties) {
		this(new ArrayList<>(Arrays.asList(properties)));
	}
	
	public Path(List<String> propertyChain) {
		this.propertyChain = propertyChain;
	}

	public List<String> getPropertyChain() {
		return propertyChain;
	}

	public Path getObjectPath() {
		return new Path(propertyChain.subList(0, propertyChain.size() - 1));
	}

	public Path getLastProperty() {
		return new Path(propertyChain.get(propertyChain.size() - 1));
	}
	
	public boolean isSingleProperty() {
		return propertyChain.size() == 1;
	}
	
	public boolean isInPlace() {
		return propertyChain.isEmpty();
	}
}

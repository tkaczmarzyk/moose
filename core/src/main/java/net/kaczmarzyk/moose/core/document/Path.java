package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Path {
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
}

package net.kaczmarzyk.moose.core.function;

import java.util.Collection;


public interface FunctionRegistry {

	Function get(String functionName);
	
	Collection<String> getAvailableFunctions();
}

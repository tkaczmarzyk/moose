package net.kaczmarzyk.moose.core.function;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


public class SpringFunctionRegistry implements FunctionRegistry {

	private Map<String, Function> functions = new HashMap<>();
	
	
	@Autowired
	public SpringFunctionRegistry(List<Function> functions) {
		for (Function function :functions) {
			this.functions.put(function.getName(), function);
		}
	}
	
	@Override
	public Function get(String functionName) {
		return functions.get(functionName);
	}

	@Override
	public Collection<String> getAvailableFunctions() {
		return functions.keySet();
	}

}

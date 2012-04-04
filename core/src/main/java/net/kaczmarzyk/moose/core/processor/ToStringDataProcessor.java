package net.kaczmarzyk.moose.core.processor;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.ScalarDataObject;


public class ToStringDataProcessor implements DataProcessor<String> {

	@Override
	public String process(ScalarDataObject<?> scalarData) {
		return "(" + scalarData.getValue().toString() + ")";
	}
	
	@Override
	public String process(DataObject data) {
		StringBuilder result = new StringBuilder("(");
		for (String property : data.getPropertyNames()) {
			result.append(property).append("=");
			result.append(data.getProperty(property).accept(this));
			result.append(", ");
		}
		if (result.length() > 2) {
			result.replace(result.length() - 2, result.length(), "");
		}
		result.append(")");
		return result.toString();
	}

}

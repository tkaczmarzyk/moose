package net.kaczmarzyk.moose.core.processor;

import java.util.HashMap;
import java.util.Map;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Path;
import net.kaczmarzyk.moose.core.document.Scalar;


public class MapDataProcessor implements DataProcessor<Object> {

	@Override
	public Object process(Scalar<?> scalarData) {
		return scalarData.getValue();
	}

	@Override
	public Object process(DataObject data) {
		Map<String, Object> result = new HashMap<String, Object>();
		for (String property : data.getPropertyNames()) {
			result.put(property, data.getProperty(property).accept(this));
		}
		return result;
	}

}

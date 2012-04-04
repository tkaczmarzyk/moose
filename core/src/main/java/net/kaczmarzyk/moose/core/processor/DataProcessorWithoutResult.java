package net.kaczmarzyk.moose.core.processor;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.ScalarDataObject;


public abstract class DataProcessorWithoutResult implements DataProcessor<Object> {

	@Override
	public final Object process(ScalarDataObject<?> scalarData) {
		doProcess(scalarData);
		return null;
	}

	@Override
	public final Object process(DataObject data) {
		doProcess(data);
		return null;
	}

	protected abstract void doProcess(DataObject data);
	protected abstract void doProcess(ScalarDataObject<?> scalarData);
}

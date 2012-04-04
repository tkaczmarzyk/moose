package net.kaczmarzyk.moose.core.processor;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Scalar;


public abstract class DataProcessorWithoutResult implements DataProcessor<Object> {

	@Override
	public final Object process(Scalar<?> scalarData) {
		doProcess(scalarData);
		return null;
	}

	@Override
	public final Object process(DataObject data) {
		doProcess(data);
		return null;
	}

	protected abstract void doProcess(DataObject data);
	protected abstract void doProcess(Scalar<?> scalarData);
}

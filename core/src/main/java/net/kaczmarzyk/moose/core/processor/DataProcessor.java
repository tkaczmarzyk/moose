package net.kaczmarzyk.moose.core.processor;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.DataObjectReference;
import net.kaczmarzyk.moose.core.document.ScalarDataObject;


public interface DataProcessor<T> { // TODO abstarct class for recursive processing, strategy for returning

	T process(ScalarDataObject scalarData);
	
	T process(DataObject data);

	T process(DataObjectReference reference);
}

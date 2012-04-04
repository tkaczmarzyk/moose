package net.kaczmarzyk.moose.core.processor;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.ScalarDataObject;


public interface DataProcessor<T> { // TODO abstarct class for recursive processing, strategy for returning

	T process(ScalarDataObject<?> scalarData); // TODO gentle handling of different types of scalar data
	
	T process(DataObject data);

}

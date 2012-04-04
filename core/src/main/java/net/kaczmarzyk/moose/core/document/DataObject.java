package net.kaczmarzyk.moose.core.document;

import java.util.List;

import net.kaczmarzyk.moose.core.processor.DataProcessor;


public interface DataObject {

	List<String> getPropertyNames();
	
	DataObject getProperty(String propName); // FIXME nested data objects! // TODO paths?
	
	<T> T accept(DataProcessor<T> visitor);
}

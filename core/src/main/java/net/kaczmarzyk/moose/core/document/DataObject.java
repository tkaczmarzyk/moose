package net.kaczmarzyk.moose.core.document;

import java.util.List;

import net.kaczmarzyk.moose.core.processor.DataProcessor;


public interface DataObject {

	List<String> getPropertyNames();
	
	DataObject getProperty(String propName);
	
	<T> T accept(DataProcessor<T> visitor);
	
	void refresh(Sheet sheet, CellAddress addr); // FIXME i don't like the parameters// FIXME cell or obj address?

	DataObject getProperty(Path path);
}

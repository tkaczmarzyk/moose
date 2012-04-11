package net.kaczmarzyk.moose.core.document;

import java.util.List;

import net.kaczmarzyk.moose.core.common.Copyable;
import net.kaczmarzyk.moose.core.processor.DataProcessor;


public interface DataObject extends Copyable<DataObject> {

	List<String> getPropertyNames();
	
	DataObject getProperty(String propName);
	DataObject getProperty(Path path);

	void setProperty(Path path, DataObject obj);
	
	<T> T accept(DataProcessor<T> visitor);
	
	void refresh(CellAddress addr);
	void placedAtAddress(ObjectAddress address);
	
	DataObject copy();
}

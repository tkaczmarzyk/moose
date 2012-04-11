package net.kaczmarzyk.moose.core.document;

import java.util.List;

import net.kaczmarzyk.moose.core.common.Copyable;
import net.kaczmarzyk.moose.core.processor.DataProcessor;


public interface DataObject extends Copyable<DataObject> {

	List<String> getPropertyNames();
	
	DataObject getProperty(String propName);
	
	<T> T accept(DataProcessor<T> visitor);
	
	void refresh(CellAddress addr);

	DataObject getProperty(Path path);

	DataObject copy();

	void setProperty(Path path, DataObject obj);

	void placedAtAddress(ObjectAddress address);
}

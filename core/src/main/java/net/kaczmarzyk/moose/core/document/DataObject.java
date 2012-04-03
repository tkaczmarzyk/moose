package net.kaczmarzyk.moose.core.document;

import java.util.List;


public interface DataObject {

	List<String> getPropertyNames();
	
	Data<?> getProperty(String propName);
}

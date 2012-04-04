package net.kaczmarzyk.moose.core.operator;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;


public interface DocumentOperator {

	void assign(String coordinatesDef, String valueDefinition);
	String getDefinition(String coordinatesDef);
	DataObject getValue(String coodinatesDef);
	
	Document getDocument();
}

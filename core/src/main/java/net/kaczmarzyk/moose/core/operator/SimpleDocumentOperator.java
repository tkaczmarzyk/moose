package net.kaczmarzyk.moose.core.operator;

import net.kaczmarzyk.moose.core.document.Coordinates;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.parser.CoordinatesParser;
import net.kaczmarzyk.moose.core.parser.DataObjectParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


@Configurable
public class SimpleDocumentOperator implements DocumentOperator {

	@Autowired
	private DataObjectParser objectParser;
	
	@Autowired
	private CoordinatesParser coordsParser;

	private Document doc;
	
	
	public SimpleDocumentOperator(String documentName) {
		doc = new Document(documentName);
	}
	
	@Override
	public void assign(String coordinates, String valueDefinition) {
		Coordinates coords = coordsParser.parse(coordinates);
		DataObject value = objectParser.parse(doc, valueDefinition);
		
		doc.getCell(coords).setValue(value);
	}

	@Override
	public Document getDocument() {
		return doc;
	}

	@Override
	public String getDefinition(String coordinatesDef) {
		throw new UnsupportedOperationException(); //TODO
	}

	@Override
	public DataObject getValue(String coodinatesDef) {
		return doc.getCell(coordsParser.parse(coodinatesDef)).getValue();
	}
	
}

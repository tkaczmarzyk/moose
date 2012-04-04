package net.kaczmarzyk.moose.core.operator;

import net.kaczmarzyk.moose.core.document.Coordinates;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.parser.CoordinatesParser;
import net.kaczmarzyk.moose.core.parser.DataObjectParser;
import net.kaczmarzyk.moose.core.processor.DataProcessor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


@Configurable
public class SimpleDocumentOperator<T> implements DocumentOperator {

	@Autowired
	private DataObjectParser objectParser;
	
	@Autowired
	private CoordinatesParser coordsParser;

	private DataProcessor<T> dataProcessor;
	
	private Document doc;
	
	
	public SimpleDocumentOperator(String documentName, DataProcessor<T> dataProcessor) {
		this.doc = new Document(documentName);
		this.dataProcessor = dataProcessor;
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
	public DataObject getValue(String coordinatesDef) {
		return doc.getCell(coordsParser.parse(coordinatesDef)).getValue();
	}
	
	public T getProcessedValue(String coordinatesDef) {
		return getValue(coordinatesDef).accept(dataProcessor);
	}
}

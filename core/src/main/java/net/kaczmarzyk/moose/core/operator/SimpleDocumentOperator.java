package net.kaczmarzyk.moose.core.operator;

import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.Sheet;
import net.kaczmarzyk.moose.core.parser.AddressParser;
import net.kaczmarzyk.moose.core.parser.DataObjectParser;
import net.kaczmarzyk.moose.core.processor.DataProcessor;
import net.kaczmarzyk.moose.core.recalc.Recalculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;


@Configurable
public class SimpleDocumentOperator<T> implements DocumentOperator {

	@Autowired
	private DataObjectParser objectParser;
	
	@Autowired
	private AddressParser coordsParser;
	
	@Autowired
	private Recalculator recalculator;

	private DataProcessor<T> dataProcessor;
	
	private Document doc;
	private Sheet currentSheet;
	
	public SimpleDocumentOperator(String documentName, DataProcessor<T> dataProcessor) {
		this.doc = new Document(documentName);
		currentSheet = doc.getSheets().get(0);
		this.dataProcessor = dataProcessor;
	}
	
	@Override
	public void assign(String coordinatesDef, String valueDefinition) {
		ObjectAddress coords = coordsParser.parse(currentSheet, coordinatesDef);
		DataObject value = objectParser.parse(currentSheet, valueDefinition);
		
		doc.getCell(coords.getCellAddr()).setValue(value);
		
		recalculator.recalculate(doc);
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
		return doc.getCell(coordsParser.parse(currentSheet, coordinatesDef).getCellAddr()).getValue();
	}
	
	public T getProcessedValue(String coordinatesDef) {
		return getValue(coordinatesDef).accept(dataProcessor);
	}
}

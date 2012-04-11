package net.kaczmarzyk.moose.core.operator;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
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
	private AddressParser addrParser;
	
	@Autowired
	private Recalculator recalculator;

	private DataProcessor<? extends T> dataProcessor;
	
	private Document doc;
	private Sheet currentSheet;
	
	public SimpleDocumentOperator(String documentName, DataProcessor<T> dataProcessor) {
		this.doc = new Document(documentName);
		currentSheet = doc.getSheets().get(0);
		this.dataProcessor = dataProcessor;
	}
	
	@Override
	public SimpleDocumentOperator<T> assign(String coordinatesDef, String valueDefinition) {
		ObjectAddress coords = addrParser.parse(currentSheet, coordinatesDef);
		DataObject value = objectParser.parse(currentSheet, valueDefinition);
		
		coords.getCellAddr().getCell().put(value);
		
		recalculator.recalculate(doc);
		
		return this;
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
		return addrParser.parse(currentSheet, coordinatesDef).getCellAddr().getCell().getValue();
	}
	
	public T getProcessedValue(String coordinatesDef) {
		return getValue(coordinatesDef).accept(dataProcessor);
	}
	
	public void setDataProcessor(DataProcessor<? extends T> processor) {
		this.dataProcessor = processor;
	}

	public void copy(String sourceAddrDef, String targetAddrRef) {
		ObjectAddress sourceAddr = addrParser.parse(currentSheet, sourceAddrDef);
		ObjectAddress targetAddr = addrParser.parse(currentSheet, targetAddrRef);
		DataObject obj = sourceAddr.getObject();
		targetAddr.put(obj.copy());
		recalculator.recalculate(doc);
	}
}

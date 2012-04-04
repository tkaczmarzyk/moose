package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.DataObjectReference;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.ScalarDataObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PocDataObjectParser implements DataObjectParser {

	@Autowired
	private CoordinatesParser coordsParser;
	
	
	@Override
	public DataObject parse(Document doc, String input) {
		if (!input.startsWith("=")) {
			return new ScalarDataObject(doc, input) ;
		}
		else {
			return evaluate(doc, input.substring(1));
		}
	}

	private DataObject evaluate(Document doc, String coordsDef) {
		return new DataObjectReference(doc, coordsParser.parse(coordsDef)); //FIXME null
	}

}

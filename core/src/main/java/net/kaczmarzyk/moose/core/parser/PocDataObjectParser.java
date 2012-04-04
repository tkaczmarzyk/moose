package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.Formula;
import net.kaczmarzyk.moose.core.document.MapData;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.expression.ObjectReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PocDataObjectParser implements DataObjectParser {

	@Autowired
	private AddressParser coordsParser;
	
	
	@Override
	public DataObject parse(Document doc, String input) {
		if (!input.startsWith("=")) {
			if (!input.contains(":")) {
				return new Scalar<String>(doc, input);
			}
			else {
				return parseMapData(doc, input);
			}
		}
		else {
			return evaluate(doc, input.substring(1));
		}
	}

	private DataObject parseMapData(Document doc, String input) {
		MapData map = new MapData(doc);
		for (String pair : input.split(",")) {
			String[] keyval = pair.split(":");
			map.put(keyval[0], new Scalar<String>(doc, keyval[1]));
		}
		return map;
	}

	private DataObject evaluate(Document doc, String coordsDef) {
		return new Formula(doc, new ObjectReference(coordsParser.parse(coordsDef)));
	}

}

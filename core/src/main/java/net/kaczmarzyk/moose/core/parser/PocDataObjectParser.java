package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Formula;
import net.kaczmarzyk.moose.core.document.MapData;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.document.Sheet;
import net.kaczmarzyk.moose.core.expression.ObjectReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PocDataObjectParser implements DataObjectParser {

	@Autowired
	private AddressParser coordsParser;
	
	
	@Override
	public DataObject parse(Sheet sheet, String input) {
		if (!input.startsWith("=")) {
			if (!input.contains(":")) {
				return new Scalar<String>(sheet, input);
			}
			else {
				return parseMapData(sheet, input);
			}
		}
		else {
			return evaluate(sheet, input.substring(1));
		}
	}

	private DataObject parseMapData(Sheet sheet, String input) {
		MapData map = new MapData(sheet);
		for (String pair : input.split(",")) {
			String[] keyval = pair.split(":");
			map.put(keyval[0], new Scalar<String>(sheet, keyval[1]));
		}
		return map;
	}

	private DataObject evaluate(Sheet sheet, String coordsDef) {
		return new Formula(sheet, new ObjectReference(coordsParser.parse(sheet, coordsDef)));
	}

}

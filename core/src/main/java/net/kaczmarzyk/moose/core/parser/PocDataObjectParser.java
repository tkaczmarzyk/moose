package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Formula;
import net.kaczmarzyk.moose.core.document.MapData;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.document.Sheet;
import net.kaczmarzyk.moose.core.expression.Expression;
import net.kaczmarzyk.moose.core.expression.FunctionCall;
import net.kaczmarzyk.moose.core.expression.ObjectReference;
import net.kaczmarzyk.moose.core.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class PocDataObjectParser implements DataObjectParser {

	@Autowired
	private AddressParser coordsParser;
	
	@Autowired @Qualifier("abs")
	private Function abs;
	
	
	@Override
	public DataObject parse(Sheet sheet, String input) {
		if (!input.startsWith("=")) {
			if (!input.contains(":")) {
				try {
					Double d = Double.parseDouble(input);
					return new Scalar<Double>(sheet, d);
				} catch (NumberFormatException e) {
					return new Scalar<String>(sheet, input);
				}
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

	private DataObject evaluate(Sheet sheet, String exprDef) {
		return new Formula(sheet, refOrAbs(sheet, exprDef));
	}

	private Expression refOrAbs(Sheet sheet, String exprDef) {
		if (exprDef.startsWith("abs")) {
			return new FunctionCall(abs, refOrAbs(sheet, exprDef.substring(4, exprDef.length()-1)));
		}
		else {
			return new ObjectReference(coordsParser.parse(sheet, exprDef));
		}
	}

}

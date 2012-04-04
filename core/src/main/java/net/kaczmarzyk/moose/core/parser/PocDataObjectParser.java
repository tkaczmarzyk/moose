package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.Formula;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.expression.ObjectReferenceExpression;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PocDataObjectParser implements DataObjectParser {

	@Autowired
	private CoordinatesParser coordsParser;
	
	
	@Override
	public DataObject parse(Document doc, String input) {
		if (!input.startsWith("=")) {
			return new Scalar<String>(doc, input) ;
		}
		else {
			return evaluate(doc, input.substring(1));
		}
	}

	private DataObject evaluate(Document doc, String coordsDef) {
		return new Formula(doc, new ObjectReferenceExpression(coordsParser.parse(coordsDef)));
	}

}

package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.DataObjectReference;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.Coordinates;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.ScalarDataObject;


/**
 * Handles just {@code =ref} and {@code =ref1+ref2} with following limitations:
 *  <li>reference can include only column and row dimensions</li>
 *  <li>only first sheet in document is used</li>
 */
public class PocExpressionEvaluator implements ExpressionEvaluator {

	@Override
	public DataObject evaluate(String input, Document doc) {
		if (isExpression(input)) {
			if (input.contains("+")) {
				return evaluateAddiction(input.substring(1), doc);
			}
			else {
				return evaluateSingleRef(input.substring(1), doc);
			}
		}
		else {
			return new ScalarDataObject(input);
		}
	}

	private DataObject evaluateAddiction(String expression, Document doc) {
		throw new UnsupportedOperationException("not implemented yet");
	}

	private DataObject evaluateSingleRef(String expression, Document doc) {
		Coordinate<String> columnCoord = Coordinate.of(expression.substring(0,1));
		Coordinate<Integer> rowCoord = Coordinate.of(Integer.parseInt(expression.substring(1,2)));
		Coordinates coords = new Coordinates(columnCoord, rowCoord);
		return new DataObjectReference(coords, doc);
	}
	
	private boolean isExpression(String input) {
		return input.startsWith("=");
	}

}

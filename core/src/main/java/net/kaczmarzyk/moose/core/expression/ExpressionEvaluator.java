package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;


/**
 * temporary facade for evaluations
 */
public interface ExpressionEvaluator {

	DataObject evaluate(String input, Document doc);
}

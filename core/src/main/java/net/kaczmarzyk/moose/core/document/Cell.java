package net.kaczmarzyk.moose.core.document;

import net.kaczmarzyk.moose.core.expression.ExpressionEvaluator;
import net.kaczmarzyk.moose.core.expression.PocExpressionEvaluator;


public class Cell {

	private ExpressionEvaluator evaluator = new PocExpressionEvaluator();
	private DataObject value;
	
	private Document doc;

	
	public Cell(Document doc) {
		this.doc = doc;
	}
	
	public DataObject getValue() {
		return value;
	}
	
	public void clear() {
		value = null;
	}

	public void setValue(String input) {
		value = evaluator.evaluate(input, doc);
	}
	
	public void setValues(DataObject value) {
		this.value = value;
	}
}

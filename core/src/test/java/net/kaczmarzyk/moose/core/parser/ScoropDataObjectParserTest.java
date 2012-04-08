package net.kaczmarzyk.moose.core.parser;

import static net.kaczmarzyk.moose.core.utils.DataObjectUtil.relObjAddr;
import static net.kaczmarzyk.moose.core.utils.ExpressionUtil.constant;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import net.kaczmarzyk.moose.core.document.Dimension;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.Formula;
import net.kaczmarzyk.moose.core.document.Path;
import net.kaczmarzyk.moose.core.document.Sheet;
import net.kaczmarzyk.moose.core.expression.AreaReference;
import net.kaczmarzyk.moose.core.expression.Expression;
import net.kaczmarzyk.moose.core.expression.FunctionCall;
import net.kaczmarzyk.moose.core.function.Abs;
import net.kaczmarzyk.moose.core.function.Add;
import net.kaczmarzyk.moose.core.function.Function;
import net.kaczmarzyk.moose.core.function.Neg;
import net.kaczmarzyk.moose.core.function.SpringFunctionRegistry;
import net.kaczmarzyk.moose.core.function.Sum;
import net.kaczmarzyk.moose.core.processor.MapDataProcessor;
import net.kaczmarzyk.moose.support.utils.ReflectionUtil;

import org.junit.Before;
import org.junit.Test;


public class ScoropDataObjectParserTest {

	private Document doc = new Document("test doc");
	private Sheet sheet1 = doc.getSheet("Sheet1");
	private Dimension<?> s1dimension1 = sheet1.getDimensions().get(0);
	private Dimension<?> s1dimension2 = sheet1.getDimensions().get(1);
	private Sheet sheet2 = doc.getSheet("Sheet2");
	private Dimension<?> s2dimension1 = sheet2.getDimensions().get(0);
	private Dimension<?> s2dimension2 = sheet2.getDimensions().get(1);
	
	private ScoropDataObjectParser parser = new ScoropDataObjectParser();
	
	
	@Before
	public void init() {
		parser.funRegistry = new SpringFunctionRegistry(Arrays.asList(new Add(), new Abs(), new Sum(), new Neg()));
	}
	
	
	@Test
	public void parse_shouldRecognizeFunctionCallWithThreeArguments() {
		Formula parsed = (Formula) parser.parse(sheet1, "=sum(2.0, 3.0, 5.0)");
		
		Expression expression = (Expression) ReflectionUtil.get(parsed, "expression");
		assertEquals(FunctionCall.class, expression.getClass());
		Function function = (Function) ReflectionUtil.get(expression, "function");
		assertEquals(parser.funRegistry.get("sum"), function);
		assertEquals(Arrays.asList(constant(2.0), constant(3.0), constant(5.0)), ReflectionUtil.get(expression, "args"));
	}
	
	@Test
	public void parse_shouldRecognizeFunctionCallWithAConstantArg() {
		Formula parsed = (Formula) parser.parse(sheet1, "=abs(-2.0)");
		
		Expression expression = (Expression) ReflectionUtil.get(parsed, "expression");
		assertEquals(FunctionCall.class, expression.getClass());
		assertEquals(parser.funRegistry.get("abs"), ReflectionUtil.get(expression, "function"));
	}
	
	@Test
	public void parse_shouldRecognizeNegativeDoubleConstant() {
		Formula parsed = (Formula) parser.parse(sheet1, "=-2.0");
		parsed.refresh(null);
		
		assertEquals(-2.0, parsed.accept(new MapDataProcessor()));
	}
	
	@Test
	public void parse_shouldRecognizeDoubleConstant() {
		Formula parsed = (Formula) parser.parse(sheet1, "=2.0");
		parsed.refresh(null);
		
		assertEquals(2.0, parsed.accept(new MapDataProcessor()));
	}
	
	@Test
	public void parse_shouldRecognizeAreaReferenceWithPath() { // TODO should be allowed to have formula with just area ref?
		Formula parsed = (Formula) parser.parse(sheet1, "=C0R0:C2R2#bubu.foo");
		
		Expression expression = (Expression) ReflectionUtil.get(parsed, "expression");
		assertEquals(AreaReference.class, expression.getClass());
		
		assertEquals(relObjAddr(sheet1, Path.of("bubu", "foo"), 0, 0), ReflectionUtil.get(expression, "leftUpAddress"));
		assertEquals(relObjAddr(sheet1, Path.of("bubu", "foo"), 2, 2), ReflectionUtil.get(expression, "rightDownAddress"));
	}
	
	@Test
	public void parse_shouldRecognizeAreaReference() {
		Formula parsed = (Formula) parser.parse(sheet1, "=C0R0:C2R2");
		
		Expression expression = (Expression) ReflectionUtil.get(parsed, "expression");
		assertEquals(AreaReference.class, expression.getClass());
		
		assertEquals(relObjAddr(sheet1, Path.IN_PLACE, 0, 0), ReflectionUtil.get(expression, "leftUpAddress"));
		assertEquals(relObjAddr(sheet1, Path.IN_PLACE, 2, 2), ReflectionUtil.get(expression, "rightDownAddress"));
	}
}

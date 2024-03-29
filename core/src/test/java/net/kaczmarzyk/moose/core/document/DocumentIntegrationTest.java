package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.assertEquals;
import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.SpringTestBase;
import net.kaczmarzyk.moose.core.expression.AreaReference;
import net.kaczmarzyk.moose.core.expression.FunctionCall;
import net.kaczmarzyk.moose.core.expression.ObjectReference;
import net.kaczmarzyk.moose.core.function.Abs;
import net.kaczmarzyk.moose.core.function.Add;
import net.kaczmarzyk.moose.core.function.Sum;
import net.kaczmarzyk.moose.core.processor.MapDataProcessor;
import net.kaczmarzyk.moose.core.recalc.Recalculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;


@Category(Integration.class)
public class DocumentIntegrationTest extends SpringTestBase {

	@Autowired private Add add;
	@Autowired private Abs abs;
	@Autowired private Sum sum;
	
	@Autowired
	private Recalculator recalculator;
	
	private Document doc = new Document("test doc");
	private Sheet sheet = doc.getSheet("Sheet1");

	private CellAddress a1;

	private ObjectAddress xAddr;
	private ObjectAddress yAddr;

	private CellAddress a2;

	private FunctionCall xAddY;
	
	
	@Before
	public void initData() {
		a1 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 0), Coordinate.abs(sheet.rows(), 0));
		
		a1.getCell().put(new Scalar<Double>(2.0), Path.of("x"));
		a1.getCell().put(new Scalar<Double>(3.0), Path.of("y"));
		a1.getCell().put(new Scalar<Double>(7.0), Path.of("z", "a", "b"));
		a1.getCell().put(new Scalar<Double>(-7.0), Path.of("z", "a", "b2"));
		
		xAddr = new ObjectAddress(a1, Path.of("x"));
		yAddr = new ObjectAddress(a1, Path.of("y"));
		
		a2 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 0), Coordinate.abs(sheet.rows(), 1));
		
		xAddY = new FunctionCall(add, new ObjectReference(xAddr), new ObjectReference(yAddr));
	}
	
	@Test
	public void shouldBeAbleToHaveAMapDataWithScalarsAndFormula() {
		a1.getCell().put(new Formula(xAddY), Path.of("z"));
		recalculator.recalculate(doc);
		
		assertEquals(5.0, a1.getCell().getValue().getProperty("z").accept(new MapDataProcessor()));
	}
	
	@Test
	public void shouldBeAbleToHaveAFormulaReferencingNestedObject() {
		a2.getCell().put(new Formula(xAddY), Path.IN_PLACE);
		recalculator.recalculate(doc);
		
		assertEquals(5.0, a2.getCell().getValue().accept(new MapDataProcessor()));
	}
	
	@Test
	public void shouldBeAbleToPutValueOnNestedPath() {
		Path path = Path.of("a", "b", "c", "d");
		a2.getCell().put(new Scalar<>(2.0), path);
		
		assertEquals(2.0, path.getValue(a2.getCell().getValue()).accept(new MapDataProcessor()));
	}
	
	// TODO test for puting data into cell with data (overwriting)
	
	@Test
	public void shouldBeAbleToPutReferenceToPropertyOnNestedPath() {
		Path path = Path.of("a", "b", "c", "d");
		ObjectReference a1ObjRef = new ObjectReference(new ObjectAddress(a1, Path.of("x")));
		a2.getCell().put(new Formula(a1ObjRef), path);
		
		recalculator.recalculate(doc);
		
		assertEquals(2.0, path.getValue(a2.getCell().getValue()).accept(new MapDataProcessor()));
	}
	
	@Test
	public void shouldBeAbleToPutReferenceTo3rdLevelProperyOnNestedPath() {
		Path path = Path.of("a", "b", "c", "d");
		ObjectReference a1ObjRef = new ObjectReference(new ObjectAddress(a1, Path.of("z", "a", "b")));
		a2.getCell().put(new Formula(a1ObjRef), path);
		
		recalculator.recalculate(doc);
		
		assertEquals(7.0, path.getValue(a2.getCell().getValue()).accept(new MapDataProcessor()));
	}
	
	@Test
	public void shouldBeAbleToPutReferenceToNestedSubTreeOnNestedPath() {
		Path path = Path.of("a", "b", "c", "d");
		ObjectReference a1ObjRef = new ObjectReference(new ObjectAddress(a1, Path.of("z", "a")));
		a2.getCell().put(new Formula(a1ObjRef), path);
		
		recalculator.recalculate(doc);
		
		assertEquals(7.0, path.with("b").getValue(a2.getCell().getValue()).accept(new MapDataProcessor()));
	}
	
	@Test
	public void shouldBeAbleToCallFunctionWithNestedArgument() {
		ObjectReference zab2Ref = new ObjectReference(new ObjectAddress(a1, Path.of("z", "a", "b2")));
		FunctionCall absFunCall = new FunctionCall(abs, zab2Ref);
		a2.getCell().put(new Formula(absFunCall));
		
		recalculator.recalculate(doc);
		
		assertEquals(7.0, a2.getCell().getValue().accept(new MapDataProcessor()));
	}
	
	@Test
	public void shouldBeAbleToExecuteFunctionWithAreaReferenceArg() {
		CellAddress b1 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 1), Coordinate.abs(sheet.rows(), 0));
		b1.getCell().put(new Scalar<>(2.0));
		CellAddress b2 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 1), Coordinate.abs(sheet.rows(), 1));
		b2.getCell().put(new Scalar<>(2.0));
		CellAddress b3 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 1), Coordinate.abs(sheet.rows(), 2));
		
		AreaReference b1b2 = new AreaReference(ObjectAddress.inPlace(b1), ObjectAddress.inPlace(b2));
		
		b3.getCell().put(new Formula(new FunctionCall(sum, b1b2)));
		
		recalculator.recalculate(doc);
		
		assertEquals(4.0, b3.getCell().getValue().accept(new MapDataProcessor()));
	}
}

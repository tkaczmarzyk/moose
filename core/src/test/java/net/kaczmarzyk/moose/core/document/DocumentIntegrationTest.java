package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.assertEquals;
import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.SpringTestBase;
import net.kaczmarzyk.moose.core.expression.FunctionCall;
import net.kaczmarzyk.moose.core.expression.ObjectReference;
import net.kaczmarzyk.moose.core.function.Function;
import net.kaczmarzyk.moose.core.processor.MapDataProcessor;
import net.kaczmarzyk.moose.core.recalc.Recalculator;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


@Category(Integration.class)
public class DocumentIntegrationTest extends SpringTestBase {

	@Autowired @Qualifier("add")
	private Function add;
	
	@Autowired
	private Recalculator recalculator;
	
	private Document doc = new Document("test doc");
	private Sheet sheet = doc.getSheet("Sheet 1");

	private CellAddress a1;

	private ObjectAddress xAddr;
	private ObjectAddress yAddr;

	private CellAddress a2;

	private FunctionCall xAddY;
	
	
	@Before
	public void initData() {
		a1 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 0), Coordinate.abs(sheet.rows(), 0));
		
		a1.getCell().put(new Scalar<Double>(2.0), new Path("x"));
		a1.getCell().put(new Scalar<Double>(3.0), new Path("y"));
		
		xAddr = new ObjectAddress(a1, new Path("x"));
		yAddr = new ObjectAddress(a1, new Path("y"));
		
		a2 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 0), Coordinate.abs(sheet.rows(), 1));
		
		xAddY = new FunctionCall(add, new ObjectReference(xAddr), new ObjectReference(yAddr));
	}
	
	@Test
	public void shouldBeAbleToHaveAMapDataWithScalarsAndFormula() {
		a1.getCell().put(new Formula(xAddY), new Path("z"));
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
		Path path = new Path("a", "b", "c", "d");
		a2.getCell().put(new Scalar<>(2.0), path);
		
		assertEquals(2.0, a2.getCell().getValue().getProperty(path).accept(new MapDataProcessor()));
	}
	
	// TODO test for puting data into cell with data (overwriting)
}

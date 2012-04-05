package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.assertEquals;
import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.SpringTestBase;
import net.kaczmarzyk.moose.core.expression.FunctionCall;
import net.kaczmarzyk.moose.core.expression.ObjectReference;
import net.kaczmarzyk.moose.core.function.Function;
import net.kaczmarzyk.moose.core.processor.MapDataProcessor;
import net.kaczmarzyk.moose.core.recalc.Recalculator;

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
	
	
	@Test
	public void shouldBeAbleToHaveAMapDataWithScalarsAndFormula() {
		CellAddress a1 = new CellAddress(sheet, Coordinate.of("A"), Coordinate.of(1));
		
		doc.getCell(a1).put(new Scalar<Double>(sheet, 2.0), new Path("x"));
		doc.getCell(a1).put(new Scalar<Double>(sheet, 3.0), new Path("y"));
		
		ObjectAddress xAddr = new ObjectAddress(a1, new Path("x"));
		ObjectAddress yAddr = new ObjectAddress(a1, new Path("y"));
		
		FunctionCall xAddY = new FunctionCall(add, new ObjectReference(xAddr), new ObjectReference(yAddr));
		
		doc.getCell(a1).put(new Formula(sheet, xAddY), new Path("z"));
		
		recalculator.recalculate(doc);
		
		assertEquals(5.0, a1.getCell().getValue().getProperty("z").accept(new MapDataProcessor()));
	}
}

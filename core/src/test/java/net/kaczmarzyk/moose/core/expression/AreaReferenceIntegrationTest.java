package net.kaczmarzyk.moose.core.expression;

import java.util.List;

import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Path;
import static org.junit.Assert.*;
import net.kaczmarzyk.moose.core.document.Sheet;
import net.kaczmarzyk.moose.support.utils.ReflectionUtil;

import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(Integration.class)
public class AreaReferenceIntegrationTest {

	private Document doc = new Document("test doc");
	private Sheet sheet = doc.getSheet("Sheet 1");
	
	private CellAddress a1 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 0), Coordinate.abs(sheet.rows(), 0));
	private ObjectAddress a1_x = new ObjectAddress(a1, Path.of("x"));
	
	private CellAddress c3 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 2), Coordinate.abs(sheet.rows(), 2));
	private ObjectAddress c3_x = new ObjectAddress(c3, Path.of("x"));
	
	private CellAddress d5 = new CellAddress(sheet, Coordinate.abs(sheet.cols(), 3), Coordinate.abs(sheet.rows(), 4));
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void evaluate_shouldReturnCorrectNumberOfObjects() {
		AreaReference area = new AreaReference(a1_x, c3_x);
		
		List<DataObject> objs = (List<DataObject>) ReflectionUtil.get(area.evaluate(d5), "objects");
		
		assertEquals(9, objs.size());
	}
}

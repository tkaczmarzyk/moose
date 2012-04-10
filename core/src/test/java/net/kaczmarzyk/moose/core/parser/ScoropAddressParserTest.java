package net.kaczmarzyk.moose.core.parser;

import static org.junit.Assert.assertEquals;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.Dimension;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Path;
import net.kaczmarzyk.moose.core.document.Sheet;

import org.junit.Test;


public class ScoropAddressParserTest { // TODO sheet names with special chars

	private Document doc = new Document("test doc");
	private Sheet sheet1 = doc.getSheet("Sheet1");
	private Sheet sheet2 = doc.getSheet("Sheet2");
	private Dimension<?> s2dimension1 = sheet2.getDimensions().get(0);
	private Dimension<?> s2dimension2 = sheet2.getDimensions().get(1);
	private ScoropAddressParser parser = new ScoropAddressParser();
	
	
	@Test
	public void parse_shouldRecognizeNegativeCooridnates() {
		ObjectAddress parsed = parser.parse(sheet1, "Sheet2!C-1R-2#foo.bar.baz");
		
		assertEquals(Coordinate.rel(s2dimension1, -1), parsed.getCellAddr().getCoordinate(s2dimension1));
		assertEquals(Coordinate.rel(s2dimension2, -2), parsed.getCellAddr().getCoordinate(s2dimension2));
	}
	
	@Test
	public void parse_shouldRecognizeRelativeColumnAndRowCoordinates() {
		ObjectAddress parsed = parser.parse(sheet1, "Sheet2!C0R1#foo.bar.baz");
		
		assertEquals(Coordinate.rel(s2dimension1, 0), parsed.getCellAddr().getCoordinate(s2dimension1));
		assertEquals(Coordinate.rel(s2dimension2, 1), parsed.getCellAddr().getCoordinate(s2dimension2));
	}
	
	@Test
	public void parse_shouldRecognizeAbsoluteColumnAndRowCoordinates() {
		ObjectAddress parsed = parser.parse(sheet1, "Sheet2!C[0]R[1]#foo.bar.baz");
		
		assertEquals(Coordinate.abs(s2dimension1, 0), parsed.getCellAddr().getCoordinate(s2dimension1));
		assertEquals(Coordinate.abs(s2dimension2, 1), parsed.getCellAddr().getCoordinate(s2dimension2));
	}
	
	@Test
	public void parse_shouldUseExplicitlySpecifiedSheet() {
		ObjectAddress parsed = parser.parse(sheet1, "Sheet2!C[0]R[1]#foo.bar.baz");
		
		assertEquals(sheet2, parsed.getCellAddr().getSheet());
	}
	
	@Test
	public void parse_shouldUseCurrentSheetIfSheetNotSpecified() {
		ObjectAddress parsed = parser.parse(sheet1, "C[0]R[1]#foo.bar.baz");
		
		assertEquals(sheet1, parsed.getCellAddr().getSheet());
	}
	
	@Test
	public void parse_shouldRecognizePathCorrectly() {
		ObjectAddress parsed = parser.parse(sheet1, "Sheet1!C[0]R[1]#foo.bar.baz");
		
		assertEquals(Path.of("foo", "bar", "baz"), parsed.getPath());
	}
}

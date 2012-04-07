package net.kaczmarzyk.moose.core.parser;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Path;
import net.kaczmarzyk.moose.core.document.Sheet;

import org.junit.Test;


public class ScoropAddressParserTest {

	private Sheet sheet = mock(Sheet.class);
	private ScoropAddressParser parser = new ScoropAddressParser();
	
	
	@Test
	public void parse_shouldUseCurrentSheetIfSheetNotSpecified() {
		ObjectAddress parsed = parser.parse(sheet, "C[0]R[1]#foo.bar.baz");
		
		assertEquals(sheet, parsed.getCellAddr().getSheet());
	}
	
	@Test
	public void parse_shouldRecognizePathCorrectly() {
		ObjectAddress parsed = parser.parse(sheet, "Sheet1!C[0]R[1]#foo.bar.baz");
		
		assertEquals(Path.of("foo", "bar", "baz"), parsed.getPath());
	}
}

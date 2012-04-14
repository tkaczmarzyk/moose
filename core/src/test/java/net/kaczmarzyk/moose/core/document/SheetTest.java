package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.*;
import net.kaczmarzyk.moose.core.MockedTestBase;

import org.junit.Test;
import org.mockito.Mock;


public class SheetTest extends MockedTestBase {

	@Mock
	private Document mockDoc;
	
	@Test
	public void constructor_shouldInitialzeColumnAndRowDimensions() {
		Sheet sheet = new Sheet(mockDoc);
		DimensionsList dimensions = sheet.getDimensions();
		assertEquals(2, dimensions.size());
		assertEquals(SpatialDimension.class, dimensions.get(0).getClass());
		assertEquals(SpatialDimension.class, dimensions.get(1).getClass());
	}
	
}

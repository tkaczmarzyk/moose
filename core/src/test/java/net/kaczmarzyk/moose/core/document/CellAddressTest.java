package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import net.kaczmarzyk.moose.core.MockedTestBase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


public class CellAddressTest extends MockedTestBase {

	@Mock private Sheet sheet;
	@Mock private Coordinate coord1;
	@Mock private Coordinate coord2;
	@Mock private Coordinate coord3;
	@Mock private Coordinate coord4;
	@Mock private Coordinate coord1Abs;
	@Mock private Coordinate coord2Abs;
	@Mock private Dimension dimension1; //TODO nicer generics
	@Mock private Dimension dimension2;
	
	private CellAddress cellAddr1;
	private CellAddress cellAddr2;
	
	
	@SuppressWarnings("unchecked")
	@Before
	public void init() {
		when(coord1.getDimension()).thenReturn(dimension1);
		when(coord3.getDimension()).thenReturn(dimension1);
		when(coord1Abs.getDimension()).thenReturn(dimension1);
		
		when(coord2.getDimension()).thenReturn(dimension2);
		when(coord4.getDimension()).thenReturn(dimension2);
		when(coord2Abs.getDimension()).thenReturn(dimension2);
		
		when(coord1.absolute(coord3)).thenReturn(coord1Abs);
		when(coord2.absolute(coord4)).thenReturn(coord2Abs);
		
		cellAddr1 = new CellAddress(sheet, Arrays.asList(coord1, coord2));
		cellAddr2 = new CellAddress(sheet, Arrays.asList(coord3, coord4));
	}
	
	@Test
	public void absolute_shouldCreateAddressWithTheSameSheetAndAbsoluteCoordinates() {
		CellAddress abs = cellAddr1.absolute(cellAddr2);
		
		assertSame(cellAddr1.getSheet(), abs.getSheet());
		assertEquals(coord1Abs, abs.getCoordinate(dimension1));
		assertEquals(coord2Abs, abs.getCoordinate(dimension2));
	}
}

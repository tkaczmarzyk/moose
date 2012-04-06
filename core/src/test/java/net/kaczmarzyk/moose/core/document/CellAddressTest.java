package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import net.kaczmarzyk.moose.core.MockedTestBase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


public class CellAddressTest extends MockedTestBase {

	@Mock private Sheet sheet;
	@Mock private Coordinate<Object> coord1;
	@Mock private Coordinate<Object> coord2;
	@Mock private Coordinate<Object> coord3;
	@Mock private Coordinate<Object> coord4;
	@Mock private Coordinate<Object> coord1Abs;
	@Mock private Coordinate<Object> coord2Abs;
	@Mock private Dimension<Object> dimension1;
	@Mock private Dimension<Object> dimension2;
	
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
		
		cellAddr1 = new CellAddress(sheet, (List)Arrays.asList(coord1, coord2)); //FIXME nicer generics
		cellAddr2 = new CellAddress(sheet, (List)Arrays.asList(coord3, coord4));
	}
	
	@Test
	public void absolute_shouldCreateAddressWithTheSameSheetAndAbsoluteCoordinates() {
		CellAddress abs = cellAddr1.absolute(cellAddr2);
		
		assertSame(cellAddr1.getSheet(), abs.getSheet());
		assertEquals(coord1Abs, abs.getCoordinate(dimension1));
		assertEquals(coord2Abs, abs.getCoordinate(dimension2));
	}
}

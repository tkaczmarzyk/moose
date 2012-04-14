package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.*;
import net.kaczmarzyk.moose.core.MockedTestBase;
import net.kaczmarzyk.moose.support.utils.ReflectionUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


public class CoordinateTest extends MockedTestBase {

	@Mock private Dimension<Object> dimension;
	
	private Coordinate absCoord, absCoord2;
	private Coordinate relCoord;
	
	
	@Before
	public void init() {
		absCoord = Coordinate.abs(dimension, 0);
		absCoord2 = Coordinate.abs(dimension, 1);
		relCoord = Coordinate.rel(dimension, 1);
	}
	
	@Test
	public void absolute_shouldReturnItselfIfAlreadyAbsolute() {
		assertSame(absCoord, absCoord.absolute(absCoord2));
	}
	
	@Test
	public void absolute_shouldReturnAbsoluteCoordWithSumOfShifts() {
		Coordinate abs = relCoord.absolute(absCoord2);
		assertAbsolute(abs);
		assertEquals(2, ReflectionUtil.get(abs, "shift")); // TODO avoid reflection, but do not expose if not neccessary
	}
	
	private static void assertAbsolute(Coordinate coord) {
		assertTrue("coordinate is not absolute", (boolean) ReflectionUtil.get(coord, "absolute"));
	}
}

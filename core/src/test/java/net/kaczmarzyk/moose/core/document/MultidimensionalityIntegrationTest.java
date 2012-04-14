package net.kaczmarzyk.moose.core.document;

import static net.kaczmarzyk.moose.support.utils.DateUtil.date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.common.MockClock;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(Integration.class)
public class MultidimensionalityIntegrationTest {

	private MockClock clock = MockClock.of(date(2012, 04, 12));
	private Document doc = mock(Document.class);
	private Sheet sheet;
	
	
	@Before
	public void setup() {
		sheet = new Sheet(doc);
		sheet.addDimension(new TimeDimension(clock));
	}
	
	@Test
	public void shouldInferCurrentTimeCoordinate() {
		CellAddress addr = sheet.cellAddress(Coordinate.abs(sheet.getDimensions().get(0), 0),
				Coordinate.abs(sheet.getDimensions().get(1), 0));
		
		assertEquals(3, addr.getDimensions().size());
		assertEquals(TimeDimension.class, addr.getDimensions().get(2).getClass());
		
		assertEquals(clock.currentTime(), addr.getCoordinate(2).getValue());
	}
	
	@Test
	public void shouldBeAbleToListAllNotEmptyCoordinates() {
		fail();
	}
	
	@Test
	public void shouldBeAbleToRetrieveLastNotEmptyCoordinate() {
		fail();
	}
}

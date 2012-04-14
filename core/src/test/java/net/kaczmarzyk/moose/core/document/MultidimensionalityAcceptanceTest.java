package net.kaczmarzyk.moose.core.document;

import static net.kaczmarzyk.moose.support.utils.DateUtil.date;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Date;
import java.util.List;

import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.common.MockClock;
import net.kaczmarzyk.moose.core.utils.DataObjectUtil;
import net.kaczmarzyk.moose.support.utils.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(Integration.class)
public class MultidimensionalityAcceptanceTest {

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
		CellAddress partialAddr = DataObjectUtil.absCellAddr(sheet, 0, 0);
		
		sheet.put(partialAddr, new Scalar<>(7.0));
		
		CellAddress fullAddress = DataObjectUtil.absCellAddr(sheet, 0, 0, 0);
		
		assertEquals(new Scalar<>(7.0), sheet.get(fullAddress));
	}
	
	@Test
	public void shouldBeAbleToListAllNotEmptyCoordinates() { // FIXME it's wrong as for spatial dimensions it would return indexes with potentially empty cells
		CellAddress addr = DataObjectUtil.absCellAddr(sheet, 0, 0);
		sheet.put(addr, new Scalar<>(7.0));
		
		clock.time(date(2012, 04, 13));
		
		sheet.put(addr, new Scalar<>(9.0));
		
		List<?> values = sheet.getDimensions().get(2).getValues();
		assertEquals(2, values.size());
		assertEquals(date(2012, 04, 12), values.get(0));
		assertEquals(date(2012, 04, 13), values.get(1));
	}
	
	@Test
	public void shouldBeAbleToRetrieveLastNotEmptyCellValue() {
		CellAddress addr = DataObjectUtil.absCellAddr(sheet, 0, 0);
		sheet.put(addr, new Scalar<>(7.0));
		
		clock.forward(1000);
		
		assertEquals(new Scalar<>(7.0), sheet.get(addr));
	}
	
	@Test
	public void shouldPutToCorrectCellsAsTimePasses() {
		CellAddress addr = DataObjectUtil.absCellAddr(sheet, 0, 0);
		sheet.put(addr, new Scalar<>(1));
		
		clock.forward(DateUtil.MILLIS_IN_DAY);
		sheet.put(addr, new Scalar<>(2));
		
		clock.forward(DateUtil.MILLIS_IN_DAY);
		sheet.put(addr, new Scalar<>(3));
		
		assertEquals(new Scalar<>(1), sheet.get(DataObjectUtil.absCellAddr(sheet, 0, 0, 0)));
		assertEquals(new Scalar<>(2), sheet.get(DataObjectUtil.absCellAddr(sheet, 0, 0, 1)));
		assertEquals(new Scalar<>(3), sheet.get(DataObjectUtil.absCellAddr(sheet, 0, 0, 2)));
	}
	
	@Test
	public void shouldBeAbleToHandleRelativeTimeCoordinate() {
		sheet.put(DataObjectUtil.absCellAddr(sheet, 0, 0, 0), new Scalar<>("test"));
		
		CellAddress addr = DataObjectUtil.relCellAddr(sheet, 0, 0, -1);
		CellAddress absoulte = addr.absolute(DataObjectUtil.absCellAddr(sheet, 0, 0, 1));
		
		assertEquals(new Scalar<>("test"), sheet.get(absoulte));		
	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldBeAbleToSpecifyTimeCoordExplicitely() {
		Date date = new Date(0L);
		CellAddress addr = new CellAddress(sheet, sheet.getDimensions().get(0).coordOf(0), //FIXME do: getDimensions().coordOf(i, val) ?
				sheet.getDimensions().get(1).coordOf(0), sheet.getDimensions().get(2).coordOf(date));
		
		sheet.put(addr, new Scalar<>("test"));
		
		assertEquals(new Scalar<>("test"), sheet.get(addr));
	}
}

package net.kaczmarzyk.moose.core.document;

import static net.kaczmarzyk.moose.support.utils.DateUtil.date;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.List;

import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.common.MockClock;
import net.kaczmarzyk.moose.core.utils.DataObjectUtil;

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
	public void shouldBeAbleToHandleRelativeTimeCoordinate() {
		sheet.put(DataObjectUtil.absCellAddr(sheet, 0, 0, 0), new Scalar<>("test"));
		
		CellAddress addr = DataObjectUtil.relCellAddr(sheet, 0, 0, -1);
		CellAddress absoulte = addr.absolute(DataObjectUtil.absCellAddr(sheet, 0, 0, 1));
		
		assertEquals(new Scalar<>("test"), sheet.get(absoulte));		
	}
}

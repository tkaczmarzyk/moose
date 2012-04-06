package net.kaczmarzyk.moose.core.document;

import net.kaczmarzyk.moose.core.MockedTestBase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class ObjectAddressTest extends MockedTestBase {

	@Mock private CellAddress cellAddr1;
	@Mock private CellAddress cellAddr2;
	@Mock private CellAddress cellAddr3;
	
	private ObjectAddress objAddr;
	
	
	@Before
	public void init() {
		objAddr = new ObjectAddress(cellAddr1, Path.of("x", "y", "z"));
		
		when(cellAddr1.absolute(cellAddr2)).thenReturn(cellAddr3);
	}
	
	@Test
	public void absolute_shouldReturnCopyWithAbsoluteCellAddressAndTheSamePath() {
		ObjectAddress absolute = objAddr.absolute(cellAddr2);
		assertEquals(cellAddr3, absolute.getCellAddr());
		assertSame(objAddr.getPath(), absolute.getPath());
	}
}

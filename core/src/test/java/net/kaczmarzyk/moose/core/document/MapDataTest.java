package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;


public class MapDataTest {

	private MapData obj;
	private MapData nested;
	
	@Before
	public void init() {
		obj  = new MapData();
		obj.placedAtAddress(new ObjectAddress(mock(CellAddress.class), Path.IN_PLACE));
		nested = new MapData();
		nested.setProperty(Path.of("y"), new Scalar<>(7.0));
		obj.setProperty(Path.of("x"), nested);
	}
	
	
	@Test
	public void setProperty_shouldSetNestedProperty() {
		DataObject value = new Scalar<>(7.0);
		obj.setProperty(Path.of("x", "z"), value);
		
		assertEquals(value, obj.getProperty("x").getProperty("z"));
		assertEquals(value, nested.getProperty("z"));
	}
	
	@Test
	public void setProperty_shouldSetFirstLevelProperty() {
		DataObject value = new Scalar<>(7.0);
		obj.setProperty(Path.of("z"), value);
		assertEquals(value, obj.getProperty("z"));
	}
	
	@Test
	public void getProperty_shouldReturnEmptyMapIfPropertyNotFound() {
		DataObject prop = obj.getProperty("z");
		
		assertSame(MapData.class, prop.getClass());
		assertTrue(prop.getPropertyNames().isEmpty());
	}
	
	@Test
	public void getProperty_shouldReturnFirstLevelPropertyValue() {
		assertEquals(nested, obj.getProperty("x"));
	}
	
}

package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.*;
import net.kaczmarzyk.moose.core.Integration;

import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(Integration.class)
public class DocumentIntegrationTest {

	@Test
	public void shouldBeAbleToReferenceAnExplicitDataObject() {
		Document doc = new Document("test document");
		
		Cell a1 = doc.getCell(new Coordinates(new Coordinate<>("A"), new Coordinate<>(1))); // TODO doc.getCell("A1") ?
		a1.setValue("2"); // TODO should cell be responsible for parsing? // TODO converters (i.e. this example should create integer data instead of string)
		Cell a2 = doc.getCell(new Coordinates(new Coordinate<>("A"), new Coordinate<>(2)));
		a2.setValue("=A1");
		
		assertEquals("2", a1.getValue().getProperty("value").getExplicitValue(doc)); // TODO gentle way of presenting data objects
		assertEquals("2", a2.getValue().getProperty("value").getExplicitValue(doc));
		
		a1.setValue("3");
		assertEquals("3", a1.getValue().getProperty("value").getExplicitValue(doc));
		assertEquals("3", a2.getValue().getProperty("value").getExplicitValue(doc));
	}
}

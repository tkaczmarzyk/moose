package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.*;
import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.SpringTestBase;
import net.kaczmarzyk.moose.core.operator.DocumentOperator;
import net.kaczmarzyk.moose.core.operator.SimpleDocumentOperator;

import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(Integration.class)
public class DocumentIntegrationTest extends SpringTestBase {

	@Test
	public void shouldBeAbleToReferenceAnExplicitDataObject() {
		DocumentOperator docOp = new SimpleDocumentOperator("test document");
		
		docOp.assign("A1", "2"); // TODO converters (i.e. this example should create integer data instead of string)
		docOp.assign("A2", "=A1");
		
		assertEquals("2", docOp.getValue("A1").getProperty("value").getExplicitValue(docOp.getDocument())); // TODO gentle way of presenting data objects
		assertEquals("2", docOp.getValue("A2").getProperty("value").getExplicitValue(docOp.getDocument()));
		
		docOp.assign("A1", "3");
		assertEquals("3", docOp.getValue("A1").getProperty("value").getExplicitValue(docOp.getDocument()));
		assertEquals("3", docOp.getValue("A2").getProperty("value").getExplicitValue(docOp.getDocument()));
	}
}

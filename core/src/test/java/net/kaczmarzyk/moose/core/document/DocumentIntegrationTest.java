package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.assertEquals;
import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.SpringTestBase;
import net.kaczmarzyk.moose.core.operator.SimpleDocumentOperator;
import net.kaczmarzyk.moose.core.processor.ToStringDataProcessor;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(Integration.class)
public class DocumentIntegrationTest extends SpringTestBase {

	private SimpleDocumentOperator<String> docOp;
	
	
	@Before
	public void init() {
		docOp = new SimpleDocumentOperator<>("test document", new ToStringDataProcessor());
	}
	
	@Test
	public void shouldBeAbleToReferenceAnExplicitDataObject() {
		docOp.assign("A1", "2"); // TODO converters (i.e. this example should create integer data instead of string)
		docOp.assign("A2", "=A1");
		
		assertEquals("(2)", docOp.getProcessedValue("A1"));
		assertEquals("(2)", docOp.getProcessedValue("A2"));
		
		docOp.assign("A1", "3");
		assertEquals("(3)", docOp.getProcessedValue("A1"));
		assertEquals("(3)", docOp.getProcessedValue("A2"));
	}
	
	@Test
	public void shouldBeAbleToReferenceAPropertyOfAnExplicitDataObject() {
		docOp.assign("A1", "x:12,y:10");
		docOp.assign("A2", "=A1#x");
		
		assertEquals("(x=(12), y=(10))", docOp.getProcessedValue("A1"));
		assertEquals("(12)", docOp.getProcessedValue("A2"));
	}
}

package net.kaczmarzyk.moose.core.document;

import static org.junit.Assert.assertEquals;
import net.kaczmarzyk.moose.core.Integration;
import net.kaczmarzyk.moose.core.SpringTestBase;
import net.kaczmarzyk.moose.core.operator.SimpleDocumentOperator;
import net.kaczmarzyk.moose.core.processor.MapDataProcessor;
import net.kaczmarzyk.moose.core.processor.ToStringDataProcessor;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;


@Category(Integration.class)
public class DocumentAcceptanceTest extends SpringTestBase {

	private SimpleDocumentOperator<Object> docOp;
	
	
	@Before
	public void init() {
		docOp = new SimpleDocumentOperator<>("test document", new MapDataProcessor());
	}
	
	@Test
	public void shouldStoreAnExplicitValue() {
		docOp.assign("A1", "tk");
		assertEquals("tk", docOp.getProcessedValue("A1"));
	}
	
	@Test
	public void shouldBeAbleToReferenceAnExplicitDataObject() {
		docOp.assign("A1", "tk"); // TODO converters (i.e. this example should create integer data instead of string)
		docOp.assign("A2", "=A1");
		
		assertEquals("tk", docOp.getProcessedValue("A2"));
		
		docOp.assign("A1", "fu");
		assertEquals("fu", docOp.getProcessedValue("A2"));
	}
	
	@Test
	public void shouldBeAbleToReferenceAPropertyOfAnExplicitDataObject() {
		docOp.setDataProcessor(new ToStringDataProcessor());
		docOp.assign("A1", "x:12,y:10"); // TODO map syntax
		docOp.assign("A2", "=A1#x");
		
		assertEquals("(x=(12), y=(10))", docOp.getProcessedValue("A1"));
		assertEquals("(12)", docOp.getProcessedValue("A2"));
	}
	
	@Test
	public void shouldSupportExplicitReferenceToObjectFromAnotherSheet() {
		docOp.setDataProcessor(new ToStringDataProcessor());
		docOp.assign("Sheet 1!A1", "x:2,y:3");
		docOp.assign("Sheet 2!A2", "=Sheet 1!A1");
		
		assertEquals("(x=(2), y=(3))", docOp.getProcessedValue("Sheet 1!A1"));
		assertEquals("(x=(2), y=(3))", docOp.getProcessedValue("Sheet 2!A2"));
	}
	
	@Test
	public void shouldUseDefaultSheetIfNotProvidedInAddress() {
		docOp.assign("A1", "33t");
		assertEquals("33t", docOp.getProcessedValue("Sheet 1!A1"));
	}
	
	@Test
	public void shouldEvaluateFunction() {
		docOp.assign("A1", "-22.0");
		docOp.assign("A2", "=abs(A1)");
		assertEquals(22.0, docOp.getProcessedValue("A2"));
	}
	
	@Test
	public void shouldBeAbleToCopyCellValue() {
		docOp.assign("A1", "2");
		docOp.copy("A1", "A2");
		
		assertEquals(2.0, docOp.getProcessedValue("A2"));
	}
	
	@Test
	public void shouldHandleRelativeAddressDuringCopying() {
		docOp.assign("A1", "2").assign("B1", "3");
		docOp.assign("A2", "=A1");
		docOp.copy("A2", "B2");
		
		assertEquals(3.0, docOp.getProcessedValue("B2"));
	}
	
}

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
		docOp.assign("C[0]R[0]", "tk");
		assertEquals("tk", docOp.getProcessedValue("C[0]R[0]"));
	}
	
	@Test
	public void shouldBeAbleToReferenceAnExplicitDataObject() {
		docOp.assign("C[0]R[0]", "tk");
		docOp.assign("C[0]R[1]", "=C[0]R[0]");
		
		assertEquals("tk", docOp.getProcessedValue("C[0]R[1]"));
		
		docOp.assign("C[0]R[0]", "fu");
		assertEquals("fu", docOp.getProcessedValue("C[0]R[1]"));
	}
	
	@Test
	public void shouldBeAbleToReferenceAPropertyOfAnExplicitDataObject() {
		docOp.setDataProcessor(new ToStringDataProcessor());
		docOp.assign("C[0]R[0]", "{x:12,y:10}");
		docOp.assign("C[0]R[1]", "=C[0]R[0]#x");
		
		assertEquals("(x=(12), y=(10))", docOp.getProcessedValue("C[0]R[0]"));
		assertEquals("(12)", docOp.getProcessedValue("C[0]R[1]"));
	}
	
	@Test
	public void shouldSupportExplicitReferenceToObjectFromAnotherSheet() {
		docOp.setDataProcessor(new ToStringDataProcessor());
		docOp.assign("Sheet1!C[0]R[0]", "{x:2,y:3}");
		docOp.assign("Sheet2!C[0]R[1]", "=Sheet1!C[0]R[0]");
		
		assertEquals("(x=(2), y=(3))", docOp.getProcessedValue("Sheet1!C[0]R[0]"));
		assertEquals("(x=(2), y=(3))", docOp.getProcessedValue("Sheet2!C[0]R[1]"));
	}
	
	@Test
	public void shouldUseDefaultSheetIfNotProvidedInAddress() {
		docOp.assign("C[0]R[0]", "33t");
		assertEquals("33t", docOp.getProcessedValue("Sheet1!C[0]R[0]"));
	}
	
	@Test
	public void shouldEvaluateFunction() {
		docOp.assign("C[0]R[0]", "-22.0");
		docOp.assign("C[0]R[1]", "=abs(C[0]R[0])");
		assertEquals(22.0, docOp.getProcessedValue("C[0]R[1]"));
	}
	
	@Test
	public void shouldBeAbleToCopyCellValue() {
		docOp.assign("C[0]R[0]", "2.0");
		docOp.copy("C[0]R[0]", "C[0]R[1]");
		
		assertEquals(2.0, docOp.getProcessedValue("C[0]R[1]"));
	}
	
	@Test
	public void shouldHandleRelativeAddressDuringCopying() {
		docOp.assign("C[0]R[0]", "2").assign("C[1]R[0]", "3.0");
		docOp.assign("C[0]R[1]", "=C0R-1");
		docOp.copy("C[0]R[1]", "C[1]R[1]");
		
		assertEquals(3.0, docOp.getProcessedValue("C[1]R[1]"));
	}
	
}

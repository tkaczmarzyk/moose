package net.kaczmarzyk.moose.web.document.zk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.kaczmarzyk.moose.core.document.Cell;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.Dimension;
import net.kaczmarzyk.moose.core.document.Sheet;

import org.zkoss.bind.annotation.Init;
import org.zkoss.web.util.resource.ServletRequestResolver;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;


@VariableResolver(ServletRequestResolver.class)
public class SheetViewModel {

	@WireVariable("sheet")
	private Sheet sheet;
	
	
	@Init
	public void setup() {
	}
	
	public List<List<Cell>> getRows() {
		List<List<Cell>> result = new ArrayList<>();
		
		Dimension<?> d1 = sheet.getDimensions().get(0);
		Dimension<?> d2 = sheet.getDimensions().get(1);
		
		for (int i = 0; i < d2.getSize(); i++) {
			List<Cell> row = new ArrayList<>();
			result.add(row);
			
			for (int j = 0; j < d1.getSize(); j++) {
				row.add(sheet.getCell(Coordinate.abs(d1, i), Coordinate.abs(d2, j)));
			}
		}
		
		return result;
	}
	
	public List<String> getCols() {
		return Arrays.asList("bb", "cc");
	}
}

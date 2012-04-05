package net.kaczmarzyk.moose.core.recalc;

import org.springframework.stereotype.Component;

import net.kaczmarzyk.moose.core.document.Cell;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.Sheet;


@Component
public class BruteForceRecalculator implements Recalculator {

	@Override
	public void recalculate(Document doc) {
		for (Sheet sheet : doc.getSheets()) {
			for (Cell cell : sheet.getCells()) {
				cell.getValue().refresh(cell.getAddress());
			}
		}
		// TODO proper implementation
	}

}

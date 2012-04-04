package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Path;
import net.kaczmarzyk.moose.core.document.Sheet;

import org.springframework.stereotype.Component;


@Component
public class PocAddressParser implements AddressParser {

	@Override
	public ObjectAddress parse(Sheet sheet, String input) {
		Sheet sheet2 = sheet;
		if (input.contains("!")) {
			String sheetName = input.substring(0, input.indexOf('!'));
			sheet2 = sheet.getDoc().getSheet(sheetName);
			input = input.substring(input.indexOf('!') + 1);
		}
		Coordinate<String> columnCoord = Coordinate.of(input.substring(0,1));
		Coordinate<Integer> rowCoord = Coordinate.of(Integer.parseInt(input.substring(1,2)));
		Path path = new Path();
		if (input.length() > 2) {
			path = new Path(input.substring(3).split("#"));
		}
		return new ObjectAddress(new CellAddress(sheet2, columnCoord, rowCoord), path);
	}
	
}

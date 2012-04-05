package net.kaczmarzyk.moose.core.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		Coordinate<String> columnCoord = parseCoord(sheet, "C", input);
		Coordinate<Integer> rowCoord = parseCoord(sheet, "R", input);
		Path path = new Path();
		if (input.contains("#")) {
			path = new Path(input.substring(input.indexOf("#") + 1).split("#"));
		}
		return new ObjectAddress(new CellAddress(sheet2, columnCoord, rowCoord), path);
	}

	@SuppressWarnings("unchecked")
	private <K> Coordinate<K> parseCoord(Sheet sheet, String rowCol, String input) {
		Pattern pattern = Pattern.compile(rowCol + "(\\[?)(-?)(\\d+)\\]?");
		Matcher m = pattern.matcher(input);
		if (m.find()) {
			boolean absolute = m.group(1).length() > 0;
			Integer i = Integer.parseInt(m.group(3));
			if (m.group(2).length() > 0) {
				i = -i;
			}
			return (Coordinate<K>) new Coordinate<>(sheet.getDimensions().get("C".equals(rowCol) ? 0 : 1), i, absolute);
		}
		throw new IllegalStateException();
	}
	
}

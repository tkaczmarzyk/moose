package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Path;

import org.springframework.stereotype.Component;


@Component
public class PocAddressParser implements AddressParser {

	@Override
	public ObjectAddress parse(String input) {
		Coordinate<String> columnCoord = Coordinate.of(input.substring(0,1));
		Coordinate<Integer> rowCoord = Coordinate.of(Integer.parseInt(input.substring(1,2)));
		Path path = new Path();
		if (input.length() > 2) {
			path = new Path(input.substring(3).split("#"));
		}
		return new ObjectAddress(new CellAddress(columnCoord, rowCoord), path);
	}
	
}

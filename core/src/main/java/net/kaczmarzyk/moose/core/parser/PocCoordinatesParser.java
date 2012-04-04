package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.Coordinates;

import org.springframework.stereotype.Component;


@Component
public class PocCoordinatesParser implements CoordinatesParser {

	@Override
	public Coordinates parse(String input) {
		Coordinate<String> columnCoord = Coordinate.of(input.substring(0,1));
		Coordinate<Integer> rowCoord = Coordinate.of(Integer.parseInt(input.substring(1,2)));
		return new Coordinates(columnCoord, rowCoord);
	}
	
}

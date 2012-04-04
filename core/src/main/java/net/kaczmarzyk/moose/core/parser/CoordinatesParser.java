package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.Coordinates;


public interface CoordinatesParser { // TODO generic parser interface?

	Coordinates parse(String input);
}

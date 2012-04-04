package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Sheet;


public interface AddressParser { // TODO generic parser interface?

	ObjectAddress parse(Sheet sheet, String input);
}

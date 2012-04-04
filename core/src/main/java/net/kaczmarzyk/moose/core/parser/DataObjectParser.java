package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Sheet;


public interface DataObjectParser {

	DataObject parse(Sheet sheet, String input);
}

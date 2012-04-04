package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Document;


public interface DataObjectParser {

	DataObject parse(Document doc, String input);
}

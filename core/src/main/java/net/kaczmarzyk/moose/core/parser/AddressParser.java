package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.ObjectAddress;


public interface AddressParser { // TODO generic parser interface?

	ObjectAddress parse(String input);
}

package net.kaczmarzyk.moose.shell.cli;

import org.springframework.roo.shell.CliAvailabilityIndicator;
import org.springframework.roo.shell.CliCommand;
import org.springframework.roo.shell.CliOption;
import org.springframework.roo.shell.CommandMarker;
import org.springframework.stereotype.Component;


@Component
public class MooseCommands implements CommandMarker {

	@CliAvailabilityIndicator({"moose help"})
	public boolean isCommandAvailable() {
		return true;
	}
	
	@CliCommand(value = "moose hw", help = "Print a hello world message")
	public void hello(@CliOption(key = { "message" }, mandatory = true, help = "The hello world message") final String message) {		
		System.out.println("Hello in moose-world: " + message);
	}
}

package net.kaczmarzyk.moose.core;

import static org.junit.Assert.*;
import net.kaczmarzyk.moose.core.common.ReferenceService;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ReferenceTest extends SpringTestBase {

	@Autowired
	private ReferenceService service;
	
	@Test
	public void test() {
		assertNotNull(service);
	}
}

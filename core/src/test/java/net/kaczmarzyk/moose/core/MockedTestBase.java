package net.kaczmarzyk.moose.core;

import org.junit.Before;
import org.mockito.MockitoAnnotations;


public abstract class MockedTestBase {

	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
}

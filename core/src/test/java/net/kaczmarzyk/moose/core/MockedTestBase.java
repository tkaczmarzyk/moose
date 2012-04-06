package net.kaczmarzyk.moose.core;

import org.junit.Before;
import org.mockito.MockitoAnnotations;


public abstract class MockedTestBase extends UnitTestBase {

	@Before
	public void initAnnotatedMocks() {
		MockitoAnnotations.initMocks(this);
	}
}

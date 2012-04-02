package net.kaczmarzyk.moose.core;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration({"classpath:/META-INF/spring/applicationContext*.xml",
	"classpath:/testContext.xml"
})
public abstract class IntegrationTestBase extends AbstractJUnit4SpringContextTests {

}

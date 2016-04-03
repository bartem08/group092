package com.interview.service.mongo;

import com.interview.config.MvcConfigurer;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * @author Anton Kruglikov.
 */

@SpringApplicationConfiguration(MvcConfigurer.class)
@WebIntegrationTest("server.port:0")
@DirtiesContext
public class TemplateServiceImplTest extends AbstractTestNGSpringContextTests {

    //TODO: Add tests :)

}

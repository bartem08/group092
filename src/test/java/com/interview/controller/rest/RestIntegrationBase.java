package com.interview.controller.rest;

import com.interview.config.MvcConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Artem Pozdeev on 04.04.2016.
 */
@SpringApplicationConfiguration(MvcConfigurer.class)
@WebIntegrationTest
@Transactional
public class RestIntegrationBase extends AbstractTestNGSpringContextTests {

    @Value("${local.server.port}")
    protected int port;

    RestTemplate restTemplate = new TestRestTemplate("admin", "pwd");


}

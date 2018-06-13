package org.github.restapi;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GitHubRestApiTest {

    private static final String rightUrl = "https://api.github.com/gists/6a6400c84773006cd5cd644310ba667f";
    private RestTemplate restTemplate;

    @BeforeClass
    public void startUp() {
        restTemplate = new RestTemplate();
    }

    @Test
    public void checkGistExistTest() {
        ResponseEntity<String> response = restTemplate.getForEntity(rightUrl, String.class);
        HttpStatus expectedStatus = response.getStatusCode();

        Assert.assertEquals(HttpStatus.OK, expectedStatus);
    }
}

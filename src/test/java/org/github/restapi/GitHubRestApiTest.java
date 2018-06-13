package org.github.restapi;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.service.GistService;
import org.github.restapi.core.GitHubRestApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class GitHubRestApiTest {

    private final static String credentialsFile = "D:/github/credentials.json";
    private GistService service;
    private Gist actualGist;
    private GitHubRestApi githubApiClient;
    private RestTemplate restTemplate;

    @BeforeClass
    public void startUp() {
        restTemplate = new RestTemplate();
        service = new GistService();
        githubApiClient = new GitHubRestApi(service, credentialsFile);
    }

    @Test
    public void checkGistCreation() throws IOException {
        //given
        Gist testGist = new Gist();
        //when
        actualGist = githubApiClient.createGist(testGist);
        HttpStatus expectedStatus = getHttpStatus(actualGist);
        //then
        Assert.assertEquals(HttpStatus.OK, expectedStatus);
    }

    @Test(dependsOnMethods = "checkGistCreation")
    public void checkGistExistTest() throws IOException {
        Gist readedGist = githubApiClient.readGist(actualGist.getId());
        HttpStatus expectedStatus = getHttpStatus(readedGist);

        Assert.assertEquals(HttpStatus.OK, expectedStatus);
    }

    @Test(dependsOnMethods = "checkGistExistTest")
    public void checkGistUpdate() throws IOException {
        Gist updatedGist = githubApiClient.updateGist(actualGist);
        HttpStatus expectedStatus = getHttpStatus(updatedGist);

        Assert.assertEquals(HttpStatus.OK, expectedStatus);
    }

    @Test(dependsOnMethods = "checkGistUpdate", expectedExceptions = org.springframework.web.client.HttpClientErrorException.class)
    public void checkGistDeleted() throws IOException {
        githubApiClient.deleteGist(actualGist.getId());
        HttpStatus expectedStatus = getHttpStatus(actualGist);// should get Error 404, but it is right because resource is deleted
    }

    private HttpStatus getHttpStatus(Gist updatedGist) {
        ResponseEntity<String> response = restTemplate.getForEntity(updatedGist.getUrl(), String.class);
        return response.getStatusCode();
    }
}

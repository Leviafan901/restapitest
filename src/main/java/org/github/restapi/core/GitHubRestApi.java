package org.github.restapi.core;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;
import org.github.restapi.utils.JsonReader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;

public class GitHubRestApi {

    private GistService service;
    private String credentialsFile;

    public GitHubRestApi(GistService service, String credentialsFile) {
        this.service = service;
        this.credentialsFile = credentialsFile;
    }

    public Gist createGist(Gist gist) throws IOException {
        GistFile file = new GistFile();
        file.setContent("System.out.println(\"Hello World\");");
        String jsonString = JsonReader.read(credentialsFile);
        JSONObject credentials = new JSONObject(jsonString);
        service.getClient().setCredentials(credentials.getString("login"),
                credentials.getString("password"));
        gist.setDescription("Prints a string to standard out");
        gist.setFiles(Collections.singletonMap("Hello.java", file));
        return service.createGist(gist); //returns the created gist
    }

    public Gist readGist(String gistId) throws IOException {
        return service.getGist(gistId);
    }

    public Gist updateGist(Gist gist) throws IOException {
        gist.setDescription("Updated gist!");
        return service.updateGist(gist);
    }

    public void deleteGist(String gistId) throws IOException {
        service.deleteGist(gistId);
    }
}
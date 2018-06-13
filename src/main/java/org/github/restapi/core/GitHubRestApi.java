package org.github.restapi.core;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;
import org.github.restapi.utils.JsonReader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;

public class GitHubRestApi {

    public Gist createGist(Gist gist, GistService service, String filePathtoCredentials) {
        GistFile file = new GistFile();
        file.setContent("System.out.println(\"Hello World\");");
        String jsonString = JsonReader.read(filePathtoCredentials);
        JSONObject credentials = new JSONObject(jsonString);
        service.getClient().setCredentials(credentials.getString("login"), credentials.getString("password"));
        Gist newGist = null;
        try {
            gist.setDescription("Prints a string to standard out");
            gist.setFiles(Collections.singletonMap("Hello.java", file));
            newGist = service.createGist(gist); //returns the created gist
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newGist;
    }
}

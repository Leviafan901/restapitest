package org.github.resrapitest.core;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;
import org.github.resrapitest.utils.JsonReader;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;

public class GitHubRestApi {

    public static void main(String... args) {
        GistFile file = new GistFile();
        file.setContent("System.out.println(\"Hello World\");");
        Gist gist = new Gist();

        GistService service = new GistService();

        String jsonString = JsonReader.read("D:/github/credentials.json");
        JSONObject credentials = new JSONObject(jsonString);
        service.getClient().setCredentials(credentials.getString("login"), credentials.getString("password"));

        try {
            gist.setDescription("Prints a string to standard out");
            gist.setFiles(Collections.singletonMap("Hello.java", file));
            gist = service.createGist(gist); //returns the created gist
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

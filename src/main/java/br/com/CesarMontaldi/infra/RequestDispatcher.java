package br.com.CesarMontaldi.infra;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestDispatcher {

    public HttpResponse<String> requestGet(String uri) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> requestPost(String uri, Object entity) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(new Gson().toJson(entity)))
                    .build();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

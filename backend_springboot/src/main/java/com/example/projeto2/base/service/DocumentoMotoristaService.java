package com.example.projeto2.base.service;

import com.example.projeto2.base.model.DocumentoMotorista;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class DocumentoMotoristaService {

    private final String BASE_URL = "http://localhost:8080/documentos-motorista";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<DocumentoMotorista> getAllDocumentosMotorista() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<DocumentoMotorista>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public DocumentoMotorista getDocumentoMotoristaById(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), DocumentoMotorista.class);
    }

    public DocumentoMotorista criarDocumentoMotorista(DocumentoMotorista documento) throws IOException, InterruptedException {
        String json = gson.toJson(documento);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), DocumentoMotorista.class);
    }

    public DocumentoMotorista atualizarDocumentoMotorista(Long id, DocumentoMotorista documento) throws IOException, InterruptedException {
        String json = gson.toJson(documento);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), DocumentoMotorista.class);
    }

    public void apagarDocumentoMotorista(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
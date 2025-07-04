package com.example.projeto2.base.service;

import com.example.projeto2.base.model.CodigoPostal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class CodigoPostalService {

    private final String BASE_URL = "http://localhost:8080/codigos-postais";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<CodigoPostal> getAllCodigosPostais() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<CodigoPostal>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public CodigoPostal getCodigoPostalById(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), CodigoPostal.class);
    }

    public CodigoPostal criarCodigoPostal(CodigoPostal codigoPostal) throws IOException, InterruptedException {
        String json = gson.toJson(codigoPostal);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), CodigoPostal.class);
    }

    public CodigoPostal atualizarCodigoPostal(Long id, CodigoPostal codigoPostal) throws IOException, InterruptedException {
        String json = gson.toJson(codigoPostal);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), CodigoPostal.class);
    }

    public void apagarCodigoPostal(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Wrapper methods for controller compatibility
    public List<CodigoPostal> listarTodos() throws IOException, InterruptedException {
        return getAllCodigosPostais();
    }
    public CodigoPostal procurarPorId(Long id) throws IOException, InterruptedException {
        return getCodigoPostalById(id);
    }
    public CodigoPostal guardar(CodigoPostal codigoPostal) throws IOException, InterruptedException {
        return criarCodigoPostal(codigoPostal);
    }
    public CodigoPostal atualizar(Long id, CodigoPostal codigoPostal) throws IOException, InterruptedException {
        return atualizarCodigoPostal(id, codigoPostal);
    }
    public boolean deletar(Long id) throws IOException, InterruptedException {
        apagarCodigoPostal(id);
        return true;
    }
}

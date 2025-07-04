package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Viatura;
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
public class ViaturaService {

    private final String BASE_URL = "http://localhost:8080/viaturas";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<Viatura> getAllViaturas() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<Viatura>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public Viatura getViaturaById(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Viatura.class);
    }

    public Viatura criarViatura(Viatura viatura) throws IOException, InterruptedException {
        String json = gson.toJson(viatura);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Viatura.class);
    }

    public Viatura atualizarViatura(Long id, Viatura viatura) throws IOException, InterruptedException {
        String json = gson.toJson(viatura);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Viatura.class);
    }

    public void apagarViatura(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Wrapper methods for controller compatibility
    public List<Viatura> listarTodos() throws IOException, InterruptedException {
        return getAllViaturas();
    }
    public Viatura procurarPorId(Long id) throws IOException, InterruptedException {
        return getViaturaById(id);
    }
    public Viatura guardar(Viatura viatura) throws IOException, InterruptedException {
        return criarViatura(viatura);
    }
    public Viatura atualizar(Long id, Viatura viatura) throws IOException, InterruptedException {
        return atualizarViatura(id, viatura);
    }
    public boolean deletar(Long id) throws IOException, InterruptedException {
        apagarViatura(id);
        return true;
    }
}
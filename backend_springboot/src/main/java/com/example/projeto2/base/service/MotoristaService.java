package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Motorista;
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
public class MotoristaService {

    private final String BASE_URL = "http://localhost:8080/motoristas";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<Motorista> getAllMotoristas() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<Motorista>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public Motorista getMotoristaById(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Motorista.class);
    }

    public Motorista criarMotorista(Motorista motorista) throws IOException, InterruptedException {
        String json = gson.toJson(motorista);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Motorista.class);
    }

    public Motorista atualizarMotorista(Long id, Motorista motorista) throws IOException, InterruptedException {
        String json = gson.toJson(motorista);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Motorista.class);
    }

    public void apagarMotorista(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Wrapper methods for controller compatibility
    public List<Motorista> listarTodos() throws IOException, InterruptedException {
        return getAllMotoristas();
    }
    public Motorista procurarPorId(Long id) throws IOException, InterruptedException {
        return getMotoristaById(id);
    }
    public Motorista guardar(Motorista motorista) throws IOException, InterruptedException {
        return criarMotorista(motorista);
    }
    public Motorista atualizar(Long id, Motorista motorista) throws IOException, InterruptedException {
        return atualizarMotorista(id, motorista);
    }
    public boolean deletar(Long id) throws IOException, InterruptedException {
        apagarMotorista(id);
        return true;
    }
}
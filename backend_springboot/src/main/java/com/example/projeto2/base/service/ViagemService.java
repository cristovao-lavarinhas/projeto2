package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Viagem;
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
public class ViagemService {

    private final String BASE_URL = "http://localhost:8080/viagens";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<Viagem> getAllViagens() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<Viagem>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public Viagem getViagemById(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Viagem.class);
    }

    public Viagem criarViagem(Viagem viagem) throws IOException, InterruptedException {
        String json = gson.toJson(viagem);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Viagem.class);
    }

    public Viagem atualizarViagem(Long id, Viagem viagem) throws IOException, InterruptedException {
        String json = gson.toJson(viagem);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Viagem.class);
    }

    public void apagarViagem(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Wrapper methods for controller compatibility
    public List<Viagem> listarTodos() throws IOException, InterruptedException {
        return getAllViagens();
    }
    public Viagem procurarPorId(Long id) throws IOException, InterruptedException {
        return getViagemById(id);
    }
    public Viagem guardar(Viagem viagem) throws IOException, InterruptedException {
        return criarViagem(viagem);
    }
    public Viagem atualizar(Long id, Viagem viagem) throws IOException, InterruptedException {
        return atualizarViagem(id, viagem);
    }
    public boolean deletar(Long id) throws IOException, InterruptedException {
        apagarViagem(id);
        return true;
    }
}
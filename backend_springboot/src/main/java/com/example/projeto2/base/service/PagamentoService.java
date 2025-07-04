package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Pagamento;
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
public class PagamentoService {

    private final String BASE_URL = "http://localhost:8080/pagamentos";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<Pagamento> getAllPagamentos() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<Pagamento>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public Pagamento getPagamentoById(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Pagamento.class);
    }

    public Pagamento criarPagamento(Pagamento pagamento) throws IOException, InterruptedException {
        String json = gson.toJson(pagamento);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Pagamento.class);
    }

    public Pagamento atualizarPagamento(Long id, Pagamento pagamento) throws IOException, InterruptedException {
        String json = gson.toJson(pagamento);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Pagamento.class);
    }

    public void apagarPagamento(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Wrapper methods for controller compatibility
    public List<Pagamento> listarTodos() throws IOException, InterruptedException {
        return getAllPagamentos();
    }
    public Pagamento procurarPorId(Long id) throws IOException, InterruptedException {
        return getPagamentoById(id);
    }
    public Pagamento guardar(Pagamento pagamento) throws IOException, InterruptedException {
        return criarPagamento(pagamento);
    }
    public Pagamento atualizar(Long id, Pagamento pagamento) throws IOException, InterruptedException {
        return atualizarPagamento(id, pagamento);
    }
    public boolean deletar(Long id) throws IOException, InterruptedException {
        apagarPagamento(id);
        return true;
    }
}
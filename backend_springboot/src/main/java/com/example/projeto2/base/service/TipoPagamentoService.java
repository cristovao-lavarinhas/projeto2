package com.example.projeto2.base.service;

import com.example.projeto2.base.model.TipoPagamento;
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
public class TipoPagamentoService {

    private final String BASE_URL = "http://localhost:8080/tipos-pagamento";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<TipoPagamento> getAllTiposPagamento() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<TipoPagamento>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public TipoPagamento getTipoPagamentoById(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoPagamento.class);
    }

    public TipoPagamento criarTipoPagamento(TipoPagamento tipoPagamento) throws IOException, InterruptedException {
        String json = gson.toJson(tipoPagamento);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoPagamento.class);
    }

    public TipoPagamento atualizarTipoPagamento(Long id, TipoPagamento tipoPagamento) throws IOException, InterruptedException {
        String json = gson.toJson(tipoPagamento);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), TipoPagamento.class);
    }

    public void apagarTipoPagamento(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Wrapper methods for controller compatibility
    public List<TipoPagamento> listarTodos() throws IOException, InterruptedException {
        return getAllTiposPagamento();
    }
    public TipoPagamento procurarPorId(Long id) throws IOException, InterruptedException {
        return getTipoPagamentoById(id);
    }
    public TipoPagamento guardar(TipoPagamento tipoPagamento) throws IOException, InterruptedException {
        return criarTipoPagamento(tipoPagamento);
    }
    public TipoPagamento atualizar(Long id, TipoPagamento tipoPagamento) throws IOException, InterruptedException {
        return atualizarTipoPagamento(id, tipoPagamento);
    }
    public boolean deletar(Long id) throws IOException, InterruptedException {
        apagarTipoPagamento(id);
        return true;
    }
}

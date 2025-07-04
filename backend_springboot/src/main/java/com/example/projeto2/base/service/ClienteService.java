package com.example.projeto2.base.service;

import com.example.projeto2.base.model.Cliente;
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
public class ClienteService {

    private final String BASE_URL = "http://localhost:8080/clientes";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    public List<Cliente> getAllClientes() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Type listType = new TypeToken<List<Cliente>>() {}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public Cliente getClienteById(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Cliente.class);
    }

    public Cliente criarCliente(Cliente cliente) throws IOException, InterruptedException {
        String json = gson.toJson(cliente);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Cliente.class);
    }

    public Cliente atualizarCliente(Long id, Cliente cliente) throws IOException, InterruptedException {
        String json = gson.toJson(cliente);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Cliente.class);
    }

    public void apagarCliente(Long id) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/" + id))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    // Wrapper methods for controller compatibility
    public List<Cliente> listarTodos() throws IOException, InterruptedException {
        return getAllClientes();
    }
    public Cliente procurarPorId(Long id) throws IOException, InterruptedException {
        return getClienteById(id);
    }
    public Cliente guardar(Cliente cliente) throws IOException, InterruptedException {
        return criarCliente(cliente);
    }
    public Cliente atualizar(Long id, Cliente cliente) throws IOException, InterruptedException {
        return atualizarCliente(id, cliente);
    }
    public boolean deletar(Long id) throws IOException, InterruptedException {
        apagarCliente(id);
        return true;
    }
}
package com.example.javafxclient.service;

import com.example.javafxclient.model.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.hc.client5.http.classic.methods.*;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080";
    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create();
    
    private final CloseableHttpClient httpClient;

    public ApiClient() {
        this.httpClient = HttpClients.createDefault();
    }

    // Cliente operations
    public CompletableFuture<List<Cliente>> getAllClientes() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpGet request = new HttpGet(BASE_URL + "/clientes");
                String response = httpClient.execute(request, response1 -> 
                    EntityUtils.toString(response1.getEntity()));
                
                Type listType = new TypeToken<List<Cliente>>(){}.getType();
                return gson.fromJson(response, listType);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public CompletableFuture<Cliente> getClienteById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpGet request = new HttpGet(BASE_URL + "/clientes/" + id);
                String response = httpClient.execute(request, response1 -> 
                    EntityUtils.toString(response1.getEntity()));
                
                return gson.fromJson(response, Cliente.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public CompletableFuture<Cliente> createCliente(Cliente cliente) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpPost request = new HttpPost(BASE_URL + "/clientes/add");
                String json = gson.toJson(cliente);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
                
                String response = httpClient.execute(request, response1 -> 
                    EntityUtils.toString(response1.getEntity()));
                
                return gson.fromJson(response, Cliente.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public CompletableFuture<Cliente> updateCliente(Long id, Cliente cliente) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpPut request = new HttpPut(BASE_URL + "/clientes/" + id);
                String json = gson.toJson(cliente);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
                
                String response = httpClient.execute(request, response1 -> 
                    EntityUtils.toString(response1.getEntity()));
                
                return gson.fromJson(response, Cliente.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public CompletableFuture<Boolean> deleteCliente(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpDelete request = new HttpDelete(BASE_URL + "/clientes/" + id);
                return httpClient.execute(request, response -> response.getCode() == 204);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    // Motorista operations
    public CompletableFuture<List<Motorista>> getAllMotoristas() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpGet request = new HttpGet(BASE_URL + "/motoristas");
                String response = httpClient.execute(request, response1 -> 
                    EntityUtils.toString(response1.getEntity()));
                
                Type listType = new TypeToken<List<Motorista>>(){}.getType();
                return gson.fromJson(response, listType);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    // Viagem operations
    public CompletableFuture<List<Viagem>> getAllViagens() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpGet request = new HttpGet(BASE_URL + "/viagens");
                String response = httpClient.execute(request, response1 -> 
                    EntityUtils.toString(response1.getEntity()));
                
                Type listType = new TypeToken<List<Viagem>>(){}.getType();
                return gson.fromJson(response, listType);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public CompletableFuture<Viagem> createViagem(Viagem viagem) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                HttpPost request = new HttpPost(BASE_URL + "/viagens");
                String json = gson.toJson(viagem);
                request.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));
                
                String response = httpClient.execute(request, response1 -> 
                    EntityUtils.toString(response1.getEntity()));
                
                return gson.fromJson(response, Viagem.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }

    public void close() {
        try {
            httpClient.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 
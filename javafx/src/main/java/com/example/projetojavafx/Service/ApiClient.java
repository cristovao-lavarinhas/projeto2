package com.example.projetojavafx.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {
    private static final String BASE_URL = "http://localhost:8080";
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    static {
        objectMapper.registerModule(new JavaTimeModule());
    }
    
    // Generic GET request
    public static <T> CompletableFuture<List<T>> getList(String endpoint, Class<T> clazz) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Request request = new Request.Builder()
                        .url(BASE_URL + endpoint)
                        .get()
                        .build();
                
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String json = response.body().string();
                        return objectMapper.readValue(json, 
                            objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    
    // Generic GET request for single object
    public static <T> CompletableFuture<T> get(String endpoint, Class<T> clazz) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Request request = new Request.Builder()
                        .url(BASE_URL + endpoint)
                        .get()
                        .build();
                
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String json = response.body().string();
                        return objectMapper.readValue(json, clazz);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    
    // Generic POST request
    public static <T> CompletableFuture<T> post(String endpoint, T object, Class<T> clazz) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String json = objectMapper.writeValueAsString(object);
                RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
                
                Request request = new Request.Builder()
                        .url(BASE_URL + endpoint)
                        .post(body)
                        .build();
                
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseJson = response.body().string();
                        return objectMapper.readValue(responseJson, clazz);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    
    // Generic PUT request
    public static <T> CompletableFuture<T> put(String endpoint, T object, Class<T> clazz) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String json = objectMapper.writeValueAsString(object);
                RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
                
                Request request = new Request.Builder()
                        .url(BASE_URL + endpoint)
                        .put(body)
                        .build();
                
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseJson = response.body().string();
                        return objectMapper.readValue(responseJson, clazz);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    
    // Generic DELETE request
    public static CompletableFuture<Boolean> delete(String endpoint) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Request request = new Request.Builder()
                        .url(BASE_URL + endpoint)
                        .delete()
                        .build();
                
                try (Response response = client.newCall(request).execute()) {
                    return response.isSuccessful();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    // POST que devolve texto (para endpoints que devolvem apenas String)
    public static CompletableFuture<String> postText(String endpoint, Object object) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                String json = objectMapper.writeValueAsString(object);
                RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
                Request request = new Request.Builder()
                        .url(BASE_URL + endpoint)
                        .post(body)
                        .build();
                try (Response response = client.newCall(request).execute()) {
                    if (response.body() != null) {
                        return response.body().string();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
} 
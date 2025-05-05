//package com.exam.config.chatbot;
//
//
//import com.google.gson.JsonObject;
//import org.springframework.boot.configurationprocessor.json.JSONArray;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GinimiEmbeddingModel {
//    private final String apiKey;
//    private final String endpoint = "https://api.ginimi.com/v1/embedding"; // API URL của Ginimi
//
//    public GinimiEmbeddingModel(String apiKey) {
//        this.apiKey = apiKey;
//    }
//
//    public List<Float> embed(String text) {
//        try {
//            // Tạo request đến Ginimi API
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(endpoint))
//                    .header("Authorization", "Bearer " + apiKey)
//                    .header("Content-Type", "application/json")
//                    .POST(HttpRequest.BodyPublishers.ofString("{\"text\": \"" + text + "\"}"))
//                    .build();
//
//            HttpClient client = HttpClient.newHttpClient();
//            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//            // Parse JSON để lấy vector embedding
//            return parseEmbeddingResponse(response.body());
//
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to get embedding from Ginimi API", e);
//        }
//    }
//
//    private List<Float> parseEmbeddingResponse(String jsonResponse) {
//        JSONObject jsonObject = new JsonObject(jsonResponse);
//        JSONArray embeddingArray = jsonObject.getJSONArray()getJSONArray("embedding");
//        List<Float> embedding = new ArrayList<>();
//        for (int i = 0; i < embeddingArray.length(); i++) {
//            embedding.add(embeddingArray.getFloat(i));
//        }
//        return embedding;
//    }
//}
//

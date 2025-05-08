package com.kenmar.currencyconverter;

import java.net.http.*;
import java.net.URI;
import java.io.IOException;

public class ApiClient {
    public static String getRates(String baseCurrency) throws IOException, InterruptedException {
        String apiKey = "e491c86faa47117753f1ff18"; // Reemplaza con tu clave
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + baseCurrency;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}





package com.kenmar.currencyconverter;

import com.google.gson.*;

public class JsonParserExample {
    public static double obtenerTasa(String json, String monedaDestino) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");

        if (rates != null && rates.has(monedaDestino)) {
            return rates.get(monedaDestino).getAsDouble();
        } else {
            System.out.println("⚠️ No se encontró la moneda de destino.");
            return 0.0;
        }
    }
}



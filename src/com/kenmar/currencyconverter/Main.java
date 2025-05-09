package com.kenmar.currencyconverter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> historial = new ArrayList<>();

        // Menú interactivo
        while (true) {
            System.out.println("*********************************************************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
            System.out.println("1) Dólar =>> Peso argentino");
            System.out.println("2) Peso argentino =>> Dólar");
            System.out.println("3) Dólar =>> Real brasileño");
            System.out.println("4) Real brasileño =>> Dólar");
            System.out.println("5) Dólar =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dólar");
            System.out.println("7) Ver historial de conversiones");
            System.out.println("8) Salir");
            System.out.println("9) Convertir entre otras monedas");
            System.out.println("*********************************************************************************");
            System.out.print("Elija una opción válida: ");

            int opcion = scanner.nextInt();

            if (opcion < 1 || opcion > 9) {
                System.out.println("Opción no válida, por favor intente de nuevo.");
                continue;
            }

            if (opcion == 8) {
                System.out.println("¡Hasta luego!");
                break;
            }

            // Pedir al usuario el valor que desea convertir
            if (opcion != 7 && opcion != 9) {
                System.out.print("Ingrese el valor que desea convertir: ");
                double valor = scanner.nextDouble();

                String baseMoneda = "";
                String simboloMoneda = "";

                double tasaCambio = 0;

                switch (opcion) {
                    case 1:
                        baseMoneda = "USD";
                        simboloMoneda = "ARS";
                        break;
                    case 2:
                        baseMoneda = "ARS";
                        simboloMoneda = "USD";
                        break;
                    case 3:
                        baseMoneda = "USD";
                        simboloMoneda = "BRL";
                        break;
                    case 4:
                        baseMoneda = "BRL";
                        simboloMoneda = "USD";
                        break;
                    case 5:
                        baseMoneda = "USD";
                        simboloMoneda = "COP";
                        break;
                    case 6:
                        baseMoneda = "COP";
                        simboloMoneda = "USD";
                        break;
                }

                if (!baseMoneda.isEmpty() && !simboloMoneda.isEmpty()) {
                    tasaCambio = obtenerTasaDeCambio(baseMoneda, simboloMoneda);
                }

                // Realizar la conversión
                if (tasaCambio > 0) {
                    double resultado = valor * tasaCambio;
                    String resultadoMensaje = valor + " " + baseMoneda + " = " + resultado + " " + simboloMoneda;

                    // Guardar en historial
                    historial.add(generarRegistroConMarcaDeTiempo(baseMoneda, simboloMoneda, valor, resultado));

                    System.out.println("Resultado: " + resultadoMensaje);
                } else {
                    System.out.println("Error en la conversión. No se pudo obtener la tasa de cambio.");
                }
            } else if (opcion == 7) {
                // Mostrar historial
                if (historial.isEmpty()) {
                    System.out.println("No hay historial de conversiones.");
                } else {
                    System.out.println("Historial de conversiones:");
                    for (String entry : historial) {
                        System.out.println(entry);
                    }
                }
            } else if (opcion == 9) {
                // Convertir entre otras monedas
                System.out.print("Ingrese la moneda base (por ejemplo, USD, EUR, etc.): ");
                String baseMoneda = scanner.next();
                System.out.print("Ingrese la moneda objetivo (por ejemplo, ARS, COP, etc.): ");
                String simboloMoneda = scanner.next();

                System.out.print("Ingrese el valor que desea convertir: ");
                double valor = scanner.nextDouble();

                double tasaCambio = obtenerTasaDeCambio(baseMoneda, simboloMoneda);

                if (tasaCambio > 0) {
                    double resultado = valor * tasaCambio;
                    String resultadoMensaje = valor + " " + baseMoneda + " = " + resultado + " " + simboloMoneda;

                    // Guardar en historial
                    historial.add(generarRegistroConMarcaDeTiempo(baseMoneda, simboloMoneda, valor, resultado));

                    System.out.println("Resultado: " + resultadoMensaje);
                } else {
                    System.out.println("Error en la conversión. No se pudo obtener la tasa de cambio.");
                }
            }
        }

        scanner.close();
    }

    // Método para obtener la tasa de cambio real desde la API
    private static double obtenerTasaDeCambio(String base, String simbolo) {
        try {
            String url = "https://v6.exchangerate-api.com/v6/e491c86faa47117753f1ff18/latest/" + base;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
                if (jsonObject.has("conversion_rates") && jsonObject.getAsJsonObject("conversion_rates").has(simbolo)) {
                    return jsonObject.getAsJsonObject("conversion_rates").get(simbolo).getAsDouble();
                } else {
                    System.out.println("No se encontró la tasa de cambio para " + simbolo);
                    return 0;
                }
            } else {
                System.out.println("Error al obtener datos de la API");
                return 0;
            }
        } catch (Exception e) {
            System.out.println("Error de conexión con la API: " + e.getMessage());
            return 0;
        }
    }

    // Generar registro con marca de tiempo
    private static String generarRegistroConMarcaDeTiempo(String baseMoneda, String simboloMoneda, double valor, double resultado) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHora = LocalDateTime.now().format(formatter);
        return "[" + fechaHora + "] " + valor + " " + baseMoneda + " = " + resultado + " " + simboloMoneda;
    }
}


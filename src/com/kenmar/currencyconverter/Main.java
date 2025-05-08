package com.kenmar.currencyconverter;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String opcion = "";

        while (true) {
            System.out.println("*********************************************************************************");
            System.out.println("Sea bienvenido/a al Conversor de Moneda =]");
            System.out.println();
            System.out.println("1) Dólar =>> Peso argentino");
            System.out.println("2) Peso argentino =>> Dólar");
            System.out.println("3) Dólar =>> Real brasileño");
            System.out.println("4) Real brasileño =>> Dólar");
            System.out.println("5) Dólar =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dólar");
            System.out.println("7) Salir");
            System.out.println("Elija una opción válida:");
            System.out.println("*********************************************************************************");

            opcion = scanner.nextLine();

            String from = "", to = "";

            switch (opcion) {
                case "1":
                    from = "USD";
                    to = "ARS";
                    break;
                case "2":
                    from = "ARS";
                    to = "USD";
                    break;
                case "3":
                    from = "USD";
                    to = "BRL";
                    break;
                case "4":
                    from = "BRL";
                    to = "USD";
                    break;
                case "5":
                    from = "USD";
                    to = "COP";
                    break;
                case "6":
                    from = "COP";
                    to = "USD";
                    break;
                case "7":
                    System.out.println("¡Gracias por usar el conversor, Kenmar! Nos vemos la próxima.");
                    return;
                default:
                    System.out.println("Opción inválida. Intenta nuevamente.");
                    continue;
            }

            System.out.print("Ingrese el valor a convertir: ");
            double monto = scanner.nextDouble();
            scanner.nextLine(); // limpiar el buffer

            String json = ApiClient.getRates(from);
            double tasa = JsonParserExample.obtenerTasa(json, to);
            double resultado = monto * tasa;

            String jsonInverso = ApiClient.getRates(to);
            double tasaInversa = JsonParserExample.obtenerTasa(jsonInverso, from);
            double resultadoInverso = monto * tasaInversa;

            System.out.println("-------------------------------------------------------------");
            System.out.printf("💰 Resultado directo: %.2f %s = %.2f %s%n", monto, from, resultado, to);
            System.out.printf("🔁 Resultado inverso: %.2f %s = %.4f %s%n", monto, to, resultadoInverso, from);
            System.out.println("-------------------------------------------------------------\n");

        }
    }
}



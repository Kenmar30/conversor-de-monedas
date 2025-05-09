package model; // o entities

import java.time.LocalDateTime;

public class Conversion {
    private String monedaOrigen;
    private String monedaDestino;
    private double montoOrigen;
    private double montoDestino;
    private LocalDateTime fecha;

    // Constructor
    public Conversion(String monedaOrigen, String monedaDestino, double montoOrigen, double montoDestino) {
        this.monedaOrigen = monedaOrigen;
        this.monedaDestino = monedaDestino;
        this.montoOrigen = montoOrigen;
        this.montoDestino = montoDestino;
        this.fecha = LocalDateTime.now();
    }

    // Método para mostrar la conversión en formato legible
    public String toString() {
        return String.format("Conversión: %f %s a %f %s en %s", montoOrigen, monedaOrigen, montoDestino, monedaDestino, fecha);
    }

    // Getters y Setters (si los necesitas)
    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getMonedaOrigen() {
        return monedaOrigen;
    }

    public String getMonedaDestino() {
        return monedaDestino;
    }

    public double getMontoOrigen() {
        return montoOrigen;
    }

    public double getMontoDestino() {
        return montoDestino;
    }
}


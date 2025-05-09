package service; // o model

import model.Conversion; // Asegúrate de importar la clase Conversion
import java.util.ArrayList;
import java.util.List;

public class Historial {
    private List<Conversion> historial = new ArrayList<>();

    // Método para agregar una conversión al historial
    public void agregarConversion(Conversion conversion) {
        historial.add(conversion);
    }

    // Método para mostrar todas las conversiones en el historial
    public void mostrarHistorial() {
        if (historial.isEmpty()) {
            System.out.println("No hay historial de conversiones.");
        } else {
            for (Conversion c : historial) {
                System.out.println(c);
            }
        }
    }
}


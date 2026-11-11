package app;

import java.util.List;

public class ReporteFinal {

    private record Hallazgo(String antipatron, String sintoma, String solucion) {
    }

    private static final List<Hallazgo> HALLAZGOS = List.of(
            new Hallazgo("God Object", "Una clase hacia todo (Ramiro)", "Responsabilidad unica: una clase por tarea"),
            new Hallazgo("Copy-Paste Programming", "Tarifa clonada 3 veces (3 ediciones)", "Strategy con base unica (1 edicion)"),
            new Hallazgo("Magic Numbers", "tipo == 3, 0.5, rechazo silencioso", "Enum TipoVisa y constantes con nombre"),
            new Hallazgo("Acoplamiento fuerte", "Solicitud llamaba servicios a mano", "Observer: la solicitud no conoce a nadie")
    );

    private static final String FORMATO = "%-24s | %-38s | %s%n";

    public static void imprimirTabla() {
        System.out.printf(FORMATO, "ANTIPATRON", "SINTOMA", "SOLUCION");
        System.out.println("-".repeat(110));
        for (Hallazgo h : HALLAZGOS) {
            System.out.printf(FORMATO, h.antipatron(), h.sintoma(), h.solucion());
        }
    }
}
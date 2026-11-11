package app;

import legado.CalculadoraDeTarifas;
import legado.EvaluadorMagico;
import legado.OficinaDeTodo;
import legado.SolicitudAcoplada;
import solicitud.CitasObservador;
import solicitud.CorreoObservador;
import solicitud.PagosObservador;
import solicitud.SolicitudVisa;
import tarifas.ServicioDeTarifas;
import tarifas.TarifaBase;
import tramite.NotificadorCorreo;
import tramite.ProcesadorCobro;
import tramite.RepositorioExpedientes;
import tramite.TramiteVisa;
import tramite.ValidadorPasaporte;
import visas.EvaluadorDeVisas;
import visas.TipoVisa;
import visas.Veredicto;

public class Main {

    private static final String PASAPORTE = "AB123456";
    private static final String VIAJERO = "Camila";
    private static final double NUEVA_BASE = 60;
    private static final int MESES = 3;
    private static final String FILA = "%-24s | %-38s | %s%n";

    public static void main(String[] args) {
        caso1();
        caso2();
        caso3();
        caso4();
        caso5();
    }

    private static void titulo(String texto) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println(texto);
        System.out.println("=".repeat(80));
    }

    private static TramiteVisa crearTramite() {
        return new TramiteVisa(new ValidadorPasaporte(), new ProcesadorCobro(),
                new RepositorioExpedientes(), new NotificadorCorreo());
    }

    private static SolicitudVisa crearSolicitud(TipoVisa tipo, PagosObservador pagos) {
        SolicitudVisa solicitud = new SolicitudVisa(VIAJERO, tipo);
        solicitud.agregarObservador(new CorreoObservador());
        solicitud.agregarObservador(new CitasObservador());
        solicitud.agregarObservador(pagos);
        return solicitud;
    }

    private static void caso1() {
        titulo("CASO 1 - God Object");
        System.out.println("--- Heredada ---");
        new OficinaDeTodo().procesar(VIAJERO, PASAPORTE, 190.0);
        System.out.println("--- Rescatada ---");
        boolean aprobado = crearTramite().tramitar(VIAJERO, PASAPORTE, 190.0);
        System.out.println("Ambas aprueban el tramite: " + aprobado);
    }

    private static void caso2() {
        titulo("CASO 2 - Copy-Paste Programming (base 50 -> " + NUEVA_BASE + ")");

        CalculadoraDeTarifas clonada = new CalculadoraDeTarifas();
        clonada.editarBaseTurista(NUEVA_BASE);
        clonada.editarBaseEstudiante(NUEVA_BASE);
        clonada.editarBaseTrabajo(NUEVA_BASE);

        TarifaBase base = new TarifaBase();
        base.cambiarPrecioMensual(NUEVA_BASE);
        ServicioDeTarifas strategy = new ServicioDeTarifas(base);

        System.out.println("Lugares editados (clonada): " + clonada.getEdicionesRealizadas());
        System.out.println("Lugares editados (Strategy): " + base.getEdiciones());
        comparar("TURISTA", clonada.visaTurista(MESES), strategy.calcular(TipoVisa.TURISTA, MESES));
        comparar("ESTUDIANTE", clonada.visaEstudiante(MESES), strategy.calcular(TipoVisa.ESTUDIANTE, MESES));
        comparar("TRABAJO", clonada.visaTrabajo(MESES), strategy.calcular(TipoVisa.TRABAJO, MESES));
    }

    private static void comparar(String tipo, double clonada, double strategy) {
        System.out.println(tipo + ": clonada=$" + clonada + " | Strategy=$" + strategy
                + " | coinciden=" + (clonada == strategy));
    }

    private static void caso3() {
        titulo("CASO 3 - Magic Numbers");
        EvaluadorMagico magico = new EvaluadorMagico();
        EvaluadorDeVisas evaluador = new EvaluadorDeVisas();

        System.out.println("Heredada  evaluar(2, 20)          -> " + magico.evaluar(2, 20));
        System.out.println("Rescatada evaluar(ESTUDIANTE, 20) -> " + evaluador.evaluar(TipoVisa.ESTUDIANTE, 20));

        System.out.println("--- Tipo 9 ---");
        System.out.println("Heredada  evaluar(9, 20) -> " + magico.evaluar(9, 20) + " (en silencio)");
        try {
            TipoVisa.desdeCodigo(9);
        } catch (IllegalArgumentException e) {
            System.out.println("Rescatada desdeCodigo(9) -> " + e.getMessage());
        }
    }

    private static void caso4() {
        titulo("CASO 4 - Acoplamiento fuerte");
        System.out.println("--- Heredada ---");
        SolicitudAcoplada acoplada = new SolicitudAcoplada();
        acoplada.cancelar();
        System.out.println("Reembolso procesado? " + acoplada.reembolsoProcesado()
                + (acoplada.reembolsoProcesado() ? "" : "  <-- REEMBOLSO PERDIDO"));

        System.out.println("--- Con Observer ---");
        PagosObservador pagos = new PagosObservador();
        crearSolicitud(TipoVisa.ESTUDIANTE, pagos).cancelar();
        System.out.println("Reembolsos procesados: " + pagos.getReembolsosProcesados());
    }

    private static void caso5() {
        titulo("CASO 5 - Integracion: solicitar, evaluar, cobrar y cancelar");
        final int edad = 30;
        final int meses = 6;

        PagosObservador pagos = new PagosObservador();
        SolicitudVisa solicitud = crearSolicitud(TipoVisa.TRABAJO, pagos);
        System.out.println("1) SOLICITAR: visa " + solicitud.getTipo() + " para " + solicitud.getViajero()
                + " (" + solicitud.getEstado() + ")");

        Veredicto veredicto = new EvaluadorDeVisas().evaluar(solicitud.getTipo(), edad);
        System.out.println("2) EVALUAR: " + veredicto);
        if (veredicto != Veredicto.APROBADA) {
            return;
        }

        System.out.println("3) COBRAR:");
        double precio = new ServicioDeTarifas(new TarifaBase()).calcular(solicitud.getTipo(), meses);
        crearTramite().tramitar(solicitud.getViajero(), PASAPORTE, precio);

        System.out.println("4) CANCELAR:");
        solicitud.cancelar();
        System.out.println("   Estado: " + solicitud.getEstado()
                + " | reembolsos procesados: " + pagos.getReembolsosProcesados());

        System.out.println("\nTABLA FINAL: ANTIPATRONES CAPTURADOS");
        System.out.printf(FILA, "ANTIPATRON", "SINTOMA", "SOLUCION");
        System.out.printf(FILA, "God Object", "Una clase hacia todo (Ramiro)", "Una clase por responsabilidad");
        System.out.printf(FILA, "Copy-Paste Programming", "Tarifa clonada (3 ediciones)", "Strategy con base unica (1 edicion)");
        System.out.printf(FILA, "Magic Numbers", "tipo == 3, 0.5, rechazo silencioso", "Enum TipoVisa y constantes con nombre");
        System.out.printf(FILA, "Acoplamiento fuerte", "Servicios llamados a mano", "Observer: la solicitud no los conoce");
    }
}
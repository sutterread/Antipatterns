package solicitud;

import visas.TipoVisa;

import java.util.ArrayList;
import java.util.List;

public class SolicitudVisa {
    private final String viajero;
    private final TipoVisa tipo;
    private EstadoSolicitud estado = EstadoSolicitud.ACTIVA;
    private final List<ObservadorSolicitud> observadores = new ArrayList<>();

    public SolicitudVisa(String viajero, TipoVisa tipo) {
        this.viajero = viajero;
        this.tipo = tipo;
    }

    public void agregarObservador(ObservadorSolicitud observador) {
        observadores.add(observador);
    }

    public void cancelar() {
        if (estado == EstadoSolicitud.CANCELADA) {
            return;
        }
        estado = EstadoSolicitud.CANCELADA;
        for (ObservadorSolicitud observador : observadores) {
            observador.alCancelar(this);
        }
    }

    public String getViajero() { return viajero; }
    public TipoVisa getTipo() { return tipo; }
    public EstadoSolicitud getEstado() { return estado; }
}
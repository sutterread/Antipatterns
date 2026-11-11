package solicitud;

public class CitasObservador implements ObservadorSolicitud {
    @Override
    public void alCancelar(SolicitudVisa solicitud) {
        System.out.println("  [CitasObservador] Cupo liberado para " + solicitud.getViajero());
    }
}
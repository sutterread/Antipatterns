package solicitud;

public class CorreoObservador implements ObservadorSolicitud {
    @Override
    public void alCancelar(SolicitudVisa solicitud) {
        System.out.println("  [CorreoObservador] Aviso de cancelacion enviado a " + solicitud.getViajero());
    }
}
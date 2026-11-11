package solicitud;

public class PagosObservador implements ObservadorSolicitud {
    private int reembolsosProcesados = 0;

    @Override
    public void alCancelar(SolicitudVisa solicitud) {
        reembolsosProcesados++;
        System.out.println("  [PagosObservador] Reembolso procesado para " + solicitud.getViajero());
    }

    public int getReembolsosProcesados() {
        return reembolsosProcesados;
    }
}
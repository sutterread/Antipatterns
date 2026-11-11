package legado;

public class SolicitudAcoplada {
    private String estado = "ACTIVA";
    private CorreoServicio correo = new CorreoServicio();
    private CitasServicio citas = new CitasServicio();
    private PagosServicio pagos = new PagosServicio();

    public void cancelar() {
        estado = "CANCELADA";
        correo.avisarCancelacion();
        citas.liberarCupo();
        // Se olvidaron de pagos.reembolsar()
    }

    public boolean reembolsoProcesado() {
        return pagos.isReembolsado();
    }

    public String getEstado() {
        return estado;
    }
}
package tramite;

/** Solo coordina el orden del tramite; cada tarea la hace otra clase. */
public class TramiteVisa {
    private final ValidadorPasaporte validador;
    private final ProcesadorCobro cobro;
    private final RepositorioExpedientes expedientes;
    private final NotificadorCorreo correo;

    public TramiteVisa(ValidadorPasaporte validador, ProcesadorCobro cobro,
                       RepositorioExpedientes expedientes, NotificadorCorreo correo) {
        this.validador = validador;
        this.cobro = cobro;
        this.expedientes = expedientes;
        this.correo = correo;
    }

    public boolean tramitar(String nombre, String pasaporte, double precio) {
        if (!validador.esValido(pasaporte)) {
            return false;
        }
        cobro.cobrar(nombre, precio);
        expedientes.guardar(nombre);
        correo.avisarVisaEnCamino(nombre);
        System.out.println("[TramiteVisa] Tramite completado (cada quien hizo lo suyo)");
        return true;
    }
}
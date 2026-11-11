package tramite;

public class ValidadorPasaporte {
    private static final int LONGITUD_MINIMA_PASAPORTE = 6;

    public boolean esValido(String pasaporte) {
        boolean valido = pasaporte != null && pasaporte.length() >= LONGITUD_MINIMA_PASAPORTE;
        System.out.println("[ValidadorPasaporte] Pasaporte " + (valido ? "valido" : "invalido"));
        return valido;
    }
}
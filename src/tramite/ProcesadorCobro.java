package tramite;

public class ProcesadorCobro {
    public void cobrar(String nombre, double monto) {
        System.out.println("[ProcesadorCobro] Cobrando $" + monto + " a " + nombre);
    }
}
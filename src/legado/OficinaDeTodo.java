package legado;

public class OficinaDeTodo {
    public void procesar(String nombre, String pasaporte, double precio) {
        if (pasaporte == null || pasaporte.length() < 6) {
            System.out.println("[Ramiro] Pasaporte invalido");
            return;
        }
        System.out.println("[Ramiro] Cobrando $" + precio + " a " + nombre);
        System.out.println("[Ramiro] Guardando expediente de " + nombre);
        System.out.println("[Ramiro] Correo para " + nombre + ": su visa va en camino");
        System.out.println("[Ramiro] Tramite completado (el solito)");
    }
}
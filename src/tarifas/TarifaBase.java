package tarifas;

/** Unico lugar donde vive el precio base y el cargo fijo. */
public class TarifaBase {
    public static final double PRECIO_MENSUAL_INICIAL = 50;
    public static final double CARGO_FIJO = 10;

    private double precioMensual = PRECIO_MENSUAL_INICIAL;
    private int ediciones = 0;

    public double calcular(int meses) {
        return precioMensual * meses + CARGO_FIJO;
    }

    public void cambiarPrecioMensual(double nuevoPrecio) {
        this.precioMensual = nuevoPrecio;
        ediciones++;
    }

    public int getEdiciones() {
        return ediciones;
    }
}
package tarifas;

public class TarifaEstudiante implements EstrategiaTarifa {
    private static final double FACTOR_DESCUENTO_ESTUDIANTE = 0.5;

    private final TarifaBase base;

    public TarifaEstudiante(TarifaBase base) {
        this.base = base;
    }

    @Override
    public double calcular(int meses) {
        return base.calcular(meses) * FACTOR_DESCUENTO_ESTUDIANTE;
    }
}
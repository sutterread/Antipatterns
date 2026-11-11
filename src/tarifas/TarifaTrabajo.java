package tarifas;

public class TarifaTrabajo implements EstrategiaTarifa {
    private static final double FACTOR_RECARGO_TRABAJO = 1.5;

    private final TarifaBase base;

    public TarifaTrabajo(TarifaBase base) {
        this.base = base;
    }

    @Override
    public double calcular(int meses) {
        return base.calcular(meses) * FACTOR_RECARGO_TRABAJO;
    }
}
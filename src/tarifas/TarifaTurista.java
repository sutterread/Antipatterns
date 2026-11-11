package tarifas;

public class TarifaTurista implements EstrategiaTarifa {
    private final TarifaBase base;

    public TarifaTurista(TarifaBase base) {
        this.base = base;
    }

    @Override
    public double calcular(int meses) {
        return base.calcular(meses);
    }
}
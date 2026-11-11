package tarifas;

import visas.TipoVisa;

import java.util.EnumMap;
import java.util.Map;

public class ServicioDeTarifas {
    private final Map<TipoVisa, EstrategiaTarifa> estrategias = new EnumMap<>(TipoVisa.class);

    public ServicioDeTarifas(TarifaBase base) {
        estrategias.put(TipoVisa.TURISTA, new TarifaTurista(base));
        estrategias.put(TipoVisa.ESTUDIANTE, new TarifaEstudiante(base));
        estrategias.put(TipoVisa.TRABAJO, new TarifaTrabajo(base));
    }

    public double calcular(TipoVisa tipo, int meses) {
        EstrategiaTarifa estrategia = estrategias.get(tipo);
        if (estrategia == null) {
            throw new IllegalStateException("No hay tarifa registrada para la visa " + tipo);
        }
        return estrategia.calcular(meses);
    }
}
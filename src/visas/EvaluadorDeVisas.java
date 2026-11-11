package visas;

public class EvaluadorDeVisas {
    public Veredicto evaluar(TipoVisa tipo, int edad) {
        return tipo.admiteEdad(edad) ? Veredicto.APROBADA : Veredicto.RECHAZADA;
    }
}
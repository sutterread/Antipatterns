package visas;

import java.util.Arrays;

public enum TipoVisa {
    TURISTA(1, 18, Integer.MAX_VALUE),
    ESTUDIANTE(2, 16, 30),
    TRABAJO(3, 18, 65);

    private final int codigo;
    private final int edadMinima;
    private final int edadMaxima;

    TipoVisa(int codigo, int edadMinima, int edadMaxima) {
        this.codigo = codigo;
        this.edadMinima = edadMinima;
        this.edadMaxima = edadMaxima;
    }

    public boolean admiteEdad(int edad) {
        return edad >= edadMinima && edad <= edadMaxima;
    }

    public static TipoVisa desdeCodigo(int codigo) {
        for (TipoVisa tipo : values()) {
            if (tipo.codigo == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Tipo de visa invalido: " + codigo
                + ". Tipos validos: " + Arrays.toString(values()));
    }
}
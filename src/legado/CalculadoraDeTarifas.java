package legado;

public class CalculadoraDeTarifas {
    // Cada copia del codigo tiene "su propia" tarifa base (el 50 clonado 3 veces)
    private double baseTurista = 50;
    private double baseEstudiante = 50;
    private double baseTrabajo = 50;
    private int edicionesRealizadas = 0;

    public double visaTurista(int meses) {
        double total = baseTurista * meses;
        total = total + 10;
        return total;
    }

    public double visaEstudiante(int meses) {
        double total = baseEstudiante * meses;
        total = total + 10;
        return total * 0.5;
    }

    public double visaTrabajo(int meses) {
        double total = baseTrabajo * meses;
        total = total + 10;
        return total * 1.5;
    }

    // Hay que editar cada copia por separado
    public void editarBaseTurista(double nueva) {
        baseTurista = nueva;
        edicionesRealizadas++;
    }

    public void editarBaseEstudiante(double nueva) {
        baseEstudiante = nueva;
        edicionesRealizadas++;
    }

    public void editarBaseTrabajo(double nueva) {
        baseTrabajo = nueva;
        edicionesRealizadas++;
    }

    public int getEdicionesRealizadas() {
        return edicionesRealizadas;
    }
}
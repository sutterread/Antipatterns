package legado;

public class PagosServicio {
    private boolean reembolsado = false;

    public void reembolsar() {
        reembolsado = true;
        System.out.println("  [Pagos] Reembolso procesado");
    }

    public boolean isReembolsado() {
        return reembolsado;
    }
}
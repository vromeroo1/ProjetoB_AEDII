public class Celula {
    private Celula proxima;
    private Celula anterior;
    private Object elemento;

    public Celula(Object elemento) {
        this.elemento = elemento;
    }

    public Celula getProxima() {
        return this.proxima;
    }

    public void setProxima(Celula proxima) {
        this.proxima = proxima;
    }

    public Celula getAnterior() {
        return this.anterior;
    }

    public void setAnterior(Celula anterior) {
        this.anterior = anterior;
    }

    public Object getElemento() {
        return this.elemento;
    }

    public void setElemento(Object elemento) {
        this.elemento = elemento;
    }
}

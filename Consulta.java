public class Consulta {
    private int numeroPesquisado;
    private int numeroEncontrado;
    private int diferenca;
    private String urlEncontrada;

    public Consulta(int numeroPesquisado, LocalMapa localEncontrado) {
        this.numeroPesquisado = numeroPesquisado;
        this.numeroEncontrado = localEncontrado.getNumero();
        this.urlEncontrada = localEncontrado.getUrl();
        this.diferenca = Math.abs(numeroPesquisado - this.numeroEncontrado);
    }

    public int getNumeroPesquisado() {
        return this.numeroPesquisado;
    }

    public int getNumeroEncontrado() {
        return this.numeroEncontrado;
    }

    public int getDiferenca() {
        return this.diferenca;
    }

    public String getUrlEncontrada() {
        return this.urlEncontrada;
    }

    public String toString() {
        return "Pesquisado: " + this.numeroPesquisado
                + " | Encontrado: " + this.numeroEncontrado
                + " | Diferenca: " + this.diferenca
                + " | URL: " + this.urlEncontrada;
    }
}

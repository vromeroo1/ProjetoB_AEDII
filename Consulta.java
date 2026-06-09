public class Consulta {
    private String responsavel;
    private int numeroPesquisado;
    private int numeroEncontrado;
    private int diferenca;
    private String urlEncontrada;

    public Consulta(String responsavel, int numeroPesquisado, LocalMapa localEncontrado) {
        this.responsavel = responsavel;
        this.numeroPesquisado = numeroPesquisado;
        this.numeroEncontrado = localEncontrado.getNumero();
        this.urlEncontrada = localEncontrado.getUrl();
        this.diferenca = Math.abs(numeroPesquisado - this.numeroEncontrado);
    }

    public String getResponsavel() {
        return this.responsavel;
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
        return "Responsavel: " + this.responsavel
                + " | Pesquisado: " + this.numeroPesquisado
                + " | Encontrado: " + this.numeroEncontrado
                + " | Diferenca: " + this.diferenca
                + " | URL: " + this.urlEncontrada;
    }
}

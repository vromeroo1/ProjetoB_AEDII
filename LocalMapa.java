public class LocalMapa {
    private int numero;
    private String endereco;
    private String url;

    public LocalMapa(int numero, String endereco, String url) {
        this.numero = numero;
        this.endereco = endereco;
        this.url = url;
    }

    public int getNumero() {
        return this.numero;
    }

    public String getEndereco() {
        return this.endereco;
    }

    public String getUrl() {
        return this.url;
    }

    public String toString() {
        return this.endereco + " -> " + this.url;
    }
}

public class BuscadorMapa {
    public LocalMapa buscarMaisProximo(ListaDuplamenteLigada lista, int numeroPesquisado) {
        if (lista == null) {
            return null;
        }

        return lista.buscarMaisProximo(numeroPesquisado);
    }
}

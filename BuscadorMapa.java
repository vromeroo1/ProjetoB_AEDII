public class BuscadorMapa {
    public LocalMapa buscarMaisProximo(ListaDuplamenteLigada lista, int numeroPesquisado) {
        if (lista == null || lista.vazia()) {
            return null;
        }

        Celula atual = lista.getPrimeira();
        LocalMapa anterior = null;

        while (atual != null) {
            LocalMapa localAtual = (LocalMapa) atual.getElemento();

            if (localAtual.getNumero() == numeroPesquisado) {
                return localAtual;
            }

            if (localAtual.getNumero() > numeroPesquisado) {
                if (anterior == null) {
                    return localAtual;
                }

                int diferencaAnterior = Math.abs(numeroPesquisado - anterior.getNumero());
                int diferencaAtual = Math.abs(numeroPesquisado - localAtual.getNumero());

                if (diferencaAnterior <= diferencaAtual) {
                    return anterior;
                }

                return localAtual;
            }

            anterior = localAtual;
            atual = atual.getProxima();
        }

        return anterior;
    }
}

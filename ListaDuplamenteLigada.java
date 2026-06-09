public class ListaDuplamenteLigada {
    private Celula primeira;
    private Celula ultima;
    private int total = 0;

    public void adicionaNoComeco(Object elemento) {
        Celula nova = new Celula(elemento);

        if (this.total == 0) {
            this.primeira = nova;
            this.ultima = nova;
        } else {
            nova.setProxima(this.primeira);
            this.primeira.setAnterior(nova);
            this.primeira = nova;
        }

        this.total = this.total + 1;
    }

    public void adiciona(Object elemento) {
        if (this.total == 0) {
            this.adicionaNoComeco(elemento);
        } else {
            Celula nova = new Celula(elemento);
            this.ultima.setProxima(nova);
            nova.setAnterior(this.ultima);
            this.ultima = nova;
            this.total = this.total + 1;
        }
    }

    public void adiciona(int posicao, Object elemento) {
        if (posicao == 0) {
            this.adicionaNoComeco(elemento);
        } else if (posicao == this.total) {
            this.adiciona(elemento);
        } else {
            if (!this.posicaoOcupada(posicao)) {
                throw new IllegalArgumentException("Posicao nao existe");
            }

            Celula anterior = this.pegaCelula(posicao - 1);
            Celula proxima = anterior.getProxima();
            Celula nova = new Celula(elemento);

            nova.setAnterior(anterior);
            nova.setProxima(proxima);
            anterior.setProxima(nova);
            proxima.setAnterior(nova);
            this.total = this.total + 1;
        }
    }

    public void adicionaOrdenadoLocal(LocalMapa local) {
        if (this.total == 0) {
            this.adiciona(local);
            return;
        }

        LocalMapa primeiroLocal = (LocalMapa) this.primeira.getElemento();
        if (local.getNumero() <= primeiroLocal.getNumero()) {
            this.adicionaNoComeco(local);
            return;
        }

        LocalMapa ultimoLocal = (LocalMapa) this.ultima.getElemento();
        if (local.getNumero() >= ultimoLocal.getNumero()) {
            this.adiciona(local);
            return;
        }

        Celula atual = this.primeira.getProxima();
        while (atual != null) {
            LocalMapa localAtual = (LocalMapa) atual.getElemento();

            if (local.getNumero() <= localAtual.getNumero()) {
                Celula anterior = atual.getAnterior();
                Celula nova = new Celula(local);

                nova.setAnterior(anterior);
                nova.setProxima(atual);
                anterior.setProxima(nova);
                atual.setAnterior(nova);
                this.total = this.total + 1;
                return;
            }

            atual = atual.getProxima();
        }
    }

    public Object pega(int posicao) {
        return this.pegaCelula(posicao).getElemento();
    }

    public Celula pegaCelula(int posicao) {
        if (!this.posicaoOcupada(posicao)) {
            throw new IllegalArgumentException("Posicao nao existe");
        }

        Celula atual = this.primeira;

        for (int i = 0; i < posicao; i++) {
            atual = atual.getProxima();
        }

        return atual;
    }

    public boolean posicaoOcupada(int posicao) {
        return posicao >= 0 && posicao < this.total;
    }

    public int tamanho() {
        return this.total;
    }

    public boolean vazia() {
        return this.total == 0;
    }

    public LocalMapa buscarMaisProximo(int numeroPesquisado) {
        if (this.total == 0) {
            return null;
        }

        Celula atual = this.primeira;
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

    public String toString() {
        if (this.total == 0) {
            return "[]";
        }

        String texto = "";
        Celula atual = this.primeira;

        while (atual != null) {
            texto = texto + atual.getElemento();

            if (atual.getProxima() != null) {
                texto = texto + "\n";
            }

            atual = atual.getProxima();
        }

        return texto;
    }
}

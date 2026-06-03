import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeitorMapas {
    public ListaDuplamenteLigada carregar(String nomeArquivo) throws IOException {
        ListaDuplamenteLigada lista = new ListaDuplamenteLigada();
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
        String linha = leitor.readLine();

        while (linha != null) {
            LocalMapa local = this.montarLocal(linha);

            if (local != null) {
                lista.adicionaOrdenadoLocal(local);
            }

            linha = leitor.readLine();
        }

        return lista;
    }

    private LocalMapa montarLocal(String linha) {
        if (linha == null) {
            return null;
        }

        linha = linha.trim();

        if (linha.length() == 0) {
            return null;
        }

        try {
            int posicaoVirgula = linha.indexOf(",");
            int posicaoDoisPontos = linha.indexOf(":", posicaoVirgula);

            if (posicaoVirgula < 0 || posicaoDoisPontos < 0) {
                return null;
            }

            String numeroTexto = linha.substring(posicaoVirgula + 1, posicaoDoisPontos).trim();
            String endereco = linha.substring(0, posicaoDoisPontos).trim();
            String url = linha.substring(posicaoDoisPontos + 1).trim();

            if (url.length() == 0) {
                return null;
            }

            int numero = Integer.parseInt(numeroTexto);
            return new LocalMapa(numero, endereco, url);
        } catch (Exception erro) {
            return null;
        }
    }
}

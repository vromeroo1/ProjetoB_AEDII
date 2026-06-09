import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.file.Files;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class ServidorWeb {
    private ListaDuplamenteLigada listaMapas;
    private ListaDuplamenteLigada listaConsultas;
    private BuscadorMapa buscador;

    public ServidorWeb() throws IOException {
        LeitorMapas leitor = new LeitorMapas();
        this.listaMapas = leitor.carregar("Mapas.txt");
        this.listaConsultas = new ListaDuplamenteLigada();
        this.buscador = new BuscadorMapa();
    }

    public static void main(String[] args) {
        try {
            int porta = 8181;
            ServidorWeb servidorWeb = new ServidorWeb();
            servidorWeb.iniciar(porta);
        } catch (Exception erro) {
            System.out.println("Erro ao iniciar servidor web: " + erro.getMessage());
        }
    }

    public void iniciar(int porta) throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress(porta), 0);

        servidor.createContext("/buscar", new HttpHandler() {
            public void handle(HttpExchange troca) throws IOException {
                tratarBusca(troca);
            }
        });

        servidor.createContext("/enderecos", new HttpHandler() {
            public void handle(HttpExchange troca) throws IOException {
                tratarEnderecos(troca);
            }
        });

        servidor.createContext("/historico", new HttpHandler() {
            public void handle(HttpExchange troca) throws IOException {
                tratarHistorico(troca);
            }
        });

        servidor.createContext("/", new HttpHandler() {
            public void handle(HttpExchange troca) throws IOException {
                servirArquivo(troca);
            }
        });

        servidor.setExecutor(null);
        servidor.start();
        System.out.println("Servidor iniciado em http://localhost:" + porta);
    }

    private void tratarBusca(HttpExchange troca) throws IOException {
        try {
            String query = troca.getRequestURI().getQuery();
            String numeroTexto = getParametro(query, "numero");
            String responsavel = getParametro(query, "responsavel");

            if (responsavel == null || responsavel.trim().length() == 0) {
                responsavel = "Sem identificacao";
            }

            if (numeroTexto == null || numeroTexto.trim().length() == 0) {
                responderJson(troca, 400, "{\"erro\":\"Numero nao informado\"}");
                return;
            }

            int numeroPesquisado = Integer.parseInt(numeroTexto.trim());
            LocalMapa encontrado = this.buscador.buscarMaisProximo(this.listaMapas, numeroPesquisado);

            if (encontrado == null) {
                responderJson(troca, 404, "{\"erro\":\"Endereco nao encontrado\"}");
                return;
            }

            Consulta consulta = new Consulta(responsavel, numeroPesquisado, encontrado);
            this.listaConsultas.adiciona(consulta);

            String json = "{"
                    + "\"numeroPesquisado\":" + numeroPesquisado + ","
                    + "\"enderecoEncontrado\":\"" + escaparJson(encontrado.getEndereco()) + "\","
                    + "\"numeroEncontrado\":" + encontrado.getNumero() + ","
                    + "\"url\":\"" + escaparJson(encontrado.getUrl()) + "\","
                    + "\"diferenca\":" + consulta.getDiferenca() + ","
                    + "\"responsavel\":\"" + escaparJson(responsavel) + "\""
                    + "}";

            responderJson(troca, 200, json);
        } catch (NumberFormatException erro) {
            responderJson(troca, 400, "{\"erro\":\"Numero invalido\"}");
        }
    }

    private void tratarEnderecos(HttpExchange troca) throws IOException {
        String json = "[";
        Celula atual = this.listaMapas.getPrimeira();

        while (atual != null) {
            LocalMapa local = (LocalMapa) atual.getElemento();

            json = json + "{"
                    + "\"numero\":" + local.getNumero() + ","
                    + "\"endereco\":\"" + escaparJson(local.getEndereco()) + "\","
                    + "\"url\":\"" + escaparJson(local.getUrl()) + "\""
                    + "}";

            if (atual.getProxima() != null) {
                json = json + ",";
            }

            atual = atual.getProxima();
        }

        json = json + "]";
        responderJson(troca, 200, json);
    }

    private void tratarHistorico(HttpExchange troca) throws IOException {
        String json = "[";
        Celula atual = this.listaConsultas.getPrimeira();

        while (atual != null) {
            Consulta consulta = (Consulta) atual.getElemento();

            json = json + "{"
                    + "\"responsavel\":\"" + escaparJson(consulta.getResponsavel()) + "\","
                    + "\"numeroPesquisado\":" + consulta.getNumeroPesquisado() + ","
                    + "\"numeroEncontrado\":" + consulta.getNumeroEncontrado() + ","
                    + "\"diferenca\":" + consulta.getDiferenca() + ","
                    + "\"url\":\"" + escaparJson(consulta.getUrlEncontrada()) + "\""
                    + "}";

            if (atual.getProxima() != null) {
                json = json + ",";
            }

            atual = atual.getProxima();
        }

        json = json + "]";
        responderJson(troca, 200, json);
    }

    private void servirArquivo(HttpExchange troca) throws IOException {
        String caminho = troca.getRequestURI().getPath();

        if (caminho == null || caminho.equals("/")) {
            caminho = "/index.html";
        }

        String prefixoFrontend = "/frontend_web/";

        if (caminho.startsWith(prefixoFrontend)) {
            caminho = "/" + caminho.substring(prefixoFrontend.length());
        }

        File pastaFrontend = new File("frontend_web");
        File arquivo = new File(pastaFrontend, caminho.substring(1));
        String raiz = pastaFrontend.getCanonicalPath();
        String destino = arquivo.getCanonicalPath();

        if (!destino.startsWith(raiz)) {
            responderTexto(troca, 403, "Acesso negado", "text/plain");
            return;
        }

        if (!arquivo.exists() || arquivo.isDirectory()) {
            responderTexto(troca, 404, "Arquivo nao encontrado", "text/plain");
            return;
        }

        byte[] bytes = Files.readAllBytes(arquivo.toPath());
        enviarBytes(troca, 200, bytes, tipoConteudo(arquivo.getName()));
    }

    private String getParametro(String query, String nome) throws IOException {
        if (query == null || query.length() == 0) {
            return null;
        }

        String[] partes = query.split("&");

        for (int i = 0; i < partes.length; i++) {
            int posicaoIgual = partes[i].indexOf("=");

            if (posicaoIgual > 0) {
                String chave = partes[i].substring(0, posicaoIgual);
                String valor = partes[i].substring(posicaoIgual + 1);

                if (chave.equals(nome)) {
                    return URLDecoder.decode(valor, "UTF-8");
                }
            }
        }

        return null;
    }

    private void responderJson(HttpExchange troca, int codigo, String texto) throws IOException {
        responderTexto(troca, codigo, texto, "application/json; charset=UTF-8");
    }

    private void responderTexto(HttpExchange troca, int codigo, String texto, String tipo) throws IOException {
        byte[] bytes = texto.getBytes("UTF-8");
        enviarBytes(troca, codigo, bytes, tipo);
    }

    private void enviarBytes(HttpExchange troca, int codigo, byte[] bytes, String tipo) throws IOException {
        troca.getResponseHeaders().set("Content-Type", tipo);
        troca.sendResponseHeaders(codigo, bytes.length);

        OutputStream saida = troca.getResponseBody();
        saida.write(bytes);
        saida.close();
    }

    private String tipoConteudo(String nomeArquivo) {
        if (nomeArquivo.endsWith(".html")) {
            return "text/html; charset=UTF-8";
        }

        if (nomeArquivo.endsWith(".css")) {
            return "text/css; charset=UTF-8";
        }

        if (nomeArquivo.endsWith(".js")) {
            return "text/javascript; charset=UTF-8";
        }

        if (nomeArquivo.endsWith(".txt")) {
            return "text/plain; charset=UTF-8";
        }

        return "application/octet-stream";
    }

    private String escaparJson(String texto) {
        if (texto == null) {
            return "";
        }

        String saida = "";

        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i);

            if (letra == '\\') {
                saida = saida + "\\\\";
            } else if (letra == '"') {
                saida = saida + "\\\"";
            } else if (letra == '\n') {
                saida = saida + "\\n";
            } else if (letra == '\r') {
                saida = saida + "\\r";
            } else {
                saida = saida + letra;
            }
        }

        return saida;
    }
}

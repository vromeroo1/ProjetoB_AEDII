import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TelaPrincipal extends JFrame {
    private ListaDuplamenteLigada listaMapas;
    private ListaDuplamenteLigada listaConsultas;
    private JTextField campoResponsavel;
    private JTextField campoNumero;
    private JTextArea areaSaida;
    private JButton botaoAbrirMapa;
    private String ultimaUrl;

    public TelaPrincipal() {
        this.listaConsultas = new ListaDuplamenteLigada();
        this.carregarMapas();
        this.montarTela();
    }

    private void carregarMapas() {
        LeitorMapas leitor = new LeitorMapas();

        try {
            this.listaMapas = leitor.carregar("Mapas.txt");
        } catch (IOException erro) {
            this.listaMapas = new ListaDuplamenteLigada();
            JOptionPane.showMessageDialog(this,
                    "Nao foi possivel carregar o arquivo Mapas.txt.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void montarTela() {
        this.setTitle("Projeto B - Rod Presidente Dutra");
        this.setSize(820, 560);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        JPanel painelTopo = new JPanel(new GridLayout(5, 1));

        JLabel titulo = new JLabel("Projeto B - Pesquisa na Rod Presidente Dutra");
        JLabel integrantes = new JLabel("Bruno Alexandre - Victor Romero - Felipe Biscaro - Thiago Guiterrez");
        JLabel carregados = new JLabel("Enderecos carregados: " + this.listaMapas.tamanho());

        painelTopo.add(titulo);
        painelTopo.add(integrantes);
        painelTopo.add(carregados);

        JPanel painelResponsavel = new JPanel(new BorderLayout());
        painelResponsavel.add(new JLabel("Responsavel pela consulta: "), BorderLayout.WEST);
        this.campoResponsavel = new JTextField();
        painelResponsavel.add(this.campoResponsavel, BorderLayout.CENTER);
        painelTopo.add(painelResponsavel);

        JPanel painelPesquisa = new JPanel(new BorderLayout());
        painelPesquisa.add(new JLabel("Numero da Rod Presidente Dutra: "), BorderLayout.WEST);
        this.campoNumero = new JTextField();
        painelPesquisa.add(this.campoNumero, BorderLayout.CENTER);
        painelTopo.add(painelPesquisa);

        this.areaSaida = new JTextArea();
        this.areaSaida.setEditable(false);
        JScrollPane rolagem = new JScrollPane(this.areaSaida);

        JPanel painelBotoes = new JPanel();
        JButton botaoPesquisar = new JButton("Pesquisar");
        this.botaoAbrirMapa = new JButton("Abrir Mapa");
        JButton botaoListar = new JButton("Listar Enderecos");
        JButton botaoHistorico = new JButton("Historico de Consultas");

        this.botaoAbrirMapa.setEnabled(false);

        painelBotoes.add(botaoPesquisar);
        painelBotoes.add(this.botaoAbrirMapa);
        painelBotoes.add(botaoListar);
        painelBotoes.add(botaoHistorico);

        painelPrincipal.add(painelTopo, BorderLayout.NORTH);
        painelPrincipal.add(rolagem, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        this.add(painelPrincipal);

        botaoPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                pesquisar();
            }
        });

        this.botaoAbrirMapa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                abrirMapa();
            }
        });

        botaoListar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                listarEnderecos();
            }
        });

        botaoHistorico.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                mostrarHistorico();
            }
        });

        this.setVisible(true);
    }

    private void pesquisar() {
        if (this.listaMapas.vazia()) {
            this.areaSaida.setText("Nenhum endereco foi carregado.");
            return;
        }

        String textoNumero = this.campoNumero.getText().trim();

        if (textoNumero.length() == 0) {
            this.areaSaida.setText("Digite um numero para realizar a pesquisa.");
            return;
        }

        try {
            int numeroPesquisado = Integer.parseInt(textoNumero);
            BuscadorMapa buscador = new BuscadorMapa();
            LocalMapa encontrado = buscador.buscarMaisProximo(this.listaMapas, numeroPesquisado);

            if (encontrado == null) {
                this.areaSaida.setText("Nao foi encontrado nenhum endereco.");
                return;
            }

            String responsavel = this.campoResponsavel.getText().trim();
            if (responsavel.length() == 0) {
                responsavel = "Sem identificacao";
            }

            Consulta consulta = new Consulta(responsavel, numeroPesquisado, encontrado);
            this.listaConsultas.adiciona(consulta);
            this.ultimaUrl = encontrado.getUrl();
            this.botaoAbrirMapa.setEnabled(true);

            String saida = "Responsavel: " + responsavel + "\n"
                    + "Numero pesquisado: " + numeroPesquisado + "\n"
                    + "Endereco mais proximo: " + encontrado.getEndereco() + "\n"
                    + "URL: " + encontrado.getUrl() + "\n"
                    + "Diferenca: " + consulta.getDiferenca();

            this.areaSaida.setText(saida);
        } catch (NumberFormatException erro) {
            this.areaSaida.setText("Digite apenas numeros inteiros no campo de pesquisa.");
        }
    }

    private void abrirMapa() {
        if (this.ultimaUrl == null || this.ultimaUrl.length() == 0) {
            this.areaSaida.setText("Realize uma pesquisa antes de abrir o mapa.");
            return;
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(this.ultimaUrl));
            } else {
                this.areaSaida.setText("O recurso de abrir navegador nao esta disponivel.");
            }
        } catch (Exception erro) {
            this.areaSaida.setText("Nao foi possivel abrir o mapa no navegador.");
        }
    }

    private void listarEnderecos() {
        if (this.listaMapas.vazia()) {
            this.areaSaida.setText("Nenhum endereco foi carregado.");
        } else {
            this.areaSaida.setText(this.listaMapas.toString());
        }
    }

    private void mostrarHistorico() {
        if (this.listaConsultas.vazia()) {
            this.areaSaida.setText("Nenhuma consulta foi realizada.");
        } else {
            this.areaSaida.setText(this.listaConsultas.toString());
        }
    }
}

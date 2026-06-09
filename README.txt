Projeto B - Localizador de Enderecos da Rodovia Presidente Dutra
Disciplina: Algoritmos e Estrutura de Dados II

Integrantes:
Bruno Alexandre
Victor Romero
Felipe Biscaro
Thiago Guiterrez

Objetivo:
Criar uma interface grafica em Java Swing para pesquisar enderecos da Rod
Presidente Dutra. O usuario informa um numero e o programa mostra a URL do
endereco mais proximo carregado a partir do arquivo Mapas.txt.

Estrutura de dados usada:
O projeto usa uma Lista Duplamente Encadeada propria, formada pelas classes
Celula e ListaDuplamenteLigada. Cada Celula guarda um Object elemento, uma
referencia para a proxima celula e uma referencia para a celula anterior.
A classe Celula fica encapsulada dentro da ListaDuplamenteLigada. A busca do
endereco mais proximo e feita pelo metodo buscarMaisProximo() da propria lista,
e o BuscadorMapa apenas solicita essa busca.

Dados do mapa:
Cada linha de Mapas.txt possui o formato:
Rod Presidente Dutra, NUMERO:URL

O programa le cada linha, separa o numero, o endereco e a URL, cria um objeto
LocalMapa e insere esse objeto na lista de forma ordenada pelo numero do
endereco.

Cadastro de identificacao:
A tela possui o campo Responsavel pela consulta. Cada pesquisa gera um objeto
Consulta, que guarda o responsavel, o numero pesquisado, o numero encontrado,
a diferenca e a URL. As consultas ficam armazenadas em outra lista propria em
memoria RAM.

Ligacao com Google Maps:
Cada endereco do arquivo Mapas.txt possui uma URL do Google Maps. Depois que a
pesquisa encontra o endereco mais proximo, o botao Abrir Mapa abre essa URL no
navegador. Nao foi usada API externa do Google Maps.

Como o algoritmo encontra a URL mais proxima:
Como a lista esta ordenada, a propria ListaDuplamenteLigada percorre os
enderecos em ordem crescente.
Quando encontra um numero maior que o pesquisado, a busca para e compara o
endereco atual com o endereco anterior. O que tiver menor diferenca absoluta em
relacao ao numero digitado e escolhido. Em caso de empate, o menor numero e
retornado.

Complexidade:
Leitura do arquivo: O(n)
Insercao ordenada na lista: O(n)
Busca aproximada ordenada: O(n), com parada antecipada
Listagem: O(n)
Memoria usada: O(n)

Como executar:
1. Deixe Mapas.txt na mesma pasta dos arquivos .java.
2. Compile os arquivos:
   javac *.java
3. Execute o programa:
   java Main

Modo demonstracao web usando Java:
Tambem foi criada uma classe ServidorWeb.java. Ela e opcional e serve apenas
para demonstrar o projeto em uma pagina web. O projeto oficial continua sendo o
Java com Swing.

Como executar a demonstracao web:
1. Compile os arquivos:
   javac *.java
2. Execute o servidor:
   java ServidorWeb
3. Abra no navegador:
   http://localhost:8181

Nesse modo, o servidor Java carrega o Mapas.txt usando LeitorMapas, guarda os
enderecos na ListaDuplamenteLigada e usa BuscadorMapa para fazer a pesquisa. O
BuscadorMapa chama o metodo buscarMaisProximo() da lista, mantendo o percurso
das celulas dentro da propria estrutura. O front-end chama o Java pelas rotas
/buscar, /enderecos e /historico.

Front-end web complementar:
Tambem foi criada uma pasta frontend_web com uma demonstracao visual em HTML,
CSS e JavaScript puro. Essa parte web foi feita apenas para ajudar na
apresentacao do trabalho e nao substitui o projeto Java oficial.

Arquivos do front-end:
frontend_web/index.html
frontend_web/style.css
frontend_web/script.js
frontend_web/Mapas.txt
frontend_web/README_FRONTEND.txt

O front-end nao faz a busca sozinho em JavaScript. Ele chama o servidor Java
local e mostra o JSON retornado pela propria logica do projeto. A tela possui
um mapa embutido por iframe e nao usa Google Maps API com chave, banco de
dados, Node.js ou framework.

Observacoes:
O projeto nao usa ArrayList, LinkedList, HashMap, TreeMap, generics,
Collections.sort, Stream API, lambda, Maven, Gradle ou banco de dados. Os dados
sao manipulados em memoria RAM.

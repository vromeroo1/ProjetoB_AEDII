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
Como a lista esta ordenada, a busca percorre os enderecos em ordem crescente.
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

Front-end web complementar:
Tambem foi criada uma pasta frontend_web com uma demonstracao visual em HTML,
CSS e JavaScript puro. Essa versao web e apenas complementar para apresentacao
e nao substitui o projeto Java oficial.

Arquivos do front-end:
frontend_web/index.html
frontend_web/style.css
frontend_web/script.js
frontend_web/Mapas.txt
frontend_web/README_FRONTEND.txt

Como executar o front-end com Live Server:
1. Abra a pasta do projeto no VS Code.
2. Clique com o botao direito em frontend_web/index.html.
3. Escolha Open with Live Server.

Como executar o front-end com Python:
1. Abra o terminal dentro da pasta frontend_web.
2. Execute:
   python -m http.server 8000
3. Abra no navegador:
   http://localhost:8000

O front-end tenta carregar automaticamente o Mapas.txt da pasta frontend_web
usando fetch("Mapas.txt"). Se o navegador bloquear a leitura ao abrir o HTML
direto por duplo clique, a pagina usa uma copia local dos dados dentro do
script.js. A tela possui um mapa embutido por iframe e nao usa Google Maps API
com chave, banco de dados, Node.js ou framework.

Observacoes:
O projeto nao usa ArrayList, LinkedList, HashMap, TreeMap, generics,
Collections.sort, Stream API, lambda, Maven, Gradle ou banco de dados. Os dados
sao manipulados em memoria RAM.

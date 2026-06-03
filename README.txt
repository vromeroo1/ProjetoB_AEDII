Projeto B - Algoritmos e Estrutura de Dados II

Integrantes:
Bruno Alexandre
Victor Romero
Felipe Biscaro
Thiago Guiterrez

Objetivo:
Criar uma interface grafica em Java Swing para pesquisar enderecos da
Rod Presidente Dutra. O usuario informa um numero e o programa mostra a
URL do endereco mais proximo carregado a partir do arquivo Mapas.txt.

Estrutura de dados usada:
O projeto usa uma lista duplamente encadeada propria, formada pelas
classes Celula e ListaDuplamenteLigada. Cada celula guarda um Object
elemento, uma referencia para a proxima celula e uma referencia para a
celula anterior.

Dados do mapa:
Cada linha de Mapas.txt possui o formato:
Rod Presidente Dutra, NUMERO:URL

O programa le cada linha, separa o numero, o endereco e a URL, cria um
objeto LocalMapa e insere esse objeto na lista de forma ordenada pelo
numero do endereco.

Cadastro de identificacao:
A tela possui um campo para informar o responsavel pela consulta. Cada
pesquisa gera um objeto Consulta, que e guardado em outra lista propria.
O botao Historico de Consultas mostra as consultas feitas na memoria RAM.

Como o algoritmo encontra a URL mais proxima:
Como a lista esta ordenada, a busca percorre os enderecos em ordem
crescente. Quando encontra um numero maior que o pesquisado, a busca
para e compara o endereco atual com o endereco anterior. O que tiver
menor diferenca absoluta em relacao ao numero digitado e escolhido.
Em caso de empate, o menor numero e retornado.

Complexidade:
Leitura do arquivo: O(n)
Insercao ordenada na lista: O(n)
Busca aproximada ordenada: O(n), com parada antecipada
Memoria usada: O(n)

Justificativa da lista ordenada:
A lista ordenada deixa os enderecos organizados pelo numero da Rod
Presidente Dutra. Isso permite interromper a busca quando o numero atual
passa do numero pesquisado, evitando percorrer o restante da lista sem
necessidade.

Como executar:
1. Abra a pasta do projeto no VS Code, Eclipse ou terminal.
2. Compile os arquivos:
   javac *.java
3. Execute o programa:
   java Nome_program

Observacoes:
O projeto nao usa ArrayList, LinkedList, HashMap, TreeMap, generics,
Collections.sort, Stream API, lambda, Maven, Gradle ou banco de dados.
Os dados sao manipulados em memoria RAM.

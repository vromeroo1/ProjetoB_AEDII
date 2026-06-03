# Projeto B - AED II

Projeto final da disciplina **Algoritmos e Estrutura de Dados II** do curso de
Engenharia da Computacao - UNIVAP.

O projeto escolhido foi o **Projeto B**, que pede uma interface grafica para
pesquisar enderecos da **Rod Presidente Dutra** e retornar a URL do Google Maps
mais proxima do numero informado.

## Integrantes

- Bruno Alexandre
- Victor Romero
- Felipe Biscaro
- Thiago Guiterrez

## O que o programa faz

O usuario informa:

- o nome do responsavel pela consulta;
- um numero da Rod Presidente Dutra.

O programa entao procura, nos dados carregados do arquivo `Mapas.txt`, qual
endereco possui o numero mais proximo ao numero pesquisado.

Na tela sao mostrados:

- numero pesquisado;
- endereco mais proximo encontrado;
- URL correspondente do Google Maps;
- diferenca entre o numero pesquisado e o numero encontrado.

Tambem existem botoes para:

- abrir a URL no navegador;
- listar todos os enderecos carregados;
- mostrar o historico das consultas feitas.

## Estrutura de dados utilizada

O projeto usa uma **lista duplamente encadeada propria**, sem usar estruturas
prontas como `ArrayList`, `LinkedList`, `HashMap` ou `TreeMap`.

Classes principais da estrutura:

- `Celula.java`
- `ListaDuplamenteLigada.java`

Cada celula possui:

- `Object elemento`
- referencia para a proxima celula
- referencia para a celula anterior

Os enderecos sao armazenados como objetos da classe `LocalMapa`.

## Ordenacao dos dados

Durante a leitura do arquivo `Mapas.txt`, cada endereco e inserido na lista de
forma ordenada pelo numero da Rod Presidente Dutra.

Exemplo:

```text
Rod Presidente Dutra, 120
Rod Presidente Dutra, 145
Rod Presidente Dutra, 160
Rod Presidente Dutra, 161
Rod Presidente Dutra, 200
Rod Presidente Dutra, 300
```

Essa ordenacao permite que a busca pare mais cedo quando o numero atual passar
do numero pesquisado.

## Como a busca funciona

A busca percorre a lista em ordem crescente.

Quando encontra um numero maior que o numero pesquisado, o programa compara:

- o endereco anterior;
- o endereco atual.

O endereco com a menor diferenca e retornado. Em caso de empate, o menor numero
e escolhido.

Exemplo:

Se existem os numeros:

```text
145, 160, 161, 200, 300
```

e o usuario pesquisar:

```text
170
```

o programa compara `161` e `200`, retornando `161`, pois esta mais perto de
`170`.

## Complexidade

- Leitura do arquivo: `O(n)`
- Insercao ordenada na lista: `O(n)`
- Busca aproximada em lista ordenada: `O(n)`, com parada antecipada
- Memoria: `O(n)`

## Arquivos do projeto

- `Nome_program.java`: classe principal com o `main`
- `TelaPrincipal.java`: interface grafica em Swing
- `Celula.java`: celula da lista duplamente encadeada
- `ListaDuplamenteLigada.java`: estrutura propria usada no projeto
- `LocalMapa.java`: representa um endereco e sua URL
- `Consulta.java`: registra as pesquisas realizadas
- `LeitorMapas.java`: le o arquivo `Mapas.txt`
- `BuscadorMapa.java`: faz a busca do endereco mais proximo
- `Mapas.txt`: base de dados usada pelo programa
- `README.txt`: explicacao em formato simples para entrega academica

## Como rodar

Abra a pasta do projeto no terminal e compile:

```bash
javac *.java
```

Depois execute:

```bash
java Nome_program
```

## Requisitos atendidos

- Java simples, sem pacotes
- Interface grafica com Swing
- Dados carregados de `Mapas.txt`
- Manipulacao dos dados em memoria RAM
- Lista duplamente encadeada propria
- Insercao ordenada
- Busca sequencial ordenada com parada antecipada
- Cadastro de identificacao das consultas
- Historico das consultas em lista propria
- Sem `ArrayList`, generics, `Collections.sort`, Stream API, lambda, Maven,
  Gradle ou banco de dados

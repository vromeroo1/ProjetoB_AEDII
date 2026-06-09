# Projeto B - Localizador de Enderecos da Rodovia Presidente Dutra

Disciplina: Algoritmos e Estrutura de Dados II

## Integrantes

- Bruno Alexandre
- Victor Romero
- Felipe Biscaro
- Thiago Guiterrez

## Projeto Java oficial

Este trabalho foi feito para o Projeto B da disciplina. A parte principal e o
programa Java com interface grafica em Swing. O usuario digita um numero da
Rodovia Presidente Dutra e o sistema mostra o endereco cadastrado mais proximo,
junto com a URL do Google Maps.

O Java usa uma lista duplamente encadeada propria, formada pelas classes
`Celula` e `ListaDuplamenteLigada`. Cada celula guarda um `Object elemento`,
uma referencia para a proxima celula e uma referencia para a celula anterior.

## Como executar o Java

Compile:

```bash
javac *.java
```

Execute:

```bash
java Main
```

## Demonstracao web usando Java

Tambem foi adicionada uma camada opcional para demonstracao web. Ela nao troca
o projeto oficial em Swing. A ideia e apenas mostrar a mesma busca em uma tela
HTML, mas usando a logica real do Java.

Para executar:

```bash
javac *.java
java ServidorWeb
```

Depois abra no navegador:

```text
http://localhost:8181
```

Nesse modo, o Java abre um servidor local simples, sem framework. O servidor
carrega o `Mapas.txt` com `LeitorMapas`, guarda os dados na
`ListaDuplamenteLigada` e usa `BuscadorMapa` para encontrar o endereco mais
proximo. O front-end chama as rotas `/buscar`, `/enderecos` e `/historico`.

## Front-end web complementar

A pasta `frontend_web` foi criada apenas para ajudar na apresentacao. Ela usa
HTML, CSS e JavaScript puro. O JavaScript nao faz a busca sozinho: ele envia a
pesquisa para o servidor Java local, recebe o JSON de resposta e mostra o
resultado na tela.

Nao foi usada Google Maps API com chave, API externa de calculo, Node.js, banco
de dados ou framework.

## Busca pelo endereco mais proximo

Os enderecos sao carregados pelo Java a partir do `Mapas.txt`, separados pela
virgula e pelos dois pontos, ordenados pelo numero e pesquisados em ordem
crescente.

Quando o numero atual passa do numero pesquisado, o programa compara o endereco
anterior com o atual. O endereco com menor diferenca e retornado. Em caso de
empate, retorna o menor numero.

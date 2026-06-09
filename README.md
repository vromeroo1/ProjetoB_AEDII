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

## Front-end web complementar

A pasta `frontend_web` foi criada apenas para ajudar na apresentacao. Ela mostra
a mesma ideia do projeto em uma pagina HTML, com CSS e JavaScript puro. Essa
versao web nao substitui o Java oficial.

O front-end tenta carregar `frontend_web/Mapas.txt` automaticamente. Para a
pagina tambem funcionar quando for aberta direto pelo arquivo `index.html`, os
mesmos dados foram deixados como reserva dentro do `script.js`. A tela mostra um
mapa por `iframe` e atualiza esse mapa depois da pesquisa.

Nao foi usada Google Maps API com chave, API externa de calculo, Node.js, banco
de dados ou framework.

## Como executar o front-end

Abrindo direto:

1. Abra a pasta `frontend_web`.
2. Clique duas vezes em `index.html`.
3. Pesquise um numero da rodovia.

Com Python, se quiser servir localmente:

```bash
cd frontend_web
python -m http.server 8000
```

Depois acesse:

```text
http://localhost:8000
```

## Busca pelo endereco mais proximo

Os enderecos sao carregados do `Mapas.txt`, separados pela virgula e pelos dois
pontos, ordenados pelo numero e pesquisados em ordem crescente.

Quando o numero atual passa do numero pesquisado, o programa compara o endereco
anterior com o atual. O endereco com menor diferenca e retornado. Em caso de
empate, retorna o menor numero.

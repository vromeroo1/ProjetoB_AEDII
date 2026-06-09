# Projeto B - Localizador de Enderecos da Rodovia Presidente Dutra

Disciplina: Algoritmos e Estrutura de Dados II

## Integrantes

- Bruno Alexandre
- Victor Romero
- Felipe Biscaro
- Thiago Guiterrez

## Projeto Java oficial

O projeto oficial da disciplina e uma aplicacao Java com interface grafica em
Swing. O usuario informa um numero da Rodovia Presidente Dutra e o programa
mostra a URL do endereco mais proximo carregado a partir do arquivo `Mapas.txt`.

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

A pasta `frontend_web` contem uma demonstracao visual em HTML, CSS e JavaScript
puro. Essa versao e apenas complementar para apresentacao e nao substitui o
projeto Java oficial.

O front-end carrega automaticamente `frontend_web/Mapas.txt` usando
`fetch("Mapas.txt")`, mostra um mapa por `iframe` e atualiza o mapa quando a
pesquisa encontra o endereco mais proximo.

Nao foi usada Google Maps API com chave, API externa de calculo, Node.js,
banco de dados ou framework.

## Como executar o front-end

Com Live Server:

1. Abra a pasta do projeto no VS Code.
2. Clique com o botao direito em `frontend_web/index.html`.
3. Escolha `Open with Live Server`.

Com Python:

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
